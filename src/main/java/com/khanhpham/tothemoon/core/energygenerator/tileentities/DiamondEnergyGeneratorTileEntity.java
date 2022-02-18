package com.khanhpham.tothemoon.core.energygenerator.tileentities;

import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModTileEntityTypes;
import com.khanhpham.tothemoon.utils.te.energygenerator.AbstractEnergyGeneratorTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DiamondEnergyGeneratorTileEntity extends AbstractEnergyGeneratorTileEntity {
    public DiamondEnergyGeneratorTileEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, int i, int i1, int i2, Component label) {
        super(pType, pWorldPosition, pBlockState, i, i1, i2, label);
    }

    public DiamondEnergyGeneratorTileEntity(BlockPos pos, BlockState state) {
        this(ModTileEntityTypes.DIAMOND_ENERGY_GENERATOR_TE, pos, state, 300000, 500, 1500, new TranslatableComponent(ModBlocks.DIAMOND_ENERGY_GENERATOR.getDescriptionId()));
    }
}
