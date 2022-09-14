package com.khanhpham.tothemoon.datagen.tags;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class AppendableItemTagKey extends AbstractAppendableTag<Item> {

    public AppendableItemTagKey(TagKey<Item> mainTag) {
        super(mainTag);
    }

    public TagKey<Item> append(String name) {
        return append(name, (ItemLike) null);
    }

    public TagKey<Item> append(String name, @Nullable ItemLike itemLike) {
        TagKey<Item> child = ItemTags.create(ModUtils.append(super.mainTag.location(), "/" + name));
        if (itemLike != null) super.map.put(child, itemLike.asItem()); else super.map.put(child, null);
        return child;
    }

    @Override
    public TagKey<Item> append(String name, Supplier<? extends Item> supplier) {
        return append(name, supplier.get());
    }


}