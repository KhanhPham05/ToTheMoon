package com.khanhtypo.tothemoon.data;

import com.khanhtypo.tothemoon.common.tag.BlockItemTag;
import com.khanhtypo.tothemoon.common.tag.BlockItemTagFamily;
import com.khanhtypo.tothemoon.common.tag.TagFamily;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

import static com.khanhtypo.tothemoon.registration.ModBlocks.*;

@SuppressWarnings("unused")
public class ModBlockItemTags {
    public static final BlockItemTagFamily STORAGE_BLOCKS = TagFamily.forBlockItem(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
    public static final BlockItemTag STORAGE_BLOCK_STEEL = BlockItemTag.of(STORAGE_BLOCKS, "steel", STEEL_BLOCK);
    public static final BlockItemTag STORAGE_BLOCK_ZIRCONIUM = BlockItemTag.of(STORAGE_BLOCKS, "zirconium", ZIRCONIUM_BLOCK);
    public static final BlockItemTag STORAGE_BLOCK_PURE_ZIRCONIUM = BlockItemTag.of(STORAGE_BLOCKS, "pure_zirconium", PURE_ZIRCONIUM_BLOCK);
    public static final BlockItemTag STORAGE_BLOCK_RAW_URANIUM = BlockItemTag.of(STORAGE_BLOCKS, "raw_uranium", RAW_URANIUM_BLOCK);
    public static final BlockItemTag STORAGE_BLOCK_RAW_ZIRCONIUM = BlockItemTag.of(STORAGE_BLOCKS, "raw_zirconium", RAW_ZIRCONIUM_BLOCK);
    public static final BlockItemTag STORAGE_BLOCK_REDSTONE_STEEL = BlockItemTag.of(STORAGE_BLOCKS, "redstone_steel", REDSTONE_STEEL_ALLOY_BLOCK);
    public static final BlockItemTag STORAGE_BLOCK_REDSTONE_METAL = BlockItemTag.of(STORAGE_BLOCKS, "redstone_metal", REDSTONE_METAL_BLOCK);

    public static final BlockItemTagFamily SHEETMETALS = TagFamily.forBlockItem("forge", "sheetmetals");
    public static final BlockItemTag SHEETMETAL_COPPER = BlockItemTag.of(SHEETMETALS, "copper", COPPER_SHEET_BLOCK);
    public static final BlockItemTag SHEETMETAL_GOLD = BlockItemTag.of(SHEETMETALS, "gold", GOLD_SHEET_BLOCK);
    public static final BlockItemTag SHEETMETAL_IRON = BlockItemTag.of(SHEETMETALS, "iron", IRON_SHEET_BLOCK);
    public static final BlockItemTag SHEETMETAL_REDSTONE_STEEL = BlockItemTag.of(SHEETMETALS, "redstone_steel", REDSTONE_STEEL_ALLOY_SHEET_BLOCK);
    public static final BlockItemTag SHEETMETAL_STEEL = BlockItemTag.of(SHEETMETALS, "redstone_metal", STEEL_SHEET_BLOCK);

    public static final BlockItemTagFamily PLANKS = TagFamily.forBlockItem(BlockTags.PLANKS, ItemTags.PLANKS);
    public static final BlockItemTag PLANK_PROCESSED = BlockItemTag.of(PLANKS, "processed", PROCESSED_WOOD_PLANKS);

    public static void staticInit() {}
}
