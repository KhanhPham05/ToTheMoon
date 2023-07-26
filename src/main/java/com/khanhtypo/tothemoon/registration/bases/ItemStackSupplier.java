package com.khanhtypo.tothemoon.registration.bases;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public interface ItemStackSupplier extends ItemLike {
    default ItemStack asStack(int count) {
        return new ItemStack(this, Math.min(count, this.maxStack()));
    }

    default ItemStack asStack() {
        return this.asStack(1);
    }

    @SuppressWarnings("deprecation")
    default int maxStack() {
        return this.asItem().getMaxStackSize();
    }
}
