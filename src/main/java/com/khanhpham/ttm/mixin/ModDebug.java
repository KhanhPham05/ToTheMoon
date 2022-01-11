package com.khanhpham.ttm.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiComponent.class)
public class ModDebug {

   @Inject(at = @At("RETURN"), method = "blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V")
    private void blit(PoseStack pPoseStack, int pX, int pY, int pUOffset, int pVOffset, int pUWidth, int pVHeight, CallbackInfo ci) {
           System.out.println("Rendering Screen at [ " + pX + '-' + pY + " ] on game screen at [ "+ pUOffset + '-' + pVOffset +" ] on png with size [ "+ pUWidth + '-' + pVHeight +" ]");
   }
}
