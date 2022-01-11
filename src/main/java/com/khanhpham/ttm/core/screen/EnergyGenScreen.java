package com.khanhpham.ttm.core.screen;

import com.khanhpham.ttm.core.containers.EnergyGenMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class EnergyGenScreen extends BaseGeneratorScreen<EnergyGenMenu> {
    public EnergyGenScreen(EnergyGenMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void drawCustomLabel(PoseStack poseStack) {

    }
}
