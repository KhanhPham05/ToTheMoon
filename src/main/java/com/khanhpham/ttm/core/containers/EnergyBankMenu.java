package com.khanhpham.ttm.core.containers;

import com.khanhpham.ttm.init.ModBlocks;
import com.khanhpham.ttm.init.ModMisc;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.Block;

public class EnergyBankMenu extends Screen {
    public EnergyBankMenu(Component component) {
        super(component);
    }

    protected Block getBlockForContainer() {
        return ModBlocks.ENERGY_BANK;
    }
}
