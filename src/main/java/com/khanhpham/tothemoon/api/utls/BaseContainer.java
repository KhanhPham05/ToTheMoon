package com.khanhpham.tothemoon.api.utls;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;

import javax.annotation.Nullable;

/**
 * @see net.minecraft.inventory.container.BeaconContainer
 */

public abstract class BaseContainer extends Container {
    protected final IWorldPosCallable access;
    protected IIntArray dataSlots;
    private boolean flag1 = true;

    protected BaseContainer(@Nullable ContainerType<?> p_i50105_1_, int p_i50105_2_, IIntArray dataSlot) {
        super(p_i50105_1_, p_i50105_2_);
        this.dataSlots = dataSlot;
        this.access = IWorldPosCallable.NULL;

        super.addDataSlots(dataSlot);
    }

    protected void addPlayerInventorySlots(PlayerInventory inventory, int startPosX, int startPosY) {
        if (flag1) {
            //player inv
            for (int i1 = 0; i1 < 3; ++i1) {
                for (int l = 0; l < 9; ++l) {
                    this.addSlot(new Slot(inventory, l + i1 * 9 + 9, startPosX + l * 18, startPosY + i1 * 18));
                }
            }
            //hot bar
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(inventory, i1, startPosX + i1 * 18, startPosY + 58));
            }

            flag1 = false;
        } else {
            throw new IllegalArgumentException("You have already created player inventory");
        }
    }

    @Override
    public boolean stillValid(PlayerEntity playerEntity) {
        return Container.stillValid(access, playerEntity, getBlockForContainer());
    }

    public int getCurrentEnergy(int dataIndex) {
        return dataSlots.get(dataIndex);
    }

    public int getMaxEnergy(int dataIndex) {
        return dataSlots.get(dataIndex);
    }

    protected abstract Block getBlockForContainer();

    public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_82846_2_);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_82846_2_ == 0) {
                if (!this.moveItemStackTo(itemstack1, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(p_82846_1_, itemstack1);
        }

        return itemstack;
    }
}
