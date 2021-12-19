package com.khanhpham.tothemoon.api.block;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * This TE only have Forge Energy and no inventory slot, useful for energy capacity
 */
public abstract class TTMEnergyStorageTileEntity extends TileEntity implements INamedContainerProvider {
      public TTMEnergyStorageTileEntity(TileEntityType<?> type) {
        super(type);
    }

    public abstract Block getBlock();

    protected abstract LazyOptional<IEnergyStorage> energyOptional();

    protected final List<Direction> getSidesForEnergy() {
        return Lists.newArrayList(Direction.values());
    }

    @Nonnull
    public abstract TTMEnergyStorage getEnergyData();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY || getSidesForEnergy().contains(side) || side == null) {
            return energyOptional().cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        getEnergyData().save(nbt);
        return nbt;
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT nbt) {
        super.load(p_230337_1_, nbt);
        getEnergyData().load(nbt);
    }
}
