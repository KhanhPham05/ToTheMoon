package com.khanhpham.ttm.core.containers.bases;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public abstract class BaseMenuContainer extends AbstractContainerMenu {
    private static final ContainerLevelAccess access = ContainerLevelAccess.NULL;
    protected final Inventory inventory;
    protected final ContainerData dataSlots;

    public BaseMenuContainer(@Nullable MenuType<?> pMenuType, int pContainerId, Inventory inventory, ContainerData dataSlot) {
        super(pMenuType, pContainerId);
        this.inventory = inventory;

        this.dataSlots = dataSlot;

        addDataSlots(dataSlot);
    }

    protected int getDataSlot(int index) {
        return dataSlots.get(index);
    }

    protected void addPlayerSlots(Inventory inventory, int startX, int startY) {
        Slot slot;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                int in = j + i * 9 + 9;
                slot = new Slot(inventory, in, startX + j * 18, startY + i * 18);
                System.out.print(in + "  ");

                addSlot(slot);
            }

            System.out.println();
        }

        for (int k = 0; k < 9; ++k) {
            slot = new Slot(inventory, k, startX + k * 18, startY + 58);
            this.addSlot(slot);
            System.out.print('0' + k + "  ");
        }
        System.out.println();
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(access, pPlayer, getBlockForContainer());
    }

    protected abstract Block getBlockForContainer();
}
