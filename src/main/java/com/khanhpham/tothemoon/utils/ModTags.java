package com.khanhpham.tothemoon.utils;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<Item> TAG_PRESS_MOLDS = create("metal_press_molds");
    public static final TagKey<Item> TAG_PLATE_PRESS_MOLD = create("metal_press_molds/plate");

    private static TagKey<Item> create(final String name) {
        return ItemTags.create(ModUtils.modLoc(name));
    }

}
