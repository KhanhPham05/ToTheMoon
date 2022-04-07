package com.khanhpham.tothemoon.core.machines.energygenerator.containerscreens;

import com.khanhpham.tothemoon.core.machines.energygenerator.containers.EnergyGeneratorMenu;
import com.khanhpham.tothemoon.utils.gui.energygenerator.AbstractEnergyGeneratorMenuScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class EnergyGeneratorMenuScreen extends AbstractEnergyGeneratorMenuScreen<EnergyGeneratorMenu> {
    public static final ResourceLocation GUI = new ResourceLocation("tothemoon", "textures/gui/energy_generator_new.png");

    public EnergyGeneratorMenuScreen(EnergyGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, GUI);
    }
}
