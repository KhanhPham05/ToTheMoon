package com.khanhpham.ttm.core.screen.tier;

import com.khanhpham.ttm.TTMLang;
import com.khanhpham.ttm.core.containers.tier.generator.CopperGenMenu;
import com.khanhpham.ttm.core.screen.BaseGeneratorScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CopperGenScreen extends BaseGeneratorScreen<CopperGenMenu> {
    public CopperGenScreen(CopperGenMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void drawCustomLabel(PoseStack poseStack) {
        font.draw(poseStack, TTMLang.COPPER_GEN_LABEL, 6, 6, 0xE3826C);
    }
}
