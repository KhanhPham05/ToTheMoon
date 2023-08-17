package com.khanhtypo.tothemoon.multiblock.blackstonefurnace;

import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.display.BlackStoneFurnaceMenu;
import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import it.zerono.mods.zerocore.lib.block.multiblock.IMultiblockPartTypeProvider;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ControllerPartEntity extends BasicBlackStoneFurnacePartEntity implements IMultiblockPartTypeProvider<MultiblockBlackStoneFurnace, BlackStoneFurnaceFurnacePartTypes>, IMultiblockPart<MultiblockBlackStoneFurnace>, MenuProvider {
    public ControllerPartEntity(BlockPos position, BlockState blockState) {
        super(ModBlockEntities.BLACK_STONE_FURNACE_CONTROLLER_PART.get(), position, blockState);
    }

    @Override
    public boolean canOpenGui(Level world, BlockPos position, BlockState state) {
        return super.isMachineAssembled();
    }

    @Override
    public boolean isGoodForPosition(it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition partPosition, IMultiblockValidator iMultiblockValidator) {
        if (partPosition.isFace()) {
            if (partPosition.getDirection().map(Direction.Plane.HORIZONTAL::test).orElse(false)) {
                return true;
            } else {
                iMultiblockValidator.setLastError(super.getWorldPosition(), ModLanguageGenerator.CONTROLLER_FACE_ERROR);
                return false;
            }
        }

        iMultiblockValidator.setLastError(super.getWorldPosition(), ModLanguageGenerator.CONTROLLER_FRAME_IS_ERROR);
        return false;
    }

    @Override
    public Component getDisplayName() {
        return ModLanguageGenerator.TITLE_BLACK_STONE_FURNACE;
    }

    @Nullable
    @Override
    @SuppressWarnings({"OptionalGetWithoutIsPresent"})
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        var multiblock = super.getMultiblockController().get();
        return new BlackStoneFurnaceMenu(pContainerId, pPlayerInventory, ContainerLevelAccess.create(getCurrentWorld(), getWorldPosition()), multiblock.itemStackHolder, multiblock);
    }

    @Override
    public CompoundTag syncDataTo(CompoundTag data, SyncReason syncReason) {
        return super.syncDataTo(data, syncReason);
    }
}
