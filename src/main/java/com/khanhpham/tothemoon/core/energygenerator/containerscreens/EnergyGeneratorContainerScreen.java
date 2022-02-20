package com.khanhpham.tothemoon.core.energygenerator.containerscreens;

import com.khanhpham.tothemoon.core.energygenerator.containers.EnergyGeneratorMenu;
import com.khanhpham.tothemoon.utils.gui.energygenerator.AbstractEnergyGeneratorContainerScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EnergyGeneratorContainerScreen extends AbstractEnergyGeneratorContainerScreen<EnergyGeneratorMenu> {
    public static final ResourceLocation GUI = new ResourceLocation("tothemoon:textures/gui/energy_generator_new.png");

    public EnergyGeneratorContainerScreen(EnergyGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, GUI);
    }

    @Override
    protected void renderLabels(PoseStack poseStack) {
        font.draw(poseStack, super.title, 7, 8, 0x404040);
        font.draw(poseStack, super.playerInventoryTitle, 7, 86, 0x404040);
    }
}
