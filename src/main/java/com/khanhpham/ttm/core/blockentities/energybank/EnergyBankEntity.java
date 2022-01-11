package com.khanhpham.ttm.core.blockentities.energybank;

import com.khanhpham.ttm.core.containers.EnergyBankMenu;
import com.khanhpham.ttm.core.energy.EnergyData;
import com.khanhpham.ttm.init.ModMisc;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//FOR TEST ONLY
public final class EnergyBankEntity extends BaseEnergyBankEntity {
    private static final EnergyData energyData = new EnergyData(10000, 2000) {
        @Override
        public void setChanged(Level level, BlockPos pos, BlockState state) {
            BlockEntity.setChanged(level, pos, state);
        }
    };

    public EnergyBankEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModMisc.ENERGY_BANK_ENTITY, pWorldPosition, pBlockState);
    }

    @Deprecated
    @SuppressWarnings("unused")
    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        energyData.addTestEnergy(true);
        markDirty(energyData, level, blockPos, blockState);
    }

    @Override
    protected @NotNull EnergyData getEnergyData() {
        return energyData;
    }

    @Override
    protected CompoundTag saveCustom(CompoundTag tag) {
        tag = energyData.save(tag);
        return tag;
    }

    @Override
    protected void loadCustom(CompoundTag nbt) {
        energyData.load(nbt);
    }
}
