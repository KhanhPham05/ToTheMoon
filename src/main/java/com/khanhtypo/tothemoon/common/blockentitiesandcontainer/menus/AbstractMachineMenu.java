package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.menus;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicMenu;
import com.khanhtypo.tothemoon.registration.elements.MenuObject;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;

public abstract class AbstractMachineMenu extends BasicMenu {
    protected final Container container;
    protected final ContainerData containerData;

    public AbstractMachineMenu(MenuObject<? extends BasicMenu> menuObject, int windowId, Inventory playerInventory, ContainerLevelAccess accessor, Container container, ContainerData containerData) {
        super(menuObject, windowId, playerInventory, accessor);
        this.container = container;
        this.containerData = containerData;
        super.addDataSlots(this.containerData);
    }

    public Container getContainer() {
        return this.container;
    }
}
