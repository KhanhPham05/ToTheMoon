package com.khanhtypo.tothemoon.common.machine;

import com.google.common.base.Preconditions;
import com.khanhtypo.tothemoon.client.SlotUtils;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BaseMenu;
import com.khanhtypo.tothemoon.data.ModItemTags;
import com.khanhtypo.tothemoon.registration.elements.MenuObject;
import com.khanhtypo.tothemoon.serverdata.recipes.BaseRecipe;
import com.khanhtypo.tothemoon.serverdata.recipes.RecipeTypeObject;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public abstract class AbstractSingleItemProcessingMachineMenu extends AbstractMachineMenu {
    public AbstractSingleItemProcessingMachineMenu(MenuObject<? extends BaseMenu> menuObject, int windowId, Inventory playerInventory, ContainerLevelAccess accessor, Container container, Container upgradeContainer, ContainerData containerData) {
        super(menuObject, windowId, playerInventory, accessor, container, upgradeContainer, containerData);
        Preconditions.checkState(container.getContainerSize() == 2, "Containers can only has 2 slots but %s was found".formatted(container.getContainerSize()));
        Preconditions.checkState(upgradeContainer.getContainerSize() == 3, "Upgrade Container can only has 3 slots but %s was found".formatted(upgradeContainer.getContainerSize()));
        super.addSlot(new Slot(container, 0, 41, 41));
        super.addSlot(SlotUtils.createTakeOnly(container, 1, 107, 41));
        super.addUpgradeSlots(175, ModItemTags.MACHINE_UPGRADES);
        super.addPlayerInvSlots(8, 95);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = super.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();

            if (pIndex < super.inventorySlotIndex) {
                if (pIndex > 1) {
                    slot.onTake(pPlayer, itemStack1);
                }

                if (!super.moveItemStackTo(itemStack1, super.inventorySlotIndex, super.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemStack1, itemStack);
            } else {
                if (super.getLevel().getRecipeManager().getRecipeFor(this.getRecipeType(), this.container, super.getLevel()).isPresent()) {
                   if (!moveItemStackTo(itemStack1, 0, 1, false)) {
                       return ItemStack.EMPTY;
                   }
                } else
                if (itemStack1.is(ModItemTags.MACHINE_UPGRADES)) {
                    if (!moveItemStackTo(itemStack1, 2, 5, false)) {
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

            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemStack1);
        }

        return itemStack;
    }

    protected abstract <A extends Recipe<Container>> RecipeType<A> getRecipeType();

}
