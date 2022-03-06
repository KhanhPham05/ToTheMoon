package com.khanhpham.tothemoon.core.alloysmelter;

import com.khanhpham.tothemoon.utils.MenuGuis;
import com.khanhpham.tothemoon.utils.gui.BaseMenuScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class AlloySmelterMenuScreen extends BaseMenuScreen<AlloySmelterMenu> {
    public AlloySmelterMenuScreen(AlloySmelterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, MenuGuis.ALLOY_SMELTER);
        setImageWidthAndHeight(177);
    }

    @Override
    protected void renderExtra(PoseStack pPoseStack) {
        super.blit(pPoseStack, leftPos + 64, topPos + 30, 178, 1, menu.getAlloyingProcess() + 1 , 20);
    }

    @Override
    protected void renderLabels(PoseStack poseStack) {
    }
}
