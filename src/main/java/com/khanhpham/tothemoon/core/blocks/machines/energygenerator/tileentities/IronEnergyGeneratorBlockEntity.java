package com.khanhpham.tothemoon.core.blocks.machines.energygenerator.tileentities;

import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.core.blockentities.energygenerator.AbstractEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

public class IronEnergyGeneratorBlockEntity extends AbstractEnergyGeneratorBlockEntity {
    public IronEnergyGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntityTypes.IRON_ENERGY_GENERATOR_TE.get(), pWorldPosition, pBlockState, 200000, 150, 500, ModBlocks.IRON_ENERGY_GENERATOR.get().getName());
    }
}
