package com.khanhtypo.tothemoon.client;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicScreen;
import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Locale;

public class DecorationButton implements Renderable {
    private static final ResourceLocation texture = BasicScreen.RECIPE_BOOK_WIDGET;
    public final int x;
    public final int y;
    public final int width;
    public final int height;
    private final String requiredModId;
    private final int cornerX;
    private final int cornerY;
    private final Component modNeedsToInstall;
    public boolean isHovered;
    private @Nullable Component modIsInstalled;

    public DecorationButton(String requiredModId, int x, int y, int cornerX, int cornerY) {
        this.requiredModId = requiredModId;
        this.x = x;
        this.y = y;
        this.width = 22;
        this.height = 22;
        this.cornerX = cornerX;
        this.cornerY = cornerY;
        this.modNeedsToInstall = ModLanguageGenerator.MOD_NEEDS_INSTALLATION.withParam(this.requiredModId.toUpperCase(Locale.ROOT));
    }

    @Override
    public void render(GuiGraphics renderer, int p_253973_, int p_254325_, float p_254004_) {
        int u = cornerX;
        int v = cornerY;
        if (isLoaded()) {
            if (this.isHovered) {
                v = this.cornerY + 22;
            }
        } else {
            u = 44;
            v = 0;
        }

        renderer.blit(texture, this.x, this.y, u, v, this.width, this.height);
    }

    private boolean isLoaded() {
        return ModList.get().isLoaded(this.requiredModId);
    }

    public void setHovered(boolean isHovered) {
        this.isHovered = isHovered;
    }

    public Renderable setMessageWhenModIsPresent(@Nonnull Component component) {
        this.modIsInstalled = component;
        return this;
    }

    public void renderToolTip(GuiGraphics renderer, Font font, int mouseX, int mouseY) {
        @Nullable Component component = this.isLoaded() ? this.modIsInstalled : this.modNeedsToInstall;
        if (component != null) renderer.renderTooltip(font, component, mouseX, mouseY);
    }
}
