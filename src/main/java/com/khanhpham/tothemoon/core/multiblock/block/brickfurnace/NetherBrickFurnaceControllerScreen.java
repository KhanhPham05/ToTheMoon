package com.khanhpham.tothemoon.core.multiblock.block.brickfurnace;

import com.khanhpham.tothemoon.core.abstracts.BaseMenuScreen;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.text.TextUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @see net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen
 */
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
        blit(pPoseStack, leftPos + 189, topPos + 88 - k, 219, 104 - k, 5, k);
        int l = super.menu.getBurningProcess();
        blit(pPoseStack, leftPos + 84, topPos + 42, 202, 11, l, 16);
    }

    @Override
    protected void renderTooltip(PoseStack pPoseStack, int pX, int pY) {
        if (isHovering(188, 12, 7, 77, pX, pY)) {
            Component text = TextUtils.fluidFuel(this.menu.getStoredFluid(), this.menu.getFluidCapacity());
            renderTooltip(pPoseStack, text, pX, pY);
        }
        super.renderTooltip(pPoseStack, pX, pY);
    }
}
