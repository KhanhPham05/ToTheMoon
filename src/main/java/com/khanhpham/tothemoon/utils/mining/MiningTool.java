package com.khanhpham.tothemoon.utils.mining;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public enum MiningTool {
    NEEDS_IRON_PICKAXE(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE),
    NEEDS_STONE_PICKAXE(BlockTags.NEEDS_STONE_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);

    private final Tag.Named<Block> toolTag;
    private final Tag.Named<Block> getLevelTag;

    MiningTool(Tag.Named<Block> levelTag, Tag.Named<Block> toolTag) {
        this.getLevelTag = levelTag;
        this.toolTag = toolTag;
    }

    public Tag.Named<Block> getToolTag() {
        return toolTag;
    }

    public Tag.Named<Block> getGetLevelTag() {
        return getLevelTag;
    }
}
