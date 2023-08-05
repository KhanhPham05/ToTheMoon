package com.khanhtypo.tothemoon.client.screen;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicScreen;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.menus.PowerGeneratorMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class PowerGeneratorScreen extends BasicScreen<PowerGeneratorMenu> {
    public PowerGeneratorScreen(PowerGeneratorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, 176, 179);
    }
}
