package com.khanhpham.tothemoon.core.energygenerator.tileentities;

import com.khanhpham.tothemoon.init.ModTileEntityTypes;
import com.khanhpham.tothemoon.utils.te.energygenerator.AbstractEnergyGeneratorTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

public class IronEnergyGeneratorTileEntity extends AbstractEnergyGeneratorTileEntity {
    public static final TranslatableComponent LABEL = new TranslatableComponent("gui.tothemoon.energy_generator.iron");

    public IronEnergyGeneratorTileEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntityTypes.IRON_ENERGY_GENERATOR_TE, pWorldPosition, pBlockState, 200000, 150, 500, LABEL);
    }
}
