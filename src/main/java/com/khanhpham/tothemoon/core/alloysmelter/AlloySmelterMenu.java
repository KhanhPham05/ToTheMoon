package com.khanhpham.tothemoon.core.alloysmelter;

import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.utils.containers.BaseMenu;
import com.khanhpham.tothemoon.utils.containers.DataContainerMenuHelper;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;


public class AlloySmelterMenu extends BaseMenu {
    private final ContainerData data;

    protected AlloySmelterMenu(@Nullable MenuType<?> pMenuType, Container externalContainer, Inventory playerInventory, int pContainerId, ContainerData data) {
        super(pMenuType, externalContainer, playerInventory, pContainerId);
        this.data = data;

        addSlot(0, 45, 19);
        addSlot(1, 45, 47);
        addSlot(new FurnaceResultSlot(playerInventory.player, externalContainer, 2, 107, 33));
        addPlayerInventorySlots(8, 95);

        super.addDataSlots(data);
    }

    public AlloySmelterMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(AlloySmelterBlockEntity.MENU_SIZE), new SimpleContainerData(AlloySmelterBlockEntity.DATA_CAPACITY));
    }

    public AlloySmelterMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        this(ModMenuTypes.ALLOY_SMELTER, container, playerInventory, containerId, data);
    }

    public int getEnergyBar() {
        int i = this.data.get(2);
        int j = this.data.get(3);

        return j != 0 && i != 0 ? i * 147 / j : 0;
    }

    public int getAlloyingProcess() {
        int i = data.get(0);
        int j = data.get(1);
        return j != 0 && i != 0 ? i * 35 / j : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = empty();
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            itemstack = stack1.copy();

            if (index <= 2) {
                if (!super.moveItemStackTo(stack1, 3, slots.size() - 1, true)) {
                    return empty();
                }

                slot.onQuickCraft(stack1, itemstack);
            } else {
                if (!super.moveItemStackTo(stack1, 0, 2, false)) {
                    return empty();
                } else if (index < 30) {
                    if (!super.moveItemStackTo(stack1, 30, 39, false)) {
                        return empty();
                    }
                } else if (index < 39 && !super.moveItemStackTo(stack1, 3, 30, false)) {
                    return empty();
                }
            }

            if (stack1.isEmpty()) {
                slot.set(empty());
            } else {
                slot.setChanged();
            }

            if (stack1.getCount() == itemstack.getCount()) {
                return empty();
            }

            slot.onTake(player, stack1);
        }

        return itemstack;
    }

    private ItemStack empty() {
        return ItemStack.EMPTY;
    }
}
