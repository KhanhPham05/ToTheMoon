package com.khanhpham.tothemoon.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public class GuiRenderingUtils {
    private static final Minecraft CLIENT = Minecraft.getInstance();
    private static final TextureManager TEXTURE_MANAGER = CLIENT.getTextureManager();

    private GuiRenderingUtils() {
    }

    public static void renderFluidStack(IFluidHandler tank, int tankCapacity, float x, float y, float width, float height) {
        FluidStack fluidStack = tank.getFluidInTank(tankCapacity);

        int fluidAmount = fluidStack.getAmount();
        if (fluidStack.getFluid() != Fluids.EMPTY && fluidAmount > 0) {
            TextureAtlasSprite fluidSprite = getFluidSprite(fluidStack);
            if (fluidSprite != null) {
                float renderAmount = Math.max(Math.min(height, fluidAmount * height / tankCapacity), 1);
                float posY = y - height - renderAmount;

                TEXTURE_MANAGER.bindForSetup(InventoryMenu.BLOCK_ATLAS);
                int fluidColor = fluidStack.getFluid().getAttributes().getColor();
                float r = ((fluidColor >> 16) & 0xFF) / 255f;
                float g = ((fluidColor >> 8) & 0xFF) / 255f;
                float b = (fluidColor & 0xFF) / 255f;
                RenderSystem.setShaderFogColor(r, b, g);

                RenderSystem.enableBlend();
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

                        Tesselator tesselator = Tesselator.getInstance();
                        BufferBuilder bufferBuilder = tesselator.getBuilder();

                        float v = minV + (maxV - minV) * drawHeight / 16F;
                        float v1 = minU + (maxU - minU) * drawWidth / 16F;

                        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                        bufferBuilder.vertex(drawX, drawY + drawHeight, 0).uv(minU, v).endVertex();
                        bufferBuilder.vertex(drawX + drawWidth, drawY + drawHeight, 0).uv(v1, v).endVertex();
                        bufferBuilder.vertex(drawX + drawWidth, drawY, 0).uv(v1, minV).endVertex();
                        bufferBuilder.vertex(drawX, drawY, 0).uv(minU, minV).endVertex();
                        tesselator.end();
                    }
                }
            }
        }

        RenderSystem.disableBlend();
        RenderSystem.setShaderFogColor(1, 1, 1);
    }

    @Nullable
    private static TextureAtlasSprite getFluidSprite(FluidStack fluidStack) {
        TextureAtlasSprite[] sprites = ForgeHooksClient.getFluidSprites(Minecraft.getInstance().level, BlockPos.ZERO, fluidStack.getFluid().defaultFluidState());
        return sprites.length > 0 ? sprites[0] : null;
    }
}
