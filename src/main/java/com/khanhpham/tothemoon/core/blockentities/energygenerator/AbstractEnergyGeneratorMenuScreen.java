package com.khanhpham.tothemoon.core.blockentities.energygenerator;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.core.menus.energycontainer.AbstractEnergyGeneratorMenu;
import com.khanhpham.tothemoon.core.abstracts.BaseMenuScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class AbstractEnergyGeneratorMenuScreen<T extends AbstractEnergyGeneratorMenu> extends BaseMenuScreen<T> {
    public AbstractEnergyGeneratorMenuScreen(T pMenu, Inventory pPlayerInventory, Component pTitle, ResourceLocation texture) {
        super(pMenu, pPlayerInventory, pTitle, texture);

        super.setImageHeight(179);
    }

    @Override
    protected void renderExtra(PoseStack pPoseStack) {
        super.blit(pPoseStack, leftPos + 15, topPos + 72, 14, 183, menu.getEnergyBar() + 1, 12);
        super.blit(pPoseStack, leftPos + 81, topPos + 70 - menu.getLitProgress(), 176, 14 - menu.getLitProgress(), 14, menu.getLitProgress() + 1);
    }

    @Override
    protected void renderTooltip(PoseStack pPoseStack, int pX, int pY) {
        if (super.isHovering(15, 72, 147, 12, pX, pY)) {
            super.renderTooltip(pPoseStack, ModUtils.translate("gui.tothemoon.energy_per_capacity", menu.getEnergyStored(), menu.getCapacity()), pX, pY);
        }

        super.renderTooltip(pPoseStack, pX, pY);
    }
}
