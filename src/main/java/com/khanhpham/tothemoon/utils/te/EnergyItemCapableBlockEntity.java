package com.khanhpham.tothemoon.utils.te;

import com.khanhpham.tothemoon.utils.energy.Energy;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class EnergyItemCapableBlockEntity extends EnergyCapableTileEntity implements Container, MenuProvider, Nameable {
    protected final Component label;
    protected final int containerSize;
    protected NonNullList<ItemStack> items;

    public EnergyItemCapableBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @Nonnull Component label, final int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy);
        this.label = label;
        this.containerSize = containerSize;
        this.items = NonNullList.withSize(containerSize, ItemStack.EMPTY);
    }

    protected static void markDirty(Level level, BlockPos pos, BlockState state) {
        setChanged(level, pos, state);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return this.items.get(pIndex);
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        return ContainerHelper.removeItem(this.items, pIndex, pCount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        return ContainerHelper.takeItem(this.items, pIndex);
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        this.items.set(pIndex, pStack);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return !(pPlayer instanceof FakePlayer);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public Component getName() {
        return label;
    }

    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return createMenu(pContainerId, pInventory);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        ContainerHelper.saveAllItems(pTag, this.items);
        energy.save(pTag);
        saveExtra(pTag);
    }

    protected void saveExtra(CompoundTag ignored) {
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pTag, this.items);
        energy.load(pTag);
        loadExtra(pTag);
    }

    protected void loadExtra(CompoundTag ignored) {
    }

    @Nonnull
    protected abstract AbstractContainerMenu createMenu(int containerId,@Nonnull Inventory playerInventory);

    protected void transferEnergyToOther(Level level, BlockPos blockPos) {
        transferEnergy(level, blockPos, Direction.NORTH, Direction.DOWN);
        transferEnergy(level, blockPos, Direction.SOUTH, Direction.NORTH);
        transferEnergy(level, blockPos, Direction.WEST, Direction.EAST);
        transferEnergy(level, blockPos, Direction.EAST, Direction.WEST);
        transferEnergy(level, blockPos, Direction.UP, Direction.DOWN);
        transferEnergy(level, blockPos, Direction.DOWN, Direction.UP);
    }

    protected void receiveEnergyFromOther(Level level, BlockPos blockPos) {
        receiveEnergy(level, blockPos, Direction.NORTH, Direction.DOWN);
        receiveEnergy(level, blockPos, Direction.SOUTH, Direction.NORTH);
        receiveEnergy(level, blockPos, Direction.WEST, Direction.EAST);
        receiveEnergy(level, blockPos, Direction.EAST, Direction.WEST);
        receiveEnergy(level, blockPos, Direction.UP, Direction.DOWN);
        receiveEnergy(level, blockPos, Direction.DOWN, Direction.UP);
    }

    protected void receiveEnergy(Level level, BlockPos blockPos, Direction direction, Direction direction2) {
        BlockEntity te = level.getBlockEntity(blockPos.relative(direction));

        if (te != null) {
            te.getCapability(CapabilityEnergy.ENERGY, direction2).ifPresent(e -> {
                if (e.canExtract() && e.getEnergyStored() > 0) {
                    super.energy.receiveEnergy(e.extractEnergy(super.energy.getMaxReceive(), false), false);
                }
            });
        }
    }

    protected void transferEnergy(Level level, BlockPos blockPos, Direction direction, Direction direction2) {
        BlockEntity te = level.getBlockEntity(blockPos.relative(direction));
        if (te != null) {
            te.getCapability(CapabilityEnergy.ENERGY, direction2).ifPresent(e -> {
                if (e.canReceive() && e.getEnergyStored() < e.getMaxEnergyStored()) {
                    super.energy.extractEnergy(e.receiveEnergy(super.energy.getMaxExtract(), false), false);
                }
            });
        }
    }

    /**
     * @see net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
     */
    protected <T extends Comparable<T>> BlockState setNewBlockState(Level level, BlockPos pos, BlockState state, Property<T> property, T value) {
        state = state.setValue(property, value);
        level.setBlock(pos, state, 3);
        return state;
    }
}
