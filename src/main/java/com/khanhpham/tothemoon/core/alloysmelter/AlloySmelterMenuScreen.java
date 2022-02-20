package com.khanhpham.tothemoon.core.alloysmelter;

import com.khanhpham.tothemoon.utils.gui.BaseMenuScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AlloySmelterMenuScreen extends BaseMenuScreen<AlloySmelterMenu> {
    public AlloySmelterMenuScreen(AlloySmelterMenu pMenu, Inventory pPlayerInventory, Component pTitle, ResourceLocation texture) {
        super(pMenu, pPlayerInventory, pTitle, texture);
    }

    @Override
    protected void renderLabels(PoseStack poseStack) {

    }
}
