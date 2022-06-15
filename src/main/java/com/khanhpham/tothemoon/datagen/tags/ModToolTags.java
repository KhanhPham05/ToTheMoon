package com.khanhpham.tothemoon.datagen.tags;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModToolTags {
    public static final TagKey<Block> NEEDS_STEEL_TOOLS =  create("needs_steel_tools");
    public static final TagKey<Block> NEEDS_URANIUM_TOOLS = create("needs_uranium_tools");
    private static TagKey<Block> create(String tag) {
        return BlockTags.create(ModUtils.modLoc(tag));
    }

    public static void initTags(){}
}
