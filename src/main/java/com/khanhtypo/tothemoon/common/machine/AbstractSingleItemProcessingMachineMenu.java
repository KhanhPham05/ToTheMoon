package com.khanhtypo.tothemoon.common.machine;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BaseMenu;
import com.khanhtypo.tothemoon.registration.elements.MenuObject;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractSingleItemProcessingMachineMenu extends AbstractMachineMenu {
    public AbstractSingleItemProcessingMachineMenu(MenuObject<? extends BaseMenu> menuObject, int windowId, Inventory playerInventory, ContainerLevelAccess accessor, Container container, Container upgradeContainer, ContainerData containerData) {
        super(menuObject, windowId, playerInventory, accessor, container, upgradeContainer, containerData);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }
}
