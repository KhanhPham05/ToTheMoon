package com.khanhpham.tothemoon.utils.gui.energygenerator;

import com.khanhpham.tothemoon.utils.containers.energycontainer.AbstractEnergyGeneratorContainer;
import com.khanhpham.tothemoon.utils.gui.BaseContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractEnergyGeneratorContainerScreen<T extends AbstractEnergyGeneratorContainer> extends BaseContainerScreen<T> {
    public AbstractEnergyGeneratorContainerScreen(T pMenu, Inventory pPlayerInventory, Component pTitle, ResourceLocation texture) {
        super(pMenu, pPlayerInventory, pTitle, texture);
    }


}
