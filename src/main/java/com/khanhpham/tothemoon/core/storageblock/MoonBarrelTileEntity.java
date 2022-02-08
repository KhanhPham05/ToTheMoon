package com.khanhpham.tothemoon.core.storageblock;

import com.khanhpham.tothemoon.init.ModTileEntityTypes;
import com.khanhpham.tothemoon.utils.te.ItemCapableTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class MoonBarrelTileEntity extends ItemCapableTileEntity {
    public static final TranslatableComponent MENU_DISPLAY_NAME = new TranslatableComponent("gui.tothemoon.moon_storage");

    public MoonBarrelTileEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntityTypes.MOON_STORAGE, pWorldPosition, pBlockState, MoonBarrelContainer.CAPACITY);
    }

    @Override
    protected Component getDefaultName() {
        return MENU_DISPLAY_NAME;
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new MoonBarrelContainer(pContainerId, this, pInventory);
    }
}
