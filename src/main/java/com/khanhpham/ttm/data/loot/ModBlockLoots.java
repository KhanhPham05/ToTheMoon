package com.khanhpham.ttm.data.loot;

import com.khanhpham.ttm.init.ModBlocks;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModBlockLoots extends BlockLoot implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        dropSelf(ModBlocks.ENERGY_BANK);
        dropSelf(ModBlocks.ENERGY_GEN);
        dropSelf(ModBlocks.MOON_SURFACE_STONE_BRICKS);
        cobble(ModBlocks.MOON_SURFACE_STONE, ModBlocks.MOON_COBBLESTONE);
    }

    private void cobble(Block block, Block cobbleBlock) {
        add(block, (b) -> createSingleItemTableWithSilkTouch(b, cobbleBlock));
    }
}
