package com.khanhpham.tothemoon.core.blocks.machines.energygenerator.blocks;

import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.tileentities.DiamondEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.AbstractEnergyGeneratorBlock;
import com.khanhpham.tothemoon.core.blockentities.energygenerator.AbstractEnergyGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DiamondEnergyGeneratorBlock extends AbstractEnergyGeneratorBlock<DiamondEnergyGeneratorBlockEntity> {
    public DiamondEnergyGeneratorBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<DiamondEnergyGeneratorBlockEntity> supplier) {
        super(p_49224_, supplier);
    }

    @Override
    protected BlockEntityType<DiamondEnergyGeneratorBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.DIAMOND_ENERGY_GENERATOR_TE.get();
    }

    @Override
    public @Nullable DiamondEnergyGeneratorBlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DiamondEnergyGeneratorBlockEntity(pPos, pState);
    }

    @Override
    protected <A extends BlockEntity> BlockEntityTicker<A> getTicker(Level level, BlockEntityType<A> pBlockEntityType) {
        return level.isClientSide ? null : createTickerHelper(pBlockEntityType, ModBlockEntityTypes.DIAMOND_ENERGY_GENERATOR_TE.get(), AbstractEnergyGeneratorBlockEntity::serverTick);
    }

}
