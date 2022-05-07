package com.khanhpham.tothemoon.core.blocks.machines.metalpress;

import com.khanhpham.tothemoon.core.blockentities.others.MetalPressBlockEntity;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.datagen.tags.ModItemTags;
import com.khanhpham.tothemoon.core.containers.BaseMenu;
import com.khanhpham.tothemoon.utils.slot.MetalPressSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class MetalPressMenu extends BaseMenu {
    public final ContainerData data;

    protected MetalPressMenu(@Nonnull MenuType<?> pMenuType, Container externalContainer, Inventory playerInventory, int pContainerId, ContainerData data) {
        super(pMenuType, externalContainer, playerInventory, pContainerId);

        super.addSlot(externalContainer, 0, 45, 19);
        super.addSlot(new MetalPressSlot(externalContainer, 1, 45, 47));
        super.addSlot(new FurnaceResultSlot(playerInventory.player, externalContainer, 2, 107, 33));

        super.addPlayerInventorySlots(8, 95);

        this.data = data;
        super.addDataSlots(data);

    }

    public MetalPressMenu(int containerId, Inventory playerInventory) {
        this(ModMenuTypes.METAL_PRESS, new SimpleContainer(MetalPressBlockEntity.MENU_SIZE), playerInventory, containerId, new SimpleContainerData(MetalPressBlockEntity.DATA_SIZE));
    }

    public MetalPressMenu(Container externalContainer, Inventory playerInventory, int containerId, ContainerData data) {
        this(ModMenuTypes.METAL_PRESS, externalContainer, playerInventory, containerId, data);
    }

    public int getPressingProcess() {
        int i = this.data.get(0);
        int j = this.data.get(1);

        return j != 0 && i!= 0 ? i * 22 / j : 0;
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = super.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            stack = stack1.copy();

            //Shift-click from output slot (index = 2)
            if (index == 2) {
                if (!super.moveItemStackTo(stack1, 3, super.slots.size() - 1, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(stack1, stack);
            }
            //Shift-click from inventory slots
            else if (index >= 3) {
                //check if the slot is a metal press mold, if yes move it to the press slot, otherwise move it to the ingredient slot
                if (this.isPress(stack1)) {
                    if (!super.moveItemStackTo(stack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!super.moveItemStackTo(stack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                } else if (index < 30) {
                    if (!super.moveItemStackTo(stack1, 30, super.slots.size() - 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 39 && !super.moveItemStackTo(stack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!super.moveItemStackTo(stack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (stack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack1.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack1);
        }

        return stack;
    }

    private boolean isPress(ItemStack stack) {
        return !stack.isEmpty() && stack.is(ModItemTags.GENERAL_PRESS_MOLDS.getMainTag());
    }
}
