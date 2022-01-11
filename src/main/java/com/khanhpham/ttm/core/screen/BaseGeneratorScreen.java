package com.khanhpham.ttm.core.screen;

import com.khanhpham.ttm.ToTheMoonMain;
import com.khanhpham.ttm.core.containers.bases.BaseGeneratorMenuContainer;
import com.khanhpham.ttm.core.containers.bases.BaseMenuContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class BaseGeneratorScreen<T extends BaseGeneratorMenuContainer> extends BaseMenuScreen<T> {
    public BaseGeneratorScreen(T pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    private static final ScreenTexture SCREEN_TEXTURE = new ScreenTexture(new ResourceLocation(ToTheMoonMain.MOD_ID, "textures/gui/energy_generator_new.png"), 176, 179);

    @Override
    protected ScreenTexture getScreenTexture() {
        return SCREEN_TEXTURE;
    }

    /**
     * @see net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen
     */
    @Override
    protected void blitCustom(PoseStack poseStack, int i, int j) {
        int k = menu.getLitProgress();
        blit(poseStack, i + 80, j + 69 - k, 176, 13 - k, 14, k + 1);
        blit(poseStack, i + 15, j + 72, 14, 183, menu.getEnergyBar() + 1, 12);
    }
}
