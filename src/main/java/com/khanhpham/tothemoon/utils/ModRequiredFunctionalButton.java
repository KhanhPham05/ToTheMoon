package com.khanhpham.tothemoon.utils;

import com.khanhpham.tothemoon.core.abstracts.BaseMenuScreen;
import com.khanhpham.tothemoon.core.menus.BaseMenu;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nonnull;

public class ModRequiredFunctionalButton extends AbstractWidget {
    public static final ResourceLocation WIDGET_LOCATION = ModUtils.getGuiId("widgets");
    public static final NoParamConsumer NO_ACTION = () -> {};
    private final Screen screen;
    private final int xRender;
    private final int yRender;
    private final String modid;
    private final boolean isModIncluded;
    private final NoParamConsumer action;

    public ModRequiredFunctionalButton(BaseMenuScreen<?> screen, int pX, int pY, int xRender, int yRender, Component pMessage, String modid) {
        this(screen, pX, pY, xRender, yRender, pMessage, modid, NO_ACTION);
    }

    public ModRequiredFunctionalButton(BaseMenuScreen<?> screen, int pX, int pY, int xRender, int yRender, Component pMessage, String modid, NoParamConsumer action) {
        super(screen.getGuiLeft() + pX, screen.getGuiTop() + pY, 22, 22, pMessage);
        this.screen = screen;
        this.xRender = xRender;
        this.yRender = yRender;
        this.modid = modid;
        this.isModIncluded = ModList.get().isLoaded(modid);
        this.action = action;
    }

    @Override
    public void renderButton(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, WIDGET_LOCATION);
        if (this.isModIncluded) {
            if (super.isHoveredOrFocused()) {
                super.blit(pPoseStack, super.x, super.y, this.xRender, this.yRender + 22, this.width, this.height);
                if (this.shouldShowTooltip()) this.screen.renderTooltip(pPoseStack, super.getMessage(), pMouseX, pMouseY);
            } else {
                super.blit(pPoseStack, super.x, super.y, this.xRender, this.yRender, this.width, this.height);
            }
        } else {
            super.blit(pPoseStack, super.x, super.y, this.xRender, this.yRender + 44, this.width, this.height);
            if (super.isHoveredOrFocused()) {
                this.screen.renderTooltip(pPoseStack, Component.translatable(ModLanguage.MISSING_MOD, this.modid), pMouseX, pMouseY);
            }
        }
    }

    public boolean shouldShowTooltip() {
        return true;
    }

    @Override
    public void onClick(double pMouseX, double pMouseY) {
        if (this.isModIncluded) this.action.action();
    }

    @Override
    public void updateNarration(@Nonnull NarrationElementOutput pNarrationElementOutput) {}
    @Override
    public void playDownSound(@Nonnull SoundManager pHandler) {}
}
