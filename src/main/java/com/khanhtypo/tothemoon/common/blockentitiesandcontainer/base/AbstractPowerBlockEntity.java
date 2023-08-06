package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullConsumer;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractPowerBlockEntity extends BaseContainerBlockEntity implements ImplementedWorldlyContainer, TickableBlockEntity {
    private static final Direction[] allDirection = Direction.values();
    protected final EnergyStorage energyStorage;
    private final SimpleContainer container;
    @Nonnull
    private final NonNullConsumer<IEnergyStorage> extractLogic = this::extractEnergy;
    protected LazyOptional<IEnergyStorage> energyHolder;
    protected LazyOptional<IItemHandler> itemHolder;
    private boolean active = true;

    public AbstractPowerBlockEntity(BlockEntityObject<? extends AbstractPowerBlockEntity> blockEntity,
                                    BlockPos blockPos,
                                    BlockState blockState,
                                    int containerSize,
                                    int energyCapacity) {
        this(blockEntity, blockPos, blockState, containerSize, new EnergyStorage(energyCapacity));
    }

    public AbstractPowerBlockEntity(BlockEntityObject<? extends AbstractPowerBlockEntity> blockEntity,
                                    BlockPos blockPos,
                                    BlockState blockState,
                                    int containerSize,
                                    EnergyStorage energyStorage) {
        super(blockEntity.get(), blockPos, blockState);
        this.container = new SimpleContainer(containerSize);
        this.energyStorage = energyStorage;
        this.itemHolder = createHandler(() -> new InvWrapper(this));
        this.energyHolder = createHandler(() -> this.energyStorage);
    }

    protected static <T> LazyOptional<T> createHandler(NonNullSupplier<T> supplier) {
        return LazyOptional.of(supplier);
    }

    @Override
    public final void serverTick(Level level, BlockPos pos, BlockState blockState) {
        this.tryExtractEnergy(level, pos);

        this.tick(level, pos, blockState);

        setChanged(level, pos, blockState);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    protected abstract void tick(Level level, BlockPos pos, BlockState blockState);

    public void tryExtractEnergy(Level level, BlockPos pos) {
        if (this.energyStorage.canExtract()) {
            for (Direction direction : allDirection) {
                ICapabilityProvider provider = level.getBlockEntity(pos.relative(direction));
                if (provider != null) {
                    LazyOptional<IEnergyStorage> energyStorageLazyOptional = provider.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite());
                    energyStorageLazyOptional.ifPresent(this.extractLogic);
                }
            }
        }
    }

    public void extractEnergy(IEnergyStorage energyStorage) {
        if (energyStorage.canReceive()) {
            this.energyStorage.extractEnergy(energyStorage.receiveEnergy(this.getPowerSpace(), false), false);
        }
    }

    public int getPowerSpace() {
        return this.energyStorage.getMaxEnergyStored() - this.energyStorage.getEnergyStored();
    }

    @Override
    protected Component getDefaultName() {
        return super.getBlockState().getBlock().getName();
    }

    @Override
    public Container getContainer() {
        return this.container;
    }

    @Override
    @Nonnull
    @SuppressWarnings("unchecked")
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (!super.remove) {
            if (cap == ForgeCapabilities.ENERGY) {
                return this.energyHolder.cast();
            } else if (side != null && cap == ForgeCapabilities.ITEM_HANDLER) {
                return (LazyOptional<T>) this.getHandlerForEachFace(side);
            }
        }
        return super.getCapability(cap, side);
    }

    protected abstract LazyOptional<IItemHandler> getHandlerForEachFace(Direction side);

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.energyHolder.invalidate();
        this.itemHolder.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.energyHolder = createHandler(() -> this.energyStorage);
        this.itemHolder = createHandler(() -> new InvWrapper(this));
    }

    @Override
    protected void saveAdditional(CompoundTag writer) {
        super.saveAdditional(writer);
        writer.put("Energy", this.energyStorage.serializeNBT());
    }

    @Override
    public void load(CompoundTag deserializedNBT) {
        super.load(deserializedNBT);
        this.energyStorage.deserializeNBT(deserializedNBT.get("Energy"));
    }

    protected boolean isStackPresent(ItemStack itemStack) {
        return !itemStack.isEmpty();
    }

    protected boolean isSlotPresent(int index) {
        return this.isStackPresent(this.getItem(index));
    }
}
