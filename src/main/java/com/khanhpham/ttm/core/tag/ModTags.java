package com.khanhpham.ttm.core.tag;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public final class ModTags {
    public static final Tag.Named<Block> REQ_PICKAXE = block("mineable/pickaxe");
    public static final Tag.Named<Block> REG_TIER_STONE = block("needs_stone_tool");
    public static final Tag.Named<Block> ENERGY_SOURCE = block("tothemoon:energy_source");

    private static Tag.Named<Block> block(String name) {
        return BlockTags.bind(name);
    }

    private static Tag.Named<Item> item(String name) {
        return ItemTags.bind(name);
    }
}
