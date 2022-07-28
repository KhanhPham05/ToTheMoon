package com.khanhpham.tothemoon.core.blocks.machines.energygenerator.tileentities;

import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.core.blockentities.energygenerator.AbstractEnergyGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

public class GoldEnergyGeneratorBlockEntity extends AbstractEnergyGeneratorBlockEntity {
    public GoldEnergyGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GOLD_ENERGY_GENERATOR_TE.get(), pos, state, 500000, 550, new TranslatableComponent(ModBlocks.GOLD_ENERGY_GENERATOR.get().getDescriptionId()));
    }
}
