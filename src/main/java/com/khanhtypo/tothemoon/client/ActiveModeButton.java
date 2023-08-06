package com.khanhtypo.tothemoon.client;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.AbstractPowerBlockEntity;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicScreen;
import com.khanhtypo.tothemoon.registration.elements.MenuObject;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;

public class ActiveModeButton extends Button {
    private static final ResourceLocation ON_OFF_TEXTURE = MenuObject.texturePath("");
    private final Container container;

    public ActiveModeButton(Container container, Builder builder) {
        super(builder);
        this.container = container;
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {

    }

    @Override
    public void onPress() {
        super.onPress();
    }

    @Override
    public boolean isActive() {
        return super.isActive() && this.isPowerBlock();
    }

    private boolean isPowerBlock() {
        return this.container instanceof AbstractPowerBlockEntity;
    }
}
