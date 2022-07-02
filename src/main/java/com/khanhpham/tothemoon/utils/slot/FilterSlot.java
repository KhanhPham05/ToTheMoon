package com.khanhpham.tothemoon.utils.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class FilterSlot extends Slot {
    private final Predicate<ItemStack> filter;

    public FilterSlot(Container itemHandler, int index, int xPosition, int yPosition, Predicate<ItemStack> filter) {
        super(itemHandler, index, xPosition, yPosition);
        this.filter = filter;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return filter.test(stack);
    }
}
