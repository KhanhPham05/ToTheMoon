package com.khanhpham.tothemoon.core.blocks.machines.battery;

import com.khanhpham.tothemoon.core.blockentities.BaseMenuScreen;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BatteryMenuScreen extends BaseMenuScreen<BatteryMenu> {
    public static final ResourceLocation GUI = ModUtils.modLoc("textures/gui/energy_bank.png");

    public BatteryMenuScreen(BatteryMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, GUI);
        setImageHeight(177);
    }

    @Override
    protected void renderExtra(PoseStack pPoseStack) {
        super.blit(pPoseStack, leftPos + 15, topPos + 78, 14, 183, menu.getEnergyBar() + 1, 12);
    }

    @Override
    protected void renderExtraLabels(PoseStack poseStack) {
       // font.draw(poseStack, Integer.toString(menu.getBatteryConnectionId()), 112, 3, blackColor);
    }
}
