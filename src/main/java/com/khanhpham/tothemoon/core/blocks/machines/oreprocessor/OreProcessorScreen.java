package com.khanhpham.tothemoon.core.blocks.machines.oreprocessor;

import com.khanhpham.tothemoon.core.abstracts.BaseMenuScreen;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class OreProcessorScreen extends BaseMenuScreen<OreProcessorMenu> {
    public OreProcessorScreen(OreProcessorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, ModUtils.getGuiId("energy_processor.png"));
        super.setImageSize(176, 177);
    }

    @Override
    protected void renderExtra(PoseStack pPoseStack) {
        super.blit(pPoseStack, leftPos + 15, topPos + 72, 57, 183, menu.getEnergyBar() + 1, 12);
        super.blit(pPoseStack, leftPos + 72, topPos + 36, 213, 183, menu.getProcessBar() + 1, 9);
    }
}
