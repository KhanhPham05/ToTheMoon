package com.khanhpham.tothemoon.utils.mining;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

@Deprecated(forRemoval = true, since = "1.0-alpha1")
public enum MiningTool {
    NEEDS_IRON_PICKAXE(BlockTags.NEEDS_IRON_TOOL, BlockTags.MINEABLE_WITH_PICKAXE),
    NEEDS_STONE_PICKAXE(BlockTags.NEEDS_STONE_TOOL, BlockTags.MINEABLE_WITH_PICKAXE);

    private final TagKey<Block> toolTag;
    private final TagKey<Block> getLevelTag;

    MiningTool(TagKey<Block> levelTag, TagKey<Block> toolTag) {
        this.getLevelTag = levelTag;
        this.toolTag = toolTag;
    }

    public TagKey<Block> getToolTag() {
        return toolTag;
    }

    public TagKey<Block> getGetLevelTag() {
        return getLevelTag;
    }
}
