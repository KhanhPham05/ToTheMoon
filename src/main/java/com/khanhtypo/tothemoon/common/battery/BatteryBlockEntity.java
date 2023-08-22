package com.khanhtypo.tothemoon.common.battery;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.AbstractPowerBlockEntity;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.TickableBlockEntity;
import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BatteryBlockEntity extends BlockEntity implements TickableBlockEntity {
    private final EnergyStorage energyStorage;
    private LazyOptional<IEnergyStorage> handler;

    public BatteryBlockEntity(BlockEntityObject<BatteryBlockEntity> blockEntityType, BlockPos pPos, BlockState pBlockState) {
        super(blockEntityType.get(), pPos, pBlockState);
        this.energyStorage = new EnergyStorage(this.getEnergyCapacity());
        this.handler = LazyOptional.of(() -> this.energyStorage);
    }

    public static BlockEntityType.BlockEntitySupplier<BatteryBlockEntity> createSupplier() {
        return (pPos, pState) -> new BatteryBlockEntity(ModBlockEntities.BATTERY, pPos, pState) {
            @Override
            protected int getEnergyCapacity() {
                return ((BatteryBlock) this.getBlockState().getBlock()).getEnergyCapacity();
            }
        };
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.handler = LazyOptional.of(() -> this.energyStorage);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.handler.invalidate();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!remove && cap == ForgeCapabilities.ENERGY) {
            return this.handler.cast();
        }

        return super.getCapability(cap, side);
    }

    protected abstract int getEnergyCapacity();

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState blockState) {
        AbstractPowerBlockEntity.tryExtractEnergyToNeighbour(this.energyStorage, level, pos);

        float percent = ((float) this.energyStorage.getEnergyStored() / this.energyStorage.getMaxEnergyStored()) * 100;
        ModUtils.changeBlockState(level, pos, blockState, BatteryBlock.ENERGY_LEVEL, ((int) percent) / 10, true);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("Energy", this.energyStorage.serializeNBT());
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.loadEnergy(pTag);
    }

    public void loadEnergy(CompoundTag tag) {
        this.energyStorage.deserializeNBT(tag.get("Energy"));
    }
}
