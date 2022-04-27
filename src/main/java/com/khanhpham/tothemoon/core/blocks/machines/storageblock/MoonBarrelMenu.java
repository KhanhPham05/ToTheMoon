package com.khanhpham.tothemoon.core.blocks.machines.storageblock;

import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.core.containers.BaseMenu;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @see net.minecraft.world.level.block.entity.ChestBlockEntity
 * @see net.minecraft.world.inventory.ChestMenu
 */
public class MoonBarrelMenu extends BaseMenu {
    public static final int CAPACITY = 9 * 4;
    private final int containerSize;

    protected MoonBarrelMenu(@Nullable MenuType<?> pMenuType, Container externalContainer, Inventory playerInventor, int pContainerId) {
        super(pMenuType, externalContainer, playerInventor, pContainerId);
        this.containerSize = externalContainer.getContainerSize();
        int a = 0;

        for (int i = 0 ; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(externalContainer, a, 8 + j * 18, 18 + i * 18);
                a++;
            }
            System.out.println();
        }

        super.addPlayerInventorySlots(8, 104);

    }

    public MoonBarrelMenu(int containerId, Container externalContainer, Inventory playerInventory) {
        this(ModMenuTypes.STORAGE_BLOCK, externalContainer, playerInventory, containerId);
    }

    public MoonBarrelMenu(int containerId, Inventory playerContainer) {
        this(ModMenuTypes.STORAGE_BLOCK, new SimpleContainer(CAPACITY), playerContainer, containerId);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int pIndex) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = super.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            stack = stack1.copy();

            if (pIndex < containerSize) {
                if (!super.moveItemStackTo(stack1, this.containerSize, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!super.moveItemStackTo(stack1, 0, this.containerSize, false)) {
                return ItemStack.EMPTY;
            }

            if (stack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        slot.setChanged();

        return stack;
    }
}
