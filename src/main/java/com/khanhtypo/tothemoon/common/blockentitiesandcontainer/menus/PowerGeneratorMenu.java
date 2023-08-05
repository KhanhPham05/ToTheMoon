package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.menus;

import com.khanhtypo.tothemoon.ModUtils;
import com.khanhtypo.tothemoon.client.SlotUtils;
import com.khanhtypo.tothemoon.registration.ModMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class PowerGeneratorMenu extends AbstractMachineMenu {
    private static final Predicate<ItemStack> burnCheck = ModUtils::canBurn;

    public PowerGeneratorMenu(int windowId, Inventory playerInventory, ContainerLevelAccess accessor) {
        this(windowId, playerInventory, accessor, new SimpleContainer(1), new SimpleContainerData(5));
    }

    public PowerGeneratorMenu(int windowId, Inventory playerInventory, ContainerLevelAccess accessor, Container container, ContainerData containerData) {
        super(ModMenus.POWER_GENERATOR, windowId, playerInventory, accessor, container, containerData);
        super.addSlot(SlotUtils.createPlaceFilter(container, 0, 80, 32, burnCheck));
        super.addPlayerInv(8, 97);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = super.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();
            if (pIndex == 0) {
                if (!super.moveItemStackTo(itemStack1, 1, super.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!super.moveItemStackTo(itemStack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            }


            if (itemStack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else slot.setChanged();

            slot.onTake(pPlayer, itemStack1);
        }

        return itemStack;
    }
}
