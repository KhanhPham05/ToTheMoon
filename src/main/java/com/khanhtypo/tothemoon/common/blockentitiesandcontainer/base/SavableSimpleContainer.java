package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SavableSimpleContainer implements Container {
    private final BlockEntity blockEntity;
    private final int size;
    private final NonNullList<ItemStack> items;

    public SavableSimpleContainer(BlockEntity blockEntity, int size) {
        this.blockEntity = blockEntity;
        this.size = size;
        this.items = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public void saveContainer(CompoundTag writer) {
        ContainerHelper.saveAllItems(writer, this.items);
        this.setChanged();
    }

    public void loadContainer(CompoundTag reader) {
        ContainerHelper.loadAllItems(reader, this.items);
        this.setChanged();
    }

    @Override
    public int getContainerSize() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack item : this.items) {
            if (!item.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return this.items.get(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return ContainerHelper.removeItem(this.items, pSlot, pAmount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return ContainerHelper.takeItem(this.items, pSlot);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        this.items.set(pSlot, pStack);
        if (!pStack.isEmpty() && pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    @Override
    public void setChanged() {
        this.blockEntity.setChanged();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this.blockEntity, pPlayer);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }
}
