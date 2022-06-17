package com.khanhpham.tothemoon.utils.helpers;

import com.khanhpham.tothemoon.core.abstracts.machines.UpgradableContainer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class SimpleUpgradableMenu extends SimpleContainer implements UpgradableContainer {
    public SimpleUpgradableMenu(int pSize) {
        super(pSize + 4);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return super.items;
    }
}
