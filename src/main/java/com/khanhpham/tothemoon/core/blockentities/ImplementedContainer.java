package com.khanhpham.tothemoon.core.blockentities;

import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public interface ImplementedContainer<T extends AbstractContainerMenu> extends Container, MenuProvider, Nameable {
    NonNullList<ItemStack> getItems();

    @Override
    default int getContainerSize() {
        return getItems().size();
    }

    @Override
    default boolean isEmpty() {
        return getItems().isEmpty();
    }

    @Override
    default ItemStack getItem(int pIndex) {
        return getItems().get(pIndex);
    }

    @Override
    default ItemStack removeItem(int pIndex, int pCount) {
        return ContainerHelper.removeItem(getItems(), pIndex, pCount);
    }

    @Override
    default ItemStack removeItemNoUpdate(int pIndex) {
        return ContainerHelper.takeItem(getItems(), pIndex);
    }

    @Override
    default void setItem(int pIndex, ItemStack pStack) {
        getItems().set(pIndex, pStack);
    }

    @Override
    void setChanged();

    @Override
    default boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    default void clearContent() {
        this.getItems().clear();
    }

    @Nonnull
    @Override
    T createMenu(int pContainerId, Inventory pInventory, Player pPlayer);

    Component getContainerDisplayName();

    @Override
    default Component getName() {
        return getContainerDisplayName();
    }

    @Override
    default Component getDisplayName() {
        return getContainerDisplayName();
    }
}
