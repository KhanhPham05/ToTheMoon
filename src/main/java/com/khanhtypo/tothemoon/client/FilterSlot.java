package com.khanhtypo.tothemoon.client;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class FilterSlot extends MutableSlot {
    private final Predicate<ItemStack> shouldTake;
    private final Predicate<ItemStack> shouldPlace;

    public FilterSlot(Container container, int index, int x, int y, Predicate<ItemStack> shouldPlace, Predicate<ItemStack> shouldTake) {
        super(container, index, x, y);
        this.shouldTake = shouldTake;
        this.shouldPlace = shouldPlace;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return this.shouldPlace.test(itemStack);
    }

    @Override
    public boolean mayPickup(Player player) {
        return this.shouldTake.test(super.getItem());
    }
}
