package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.workbench;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class WorkbenchScreen extends BasicScreen<WorkbenchMenu> {
    public WorkbenchScreen(WorkbenchMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_, 190, 209);
    }

    @Override
    protected void renderLabels(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
        p_281635_.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, TEXT_WHITE);
        p_281635_.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, TEXT_BLACK);
    }
}