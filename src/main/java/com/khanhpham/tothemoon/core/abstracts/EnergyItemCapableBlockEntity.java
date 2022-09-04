package com.khanhpham.tothemoon.core.abstracts;

import com.khanhpham.tothemoon.core.blockentities.TickableBlockEntity;
import com.khanhpham.tothemoon.core.energy.Energy;
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
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class EnergyItemCapableBlockEntity extends EnergyCapableBlockEntity implements TickableBlockEntity, Container, MenuProvider, Nameable {
    protected final HashMap<BlockPos, IEnergyStorage> energyStorages = new HashMap<>();
    protected final Component label;
    protected final int containerSize;
    public NonNullList<ItemStack> items;
    private LazyOptional<IItemHandler> lo = LazyOptional.of(() -> new InvWrapper(this) {
        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return canTakeItem(slot) ? super.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
        }
    });

    public EnergyItemCapableBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @Nonnull Component label, final int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy);
        this.label = label;
        this.containerSize = containerSize;
        this.items = NonNullList.withSize(containerSize, ItemStack.EMPTY);
    }

    protected boolean canTakeItem(int index) {
        return true;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return this.lo.cast();
        }
        return ForgeCapabilities.ENERGY.orEmpty(cap, this.energyHolder);
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
                be.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite()).ifPresent(energy -> energyStorages.put(pos.relative(direction), energy));
        }
    }

    protected void transferEnergy() {
        for (IEnergyStorage energyStorage : energyStorages.values()) {
            if (!energy.isEmpty() && energy.canExtract() && energyStorage.canReceive() && !isEnergyStorageFull(energyStorage)) {
                energyStorage.receiveEnergy(this.energy.extractEnergy(this.energy.getMaxExtract(), false), false);
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

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.lo.invalidate();
    }

    @Override
    public void reviveCaps() {
        this.lo = LazyOptional.of(() -> new InvWrapper(this));
    }
}
