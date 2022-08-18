package com.khanhpham.tothemoon.core.blocks.machines.energygenerator.blocks;

import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.tileentities.CopperEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.AbstractEnergyGeneratorBlock;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.tileentities.AbstractEnergyGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CopperEnergyGeneratorBlock extends AbstractEnergyGeneratorBlock<CopperEnergyGeneratorBlockEntity> {
    public CopperEnergyGeneratorBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<CopperEnergyGeneratorBlockEntity> supplier) {
        super(p_49224_, supplier);

    }

    @Override
    protected BlockEntityType<CopperEnergyGeneratorBlockEntity> getBlockEntityType() {
        return ModBlockEntities.COPPER_ENERGY_GENERATOR_TE.get();
    }

    @Override
    public @Nullable CopperEnergyGeneratorBlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CopperEnergyGeneratorBlockEntity(pPos, pState);
    }

    @Override
    protected <A extends BlockEntity> BlockEntityTicker<A> getTicker(Level level, BlockEntityType<A> pBlockEntityType) {
        return level.isClientSide ? null : createTickerHelper(pBlockEntityType, ModBlockEntities.COPPER_ENERGY_GENERATOR_TE.get(), AbstractEnergyGeneratorBlockEntity::serverTick);
    }
}
