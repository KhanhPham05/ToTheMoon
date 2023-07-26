package com.khanhtypo.tothemoon.data;

import com.khanhtypo.tothemoon.ToTheMoon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    public static final TagKey<Block> NEEDS_STEEL_TOOLS = mc("needs_steel_tools");
    public static final TagKey<Block> NEEDS_URANIUM_TOOLS = mc("needs_uranium_tools");

    private static TagKey<Block> mc(String name) {
        return BlockTags.create(new ResourceLocation(name));
    }

    private static TagKey<Block> of(String namespace, String path) {
        return BlockTags.create(new ResourceLocation(namespace, path));
    }

    public static void staticInit() {
        ToTheMoon.LOGGER.info("initializing ttm block tags");
    }
}
