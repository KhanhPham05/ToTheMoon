package com.khanhtypo.tothemoon.common.machine.powergenerator;

import com.khanhtypo.tothemoon.client.SlotUtils;
import com.khanhtypo.tothemoon.common.item.upgrades.ItemPowerGeneratorUpgrade;
import com.khanhtypo.tothemoon.common.machine.AbstractMachineMenu;
import com.khanhtypo.tothemoon.data.ModItemTags;
import com.khanhtypo.tothemoon.registration.ModMenuTypes;
import com.khanhtypo.tothemoon.utls.ModUtils;
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
        this(windowId, playerInventory, accessor, new SimpleContainer(1), new SimpleContainer(3), new SimpleContainerData(PowerGeneratorBlockEntity.DATA_SIZE));
    }

    public PowerGeneratorMenu(int windowId, Inventory playerInventory, ContainerLevelAccess accessor, Container container, Container upgradeContainer, ContainerData containerData) {
        super(ModMenuTypes.POWER_GENERATOR, windowId, playerInventory, accessor, container, upgradeContainer, containerData);
        super.addSlot(SlotUtils.createPlaceFilter(container, 0, 76, 41, burnCheck));
        super.addUpgradeSlots(179, ModItemTags.MACHINE_UPGRADES_GENERATOR);
        super.addPlayerInvSlots(8, 97);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = super.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();

            if (pIndex < super.inventorySlotIndex) {
                if (pIndex > 0) {
                    slot.onTake(pPlayer, itemStack1);
                }

                if (!super.moveItemStackTo(itemStack1, super.inventorySlotIndex, super.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (burnCheck.test(itemStack1)) {
                    if (!moveItemStackTo(itemStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemStack1.getItem() instanceof ItemPowerGeneratorUpgrade) {
                    if (!moveItemStackTo(itemStack1, 1, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= super.inventorySlotIndex && pIndex < super.hotbarSlotIndex) {
                    if (!moveItemStackTo(itemStack1, super.hotbarSlotIndex, super.slots.size(), false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= super.hotbarSlotIndex && pIndex < super.slots.size()) {
                    if (!moveItemStackTo(itemStack1, super.inventorySlotIndex, super.hotbarSlotIndex, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (itemStack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else slot.setChanged();

            slot.onTake(pPlayer, itemStack1);
        }

        return itemStack;
    }
}
