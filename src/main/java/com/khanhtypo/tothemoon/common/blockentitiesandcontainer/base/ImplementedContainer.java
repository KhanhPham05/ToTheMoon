package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

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

    default boolean isSlotSame(int slot, TagKey<Item> itemTag) {
        return this.getItem(slot).is(itemTag);
    }

    default boolean isSlotSame(int slot, Item item) {
        return this.getItem(slot).is(item);
    }

    default boolean isSlotSame(int slot, ItemStack other, boolean checkTag) {
        return checkTag ? ItemStack.isSameItemSameTags(this.getItem(slot), other) : ItemStack.isSameItem(this.getItem(slot), other);
    }

    default boolean isSlotEmpty(int slot) {
        return this.getItem(slot).isEmpty();
    }

    default boolean isSlotPresent(ItemStack itemStack) {
        return !itemStack.isEmpty();
    }

    default boolean isSlotPresent(int index) {
        return this.isSlotPresent(this.getItem(index));
    }


    default int getBurnTime(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, null);
    }

    default int getBurnTime(int slot) {
        return this.getBurnTime(this.getItem(slot));
    }

    default boolean canBurn(ItemStack stack) {
        return this.getBurnTime(stack) > 0;
    }

    default boolean canBurn(int slot) {
        return this.canBurn(this.getItem(slot));
    }
}
