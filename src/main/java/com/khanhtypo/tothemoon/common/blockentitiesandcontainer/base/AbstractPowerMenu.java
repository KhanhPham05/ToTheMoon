package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import com.khanhtypo.tothemoon.registration.elements.MenuObject;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractPowerMenu extends BaseMenu {
    protected AbstractPowerMenu(MenuObject<?> menuObject, int windowId, Inventory playerInventory, ContainerLevelAccess accessor) {
        super(menuObject, windowId, playerInventory, accessor);
    }

    protected AbstractPowerMenu(MenuObject<?> menuObject, int windowId, Inventory playerInventory, ContainerLevelAccess accessor, @Nullable Block targetedBlock) {
        super(menuObject, windowId, playerInventory, accessor, targetedBlock);
    }
}
