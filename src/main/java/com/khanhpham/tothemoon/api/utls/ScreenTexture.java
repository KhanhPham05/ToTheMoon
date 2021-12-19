package com.khanhpham.tothemoon.api.utls;

import net.minecraft.util.ResourceLocation;

public final class ScreenTexture {
    public final ResourceLocation GUI_ID;
    public final int textureWidth;
    public final int textureHeight;
    public final int renderPosX;
    public final int renderPosY;

    public ScreenTexture(ResourceLocation GUI_ID, int textureWidth, int textureHeight, int renderPosX, int renderPosY) {
        this.GUI_ID = GUI_ID;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.renderPosX = renderPosX;
        this.renderPosY = renderPosY;
    }

    public ScreenTexture(ResourceLocation GUI_ID, int textureWidth, int textureHeight) {
        this(GUI_ID, textureWidth, textureHeight, 0, 0);
    }
}
