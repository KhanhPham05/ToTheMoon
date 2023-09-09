package com.khanhtypo.tothemoon.common.tank;

import com.khanhtypo.tothemoon.api.helpers.NbtHelper;
import com.khanhtypo.tothemoon.common.block.TickableEntityBlock;
import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FluidTankBlock extends TickableEntityBlock<FluidTankBlockEntity> {
    public FluidTankBlock(Properties pProperties) {
        super(pProperties, ModBlockEntities.FLUID_TANK);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FluidTankBlockEntity(pPos, pState);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        pLevel.getBlockEntity(pPos, super.blockEntityObject.get()).ifPresent(blockEntity -> {
            CompoundTag itemTag = pStack.getTag();
            if (itemTag != null && itemTag.contains("MachineData", CompoundTag.TAG_COMPOUND)) {
                CompoundTag machineData = itemTag.getCompound("MachineData");
                NbtHelper.loadFluidTankFrom(machineData, blockEntity.getFluidTank());
            }
        });
    }
}
