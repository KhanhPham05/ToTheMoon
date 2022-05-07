package com.khanhpham.tothemoon.core.blockentities;

import com.khanhpham.tothemoon.core.containers.BaseMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class BaseMenuScreen<T extends BaseMenu> extends AbstractContainerScreen<T> {
    protected final ResourceLocation texture;
    protected final int blackColor = 0x404040;

    public BaseMenuScreen(T pMenu, Inventory pPlayerInventory, Component pTitle, ResourceLocation texture) {
        super(pMenu, pPlayerInventory, pTitle);
        this.texture = texture;
    }

    protected final void setImageHeight(int height) {
        super.imageHeight = height;
        super.imageWidth = 176;
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, this.texture);

        if (minecraft != null) {
            minecraft.textureManager.bindForSetup(this.texture);
            this.blit(pPoseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
            this.renderExtra(pPoseStack);
        }
    }

    protected void renderExtra(PoseStack pPoseStack) {
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        super.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        super.font.draw(pPoseStack, super.playerInventoryTitle, menu.playerInventorySlotStartsX - 1, menu.playerInventorySlotStartsY - 11, this.blackColor);
        super.font.draw(pPoseStack, super.title, 7, 8, blackColor);
        renderExtraLabels(pPoseStack);
    }

    protected void renderExtraLabels(PoseStack poseStack) {
    }
}
