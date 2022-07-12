package com.khanhpham.tothemoon.core.blocks.tanks;

import com.khanhpham.tothemoon.core.abstracts.BaseMenuScreen;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.text.TextUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluids;

public class FluidTankMenuScreen extends BaseMenuScreen<FluidTankMenu> {
    public static final ResourceLocation texture = ModUtils.modLoc("textures/gui/fluid_tank.png");

    public FluidTankMenuScreen(FluidTankMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, texture);
    }

    @Override
    protected void renderExtra(PoseStack pPoseStack) {
        if (menu.canRenderFluid())
            super.blit(pPoseStack, leftPos + 82, topPos + 84 - menu.getFluidHeight(), 179, 69 - menu.getFluidHeight(), 5, menu.getFluidHeight());
    }

    @Override
    protected void renderTooltip(PoseStack pPoseStack, int pX, int pY) {
        super.renderTooltip(pPoseStack, pX, pY);
        if (isHovering(81, 14, 7, 71, pX, pY)) {
            super.renderTooltip(pPoseStack, TextUtils.translateFluidTank(Fluids.LAVA, super.menu.getAmount(), super.menu.getCapacity()), pX, pY);
        }
    }
}
