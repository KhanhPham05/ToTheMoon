package com.khanhpham.tothemoon.core.energybank;

import com.khanhpham.tothemoon.utils.gui.BaseMenuScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AbstractEnergyBankMenuScreen extends BaseMenuScreen<AbstractEnergyBankMenu> {
    public AbstractEnergyBankMenuScreen(AbstractEnergyBankMenu pMenu, Inventory pPlayerInventory, Component pTitle, ResourceLocation texture) {
        super(pMenu, pPlayerInventory, pTitle, texture);
    }

    @Override
    protected void renderLabels(PoseStack poseStack) {

    }
}
