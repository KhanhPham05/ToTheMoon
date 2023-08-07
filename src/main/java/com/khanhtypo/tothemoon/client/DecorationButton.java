package com.khanhtypo.tothemoon.client;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicScreen;
import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Locale;

public class DecorationButton extends AbstractWidget {
    private static final ResourceLocation texture = BasicScreen.RECIPE_BOOK_WIDGET;
    private final Font font;
    private final String requiredModId;
    private final int u;
    private final int v;
    private @Nullable Component modIsInstalled;

    public DecorationButton(String requiredModId, int x, int y, int u, int v) {
        super(x, y, 22, 22, ModLanguageGenerator.MOD_NEEDS_INSTALLATION.withParam(requiredModId.toUpperCase(Locale.ROOT)));
        this.requiredModId = requiredModId;
        this.u = u;
        this.v = v;
        this.font = Minecraft.getInstance().font;
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.isLoaded()) {
            if (super.isHoveredOrFocused()) {
                pGuiGraphics.blit(texture, super.getX(), super.getY(), this.u, this.v + 22, 22, 22);
                if (this.modIsInstalled != null) {
                    pGuiGraphics.renderTooltip(this.font, this.modIsInstalled, pMouseX, pMouseY);
                }
            } else {
                pGuiGraphics.blit(texture, super.getX(), super.getY(), u, v, 22, 22);
            }
        } else {
            pGuiGraphics.blit(texture, super.getX(), super.getY(), 44, 0, 22, 22);
            pGuiGraphics.renderTooltip(this.font, super.getMessage(), pMouseX, pMouseY);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
        super.defaultButtonNarrationText(pNarrationElementOutput);
    }

    private boolean isLoaded() {
        return ModList.get().isLoaded(this.requiredModId);
    }

    public Renderable setMessageWhenModIsPresent(@Nonnull Component component) {
        this.modIsInstalled = component;
        return this;
    }
}
