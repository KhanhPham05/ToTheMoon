package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.menus;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.AbstractPowerBlockEntity;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BaseMenu;
import com.khanhtypo.tothemoon.registration.elements.MenuObject;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;

public abstract class AbstractMachineMenu extends BaseMenu {
    protected final Container container;
    protected final ContainerData containerData;

    public AbstractMachineMenu(MenuObject<? extends BaseMenu> menuObject, int windowId, Inventory playerInventory, ContainerLevelAccess accessor, Container container, ContainerData containerData) {
        super(menuObject, windowId, playerInventory, accessor);
        this.container = container;
        this.containerData = containerData;
        super.addDataSlots(this.containerData);
    }

    public int getData(int index) {
        return this.containerData.get(index);
    }

    public Container getContainer() {
        return this.container;
    }

    public boolean isActive() {
        return AbstractPowerBlockEntity.intToBoolean(this.containerData.get(this.containerData.getCount() - 1));
    }

    public void toggleActive() {
        int active = this.containerData.get(this.containerData.getCount() - 1);
        super.setData(
                this.containerData.getCount() - 1,
                active == 1 ? 0 : 1
        );
    }
}
