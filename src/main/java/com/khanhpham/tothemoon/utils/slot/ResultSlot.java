package com.khanhpham.tothemoon.utils.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ResultSlot extends Slot {
    public ResultSlot(Container pContainer, int pIndex, int pX, int pY) {
        super(pContainer, pIndex, pX, pY);
    }

    @Override
    public boolean mayPlace(ItemStack pStack) {
        return false;
    }
}
