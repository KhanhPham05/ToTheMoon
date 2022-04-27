package com.khanhpham.tothemoon.core.containers.energycontainer;

import com.khanhpham.tothemoon.core.containers.BaseMenu;
import com.khanhpham.tothemoon.core.containers.DataContainerMenuHelper;
import com.khanhpham.tothemoon.utils.slot.BurnableSlot;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractEnergyGeneratorMenu extends BaseMenu implements DataContainerMenuHelper {
    public final ContainerData data;
    private final ItemStack EMPTY = ItemStack.EMPTY;

    public AbstractEnergyGeneratorMenu(@Nullable MenuType<?> pMenuType, Container externalContainer, Inventory playerInventory, int pContainerId, ContainerData intData) {
        super(pMenuType, externalContainer, playerInventory, pContainerId);

        super.addSlot(new BurnableSlot(externalContainer, 0, 80, 32));
        super.addPlayerInventorySlots(8, 97);

        this.data = intData;

        super.addDataSlots(intData);
    }

    @Override
    public ContainerData getContainerData() {
        return this.data;
    }


    /**
     * @see net.minecraft.world.inventory.AbstractFurnaceMenu
     */
    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            stack = stack1.copy();

            final int containerSize = 1;
            final int inventoryEnd = containerSize + 27;
            final int hotbarEnd = inventoryEnd + 9;

            if (index != 0) {
                if (isFuel(stack1)) {
                    if (!moveItemStackTo(stack1, 0, 1, false)) {
                        return EMPTY;
                    }
                } else if (index < inventoryEnd) {
                    if (!moveItemStackTo(stack1, inventoryEnd, hotbarEnd, false)) {
                        return EMPTY;
                    }
                } else if (index < hotbarEnd && !moveItemStackTo(stack1, containerSize, inventoryEnd, false)) {
                            return EMPTY;
                }
            } else {
                if (!moveItemStackTo(stack1, containerSize, inventoryEnd, false)) {
                    return EMPTY;
                }
            }


            if (stack1.isEmpty()) {
                slot.set(EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack1.getCount() == stack.getCount()) {
                return EMPTY;
            }

            slot.onTake(player, stack1);
        }

        return stack;
    }

    private boolean isFuel(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, null) > 0;
    }

    public int getEnergyStored() {
        return data.get(2);
    }

    public int getCapacity() {
        return data.get(3);
    }

    public int getLitProgress() {
        int i = this.data.get(1);

        if (i == 0) {
            i = 200;
        }

        return this.data.get(0) * 13 / i;
    }

    public int getEnergyBar() {
        return getEnergyBar(2, 3);
    }
}
