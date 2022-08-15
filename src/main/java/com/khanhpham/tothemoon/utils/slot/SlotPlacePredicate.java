package com.khanhpham.tothemoon.utils.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class SlotPlacePredicate extends Slot {
    private final Predicate<ItemStack> filter;

    public SlotPlacePredicate(Container itemHandler, int index, int xPosition, int yPosition, Predicate<ItemStack> filter) {
        super(itemHandler, index, xPosition, yPosition);
        this.filter = filter;
    }

    public SlotPlacePredicate(Container pContainer, int pIndex, int pX, int pY) {
        this(pContainer, pIndex, pX, pY, stack -> true);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return filter.test(stack);
    }

    @Override
    public boolean mayPickup(Player pPlayer) {
        return true;
    }
}
