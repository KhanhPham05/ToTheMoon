package com.khanhpham.tothemoon.utils.slot;

import com.khanhpham.tothemoon.utils.ModTags;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MetalPressSlot extends Slot {
    public MetalPressSlot(Container pContainer, int pIndex, int pX, int pY) {
        super(pContainer, pIndex, pX, pY);
    }

    @Override
    public boolean mayPlace(ItemStack pStack) {
        return pStack.is(ModTags.TAG_PRESS_MOLDS);
    }
}
