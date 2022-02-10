package com.khanhpham.tothemoon.utils.gui.energygenerator;

import com.khanhpham.tothemoon.utils.containers.energycontainer.AbstractEnergyGeneratorContainer;
import com.khanhpham.tothemoon.utils.gui.BaseContainerScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractEnergyGeneratorContainerScreen<T extends AbstractEnergyGeneratorContainer> extends BaseContainerScreen<T> {
    public AbstractEnergyGeneratorContainerScreen(T pMenu, Inventory pPlayerInventory, Component pTitle, ResourceLocation texture) {
        super(pMenu, pPlayerInventory, pTitle, texture);

        super.setImageWidthAndHeight(176, 179);
    }

    @Override
    protected void renderExtra(PoseStack pPoseStack) {
        //blit(pPoseStack,leftPos + 16, topPos + 185, 17, 74);
        blit(pPoseStack, leftPos + 17, topPos + 7,16, 185, menu.getEnergyProcess() + 1, 12);
    }
}
