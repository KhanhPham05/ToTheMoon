package com.khanhpham.tothemoon.core.multiblock.block.brickfurnace;

import com.khanhpham.tothemoon.core.abstracts.BaseMenuScreen;
import com.khanhpham.tothemoon.utils.GuiRenderingUtils;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.text.TextUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public final class NetherBrickFurnaceControllerScreen extends BaseMenuScreen<NetherBrickFurnaceControllerMenu> {
    public static final ResourceLocation GUI = ModUtils.modLoc("textures/gui/nether_brick_furnace.png");

    public NetherBrickFurnaceControllerScreen(NetherBrickFurnaceControllerMenu pMenu, Inventory pPlayerInventory, Component title) {
        super(pMenu, pPlayerInventory, title, GUI);
        super.setImageHeightWidth(177, 201);
    }

    @Override
    protected void renderExtra(PoseStack pPoseStack) {
        int k = super.menu.getFluidAmount();
        super.blit(pPoseStack, leftPos + 189, topPos + 88 - k, 219, 104 - k, 5, k);

        int l = super.menu.getBurningProcess();
        super.blit(pPoseStack, leftPos + 84, topPos + 42, 202, 11, l, 16);

        int m = super.menu.getBlazeFuel();
        int n = Mth.clamp((18 * m + this.menu.getBlazeFuelCapacity() - 1) / this.menu.getBlazeFuelCapacity(), 0, 18);
        if (n > 0) super.blit(pPoseStack, leftPos + 30, topPos + 62, 202, 0, n, 4);
    }

    @Override
    protected void renderTooltip(PoseStack pPoseStack, int pX, int pY) {
        if (isHovering(188, 12, 7, 77, pX, pY)) {
            Component text = TextUtils.fluidFuel(this.menu.getStoredFluid(), this.menu.getFluidCapacity());
            renderTooltip(pPoseStack, text, pX, pY);
        }

        pPoseStack.pushPose();
            pPoseStack.scale(.8f, .8f, .8f);
            GuiRenderingUtils.renderToolTip(this, pPoseStack, 29, 61, 20, 6, pX, pY, this.translateBlazeFuel());
        pPoseStack.popPose();

        super.renderTooltip(pPoseStack, pX, pY);
    }

    private Component translateBlazeFuel() {
        return TextUtils.translateFormatText("gui", "blaze_fuel", this.menu.getBlazeFuel(), this.menu.getBlazeFuelCapacity()).withStyle(Style.EMPTY.withColor(0xE57A00));
    }
}
