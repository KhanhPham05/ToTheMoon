package com.khanhpham.tothemoon.core.machines.alloysmelter;

import com.khanhpham.tothemoon.utils.gui.BaseMenuScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AlloySmelterMenuScreen extends BaseMenuScreen<AlloySmelterMenu> {
    public static final ResourceLocation GUI = new ResourceLocation("tothemoon", "textures/gui/alloy_smelter.png");

    public AlloySmelterMenuScreen(AlloySmelterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, GUI);

        super.setImageHeight(177);

    }

    @Override
    protected void renderExtra(PoseStack pPoseStack) {
        super.blit(pPoseStack, leftPos + 15, topPos + 72, 14, 183, menu.getEnergyBar() + 1, 12);
        super.blit(pPoseStack, leftPos + 64, topPos + 30, 178, 1, menu.getAlloyingProcess(), 20);
    }
}
