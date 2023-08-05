package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.workbench;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicScreen;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.RecipeContainerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class WorkbenchScreen extends BasicScreen<WorkbenchMenu> implements RecipeContainerMenu {
    public static final int IMAGE_WIDTH = 190;
    public static final int IMAGE_HEIGHT = 209;

    public WorkbenchScreen(WorkbenchMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_, IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    @Override
    protected void renderLabels(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
        p_281635_.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, TEXT_WHITE, true);
        p_281635_.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, TEXT_BLACK, false);
    }
}
