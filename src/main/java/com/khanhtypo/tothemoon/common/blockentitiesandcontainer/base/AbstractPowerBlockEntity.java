package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import com.khanhtypo.tothemoon.common.capability.PowerStorage;
import com.khanhtypo.tothemoon.common.machine.MachineRedstoneMode;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

@SuppressWarnings("SameParameterValue")
public abstract class AbstractPowerBlockEntity extends BaseContainerBlockEntity implements ImplementedContainer, TickableBlockEntity {
    private static final Direction[] allDirection = Direction.values();
    public final PowerStorage energyStorage;
    private final SavableSimpleContainer container;
    private final ContainerData containerData;
    public boolean active;
    public MachineRedstoneMode redstoneMode;
    protected LazyOptional<IEnergyStorage> energyHolder;
    protected LazyOptional<IItemHandler> itemHolder;

    public AbstractPowerBlockEntity(BlockEntityObject<? extends AbstractPowerBlockEntity> blockEntity,
                                    BlockPos blockPos,
                                    BlockState blockState,
                                    int containerSize,
                                    PowerStorage energyStorage,
                                    Function<AbstractPowerBlockEntity, ContainerData> dataConstructor) {
        super(blockEntity.get(), blockPos, blockState);
        this.container = new SavableSimpleContainer(this, containerSize);
        this.energyStorage = energyStorage;
        this.itemHolder = createHandler(() -> new InvWrapper(this));
        this.energyHolder = createHandler(() -> this.energyStorage);
        this.containerData = dataConstructor.apply(this);
        this.active = true;
        this.redstoneMode = MachineRedstoneMode.IGNORE;
    }

    protected static <T> LazyOptional<T> createHandler(NonNullSupplier<T> supplier) {
        return LazyOptional.of(supplier);
    }

    public static boolean intToBoolean(int i) {
        return i == 1;
    }

    protected static <T extends Comparable<T>> void setBlockState(Level level, BlockPos pos, BlockState blockState, Property<T> property, T value, boolean checkPresent) {
        ModUtils.changeBlockState(level, pos, blockState, property, value, checkPresent);
    }

    public static void tryExtractEnergyToNeighbour(IEnergyStorage from, Level level, BlockPos pos) {
        if (from.canExtract()) {
            for (Direction direction : allDirection) {
                ICapabilityProvider provider = level.getBlockEntity(pos.relative(direction));
                if (provider != null) {
                    LazyOptional<IEnergyStorage> energyStorageLazyOptional = provider.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite());
                    energyStorageLazyOptional.filter(IEnergyStorage::canReceive).ifPresent(energyStorage -> {
                        int receiveEnergy = energyStorage.receiveEnergy(from.getEnergyStored(), true);
                        if (receiveEnergy > 0) {
                            energyStorage.receiveEnergy(from.extractEnergy(receiveEnergy, false), false);
                        }
                    });
                    return;
                }
            }
        }
    }

    @Override
    public final void serverTick(Level level, BlockPos pos, BlockState blockState) {
        if (this.energyStorage.canExtract())
            tryExtractEnergyToNeighbour(this.energyStorage, level, pos);

        if (isActive() && this.redstoneMode.canMachineWork(level, pos)) {
            this.tick(level, pos, blockState);
        } else {
            setBlockState(level, pos, blockState, BlockStateProperties.LIT, false, true);
        }

        setChanged(level, pos, blockState);
    }

    public boolean isActive() {
        return this.active;
    }

    protected abstract void tick(Level level, BlockPos pos, BlockState blockState);

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
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (!super.remove) {
            if (cap == ForgeCapabilities.ENERGY) {
                return this.energyHolder.cast();
            } else if (side != null && cap == ForgeCapabilities.ITEM_HANDLER) {
                return this.itemHolder.cast();
            }
        }
        return super.getCapability(cap, side);
    }
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
        this.container.saveContainer(writer);
        writer.putBoolean("IsActive", this.isActive());
        this.redstoneMode.saveToTag(writer);
    }

    @Override
    public void load(CompoundTag deserializedNBT) {
        super.load(deserializedNBT);
        this.energyStorage.deserializeNBT(deserializedNBT.get("Energy"));
        this.container.loadContainer(deserializedNBT);
        this.active = deserializedNBT.getBoolean("IsActive");
        this.redstoneMode = MachineRedstoneMode.loadFromTag(deserializedNBT);
    }

    protected boolean isStackPresent(ItemStack itemStack) {
        return !itemStack.isEmpty();
    }

    protected boolean isSlotPresent(int index) {
        return this.isStackPresent(this.getItem(index));
    }

    public ContainerData getContainerData() {
        return containerData;
    }


    protected int getBurnTime(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, null);
    }

    protected int getBurnTime(int slot) {
        return this.getBurnTime(this.getItem(slot));
    }

    protected boolean canBurn(ItemStack stack) {
        return this.getBurnTime(stack) > 0;
    }

    protected boolean canBurn(int slot) {
        return this.canBurn(this.getItem(slot));
    }
}
