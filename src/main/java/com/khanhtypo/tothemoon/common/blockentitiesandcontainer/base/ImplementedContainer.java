package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ImplementedContainer extends Container {
    @Override
    default int getContainerSize() {
        return this.getContainer().getContainerSize();
    }

    @Override
    default boolean isEmpty() {
        return this.getContainer().isEmpty();
    }

    @Override
    default ItemStack getItem(int index) {
        return this.getContainer().getItem(index);
    }

    @Override
    default ItemStack removeItem(int index, int amount) {
        return this.getContainer().removeItem(index, amount);
    }

    @Override
    default ItemStack removeItemNoUpdate(int index) {
        return this.getContainer().removeItemNoUpdate(index);
    }

    @Override
    default void setItem(int index, ItemStack item) {
        this.getContainer().setItem(index, item);
    }

    @Override
    void setChanged();

    @Override
    default boolean stillValid(Player p_18946_) {
        return this.getContainer().stillValid(p_18946_);
    }

    @Override
    default void clearContent() {
        this.getContainer().clearContent();
    }

    Container getContainer();

    default void shrinkItem(int slot, int amount) {
        ItemStack stack = this.getItem(slot);
        stack.shrink(amount);
        this.setItem(slot, stack);
    }
}
