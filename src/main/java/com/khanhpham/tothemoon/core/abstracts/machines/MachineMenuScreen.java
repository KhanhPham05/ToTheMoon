package com.khanhpham.tothemoon.core.abstracts.machines;

import com.khanhpham.tothemoon.core.abstracts.BaseMenuScreen;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class MachineMenuScreen<T extends BaseMachineMenu> extends BaseMenuScreen<T> {
    public MachineMenuScreen(T pMenu, Inventory pPlayerInventory, Component pTitle, ResourceLocation texture) {
        super(pMenu, pPlayerInventory, pTitle, texture);
        super.xPos = leftPos + 80;
    }

    @Override
    protected void renderExtraLabels(PoseStack poseStack) {
        font.draw(poseStack, ModLanguage.MACHINE_UPGRADE_LABELS, 12, 8, blackColor);
    }
}
