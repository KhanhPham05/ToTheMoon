package com.khanhpham.tothemoon.api.utls;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

/**
 * @see net.minecraft.client.gui.screen.inventory.BeaconScreen
 * @see net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen
 *
 */
public abstract class TTMContainerScreen<T extends BaseContainer> extends ContainerScreen<T> {

    protected final int colorTextWhite = 14737632;
    protected final int colorTextBlack = 4210752;


    public TTMContainerScreen(T p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
        imageWidth = getScreenTexture().textureWidth;
        imageHeight = getScreenTexture().textureHeight;
        leftPos = (this.width - imageWidth) / 2;
        topPos = (this.height - imageHeight) / 2;
    }

    @Nonnull
    protected abstract ScreenTexture getScreenTexture();

    protected abstract void drawLabels(MatrixStack matrixStack);

    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(@Nonnull MatrixStack matrixStack, float v, int i, int i1) {
        RenderSystem.color4f(1, 1, 1, 1);
        assert minecraft != null;
        minecraft.textureManager.bind(getScreenTexture().GUI_ID);
        blit(matrixStack, leftPos, topPos, getScreenTexture().renderPosX, getScreenTexture().renderPosY, getScreenTexture().textureWidth, getScreenTexture().textureHeight);
        extraRenderBg(matrixStack);
       // ToTheMoon.LOG.debug("Rendered screen at " + leftPos + " - " + topPos);
    }

    protected void extraRenderBg(MatrixStack matrixStack) {}

    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
    }

    protected void renderLabels(MatrixStack matrixStack, int a, int b) {
        drawLabels(matrixStack);
    }

    protected void drawPlayerInventoryLabel(MatrixStack matrixStack, int x, int y, int labelColor) {
        drawText(matrixStack, inventory.getDisplayName(), x, y, labelColor);
    }

    protected void drawText(MatrixStack matrixStack, ITextComponent component, int x, int y, int labelColor) {
        font.draw(matrixStack, component, x, y, labelColor);
    }
}
