package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import com.google.common.base.Preconditions;
import com.khanhtypo.tothemoon.common.capability.PowerStorage;
import com.khanhtypo.tothemoon.common.item.upgrades.AbstractUpgradeItem;
import com.khanhtypo.tothemoon.common.item.upgrades.IUpgradeItem;
import com.khanhtypo.tothemoon.common.item.upgrades.ItemPowerGeneratorUpgrade;
import com.khanhtypo.tothemoon.common.item.upgrades.UpgradeItemType;
import com.khanhtypo.tothemoon.common.machine.MachineRedstoneMode;
import com.khanhtypo.tothemoon.common.machine.powergenerator.PowerGeneratorBlockEntity;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;

@SuppressWarnings({"SameParameterValue", "unchecked"})
public abstract class AbstractMachineBlockEntity<T extends AbstractMachineBlockEntity<T>> extends BaseContainerBlockEntity implements ImplementedContainer, TickableBlockEntity {
    public static final int DEFAULT_FUEL_CONSUME_DURATION = 20;
    public static final Direction[] allDirections = Direction.values();
    public final PowerStorage energyStorage;
    public final SavableSimpleContainer upgradeContainer;
    private final BlockEntityObject<T> blockEntityObject;
    private final SavableSimpleContainer container;
    private final ContainerData containerData;
    public boolean active;
    public MachineRedstoneMode redstoneMode;
    protected LazyOptional<IEnergyStorage> energyHolder;
    protected LazyOptional<IItemHandler> itemHolder;
    protected int fuelConsumeDuration;
    protected int fuelConsumeTime;

    public AbstractMachineBlockEntity(BlockEntityObject<T> blockEntityObject,
                                      BlockPos blockPos,
                                      BlockState blockState,
                                      int containerSize,
                                      PowerStorage energyStorage,
                                      Function<T, ContainerData> dataConstructor) {
        super(blockEntityObject.get(), blockPos, blockState);
        this.blockEntityObject = blockEntityObject;
        this.container = new SavableSimpleContainer(this, containerSize);
        this.fuelConsumeDuration = DEFAULT_FUEL_CONSUME_DURATION;

        this.upgradeContainer = new SavableSimpleContainer(this, 3) {
            @Override
            public void onItemTaken(int slot, ItemStack removedStack) {
                if (removedStack.getItem() instanceof IUpgradeItem upgradeItem) {
                    upgradeItem.onUpgradeTaken(AbstractMachineBlockEntity.this);
                }
            }

            @Override
            public void onItemPlaced(int pSlot, ItemStack placedItem) {
                if (placedItem.getItem() instanceof AbstractUpgradeItem upgradeItem) {
                    upgradeItem.acceptUpgrade(AbstractMachineBlockEntity.this);
                }
            }

            @Override
            public boolean canPlaceItem(int pIndex, ItemStack pStack) {
                Item item = pStack.getItem();
                return (AbstractMachineBlockEntity.this instanceof PowerGeneratorBlockEntity) ? item instanceof ItemPowerGeneratorUpgrade : item instanceof IUpgradeItem;
            }

            @Override
            public boolean canTakeItem(Container pTarget, int pIndex, ItemStack pStack) {
                if (pStack.getItem() instanceof IUpgradeItem upgradeItem) {
                    return upgradeItem.canTakeFromSlot(AbstractMachineBlockEntity.this);
                }
                return super.canTakeItem(pTarget, pIndex, pStack);
            }
        };
        this.energyStorage = energyStorage;
        this.itemHolder = createHandler(() -> new InvWrapper(this));
        this.energyHolder = createHandler(() -> this.energyStorage);
        this.containerData = dataConstructor.apply((T) this);
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
            for (Direction direction : allDirections) {
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
    public void serverTick(Level level, BlockPos pos, BlockState blockState) {
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
    public <A> LazyOptional<A> getCapability(Capability<A> cap, @Nullable Direction side) {
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
        if (!this.upgradeContainer.isEmpty()) {
            CompoundTag upgrades = new CompoundTag();
            for (int i = 0; i < 3; i++) {
                ItemStack upgradeItem = this.upgradeContainer.getItem(i);
                if (!upgradeItem.isEmpty()) {
                    upgrades.putString("Slot" + i, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(upgradeItem.getItem())).toString());
                }
            }
            writer.put("Upgrades", upgrades);
        }
        writer.putInt("FuelConsumeTime", this.fuelConsumeTime);
        writer.putInt("FuelConsumeDuration", this.fuelConsumeDuration);
    }

    @Override
    public void load(CompoundTag deserializedNBT) {
        super.load(deserializedNBT);
        this.energyStorage.deserializeNBT(deserializedNBT.get("Energy"));
        this.container.loadContainer(deserializedNBT);
        this.active = deserializedNBT.getBoolean("IsActive");
        this.redstoneMode = MachineRedstoneMode.loadFromTag(deserializedNBT);
        if (deserializedNBT.contains("Upgrades", CompoundTag.TAG_COMPOUND)) {
            CompoundTag upgrades = deserializedNBT.getCompound("Upgrades");
            for (int i = 0; i < 3; i++) {
                if (upgrades.contains("Slot" + i, CompoundTag.TAG_STRING)) {
                    this.upgradeContainer.setItem(i, new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(upgrades.getString("Slot" + i))))));
                }
            }
        }
        this.fuelConsumeDuration = deserializedNBT.getInt("FuelConsumeDuration");
        this.fuelConsumeTime = deserializedNBT.getInt("FuelConsumeTime");
    }

    public ContainerData getContainerData() {
        return containerData;
    }

    public boolean canPutUpgradeIn(AbstractUpgradeItem upgradeItem) {
        for (int i = 0; i < 3; i++) {
            if (this.upgradeContainer.getItem(i).is(upgradeItem) || this.upgradeContainer.hasAnyMatching(item -> item.getItem() instanceof IUpgradeItem upgradeItem1 && UpgradeItemType.isSameTypeWith(upgradeItem, upgradeItem1))) {
                return false;
            }
        }

        return true;
    }

    public void putUpgradeIn(AbstractUpgradeItem upgradeItem) {
        for (int i = 0; i < 3; i++) {
            if (this.upgradeContainer.isSlotEmpty(i)) {
                this.upgradeContainer.setItem(i, new ItemStack(upgradeItem));
                return;
            }
        }
    }

    public SavableSimpleContainer getUpgradeContainer() {
        return upgradeContainer;
    }

    public PowerStorage getEnergyStorage() {
        return energyStorage;
    }

    public void loadEnergyFrom(CompoundTag rootTag) {
        this.energyStorage.deserializeNBT(rootTag);
    }

    public int getDefaultFuelConsumeDuration() {
        return DEFAULT_FUEL_CONSUME_DURATION;
    }

    public void setFuelConsumeDuration(int value) {
        Preconditions.checkState(value > 0, "fuelConsumeDuration can not be smaller than 1");
        this.fuelConsumeDuration = value;
    }

    protected final Level getLevelOrThrow() {
        if (super.level == null) throw ModUtils.fillCrashReport(new IllegalStateException(), "Level does not present", "Value Not Present", category -> category.setDetail("BlockEntityType", this.blockEntityObject.getId()).setDetail("BlockPos", super.getBlockPos()));
        return super.level;
    }
}
