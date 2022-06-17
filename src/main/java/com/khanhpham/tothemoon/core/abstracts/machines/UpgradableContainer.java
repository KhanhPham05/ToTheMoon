package com.khanhpham.tothemoon.core.abstracts.machines;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public interface UpgradableContainer extends Container {
    NonNullList<ItemStack> getItems();


}
