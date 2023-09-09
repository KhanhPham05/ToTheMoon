package com.khanhtypo.tothemoon.common.tank;

import com.google.common.base.Preconditions;
import com.khanhtypo.tothemoon.client.SlotUtils;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BaseMenu;
import com.khanhtypo.tothemoon.registration.ModMenuTypes;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.function.BiPredicate;

public class FluidTankMenu extends BaseMenu {
    private final Container tankContainer;

    public FluidTankMenu(int windowId, Inventory playerInventory, ContainerLevelAccess accessor) {
        this(windowId, playerInventory, accessor, new SimpleContainer(FluidTankBlockEntity.CONTAINER_SIZE), new SimpleContainerData(FluidTankBlockEntity.DATA_SIZE));
    }

    public FluidTankMenu(int windowId, Inventory playerInventory, ContainerLevelAccess accessor, Container tankContainer, ContainerData containerData) {
        super(ModMenuTypes.FLUID_TANK, windowId, playerInventory, accessor);
        this.tankContainer = tankContainer;
        super.addSlot(SlotUtils.createPlaceFilter(tankContainer, 0, 39, 35, (stack) -> testInputSlot(tankContainer, stack)));
        super.addSlot(SlotUtils.createTakeOnly(tankContainer, 1, 39, 60));
        super.addPlayerInvSlots(8, 104);
        super.addDataSlots(containerData);
        super.addContainerListeners(tankContainer);
    }

    protected static boolean testInputSlot(Container tankContainer, ItemStack toPlace) {
        if (toPlace.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
            ItemStack output = tankContainer.getItem(1);
            if (output.isEmpty()) {
                return true;
            } else {
                ItemStack toCompare = toPlace.hasCraftingRemainingItem() ? toPlace.getCraftingRemainingItem() : toPlace;
                if (ItemStack.isSameItem(toCompare, output)) {
                    return toPlace.getCount() + output.getCount() <= output.getMaxStackSize();
                }
            }
        }
        return false;
    }

    boolean hasFluid() {
        return super.getData(0) > 0 && ModRegistries.getId(Registries.FLUID, Fluids.EMPTY) != super.getData(2);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        int menuSlotsSize = super.slots.size();
        Preconditions.checkState(pIndex >= 0 && pIndex < menuSlotsSize, String.format("Unknown slot %s, it must be between 0 to %s", pIndex, menuSlotsSize));
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (pIndex >= super.inventorySlotIndex) {
                if (testInputSlot(this.tankContainer, itemstack1)) {
                    if (!super.moveItemStackTo(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= super.hotbarSlotIndex) {
                    if (!super.moveItemStackTo(itemstack1, super.inventorySlotIndex, super.hotbarSlotIndex, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!super.moveItemStackTo(itemstack1, super.hotbarSlotIndex, menuSlotsSize, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!super.moveItemStackTo(itemstack1, this.inventorySlotIndex, menuSlotsSize, true)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
        }

        return itemstack;
    }
}
