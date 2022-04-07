package com.khanhpham.tothemoon.mixin;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.renderer.TheMoonDimensionEffect;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public class MoonLevelRenderer {

    @Final
    @Shadow
    private Minecraft minecraft;

    @Inject(at = @At("RETURN"), method = "renderSky")
    public void renderSky(PoseStack p_202424_, Matrix4f p_202425_, float p_202426_, Camera p_202427_, boolean p_202428_, Runnable p_202429_, CallbackInfo ci) {
        //
        @Nullable ClientLevel level = this.minecraft.level;
        if (level != null && level.effects() instanceof TheMoonDimensionEffect) {
            this.renderTheMoonSky(p_202424_);
        }
    }

    private void renderTheMoonSky(PoseStack poseStack) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, ToTheMoon.THE_MOON_SKY_LOCATION);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder builder = tesselator.getBuilder();

        for (int i = 0; i <= 5; i++) {
            poseStack.pushPose();
            switch (i) {
                case 1:
                    rotationDegrees(poseStack, Vector3f.XP, 90);
                case 2:
                    rotationDegrees(poseStack, Vector3f.XP, -90);
                case 3:
                    rotationDegrees(poseStack, Vector3f.XP, 180);
                case 4:
                    rotationDegrees(poseStack, Vector3f.ZP, 90);
                case 5:
                    rotationDegrees(poseStack, Vector3f.ZP, -90);
            }

            Matrix4f matrix4f = poseStack.last().pose();
            builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            builder.vertex(matrix4f, -100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(40, 40, 40, 255).endVertex();
            builder.vertex(matrix4f, -100.0F, -100.0F, 100.0F).uv(0.0F, 16.0F).color(40, 40, 40, 255).endVertex();
            builder.vertex(matrix4f, 100.0F, -100.0F, 100.0F).uv(16.0F, 16.0F).color(40, 40, 40, 255).endVertex();
            builder.vertex(matrix4f, 100.0F, -100.0F, -100.0F).uv(16.0F, 0.0F).color(40, 40, 40, 255).endVertex();
            tesselator.end();
            poseStack.popPose();
        }

        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    private void rotationDegrees(PoseStack poseStack, Vector3f vector3f, float value) {
        poseStack.mulPose(vector3f.rotationDegrees(value));
    }
}
