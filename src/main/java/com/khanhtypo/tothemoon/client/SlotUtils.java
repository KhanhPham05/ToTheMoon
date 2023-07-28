package com.khanhtypo.tothemoon.client;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.AlwaysSavedResultContainer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class SlotUtils {
    static final Predicate<ItemStack> YES = (itemStack) -> true;
    static final Predicate<ItemStack> NO = itemStack -> false;

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

    public static Slot createTakeOnly(AlwaysSavedResultContainer container, Container craftingContainer, int index, int x, int y, BiConsumer<Player, ItemStack> onResultSlotTaken) {
        return new RecipeResultSlot(container, craftingContainer, index, x, y, onResultSlotTaken);
    }
}
