package com.khanhpham.tothemoon.core.blocks.machines.energysmelter;

import com.khanhpham.tothemoon.core.menus.BaseMenu;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.utils.slot.ResultSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EnergySmelterMenu extends BaseMenu {
    private final ContainerData data;

    public EnergySmelterMenu(Container externalContainer, Inventory playerInventory, int pContainerId, ContainerData data) {
        super(ModMenuTypes.ENERGY_SMELTER, externalContainer, playerInventory, pContainerId);

        super.addSlot(0, 41, 33);
        super.addSlot(new ResultSlot(externalContainer, 1, 106, 32));
        super.addPlayerInventorySlots(8, 95);

        this.data = data;
        addDataSlots(data);
    }

    public EnergySmelterMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        this(container, playerInventory, containerId, data);
    }

    public int getEnergyBar() {
        int i = data.get(2);
        int j = data.get(3);

        return j != 0 && i != 0 ? i * 147 / j : 0 ;
    }

    public int getSmeltingProcess() {
         int i = this.data.get(0);
         int j = this.data.get(1);

         return j != 0 && i != 0 ? i * 32 / j : 0;
    }

    public EnergySmelterMenu(int i, Inventory inventory) {
        this(i, inventory, new SimpleContainer(2), new SimpleContainerData(4));
    }

    @NotNull
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = empty();
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            itemStack = stack1.copy();

            if (index <= 1) {
                if (!super.moveItemStackTo(stack1, 2, slots.size() - 1, true)) {
                    return empty();
                }

                slot.onQuickCraft(stack1, itemStack);
            } else {
                if (!super.moveItemStackTo(stack1, 0, 1, false)) {
                    return empty();
                } else if (index < 29) {
                    if (!super.moveItemStackTo(stack1, 29, 38, false)) {
                        return empty();
                    } else if (!super.moveItemStackTo(stack1, 2, 29, false)) {
                        return empty();
                    }
                }
            }

            if (stack1.isEmpty()) {
                slot.set(empty());
            } else {
                slot.setChanged();
            }

            if (stack1.getCount() == itemStack.getCount()) {
                return empty();
            }

            slot.onTake(player, stack1);
        }

        return itemStack;
    }
}
