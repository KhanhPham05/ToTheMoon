package com.khanhpham.ttm.core.blockentities.core;

import com.google.common.collect.Lists;
import com.khanhpham.ttm.core.energy.EnergyData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public abstract class BaseBlockEntity extends BlockEntity {
    public BaseBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    protected List<Direction> getDirections() {
        return Lists.newArrayList(Direction.values());
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        loadCustom(pTag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.save(tag);
        saveCustom(tag);
    }

    protected static void markDirty(EnergyData energyData, Level level, BlockPos pos, BlockState state) {
        energyData.setChanged(level,pos,state);
        setChanged(level, pos, state);
        //System.out.println("Changes Has Been Saved");
    }

    protected CompoundTag saveAllItems(CompoundTag tag, NonNullList<ItemStack> storedItems) {
        ContainerHelper.saveAllItems(tag, storedItems);
        return tag;
    }

    protected void loadAllItems(CompoundTag tag, NonNullList<ItemStack> items) {
        ContainerHelper.loadAllItems(tag, items);
    }

    protected LazyOptional<IEnergyStorage> energyOptional = LazyOptional.of(this::getEnergyData);

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY || getDirections().contains(side) || side == null) {
            return energyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @NotNull
    protected abstract EnergyData getEnergyData();


    protected CompoundTag saveCustom(CompoundTag nbt) {
        //nbt.putInt(burningTimeNBT, burningTime);
        getEnergyData().save(nbt);
        return nbt;
    }

    protected void loadCustom(CompoundTag nbt) {
        //burningTime = nbt.getInt(burningTimeNBT);
        getEnergyData().load(nbt);
    }
}
