package com.khanhtypo.tothemoon.common.block.machine.powergenerator;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicScreen;
import com.khanhtypo.tothemoon.common.block.machine.powergenerator.PowerGeneratorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PowerGeneratorScreen extends BasicScreen<PowerGeneratorMenu> {
    private static final int barHeight = 66;

    public PowerGeneratorScreen(PowerGeneratorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, 176, 179);
    }

    @Override
    protected void renderBgAddition(GuiGraphics renderer, ResourceLocation guiTexture) {
        //render fuel bar
        this.drawBar(renderer, guiTexture, 150, 16, 212, 3, 2, 3);

        //render energy storage bar
        this.drawBar(renderer, guiTexture, 155, 16, 194, 10, 0, 1);

        //render energy process bar
        this.drawBar(renderer, guiTexture, 167, 16, 206, 3, 5, 6);

        //this.drawDebugText(renderer);
    }

    private void drawDebugText(GuiGraphics renderer) {
        for (int i = 0; i < 7; i++) {
            renderer.drawString(font, "%s : %s".formatted(i, menu.getData(i)), 0, super.topPos + i * 8, TEXT_WHITE, true);
        }
    }

    private void drawBar(GuiGraphics renderer, ResourceLocation guiTexture, int maxCorerX, int maxCornerY, int xOffset, int barWidth, int dataIndex, int dataIndexMax) {
        final int i = super.menu.getData(dataIndexMax);
        final int height = super.menu.getData(dataIndex) * barHeight / ((i > 0) ? i : 200);
        renderer.blit(guiTexture, super.leftPos + maxCorerX, super.topPos + maxCornerY + barHeight - height, xOffset, barHeight - height, barWidth, height);
    }
}