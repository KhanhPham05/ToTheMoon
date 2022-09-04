package com.khanhpham.tothemoon.core.menus;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public abstract class BaseMenu extends AbstractContainerMenu {
    protected final Inventory playerInventory;
    protected final Container externalContainer;
    protected final ContainerLevelAccess access;
    public int playerInventorySlotStartsX;
    public int playerInventorySlotStartsY;
    protected int inventoryIndex;
    protected int hotbarIndex;
    protected int containerEnds;

    public BaseMenu(@Nullable MenuType<?> pMenuType, int pContainerId, Inventory playerInventory, Container externalContainer) {
        super(pMenuType, pContainerId);
        this.playerInventory = playerInventory;
        this.externalContainer = externalContainer;
        this.access = ContainerLevelAccess.NULL;
    }

    @SuppressWarnings("unchecked")
    protected static <T extends BlockEntity> T getBlockEntity(Level level, FriendlyByteBuf buffer) {
        return (T) level.getBlockEntity(buffer.readBlockPos());
    }

    public Container getExternalContainer() {
        return externalContainer;
    }

    protected ItemStack empty() {
        return ItemStack.EMPTY;
    }

    protected void addPlayerInventorySlots(int beginX, int beginY) {
        int i, j;

        this.inventoryIndex = slots.size() - 1;
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 9; j++) {
                this.addSlot(playerInventory, j + i * 9 + 9, beginX + j * 18, beginY + i * 18);
            }
        }

        this.hotbarIndex = slots.size() - 1;
        for (i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, beginX + i * 18, beginY + 58));
        }

        this.playerInventorySlotStartsX = beginX;
        this.playerInventorySlotStartsY = beginY;
        this.containerEnds = slots.size() - 1;
    }

    protected void addSlot(Container container, int index, int x, int y) {
        super.addSlot(new Slot(container, index, x, y));
    }

    protected void addSlot(int index, int x, int y) {
        super.addSlot(new Slot(this.externalContainer, index, x, y));
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return !(pPlayer instanceof FakePlayer);
    }

    @Override
    @Nonnull
    public abstract ItemStack quickMoveStack(Player player, int index);
}
