package com.khanhpham.tothemoon.core.machines.energygenerator.tileentities;

import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.utils.te.energygenerator.AbstractEnergyGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

public class IronEnergyGeneratorBlockEntity extends AbstractEnergyGeneratorBlockEntity {
    public static final TranslatableComponent LABEL = new TranslatableComponent("gui.tothemoon.energy_generator.iron");

    public IronEnergyGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntityTypes.IRON_ENERGY_GENERATOR_TE, pWorldPosition, pBlockState, 200000, 150, 500, LABEL);
    }
}
