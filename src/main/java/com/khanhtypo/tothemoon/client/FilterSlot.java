package com.khanhtypo.tothemoon.client;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class FilterSlot extends Slot {
    private final Predicate<ItemStack> shouldTake;
    private final Predicate<ItemStack> shouldPlace;

    FilterSlot(Container container, int index, int x, int y, Predicate<ItemStack> shouldPlace, Predicate<ItemStack> shouldTake) {
        super(container, index, x, y);
        this.shouldTake = shouldTake;
        this.shouldPlace = shouldPlace;
    }

    @Override
    public boolean mayPlace(ItemStack p_40231_) {
        return this.shouldPlace.test(p_40231_);
    }

    @Override
    public boolean mayPickup(Player p_40228_) {
        return this.shouldTake.test(super.getItem());
    }

    protected Container getContainer() {
        return super.container;
    }
}
