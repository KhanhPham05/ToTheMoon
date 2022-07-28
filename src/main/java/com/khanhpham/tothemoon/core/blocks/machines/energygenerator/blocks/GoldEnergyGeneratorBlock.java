package com.khanhpham.tothemoon.core.blocks.machines.energygenerator.blocks;

import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.tileentities.GoldEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.AbstractEnergyGeneratorBlock;
import com.khanhpham.tothemoon.core.blockentities.energygenerator.AbstractEnergyGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GoldEnergyGeneratorBlock extends AbstractEnergyGeneratorBlock<GoldEnergyGeneratorBlockEntity> {
    public GoldEnergyGeneratorBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<GoldEnergyGeneratorBlockEntity> supplier) {
        super(p_49224_, supplier);
    }

    @Override
    protected BlockEntityType<GoldEnergyGeneratorBlockEntity> getBlockEntityType() {
        return ModBlockEntities.GOLD_ENERGY_GENERATOR_TE.get();
    }

    @Override
    public @Nullable GoldEnergyGeneratorBlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GoldEnergyGeneratorBlockEntity(pPos, pState);
    }

    @Override
    protected <A extends BlockEntity> BlockEntityTicker<A> getTicker(Level level, BlockEntityType<A> pBlockEntityType) {
        return level.isClientSide ? null : createTickerHelper(pBlockEntityType, ModBlockEntities.GOLD_ENERGY_GENERATOR_TE.get(), AbstractEnergyGeneratorBlockEntity::serverTick);
    }
}
