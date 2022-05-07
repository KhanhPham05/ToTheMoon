package com.khanhpham.tothemoon.datagen.tags;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.HashMap;
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

    public static final class Processable extends AppendableItemTagKey {

        public Processable(TagKey<Item> mainTag) {
            super(mainTag);
        }

        private final HashMap<TagKey<Item>, Supplier<? extends ItemLike>> processMap = new HashMap<>();

        public TagKey<Item> append(String name,Supplier<? extends Item> from, Supplier<? extends ItemLike> craftTo) {
            TagKey<Item> tag = super.append(name, from);
            this.processMap.put(tag, craftTo);
            return tag;
        }

        public HashMap<TagKey<Item>, Supplier<? extends ItemLike>> getProcessMap() {
            return processMap;
        }
    }
}
