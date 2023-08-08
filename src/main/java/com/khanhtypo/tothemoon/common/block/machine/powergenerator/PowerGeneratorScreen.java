package com.khanhtypo.tothemoon.common.block.machine.powergenerator;

import com.khanhtypo.tothemoon.client.ActiveModeButton;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicScreen;
import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class PowerGeneratorScreen extends BasicScreen<PowerGeneratorMenu> {
    private static final int barHeight = 66;

    public PowerGeneratorScreen(PowerGeneratorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, 176, 179);
    }

    @Override
    protected void addExtraButtons() {
        super.addRenderableWidget(
                new ActiveModeButton(super.getMenu(), this, super.getButtonX(), super.getButtonY())
        );
    }

    @Override
    protected void renderBgAddition(GuiGraphics renderer, ResourceLocation guiTexture) {
        //render fuel bar
        this.drawBar(renderer, guiTexture, 150, 16, 212, 3, 2, 3);

        //render energy storage bar
        this.drawBar(renderer, guiTexture, 155, 16, 194, 10, 0, 1);

        //render energy process bar
        this.drawBar(renderer, guiTexture, 167, 16, 206, 3, 5, 6);
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

    @Override
    protected void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY) {
        super.renderTooltip(pGuiGraphics, pX, pY);

        /*if (super.isHovering(149, 15, 5, 68, pX, pY)) {
            //fuel bar
            int i = super.menu.getData(3);
            int duration = i > 0 ? i : 200;
            int time = super.menu.getData(2);
            float f = Math.max(0.0F, (duration - (float) time) / duration);
            int color = Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
            pGuiGraphics.renderTooltip(font, ModLanguageGenerator.FUEL_LEFT.withParam(Component.literal(String.valueOf((time * 100) / duration)).withStyle(Style.EMPTY.withColor(color))), pX, pY);
        }
         */

        if (super.isHovering(154, 15, 12, 68, pX, pY)) {
            //energy bar
            int energy = super.menu.getData(0);
            int cap = super.menu.getData(1);
            float f = Math.max(0.0F, 1.0f - ((cap - (float) energy) / cap));
            int color = Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
            pGuiGraphics.renderTooltip(font, ModLanguageGenerator.ENERGY_TOOLTIP.withParam(Component.literal(String.valueOf(energy)).withStyle(Style.EMPTY.withColor(color)), cap), pX, pY);
        }

        /*
        if (super.isHovering(166, 15, 5, 68, pX, pY)) {
            //progress bar
            int i = super.menu.getData(6);
            int duration = i > 0 ? i : 200;
            int time = super.menu.getData(5);
            float f = Math.max(0.0F, (duration - (float) time) / duration);
            int color = Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
            pGuiGraphics.renderTooltip(font, ModLanguageGenerator.ACTION_TIME.withParam(
                    ModLanguageGenerator.GENERATE_ENERGY_AFTER.withParam(menu.getData(4)),
                    Component.literal(String.valueOf(time * 0.05f)).withStyle(Style.EMPTY.withColor(color))
            ), pX, pY);
        }*/
    }

}