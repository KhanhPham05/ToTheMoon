package com.khanhpham.tothemoon.utils;

import com.khanhpham.tothemoon.Names;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final Tag.Named<Item> TAG_PRESS_MOLDS = create("metal_press_molds");
    public static final Tag.Named<Item> TAG_PLATE_PRESS_MOLD = create("metal_press_molds/plate");

    private static Tag.Named<Item> create(final String name) {
        return ItemTags.bind(Names.MOD_ID + ':' + name);
    }

}
