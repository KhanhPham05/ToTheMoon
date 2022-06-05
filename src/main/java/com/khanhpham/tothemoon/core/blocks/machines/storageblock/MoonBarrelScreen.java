package com.khanhpham.tothemoon.core.blocks.machines.storageblock;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.core.abstracts.BaseMenuScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MoonBarrelScreen extends BaseMenuScreen<MoonBarrelMenu> {
    public MoonBarrelScreen(MoonBarrelMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, GUI);
        super.setImageHeight(186);
    }

    private static final ResourceLocation GUI = new ResourceLocation(Names.MOD_ID, "textures/gui/moon_rock_barrel.png");

    public static final TranslatableComponent MOON_ROCK_BARREL = new TranslatableComponent("gui.tothemoon.moon_rock_barrel");
}
