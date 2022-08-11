package com.khanhpham.tothemoon.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

public class GuiRenderingUtils {
    private static final Minecraft CLIENT = Minecraft.getInstance();
    private static final TextureManager TEXTURE_MANAGER = CLIENT.getTextureManager();

    private GuiRenderingUtils() {
    }

    //Inspired by Silent's Mechanisms
    public static void renderFluidStack(PoseStack pose, FluidStack fluidStack, int tankCapacity, int x, int y, int width, int height) {
        int fluidAmount = fluidStack.getAmount();
        if (fluidStack.getFluid() != Fluids.EMPTY && fluidAmount > 0) {
            TextureAtlasSprite fluidSprite = stillSprite(fluidStack);
            if (fluidSprite != null) {
                y += height;
                float renderAmount = Math.max(Math.min(height, fluidAmount * height / tankCapacity), 1);
                float posY = y - height - renderAmount;

                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
                Matrix4f matrix = pose.last().pose();

                int fluidColor = fluidStack.getFluid().getAttributes().getColor();
                float r = ((fluidColor >> 16) & 0xFF) / 255f;
                float g = ((fluidColor >> 8) & 0xFF) / 255f;
                float b = (fluidColor & 0xFF) / 255f;
                float a = ((fluidColor >> 24) & 0xFF) / 255F;
                RenderSystem.setShaderColor(r, g, b, a);

                for (int i = 0; i < width; i += 16) {
                    for (int j = 0; j < renderAmount; j += 16) {
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

                        RenderSystem.enableBlend();
                        Tesselator tesselator = Tesselator.getInstance();
                        BufferBuilder bufferBuilder = tesselator.getBuilder();
                        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                        bufferBuilder.vertex(matrix, drawX, drawY + drawHeight, 0).uv(minU, v).endVertex();
                        bufferBuilder.vertex(matrix, drawX + drawWidth, drawY + drawHeight, 0).uv(v1, v).endVertex();
                        bufferBuilder.vertex(matrix, drawX + drawWidth, drawY, 0).uv(v1, minV).endVertex();
                        bufferBuilder.vertex(matrix, drawX, drawY, 0).uv(minU, minV).endVertex();
                        bufferBuilder.end();
                        BufferUploader.end(bufferBuilder);
                        RenderSystem.disableBlend();
                    }
                }
            }
        }

        //RenderSystem.setShaderColor(1, 1, 1, 1);

    }


    @Deprecated
    //Inspired by JEI
    //https://github.com/mezz/JustEnoughItems/blob/211ca2cc020f56f12f0bc1cf8799b731d593e7d4/Common/src/main/java/mezz/jei/common/render/FluidTankRenderer.java#L72
    public static void renderFluidStack(PoseStack pose, FluidStack fluidStack, int width, int height, int capacity) {
        RenderSystem.enableBlend();

        //start drawing
        //https://github.com/mezz/JustEnoughItems/blob/211ca2cc020f56f12f0bc1cf8799b731d593e7d4/Common/src/main/java/mezz/jei/common/render/FluidTankRenderer.java#L95
        Fluid fluid = fluidStack.getFluid();
        if (!fluid.isSame(Fluids.EMPTY)) {
            TextureAtlasSprite sprite = stillSprite(fluidStack);

            int fluidColor = getFluidColorTint(fluidStack);

            int fluidAmount = fluidStack.getAmount();
            int scaledAmount = (fluidAmount * height) / capacity;

            if (fluidAmount > 0 && scaledAmount < 1) {
                scaledAmount = 1;
            }

            if (scaledAmount > height) {
                scaledAmount = height;
            }

            //draw tiled sprite
            //https://github.com/mezz/JustEnoughItems/blob/211ca2cc020f56f12f0bc1cf8799b731d593e7d4/Common/src/main/java/mezz/jei/common/render/FluidTankRenderer.java#L124
            RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
            Matrix4f matrix = pose.last().pose();

            //set gl color
            float r = (fluidColor >> 16 & 0xFF) / 255.0F;
            float g = (fluidColor >> 8 & 0xFF) / 255.0F;
            float b = (fluidColor & 0xFF) / 255.0F;
            float a = ((fluidColor >> 24) & 0xFF) / 255F;

            RenderSystem.setShaderColor(r, g, b, a);

            int xTileCount = width / 16;
            int xRemainder = width - (xTileCount * 16);
            long yTileCount = scaledAmount / 16;
            long yRemainder = scaledAmount - (yTileCount * 16);

            for (int xTile = 0; xTile <= xTileCount; xTile++) {
                for (int yTile = 0; yTile <= yTileCount; yTile++) {
                    int w = xTile == xTileCount ? xRemainder : 16;
                    long h = yTile == yTileCount ? yRemainder : 16;
                    int x = xTile * 16;
                    int y = height - (yTile + 1) * 16;
                    if (w > 0 && h > 0) {
                        long maskTop = 16 - h;
                        int maskRight = 16 - w;

                        //draw with masking
                        float uMin = sprite.getU0();
                        float uMax = sprite.getU1();
                        float vMin = sprite.getV0();
                        float vMax = sprite.getV1();

                        final int zLevel = 100;

                        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);

                        Tesselator tes = Tesselator.getInstance();
                        BufferBuilder buf = tes.getBuilder();
                        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                        buf.vertex(matrix, x, y + 16, zLevel).uv(uMin, vMax).endVertex();
                        buf.vertex(matrix, x + 16 - maskRight, y + 16, zLevel).uv(uMax, vMin).endVertex();
                        buf.vertex(matrix, x + 16 - maskRight, y + maskTop, zLevel).uv(uMin, vMin).endVertex();
                        tes.end();
                    }
                }
            }
        }

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.disableBlend();
    }

    private static int getFluidColorTint(FluidStack fluidStack) {
        return fluidStack.getFluid().getAttributes().getColor(fluidStack);
    }

    private static TextureAtlasSprite stillSprite(FluidStack fluidStack) {
        Fluid fluid = fluidStack.getFluid();
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluid.getAttributes().getStillTexture(fluidStack));
    }
}
