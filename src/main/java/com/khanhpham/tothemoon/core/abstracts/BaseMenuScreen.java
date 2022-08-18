package com.khanhpham.tothemoon.core.abstracts;

import com.khanhpham.tothemoon.core.menus.BaseMenu;
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
    public static final int blackFontColor = 0x404040;
    public static final int whiteFontColor = 0xffffff;
    protected int xPos = leftPos;

    public BaseMenuScreen(T pMenu, Inventory pPlayerInventory, Component pTitle, ResourceLocation texture) {
        super(pMenu, pPlayerInventory, pTitle);
        this.texture = texture;
        setImageHeightWidth(pMenu.playerInventorySlotStartsY + 82, pMenu.playerInventorySlotStartsX + 168);
    }

    protected final void setImageHeight(int height) {
        super.imageHeight = height;
    }

    protected final void setImageWidth(int width) {
        super.imageWidth = width;
    }

    protected final void setImageHeightWidth(int height, int width) {
        this.setImageHeight(height);
        this.setImageWidth(width);
    }


    @Override
    protected final void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, this.texture);

        if (super.minecraft != null) {
            super.minecraft.textureManager.bindForSetup(this.texture);
            this.blit(pPoseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
            this.renderExtra(pPoseStack);
        }
    }

    protected void renderExtra(PoseStack pPoseStack) {
    }

    @Override
    public final void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        super.font.draw(pPoseStack, super.playerInventoryTitle, menu.playerInventorySlotStartsX - 1, menu.playerInventorySlotStartsY - 11, blackFontColor);
        super.font.draw(pPoseStack, super.title, xPos + 7, 8, blackFontColor);
        renderExtraLabels(pPoseStack);
    }

    protected void renderExtraLabels(PoseStack poseStack) {
    }

    protected void drawRightFocusedString(PoseStack pose, Component translatableText, int topRightX, int topRightY) {
        int position = topRightX - super.font.width(translatableText.getVisualOrderText());
        super.font.draw(pose, translatableText, position, topRightY, blackFontColor);
    }

    @Override
    public boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
        return super.isHovering(pX, pY, pWidth, pHeight, pMouseX, pMouseY);
    }

    protected void drawCenterFocusedString(PoseStack pose, Component translatableText, int xMiddle, int yMiddle, int fontColor, float scale) {
        int textMiddleLeft = this.font.width(translatableText) / 2;
        pose.pushPose();
        pose.scale(scale, scale, scale);
        font.draw(pose, translatableText, (leftPos + xMiddle - textMiddleLeft) * scale, (topPos + yMiddle) * scale, fontColor);
        pose.popPose();
    }
}
