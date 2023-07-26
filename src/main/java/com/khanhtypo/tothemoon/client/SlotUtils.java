package com.khanhtypo.tothemoon.client;

import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.function.Predicate;

public class SlotUtils {
    private static final Predicate<ItemStack> YES = (itemStack) -> true;
    private static final Predicate<ItemStack> NO = itemStack -> false;

    public static Slot createPlaceFilter(Container container, int index, int x, int y, TagKey<Item> tagFilter) {
        return new FilterSlot(container, index, x, y, itemStack -> itemStack.is(tagFilter), YES);
    }

    public static Slot createPlaceFilter(IItemHandler container, int index, int x, int y, TagKey<Item> tagFilter) {
        return new FilterSlotItemHandler(container, index, x, y, itemStack -> itemStack.is(tagFilter), YES);
    }

    public static Slot createPlaceFilter(Container container, int index, int x, int y, Item condition) {
        return new FilterSlot(container, index, x, y, itemStack -> itemStack.is(condition), YES);
    }

    public static Slot createPlaceFilter(IItemHandler container, int index, int x, int y, Item condition) {
        return new FilterSlotItemHandler(container, index, x, y, itemStack -> itemStack.is(condition), YES);
    }

    public static Slot createTakeOnly(Container container, int index, int x, int y) {
        return new FilterSlot(container, index, x, y, NO, YES);
    }
}
