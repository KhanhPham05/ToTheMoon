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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import java.util.Optional;
import java.util.function.Predicate;

public class FluidTankMenu extends BaseMenu {

    private final Predicate<ItemStack> inputTest;
    private final Predicate<ItemStack> extractTest;

    public FluidTankMenu(int windowId, Inventory playerInventory, ContainerLevelAccess accessor) {
        this(windowId, playerInventory, accessor, new SimpleContainer(FluidTankBlockEntity.CONTAINER_SIZE), new SimpleContainerData(FluidTankBlockEntity.DATA_SIZE));
    }

    public FluidTankMenu(int windowId, Inventory playerInventory, ContainerLevelAccess accessor, Container tankContainer, ContainerData containerData) {
        super(ModMenuTypes.FLUID_TANK, windowId, playerInventory, accessor);
        this.inputTest = (i) -> FluidTankMenu.testInputSlot(i).isPresent();
        this.extractTest = (i) -> FluidTankMenu.testExtractSlot(i).isPresent();
        super.addSlot(SlotUtils.createPlaceFilter(tankContainer, 0, 39, 35, this.inputTest));
        super.addSlot(SlotUtils.createPlaceFilter(tankContainer, 1, 39, 60, this.extractTest));
        super.addPlayerInvSlots(8, 104);
        super.addDataSlots(containerData);
        super.addContainerListeners(tankContainer);
    }

    static Optional<IFluidHandlerItem> testExtractSlot(ItemStack itemStack) {
        return itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(f -> f);
    }

    static Optional<IFluidHandlerItem> testInputSlot(ItemStack toPlace) {
        return toPlace.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM)
                .filter(fluidHandlerItem -> !fluidHandlerItem.getFluidInTank(0).isEmpty());
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
            ItemStack itemStack1 = slot.getItem();
            itemstack = itemStack1.copy();

            if (pIndex >= super.inventorySlotIndex) {
                if (this.inputTest.test(itemStack1)) {
                    if (!super.moveItemStackTo(itemStack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.extractTest.test(itemStack1)) {
                    if (!super.moveItemStackTo(itemStack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= super.hotbarSlotIndex) {
                    if (!super.moveItemStackTo(itemStack1, super.inventorySlotIndex, super.hotbarSlotIndex, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!super.moveItemStackTo(itemStack1, super.hotbarSlotIndex, menuSlotsSize, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!super.moveItemStackTo(itemStack1, this.inventorySlotIndex, menuSlotsSize, true)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemStack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemStack1);
        }

        return itemstack;
    }
}
