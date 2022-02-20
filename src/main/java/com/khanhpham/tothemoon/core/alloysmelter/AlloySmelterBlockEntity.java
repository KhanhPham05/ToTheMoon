package com.khanhpham.tothemoon.core.alloysmelter;

import com.khanhpham.tothemoon.utils.energy.Energy;
import com.khanhpham.tothemoon.utils.te.EnergyItemCapableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AlloySmelterBlockEntity extends EnergyItemCapableBlockEntity {
    public AlloySmelterBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label, int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy, label, containerSize);
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
        return null;
    }
}
