package com.khanhpham.ttm.core.containers.bases;

import com.khanhpham.ttm.utils.slots.BurningFuelSlot;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public abstract class BaseGeneratorMenuContainer extends BaseMenuContainer {
    protected final int endInvIndex = 27;
    protected final int endHotBarIndex = 36;
    protected final int inventorySize = 1;

    public BaseGeneratorMenuContainer(@Nullable MenuType<?> pMenuType, int pContainerId, Inventory inventory, Container container, ContainerData dataSlot) {
        super(pMenuType, pContainerId, inventory, dataSlot);

        //0
        addSlot(new BurningFuelSlot(container, 0, 80, 32));

        //1 - 27 inv
        //28 - 36 hot
        super.addPlayerSlots(inventory, 8, 97);
    }

    /**
     * @see net.minecraft.world.inventory.AbstractFurnaceMenu
     * @see net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
     */
    public int getEnergyBar() {
        int energy = getDataSlot(2);
        int capacity = getDataSlot(3);

        return energy != 0 && capacity!= 0 ? energy * 147 / capacity : 0;
    }

    public int getLitProgress() {
        int duration = getDataSlot(1);
        if (duration == 0) {
            duration = 200;
        }

        return getDataSlot(0) * 13 / duration;
    }

    @Override
    @Nonnull
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            itemstack = stack1.copy();

            if (pIndex > 0) {
                if (canSmelt(stack1)) {
                    if (!moveItemStackTo(stack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex < endInvIndex) {
                    if (moveItemStackTo(stack1, endInvIndex, endHotBarIndex, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex < endHotBarIndex && moveItemStackTo(stack1, 1, endInvIndex, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (moveItemStackTo(stack1, inventorySize, endHotBarIndex, false)) {
                return ItemStack.EMPTY;
            }

            if (stack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, stack1);
        }

        return itemstack;
    }

    protected boolean canSmelt(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, null) > 0;
    }
}
