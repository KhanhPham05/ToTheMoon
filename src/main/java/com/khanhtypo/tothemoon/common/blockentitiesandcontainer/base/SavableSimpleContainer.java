package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import com.khanhtypo.tothemoon.ToTheMoon;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.*;

public class SavableSimpleContainer implements ImplementedContainer, INBTSerializable<CompoundTag> {
    @Nullable
    private final BlockEntity blockEntity;
    private final int size;
    private final NonNullList<ItemStack> items;
    public final Set<ServerPlayer> openingPlayers = new TreeSet<>(Comparator.comparing(ServerPlayer::getStringUUID));

    public SavableSimpleContainer(@Nullable BlockEntity blockEntity, int size) {
        this.blockEntity = blockEntity;
        this.size = size;
        this.items = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    @Override
    public void startOpen(Player pPlayer) {
        if (blockEntity != null && !blockEntity.isRemoved() && pPlayer instanceof ServerPlayer serverPlayer) {
            openingPlayers.add(serverPlayer);
        }
    }

    @Override
    public void stopOpen(Player pPlayer) {
        if (blockEntity != null && !blockEntity.isRemoved() && pPlayer instanceof ServerPlayer serverPlayer) {
            openingPlayers.remove(serverPlayer);
        }
    }

    public CompoundTag saveContainer(String tagName, CompoundTag writer) {
        var savedContainer = ContainerHelper.saveAllItems(new CompoundTag(), this.items);
        writer.put(tagName, savedContainer);
        this.setChanged();
        return writer;
    }

    public CompoundTag saveContainer(CompoundTag writer) {
        return saveContainer("BlockEntityContainer", writer);
    }

    public void loadContainer(String tagName, CompoundTag reader) {
        if (reader.contains(tagName)) {
            CompoundTag containerTag = reader.getCompound(tagName);
            ContainerHelper.loadAllItems(containerTag, this.items);
            for (int i = 0; i < this.getContainerSize(); i++) {
                if (this.isSlotPresent(i)) {
                    this.onItemPlaced(i, this.getItem(i));
                }
            }
            this.setChanged();
            return;
        }

        ToTheMoon.LOGGER.info("Couldn't load container : {}. it is not present in compound tag", tagName);
    }

    public void onItemPlaced(int pSlot, ItemStack placedItem) {
    }

    public void loadContainer(CompoundTag reader) {
        this.loadContainer("BlockEntityContainer", reader);
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
        ItemStack removed = ContainerHelper.removeItem(this.items, pSlot, pAmount);

        if (!removed.isEmpty()) {
            this.onItemTaken(pSlot, removed);
            this.setChanged();
        }

        return removed;
    }


    public void onItemTaken(int slot, ItemStack removedStack) {
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return ContainerHelper.takeItem(this.items, pSlot);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        if (!pStack.isEmpty()) {
            this.items.set(pSlot, pStack);
            this.onItemPlaced(pSlot, pStack);
            this.setChanged();
        }
    }

    @Override
    public void setChanged() {
        if (this.blockEntity != null) this.blockEntity.setChanged();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return this.blockEntity == null || Container.stillValidBlockEntity(this.blockEntity, pPlayer);
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < this.getContainerSize(); i++) {
            if (!this.isSlotPresent(i)) {
                this.onItemTaken(i, this.getItem(i));
            }
        }
        this.items.clear();
        this.setChanged();
    }

    @Override
    public Container getContainer() {
        return this;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.saveContainer(new CompoundTag());
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.saveContainer(nbt);
    }

    public int getSlotSpace(int slot) {
        ItemStack item = this.items.get(slot);
        return Math.max(0, item.getMaxStackSize() - item.getCount());
    }

    public boolean isSlotEmpty(int slot) {
        return this.getItem(slot).isEmpty();
    }

    public boolean isSlotAvailable(int slot) {
        return this.isSlotEmpty(slot) || this.getSlotSpace(slot) > 0;
    }

}
