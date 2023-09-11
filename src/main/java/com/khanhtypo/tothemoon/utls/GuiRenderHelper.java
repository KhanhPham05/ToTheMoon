package com.khanhtypo.tothemoon.utls;

import com.khanhtypo.tothemoon.client.chat.FormattedTextUnit;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import org.joml.Matrix4f;

import javax.annotation.Nullable;

@SuppressWarnings({"unused"})
public class GuiRenderHelper {
    private GuiRenderHelper() {
    }

    public static Component getStorageComponent(AppendableComponent template, int currentValue, int maxValue) {
        return template.withParam(FormattedTextUnit.create(currentValue).withStyle(Style.EMPTY.withColor(getRbgColorFromStorage(currentValue, maxValue))), FormattedTextUnit.create(maxValue));
    }

    public static void renderStorageTooltip(GuiGraphics guiGraphics, Font font, AppendableComponent template, int currentValue, int maxValue, int mouseX, int mouseY) {
        guiGraphics.renderTooltip(font, getStorageComponent(template, currentValue, maxValue), mouseX, mouseY);
    }

    public static int getRbgColorFromStorage(int currentValue, int maxValue) {
        float percent = (float) currentValue / (float) maxValue;
        return Mth.hsvToRgb(percent / 3.0f, 1, 1);
    }

    /**
     * @param guiGraphics  the renderer
     * @param minecraft    minecraft client
     * @param fluidAmount  amount of stack
     * @param tankCapacity capacity of stack
     * @param x            leftPos + bottom right corner
     * @param y            topPos + bottom right corner
     * @param width        width of the box to render
     * @param height       height of the box to render
     */
    public static void renderFluidToScreen(GuiGraphics guiGraphics, Minecraft minecraft, int fluidId, int fluidAmount, int tankCapacity, int x, int y, int width, int height) {
        if (fluidId > 0 && fluidAmount > 0) {
            IClientFluidTypeExtensions textureGetter = IClientFluidTypeExtensions.of(ModRegistries.getFromIdOrThrow(Registries.FLUID, fluidId));
            TextureAtlasSprite fluidSprite = getFluidStillSprite(minecraft, textureGetter);
            if (fluidSprite != null) {
                y += height;
                float renderAmount = Math.max(Math.min(height, fluidAmount * height / tankCapacity), 1);
                float posY = y - height - renderAmount;

                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, fluidSprite.atlasLocation());
                RenderSystem.enableBlend();

                int fluidColor = textureGetter.getTintColor();
                float r = ((fluidColor >> 16) & 0xFF) / 255f;
                float g = ((fluidColor >> 8) & 0xFF) / 255f;
                float b = (fluidColor & 0xFF) / 255f;
                float a = ((fluidColor >> 24) & 0xFF) / 255F;
                RenderSystem.setShaderColor(r, g, b, a);

                for (int i = 0; i < width; i += 16) {
                    for (int j = 0; j < renderAmount; j += 16) {
                        Matrix4f matrix = guiGraphics.pose().last().pose();
                        float drawWidth = Math.min(width - i, 16);
                        float drawHeight = Math.min(renderAmount - j, 16);

                        float drawX = x + i;
                        float drawY = posY + j;

                        float minU = fluidSprite.getU0();
                        float maxU = fluidSprite.getU1();
                        float minV = fluidSprite.getV0();
                        float maxV = fluidSprite.getV1();

                        float v = minV + (maxV - minV) * drawHeight / 16F;
                        float v1 = minU + (maxU - minU) * drawWidth / 16F;

                        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
                        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                        bufferBuilder.vertex(matrix, drawX, drawY + drawHeight, 0)
                                .uv(minU, v)
                                .endVertex();
                        bufferBuilder.vertex(matrix, drawX + drawWidth, drawY + drawHeight, 0)
                                .uv(v1, v)
                                .endVertex();
                        bufferBuilder.vertex(matrix, drawX + drawWidth, drawY, 0)
                                .uv(v1, minV)
                                .endVertex();
                        bufferBuilder.vertex(matrix, drawX, drawY, 0)
                                .uv(minU, minV)
                                .endVertex();
                        BufferUploader.drawWithShader(bufferBuilder.end());
                    }
                }
                RenderSystem.setShaderColor(1, 1, 1, 1);
                RenderSystem.disableBlend();
            }
        }
    }

    @Nullable
    public static TextureAtlasSprite getFluidStillSprite(Minecraft minecraft, int fluidId) {
        return getFluidStillSprite(minecraft, ModRegistries.getFromIdOrThrow(Registries.FLUID, fluidId));
    }

    @Nullable
    public static TextureAtlasSprite getFluidStillSprite(Minecraft minecraft, Fluid fluid) {
        return getFluidStillSprite(minecraft, IClientFluidTypeExtensions.of(fluid));
    }

    @Nullable
    public static TextureAtlasSprite getFluidStillSprite(Minecraft minecraft, IClientFluidTypeExtensions fluidRenderer) {
        @Nullable ResourceLocation resourceLocation = fluidRenderer.getStillTexture();
        return resourceLocation != null ? minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(resourceLocation) : null;
    }
}
