package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import com.khanhtypo.tothemoon.registration.elements.MenuObject;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class BaseMenu extends AbstractContainerMenu {
    protected final Inventory playerInventory;
    protected final ContainerLevelAccess accessor;
    private final MenuObject<?> menuObject;
    @Nullable private Block targetedBlock;
    private int invLabelX;
    private int invLabelY;

    protected BaseMenu(MenuObject<?> menuObject, int windowId, Inventory playerInventory, ContainerLevelAccess accessor) {
        this(menuObject, windowId, playerInventory, accessor, accessor.evaluate((level, blockPos) -> level.getBlockState(blockPos).getBlock(), null));
    }

    protected BaseMenu(MenuObject<?> menuObject, int windowId, Inventory playerInventory, ContainerLevelAccess accessor, @Nullable Block targetedBlock) {
        super(menuObject.get(), windowId);
        this.menuObject = menuObject;
        this.playerInventory = playerInventory;
        this.accessor = accessor;
        this.targetedBlock = targetedBlock;
    }

    public BaseMenu setTargetedBlock(Block targetedBlock) {
        this.targetedBlock = targetedBlock;
        return this;
    }

    @Override
    public boolean stillValid(@Nonnull Player p_38874_) {
        return this.targetedBlock == null || stillValid(accessor, p_38874_, this.targetedBlock);
    }

    public int getInvLabelX() {
        return invLabelX;
    }

    public int getInvLabelY() {
        return invLabelY;
    }

    protected void addPlayerInv(int startX, int startY) {
        this.invLabelX = startX;
        this.invLabelY = startY;
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j < 9; j++) {
                super.addSlot(new Slot(this.playerInventory, j + i * 9 + 9, startX + j * 18, startY + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            super.addSlot(new Slot(this.playerInventory, i, startX + i * 18, startY + 58));
        }
    }

    public ResourceLocation getGuiPath() {
        return this.menuObject.getGuiPath();
    }

    public Level getLevel() {
        return this.player().level();
    }

    public Player player() {
        return this.playerInventory.player;
    }
}
