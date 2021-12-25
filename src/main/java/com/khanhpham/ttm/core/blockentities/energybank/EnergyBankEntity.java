package com.khanhpham.ttm.core.blockentities.energybank;

import com.google.common.collect.Lists;
import com.khanhpham.ttm.core.energy.TTMEnergyData;
import com.khanhpham.ttm.init.ModMisc;
import com.khanhpham.ttm.testfeatures.TickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @see net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
 */
public class EnergyBankEntity extends TickableBlockEntity {
    public EnergyBankEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModMisc.ENERGY_BANK_ENTITY, pWorldPosition, pBlockState);
    }

    private final TTMEnergyData energyData = new TTMEnergyData(100000, 5000) {
        @Override
        public void setChanged() {
            EnergyBankEntity.this.setChanged();
        }
    };
    private final LazyOptional<IEnergyStorage> energyLazyOptional = LazyOptional.of(() -> energyData);

    protected final List<Direction> getSidesForEnergy() {
        return Lists.newArrayList(Direction.values());
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY || getSidesForEnergy().contains(side) || side == null) {
            return energyLazyOptional.cast();
        }

        return super.getCapability(cap, side);
    }




    @Override
    public CompoundTag save(CompoundTag tag) {
        return energyData.save(tag);
    }

    @Override
    public void load(CompoundTag pTag) {
        energyData.load(pTag);
    }
}
