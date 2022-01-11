package com.khanhpham.ttm.core.blockentities.energygen.impl;

import com.khanhpham.ttm.core.blockentities.energygen.BaseGeneratorEntity;
import com.khanhpham.ttm.core.blockentities.energygen.EnergyGeneratorBlockEntity;
import com.khanhpham.ttm.core.containers.tier.generator.CopperGenMenu;
import com.khanhpham.ttm.core.energy.EnergyData;
import com.khanhpham.ttm.data.lang.EngLangProvider;
import com.khanhpham.ttm.init.ModMisc;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//TODO: Fix energy saving issue
public class CopperGenEntity extends EnergyGeneratorBlockEntity {
    public CopperGenEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModMisc.COPPER_GEN_ENTITY, pWorldPosition, pBlockState);
    }

    private final EnergyData ENERGY_DATA = new EnergyData(50000, 50) {
        @Override
        public void setChanged(Level level, BlockPos pos, BlockState state) {
            BaseGeneratorEntity.setChanged(level, pos, state);
        }
    };

    @Override
    protected @NotNull EnergyData getEnergyData() {
        return ENERGY_DATA;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return EngLangProvider.COPPER_GEN_LABEL;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new CopperGenMenu(pContainerId, pInventory, this, DATA_SLOT);
    }
}
