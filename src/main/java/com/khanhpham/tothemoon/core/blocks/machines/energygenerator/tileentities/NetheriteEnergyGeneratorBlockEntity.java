package com.khanhpham.tothemoon.core.blocks.machines.energygenerator.tileentities;

import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class NetheriteEnergyGeneratorBlockEntity extends AbstractEnergyGeneratorBlockEntity {
    public NetheriteEnergyGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.NETHERITE_GENERATOR.get(), pWorldPosition, pBlockState, 1_750_000, 1750, ModBlocks.NETHERITE_ENERGY_GENERATOR.get().getName());
    }
}
