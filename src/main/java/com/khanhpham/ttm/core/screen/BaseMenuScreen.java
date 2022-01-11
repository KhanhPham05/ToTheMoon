package com.khanhpham.ttm.core.screen;

import com.khanhpham.ttm.core.containers.bases.BaseMenuContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

/**
@see net.minecraft.client.gui.screens.inventory.InventoryScreen
 */
public abstract class BaseMenuScreen<T extends BaseMenuContainer> extends AbstractContainerScreen<T> {
    public BaseMenuScreen(T pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        imageWidth = getScreenTexture().sizeX;
        imageHeight = getScreenTexture().sizeY;
    }

    @Override
    protected void renderBg(@Nonnull PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, getScreenTexture().texture);
        int i = (width -  imageWidth) / 2;
        int j = (height - imageHeight) / 2;
        if (minecraft != null) {
            getScreenTexture().bindTexture(minecraft);
            blit(pPoseStack, i, j, 0, 0, imageWidth, imageHeight);
            blitCustom(pPoseStack, i, j);
        }
    }

    protected void blitCustom(PoseStack poseStack, int i, int j) {

    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        font.draw(pPoseStack, super.playerInventoryTitle, 36, 111, 0x404040);
        drawCustomLabel(pPoseStack);
    }

    protected abstract void drawCustomLabel(PoseStack poseStack);

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    protected abstract ScreenTexture getScreenTexture();
}
