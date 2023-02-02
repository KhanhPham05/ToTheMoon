package com.khanhpham.tothemoon.datagen.tags;

import com.google.common.base.Preconditions;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class AppendableItemTagKey extends AbstractAppendableTag<Item> {

    public AppendableItemTagKey(TagKey<Item> mainTag) {
        super(mainTag);
    }

    public TagKey<Item> append(String name) {
        return super.createTag(ForgeRegistries.ITEMS.getRegistryKey(), name);
    }

    public TagKey<Item> append(String name, ItemLike... items) {
        Preconditions.checkNotNull(items);
        TagKey<Item> child = super.createTag(ForgeRegistries.ITEMS.getRegistryKey(), name);
        if (items.length > 0) for (ItemLike itemLike : items) {
            super.map.put(child, itemLike.asItem());
        }
        return child;
    }

    @SafeVarargs
    @Override
    public final TagKey<Item> append(String name, Supplier<? extends Item>... suppliers) {
        return suppliers.length > 0 ? append(name, Stream.of(suppliers).map(Supplier::get).toArray(Item[]::new)) : super.createTag(ForgeRegistries.ITEMS.getRegistryKey(), name);
    }
}