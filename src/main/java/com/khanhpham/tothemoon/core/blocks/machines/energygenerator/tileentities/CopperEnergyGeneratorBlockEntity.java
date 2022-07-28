package com.khanhpham.tothemoon.core.blocks.machines.energygenerator.tileentities;

import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.core.blockentities.energygenerator.AbstractEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;


public class CopperEnergyGeneratorBlockEntity extends AbstractEnergyGeneratorBlockEntity {
    public CopperEnergyGeneratorBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, @NotNull Component label) {
        super(pType, pWorldPosition, pBlockState, 100000, 500, label);
    }

    public CopperEnergyGeneratorBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntities.COPPER_ENERGY_GENERATOR_TE.get(), pos, state, ModBlocks.COPPER_ENERGY_GENERATOR.get().getName());
    }
}
