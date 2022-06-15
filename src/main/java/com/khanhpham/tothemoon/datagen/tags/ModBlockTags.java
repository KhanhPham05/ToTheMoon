package com.khanhpham.tothemoon.datagen.tags;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags.Blocks;

import java.util.function.Supplier;
public class ModBlockTags {
    public static final TagKey<Block> ORES_URANIUM = append(Blocks.ORES, "/uranium");
    public static final AppendableBlockTagKey BLOCK_SHEETMETALS = createAppendable(new ResourceLocation("forge", "sheetmetals"));

    public static final TagKey<Block> BLOCK_SHEETMETAL_COPPER = append(BLOCK_SHEETMETALS, "copper", ModBlocks.COPPER_SHEET_BLOCK);
    public static final TagKey<Block> BLOCK_SHEETMETAL_GOLD = append(BLOCK_SHEETMETALS, "gold", ModBlocks.GOLD_SHEET_BLOCK);
    public static final TagKey<Block> BLOCK_SHEETMETAL_IRON = append(BLOCK_SHEETMETALS, "iron", ModBlocks.IRON_SHEET_BLOCK);
    public static final TagKey<Block> BLOCK_SHEETMETAL_STEEL = append(BLOCK_SHEETMETALS, "steel", ModBlocks.STEEL_SHEET_BLOCK);

    public static final AppendableBlockTagKey BLOCK_STORAGE_BLOCKS = createAppendable(Blocks.STORAGE_BLOCKS.location());
    public static final TagKey<Block> BLOCK_URANIUM_STORAGE = append(BLOCK_STORAGE_BLOCKS, "uranium", ModBlocks.URANIUM_BLOCK);
    public static final TagKey<Block> BLOCK_STEEL_STORAGE = append(BLOCK_STORAGE_BLOCKS, "steel", ModBlocks.STEEL_BLOCK);
    public static final TagKey<Block> BLOCK_REDSTONE_METAL_STORAGE = append(BLOCK_STORAGE_BLOCKS, "redstone_metal", ModBlocks.REDSTONE_METAL_BLOCK);
    public static final TagKey<Block> BLOCK_REDSTONE_STEEL_STORAGE = append(BLOCK_STORAGE_BLOCKS, "redstone_steel_alloy", ModBlocks.REDSTONE_STEEL_ALLOY_BLOCK);
    public static final TagKey<Block> BLOCK_RAW_URANIUM_STORAGE = append(BLOCK_STORAGE_BLOCKS, "raw_uranium", ModBlocks.RAW_URANIUM_BLOCK);



    private static AppendableBlockTagKey createAppendable(ResourceLocation id) {
        return new AppendableBlockTagKey(id);
    }

    private static TagKey<Block> append(TagKey<Block> tag, String suf) {
        return BlockTags.create(ModUtils.append(tag.location(), suf));
    }

    private static TagKey<Block> append(AppendableBlockTagKey key, String suf, Supplier<? extends Block> block) {
        return key.append(suf, block);
    }

}
