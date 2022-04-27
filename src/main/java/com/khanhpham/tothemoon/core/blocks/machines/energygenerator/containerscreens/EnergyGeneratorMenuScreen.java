package com.khanhpham.tothemoon.core.blocks.machines.energygenerator.containerscreens;

import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.containers.EnergyGeneratorMenu;
import com.khanhpham.tothemoon.core.blockentities.energygenerator.AbstractEnergyGeneratorMenuScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EnergyGeneratorMenuScreen extends AbstractEnergyGeneratorMenuScreen<EnergyGeneratorMenu> {
    public static final ResourceLocation GUI = new ResourceLocation("tothemoon", "textures/gui/energy_generator_new.png");

    public EnergyGeneratorMenuScreen(EnergyGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, GUI);
    }
}
