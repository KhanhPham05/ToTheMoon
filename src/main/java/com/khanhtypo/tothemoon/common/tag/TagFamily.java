package com.khanhtypo.tothemoon.common.tag;

import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.*;
import java.util.function.Function;

public class TagFamily<T> {
    private static final Map<ResourceKey<? extends Registry<?>>, List<TagFamily<?>>> ALL_FAMILIES = Util.make(new HashMap<>(), map -> {
        map.put(Registries.ITEM, new LinkedList<>());
        map.put(Registries.BLOCK, new LinkedList<>());
        map.put(Registries.FLUID, new LinkedList<>());
    });
    private final TagKey<T> rootTag;
    private final ResourceLocation rootLocation;
    private final ResourceKey<? extends Registry<T>> registry;
    private final Map<TagKey<T>, ObjectSupplier<? extends T>> childMap = new LinkedHashMap<>();

    protected TagFamily(TagKey<T> rootTag) {
        this.rootTag = rootTag;
        this.rootLocation = rootTag.location();
        this.registry = rootTag.registry();
        ALL_FAMILIES.get(this.registry).add(this);
    }

    @SuppressWarnings("unchecked")
    public static <T> void generateToJson(ResourceKey<? extends Registry<T>> registryKey, Function<TagKey<T>, IntrinsicHolderTagsProvider.IntrinsicTagAppender<T>> factory) {
        List<TagFamily<?>> families = ALL_FAMILIES.get(registryKey);
        for (TagFamily<?> family : families) {
            ToTheMoon.LOGGER.info("Generating tags for [{} | {}]", family.rootLocation, registryKey.location());
            factory.apply((TagKey<T>) family.rootTag).addTags(family.allChildTags().toArray(TagKey[]::new));
            family.childMap.forEach((tag, supplier) -> factory.apply((TagKey<T>) tag).add((T) supplier.get()));
        }
    }

    public static TagFamily<Item> forItem(String namespace, String path) {
        return forItem(ItemTags.create(new ResourceLocation(namespace, path)));
    }

    public static TagFamily<Item> forItem(TagKey<Item> root) {
        return new TagFamily<>(root);
    }

    public static TagFamily<Block> forBlock(String nameSpace, String path) {
        return forBlock(BlockTags.create(new ResourceLocation(nameSpace, path)));
    }

    public static TagFamily<Block> forBlock(String name) {
        return forBlock(ToTheMoon.MODID, name);
    }

    public static TagFamily<Block> forBlock(TagKey<Block> root) {
        return new TagFamily<>(root);
    }

    public static BlockItemTagFamily forBlockItem(String nameSpace, String path) {
        ResourceLocation id = new ResourceLocation(nameSpace, path);
        final TagKey<Item> itemTag = ItemTags.create(id);
        final TagKey<Block> blockTag = BlockTags.create(id);
        return new BlockItemTagFamily(blockTag, itemTag);
    }

    public static BlockItemTagFamily forBlockItem(TagKey<Block> blockRoot, TagKey<Item> itemRoot) {
        return new BlockItemTagFamily(blockRoot, itemRoot);
    }

    private Set<TagKey<T>> allChildTags() {
        return this.childMap.keySet();
    }

    public <U extends T> TagKey<T> createChild(String childName, ObjectSupplier<U> supplier) {
        final ResourceLocation childLocation = this.rootLocation.withSuffix("/" + childName);
        TagKey<T> tag = new TagKey<>(this.registry, childLocation);
        childMap.put(tag, supplier);
        return tag;
    }

    public TagKey<T> getRootTag() {
        return rootTag;
    }
}
