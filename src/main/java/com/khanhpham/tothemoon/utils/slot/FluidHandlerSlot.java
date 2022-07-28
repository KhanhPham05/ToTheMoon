package com.khanhpham.tothemoon.utils.slot;

import net.minecraft.world.Container;
import net.minecraft.world.item.Items;

public class FluidHandlerSlot extends SlotPlacePredicate {
    public FluidHandlerSlot(Container pContainer, int pIndex, int pX, int pY) {
        super(pContainer, pIndex, pX, pY, stack -> stack.is(Items.LAVA_BUCKET));
    }
}
