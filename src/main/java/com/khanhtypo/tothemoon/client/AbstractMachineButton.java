package com.khanhtypo.tothemoon.client;

import com.khanhtypo.tothemoon.client.screen.BasicScreen;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BaseMenu;
import com.khanhtypo.tothemoon.registration.elements.MenuObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractMachineButton extends AbstractWidget {
    protected static final ResourceLocation TEXTURE = MenuObject.texturePath("buttons");
    protected final Font font;
    protected final BaseMenu menu;
    protected final AbstractContainerScreen<?> screen;
    protected final Minecraft minecraft;
    protected final int dataGetterIndex;
    private final int defaultX;
    private final int defaultY;

    public AbstractMachineButton(int x, int y, BaseMenu menu, BasicScreen<?> screen, int dataGetterIndex) {
        super(x, y, 22, 22, Component.nullToEmpty(null));
        this.menu = menu;
        this.screen = screen;
        this.minecraft = screen.getMinecraft();
        this.dataGetterIndex = dataGetterIndex;
        this.font = this.minecraft.font;
        this.defaultX = x;
        this.defaultY = y;
    }

    @Override
    public abstract Component getMessage();

    @Override
    protected abstract void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick);

    @Override
    public abstract void onClick(double pMouseX, double pMouseY, int button);

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
        super.defaultButtonNarrationText(pNarrationElementOutput);
    }

    public int getDefaultX() {
        return defaultX;
    }

    public int getDefaultY() {
        return defaultY;
    }
}
