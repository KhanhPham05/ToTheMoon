package com.khanhpham.ttm.core.blocks;

import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public enum MiningLevel {
    STONE_PICKAXE(BlockTagHelper.createTag("needs_iron_tool")),
    WOODEN_PICKAXE(BlockTagHelper.createForgeTag("needs_wood_tool"));

    public final Tag.Named<Block> tag;

    MiningLevel(Tag.Named<Block> tag) {
        this.tag = tag;
    }

    public Tag.Named<Block> getTag() {
        return tag;
    }
}
