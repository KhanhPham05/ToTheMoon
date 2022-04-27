package com.khanhpham.tothemoon.core.blocks.machines.metalpress;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.core.containers.DataContainerMenuHelper;
import com.khanhpham.tothemoon.core.blockentities.BaseMenuScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class MetalPressMenuScreen extends BaseMenuScreen<MetalPressMenu> implements DataContainerMenuHelper {
    public static final ResourceLocation GUI = ModUtils.modLoc("textures/gui/metal_press.png");

    public MetalPressMenuScreen(MetalPressMenu menu, Inventory playerInventory, Component component) {
        super(menu, playerInventory, component, GUI);
        super.setImageHeight(177);
    }

    @Override
    protected void renderExtra(PoseStack pPoseStack) {
        int i = this.getEnergyBar(2, 3);
        super.blit(pPoseStack, super.leftPos + 15, super.topPos + 72, 14, 183, i + 1, 12);
        int j = super.menu.getPressingProcess();
        super.blit(pPoseStack, super.leftPos + 71, super.topPos + 32, 177, 1, j, 16);
    }

    @Override
    public ContainerData getContainerData() {
        return super.menu.data;
    }
}
