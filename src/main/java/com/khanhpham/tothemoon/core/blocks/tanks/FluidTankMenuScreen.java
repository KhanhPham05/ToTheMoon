package com.khanhpham.tothemoon.core.blocks.tanks;

import com.khanhpham.tothemoon.core.abstracts.BaseMenuScreen;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.utils.render.GuiRenderingUtils;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.text.TextUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FluidTankMenuScreen extends BaseMenuScreen<FluidTankMenu> {
    public static final ResourceLocation texture = ModUtils.modLoc("textures/gui/fluid_tank.png");

    public FluidTankMenuScreen(FluidTankMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, texture);
        super.setImageSize(176, 186);
    }

    @Override
    protected void renderExtra(PoseStack pPoseStack) {
        GuiRenderingUtils.renderFluidStack(pPoseStack, super.menu.getFluid(), super.menu.getTankCapacity(), leftPos + 143, topPos + 84, 16, 69);
    }

    @Override
    protected void renderExtraLabels(PoseStack poseStack) {
        super.drawRightFocusedString(poseStack, ModLanguage.FILL_TANK, 73, 26);
        super.drawRightFocusedString(poseStack, ModLanguage.EMPTY_TANK, 73, 58);
    }

    @Override
    protected void renderTooltip(PoseStack pPoseStack, int pX, int pY) {
        super.renderTooltip(pPoseStack, pX, pY);
        if (isHovering(143, 15, 16, 69, pX, pY)) {
            super.renderTooltip(pPoseStack, TextUtils.translateFluidTank(super.menu.getFluidObject(), super.menu.getTankAmount(), super.menu.getEnergyCapacity()), pX, pY);
        }
    }
}
