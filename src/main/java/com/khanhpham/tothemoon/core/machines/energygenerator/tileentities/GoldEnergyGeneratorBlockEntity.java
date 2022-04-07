package com.khanhpham.tothemoon.core.machines.energygenerator.tileentities;

import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.utils.te.energygenerator.AbstractEnergyGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

public class GoldEnergyGeneratorBlockEntity extends AbstractEnergyGeneratorBlockEntity {
    public GoldEnergyGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.GOLD_ENERGY_GENERATOR_TE.get(), pos, state, 250000, 100, 500, new TranslatableComponent(ModBlocks.GOLD_ENERGY_GENERATOR.get().getDescriptionId()));
    }
}
