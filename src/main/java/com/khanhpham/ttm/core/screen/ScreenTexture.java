package com.khanhpham.ttm.core.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class ScreenTexture {
    public final ResourceLocation texture;
    public final int sizeX;
    public final int sizeY;

    public ScreenTexture(ResourceLocation texture, int sizeX, int sizeY) {
        this.texture = texture;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void bindTexture(@Nonnull Minecraft minecraft) {
        minecraft.textureManager.bindForSetup(texture);
    }
}
