package com.khanhpham.tothemoon.core.abstracts;

import com.khanhpham.tothemoon.core.blockentities.TickableBlockEntity;
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
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class EnergyItemCapableBlockEntity extends EnergyCapableBlockEntity implements TickableBlockEntity, IItemHandler, Container, MenuProvider, Nameable {
    protected final HashMap<BlockPos, IEnergyStorage> energyStorages = new HashMap<>();
    protected final Component label;
    protected final int containerSize;
    private final LazyOptional<IItemHandler> lo = LazyOptional.of(() -> this);
    public NonNullList<ItemStack> items;

    public EnergyItemCapableBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @Nonnull Component label, final int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy);
        this.label = label;
        this.containerSize = containerSize;
        this.items = NonNullList.withSize(containerSize, ItemStack.EMPTY);
    }

    @Override
    public int getSlots() {
        return items.size();
    }

    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return items.get(slot);
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (!isItemValid(slot, stack))
            return stack;

        ensureValidSlotIndex(slot);

        ItemStack existing = this.items.get(slot);

        int limit = Math.min(64, stack.getMaxStackSize());

        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            if (existing.isEmpty()) {
                this.items.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            } else {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
        }
        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    private void ensureValidSlotIndex(int slot) {
        if (!(slot >= 0 && slot < this.items.size())) {
            throw new IllegalStateException();
        }
    }

    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;

        ensureValidSlotIndex(slot);

        ItemStack existing = this.items.get(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                this.items.set(slot, ItemStack.EMPTY);
                return existing;
            } else {
                return existing.copy();
            }
        } else {
            if (!simulate) {
                this.items.set(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
            }

            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.lo.cast();
        }

        return cap.orEmpty(CapabilityEnergy.ENERGY, super.energyDataOptional.cast()).cast();
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return true;
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public boolean isEmpty(int index) {
        return this.items.get(index).isEmpty();
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
    protected final void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        ContainerHelper.saveAllItems(pTag, this.items);
        energy.save(pTag);
        saveExtra(pTag);
    }

    protected void saveExtra(CompoundTag tag) {
    }

    @Override
    public final void load(CompoundTag pTag) {
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pTag, this.items);
        energy.load(pTag);
        loadExtra(pTag);
    }

    protected void loadExtra(CompoundTag tag) {
    }

    @Nonnull
    protected abstract AbstractContainerMenu createMenu(int containerId, @Nonnull Inventory playerInventory);

    protected void collectBlockEntities(Level level, BlockPos pos) {
        energyStorages.clear();
        for (Direction direction : Direction.values()) {
            var be = level.getBlockEntity(pos.relative(direction));
            if (be != null)
                be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).ifPresent(energy -> energyStorages.put(pos.relative(direction), energy));
        }
    }

    protected void transferEnergy() {
        for (IEnergyStorage energyStorage : energyStorages.values()) {
            if (!energy.isEmpty() && energy.canExtract() && energyStorage.canReceive() && !isEnergyStorageFull(energyStorage)) {
                this.energy.extractEnergy(energyStorage.receiveEnergy(energy.getMaxExtract(), false), false);
            }
        }
    }

    protected boolean isEnergyStorageFull(IEnergyStorage storage) {
        return storage.getEnergyStored() >= storage.getMaxEnergyStored();
    }


    @javax.annotation.Nullable
    protected <C extends Container, T extends Recipe<C>> T getRecipe(Level level, RecipeType<T> recipeType, C container) {
        return level.getRecipeManager().getRecipeFor(recipeType, container, level).orElse(null);
    }

    protected <T extends Comparable<T>> BlockState setNewBlockState(Level level, BlockPos pos, BlockState state, Property<T> property, T value) {
        if (!state.getValue(property).equals(value)) {
            state = state.setValue(property, value);
            level.setBlock(pos, state, 3);
        }
        return state;
    }
}
