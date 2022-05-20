package com.khanhpham.tothemoon.core.items;

import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class HammerItem extends HandheldItem {

    private static final Random ran = new Random();

    public HammerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack copy = itemStack.copy();
        if (copy.hurt(1, ran, null)) {
            return ItemStack.EMPTY;
        }

        return copy;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }
}
