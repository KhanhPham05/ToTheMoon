package com.khanhpham.tothemoon.core.blocks.machines.energysmelter;

import com.khanhpham.tothemoon.core.abstracts.BaseMenuScreen;
import com.khanhpham.tothemoon.core.abstracts.machines.MachineMenuScreen;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class EnergySmelterScreen extends BaseMenuScreen<EnergySmelterMenu> {
    public static final ResourceLocation GUI = ModUtils.modLoc("textures/gui/energy_smelter.png");

    public EnergySmelterScreen(EnergySmelterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, GUI);
        super.setImageHeight(177);
        super.setImageWidth(176);
    }
    @Override
    protected void renderExtra(@Nonnull PoseStack poseStack) {
        super.blit(poseStack, leftPos + 15, topPos + 72, 57, 183, menu.getEnergyBar() + 1, 12);
        super.blit(poseStack, leftPos + 64, topPos + 36, 213, 183, menu.getSmeltingProcess() + 1, 9);
    }
}
