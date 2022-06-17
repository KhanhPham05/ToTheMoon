package com.khanhpham.tothemoon.datagen.tags;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Supplier;

public class AppendableItemTagKey extends AbstractAppendable<Item> {

    public AppendableItemTagKey(TagKey<Item> mainTag) {
        super(mainTag);
    }

    @Override
    public TagKey<Item> append(String name, Supplier<? extends Item> supplier) {
        TagKey<Item> child = ItemTags.create(ModUtils.append(super.mainTag.location(), "/" + name));
        super.map.put(child, supplier.get());
        return child;
    }

    public final LinkedList<Supplier<? extends Item>> perspectiveItems = new LinkedList<>();

    public final <T extends Item> AppendableItemTagKey putItemsToTag(Collection<RegistryObject<T>> suppliers) {
        perspectiveItems.addAll(suppliers);
        return this;
    }

    /**
     * This class stores tags for a specific crafting process
     * e.g : {@code items:forge:storage_blocks/iron -> items:forge:ingots/iron}
     */
    public static final class OneWayProcessable extends AppendableItemTagKey {

        private final HashMap<TagKey<Item>, Supplier<? extends ItemLike>> processMap = new HashMap<>();

        public OneWayProcessable(TagKey<Item> mainTag) {
            super(mainTag);
        }

        public TagKey<Item> append(String name, Supplier<? extends Item> from, Supplier<? extends ItemLike> craftTo) {
            TagKey<Item> tag = super.append(name, from);
            this.processMap.put(tag, craftTo);
            return tag;
        }

        public HashMap<TagKey<Item>, Supplier<? extends ItemLike>> getProcessMap() {
            return processMap;
        }
    }
}
