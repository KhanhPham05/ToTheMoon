package com.khanhpham.tothemoon.utils.slot;

import net.minecraft.world.Container;
import net.minecraft.world.item.BucketItem;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class FluidHandlerSlot extends SlotPlacePredicate {
    public FluidHandlerSlot(Container pContainer, int pIndex, int pX, int pY) {
        super(pContainer, pIndex, pX, pY, stack -> stack.getItem() instanceof BucketItem || stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent());
    }
}
