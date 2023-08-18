package com.khanhtypo.tothemoon.multiblock.blackstonefurnace.ioparts.entity;

import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.BlackStoneFurnaceAcceptorVariants;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.MultiblockBlackStoneFurnace;
import it.zerono.mods.zerocore.base.multiblock.part.AbstractMultiblockEntity;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public abstract class AbstractBlackStoneFurnaceIOPartEntity<T> extends AbstractMultiblockEntity<MultiblockBlackStoneFurnace> {

    public AbstractBlackStoneFurnaceIOPartEntity(BlockEntityType<?> type, BlockPos position, BlockState blockState) {
        super(type, position, blockState);
    }

    @Override
    protected ModelData getUpdatedModelData() {
        return ModelData.EMPTY;
    }

    @Override
    public MultiblockBlackStoneFurnace createController() {
        return new MultiblockBlackStoneFurnace(this.getCurrentWorld());
    }

    @Override
    public Class<MultiblockBlackStoneFurnace> getControllerType() {
        return MultiblockBlackStoneFurnace.class;
    }

    @Nonnull
    protected abstract Capability<T> getRequiredCapability();

    @Override
    public @NotNull <A> LazyOptional<A> getCapability(@NotNull Capability<A> cap, @Nullable Direction side) {
        if (cap == this.getRequiredCapability()) {
            return this.getHolder().cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public boolean isGoodForPosition(PartPosition partPosition, IMultiblockValidator iMultiblockValidator) {
        return this.isGoodForPos(partPosition, iMultiblockValidator, BlackStoneFurnaceAcceptorVariants.getDefinitionForBlock(this.getCurrentWorld(), this.getBlockPos()));
    }

    protected boolean isGoodForPos(PartPosition partPosition, IMultiblockValidator validator, BlackStoneFurnaceAcceptorVariants acceptorVariants) {
        if (acceptorVariants.isBlackstone()) {
            if (!partPosition.isFrame()) {
                validator.setLastError(super.getBlockPos(), ModLanguageGenerator.BLACKSTONE_ACCEPTOR_NOT_FRAME);
                return false;
            }
        } else {
            if (partPosition.isFrame()) {
                validator.setLastError(super.getBlockPos(), ModLanguageGenerator.NETHER_ACCEPTOR_NOT_FRAME);
                return false;
            }
        }

        return true;
    }

    protected abstract LazyOptional<T> getHolder();

    @Override
    public abstract void invalidateCaps();

    @Override
    public abstract void reviveCaps();

    protected void invalidateForgeCaps() {
        super.invalidateCaps();
    }

    protected void reviveForgeCaps() {
        super.reviveCaps();
    }

    @Override
    public void onMachineActivated() {
    }

    @Override
    public void onMachineDeactivated() {
    }
}
