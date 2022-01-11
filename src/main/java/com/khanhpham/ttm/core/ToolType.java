package com.khanhpham.ttm.core;

import com.khanhpham.ttm.core.blocks.BlockTagHelper;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public enum ToolType {
    PICKAXE(BlockTagHelper.createTag("mineable/pickaxe"));

    public final Tag.Named<Block> tag;

    ToolType(Tag.Named<Block> tag) {
        this.tag = tag;
    }

    public Tag.Named<Block> getTag() {
        return tag;
    }
}
