package com.khanhpham.tothemoon.utils.containers.energycontainer;

import com.khanhpham.tothemoon.utils.containers.BaseContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractEnergyGeneratorContainer extends BaseContainer {
    private final ContainerData data;

    protected AbstractEnergyGeneratorContainer(@Nullable MenuType<?> pMenuType, Container externalContainer, Inventory playerInventory, int pContainerId, ContainerData intData) {
        super(pMenuType, externalContainer, playerInventory, pContainerId);

        super.addSlot(externalContainer, 0, 81, 33);
        super.addPlayerInventorySlots(8, 97);

        this.data = intData;

        super.addDataSlots(intData);
    }

    /**
     * @see net.minecraft.world.inventory.AbstractFurnaceMenu
     */
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if(slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            stack = stack1.copy();

            if (pIndex == 0) {
                if (!super.moveItemStackTo(stack1, 1, this.slots.size() - 1, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(stack1, stack);
            } else  {
                if (this.isFuel(stack1)) {
                    if (!super.moveItemStackTo(stack1, 0, 0, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= 1 && pIndex < super.slots.size() - 9) {
                    if (!super.moveItemStackTo(stack1, super.slots.size() - 10, super.slots.size() - 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= super.slots.size() - 10 && pIndex < super.slots.size() - 1 && !super.moveItemStackTo(stack1, 0, super.slots.size() - 10, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (!super.moveItemStackTo(stack1, 1, super.slots.size() - 1, false)) {
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

            slot.onTake(pPlayer, stack1);

        }

        return stack;
    }

    private boolean isFuel(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, null) > 0;
    }

    public int getLitProgress() {
        int i = this.data.get(1);

        if (i == 0) {
            i = 200;
        }

        return this.data.get(0) * 13 / i;
    }

    /**
     * @see net.minecraft.world.inventory.AbstractFurnaceMenu
     * @see net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
     */
    public int getEnergyProcess() {
        int i = data.get(2);
        int j = data.get(3);

        return j != 0 && i != 0 ? i * 143 / j : 0;
    }
}
