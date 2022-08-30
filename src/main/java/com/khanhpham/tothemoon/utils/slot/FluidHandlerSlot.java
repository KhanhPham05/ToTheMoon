package com.khanhpham.tothemoon.utils.slot;

import net.minecraft.world.Container;
import net.minecraft.world.item.BucketItem;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class FluidHandlerSlot extends SlotPlacePredicate {
    public FluidHandlerSlot(Container pContainer, int pIndex, int pX, int pY) {
        super(pContainer, pIndex, pX, pY, stack -> stack.getItem() instanceof BucketItem || stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent());
    }
}
