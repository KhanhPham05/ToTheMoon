package com.khanhpham.tothemoon.core.storageblock;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.utils.ModLang;
import com.khanhpham.tothemoon.utils.gui.BaseMenuScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MoonBarrelScreen extends BaseMenuScreen<MoonBarrelMenu> {
    public MoonBarrelScreen(MoonBarrelMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, GUI);
        super.setImageWidthAndHeight(186);
    }

    private static final ResourceLocation GUI = new ResourceLocation(Names.MOD_ID, "textures/gui/moon_rock_barrel.png");

    @Override
    protected void renderLabels(PoseStack poseStack) {
        font.draw(poseStack, ModLang.MOON_ROCK_BARREL, 7, 5, 0x404040);
        font.draw(poseStack, super.playerInventoryTitle, 7, 92, 0x404040);
    }


}
