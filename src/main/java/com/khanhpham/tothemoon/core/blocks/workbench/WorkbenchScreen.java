package com.khanhpham.tothemoon.core.blocks.workbench;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class WorkbenchScreen extends AbstractContainerScreen<WorkbenchMenu> {
    public WorkbenchScreen(WorkbenchMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 194;
        this.imageHeight = 209;

        this.inventoryLabelX = 18;
        this.inventoryLabelY = 113;

        this.titleLabelX = 8;
        this.titleLabelY = 8;
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        ModUtils.setupMenuScreen(this, "workbench_crafting.png", pPoseStack);
        /* Just for slot position testing
        for (int i = 0; i < menu.getSlotSize(); i++) {
            Slot slot = menu.getSlot(i);
            font.draw(pPoseStack, Integer.toString(i), slot.x, slot.y, 0x404040);
        }*/
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }
}
