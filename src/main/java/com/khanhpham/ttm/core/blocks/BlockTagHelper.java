package com.khanhpham.ttm.core.blocks;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public final class BlockTagHelper {
    public static Tag.Named<Block> createTag(String tag) {
        return BlockTags.bind(tag);
    }

    public static Tag.Named<Block> createForgeTag(String tag) {
        return createTag("forge:" + tag);
    }
}
