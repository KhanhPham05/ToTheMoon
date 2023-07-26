package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import com.khanhtypo.tothemoon.registration.elements.MenuObject;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Open screen via {@link net.minecraftforge.network.NetworkHooks#openScreen(ServerPlayer, MenuProvider, BlockPos)}
 */
public abstract class BasicMenu extends AbstractContainerMenu {
    protected final Inventory playerInventory;
    protected final ContainerLevelAccess accessor;
    private final MenuObject<?> menuObject;
    private int invLabelX;
    private int invLabelY;

    protected BasicMenu(MenuObject<?> menuObject, int windowId, Inventory playerInventory, @Nullable FriendlyByteBuf extraData) {
        super(menuObject.get(), windowId);
        this.menuObject = menuObject;
        this.playerInventory = playerInventory;
        this.accessor = extraData == null ? ContainerLevelAccess.NULL : ContainerLevelAccess.create(playerInventory.player.level(), getPos(extraData));
        //BlockPos containerBlockPos = getPos(extraData);
        //Level level = playerInventory.player.level();
        //this.accessor = ContainerLevelAccess.create(level, containerBlockPos);
    }

    private static BlockPos getPos(@Nullable FriendlyByteBuf extraData) {
        return Objects.requireNonNull(
                Objects.requireNonNull(extraData, "Can not retrieve BlockPos data because buffer is null")
                        .readBlockPos(), "Can not retrieve BlockPos data because returned BlockPos is null");
    }

    @Override
    public boolean stillValid(@Nonnull Player p_38874_) {
        return stillValid(accessor, p_38874_, this.menuObject.getTargetedBlock());
    }

    protected InvWrapper simpleHandler(int size) {
        return new InvWrapper(new SimpleContainer(size));
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
}
