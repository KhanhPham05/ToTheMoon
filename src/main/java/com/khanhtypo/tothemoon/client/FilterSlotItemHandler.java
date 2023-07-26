package com.khanhtypo.tothemoon.client;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class FilterSlotItemHandler extends SlotItemHandler {
    private final Predicate<ItemStack> shouldTake;
    private final Predicate<ItemStack> shouldPlace;

    FilterSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition, Predicate<ItemStack> shouldPlace, Predicate<ItemStack> shouldTake) {
        super(itemHandler, index, xPosition, yPosition);
        this.shouldTake = shouldTake;
        this.shouldPlace = shouldPlace;
    }

    @Override
    public boolean mayPickup(Player playerIn) {
        return shouldTake.test(super.getItem()) && super.mayPickup(playerIn);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return this.shouldPlace.test(stack) && super.mayPlace(stack);
    }
}
