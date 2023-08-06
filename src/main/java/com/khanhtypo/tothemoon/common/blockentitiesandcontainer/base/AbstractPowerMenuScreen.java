package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.menus.AbstractMachineMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class AbstractPowerMenuScreen<T extends AbstractMachineMenu> extends BasicScreen<T> {
    public AbstractPowerMenuScreen(T menu, Inventory inventory, Component component, int imageWidth, int imageHeight) {
        super(menu, inventory, component, imageWidth, imageHeight);
    }

    public AbstractPowerMenuScreen(T menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }
}
