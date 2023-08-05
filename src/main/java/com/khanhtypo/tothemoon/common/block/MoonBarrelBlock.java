package com.khanhtypo.tothemoon.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class MoonBarrelBlock extends BarrelBlock {
    public MoonBarrelBlock(Properties p_49046_) {
        super(p_49046_);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_152102_, BlockState p_152103_) {
        BarrelBlockEntity barrelBlockEntity = (BarrelBlockEntity) super.newBlockEntity(p_152102_, p_152103_);
        Objects.requireNonNull(barrelBlockEntity).setCustomName(this.getName());
        return barrelBlockEntity;
    }
}
