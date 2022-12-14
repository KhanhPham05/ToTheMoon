package com.khanhpham.tothemoon.core.blocks.machine.crusher;

import com.google.common.base.Preconditions;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class MutableContainer extends SimpleContainer {
    public MutableContainer(int pSize) {
        super(pSize);
    }

    public MutableContainer setItems(ItemStack... stacks) {
        Preconditions.checkState(stacks.length == this.getContainerSize());
        for (int i = 0; i < stacks.length; i++) {
            super.setItem(i, stacks[i]);
        }

        return this;
    }
}
