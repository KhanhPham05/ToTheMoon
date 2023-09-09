package com.khanhtypo.tothemoon.common.machine.powergenerator;

import com.khanhtypo.tothemoon.common.machine.AbstractMachineScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PowerGeneratorScreen extends AbstractMachineScreen<PowerGeneratorMenu> {
    private static final int barHeight = 66;

    public PowerGeneratorScreen(PowerGeneratorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, 176, 179);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    protected int getEnergy() {
        return super.getMenu().getData(0);
    }

    @Override
    protected int getEnergyCapacity() {
        return super.getMenu().getData(1);
    }

    @Override
    protected void renderBgAddition(GuiGraphics renderer, ResourceLocation guiTexture) {
        //render fuel bar
        this.drawVerticalBar(renderer, guiTexture, 150, 16, 212, 3, barHeight, 2, 3);

        //render energy storage bar
        this.drawVerticalBar(renderer, guiTexture, 155, 16, 194, 10, barHeight, 0, 1);

        //render energy process bar
        this.drawVerticalBar(renderer, guiTexture, 167, 16, 206, 3, barHeight, 5, 6);
        super.renderBgAddition(renderer, guiTexture);
    }

    @Override
    protected void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY) {
        super.renderTooltip(pGuiGraphics, pX, pY);
        super.tryDrawEnergyStorageTooltip(pGuiGraphics, 154, 15, 12, 68, pX, pY);
    }
}