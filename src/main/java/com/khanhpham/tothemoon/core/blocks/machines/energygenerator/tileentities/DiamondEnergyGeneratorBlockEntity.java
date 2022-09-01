package com.khanhpham.tothemoon.core.blocks.machines.energygenerator.tileentities;

import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.utils.text.TextUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DiamondEnergyGeneratorBlockEntity extends AbstractEnergyGeneratorBlockEntity {
    public DiamondEnergyGeneratorBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, int capacity, int generatingRate, Component label) {
        super(pType, pWorldPosition, pBlockState, capacity, generatingRate, label);
    }

    public DiamondEnergyGeneratorBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntities.DIAMOND_ENERGY_GENERATOR_TE.get(), pos, state, 1000000, 1000, TextUtils.translatable(ModBlocks.DIAMOND_ENERGY_GENERATOR.get().getDescriptionId()));
    }
}
