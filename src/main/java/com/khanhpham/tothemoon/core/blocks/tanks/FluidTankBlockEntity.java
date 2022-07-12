package com.khanhpham.tothemoon.core.blocks.tanks;

import com.khanhpham.tothemoon.core.blockentities.FluidCapableBlockEntity;
import com.khanhpham.tothemoon.core.blockentities.ImplementedContainer;
import com.khanhpham.tothemoon.core.blockentities.TickableBlockEntity;
import com.khanhpham.tothemoon.init.nondeferred.NonDeferredBlockEntitiesTypes;
import com.khanhpham.tothemoon.init.nondeferred.NonDeferredBlocks;
import com.khanhpham.tothemoon.utils.capabilities.FixedFluidTank;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FluidTankBlockEntity extends BlockEntity implements ImplementedContainer<FluidTankMenu>, TickableBlockEntity, FluidCapableBlockEntity {
    private final NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    public FluidTank tank = new FixedFluidTank(Fluids.LAVA, TANK_CAPACITY);
    public LazyOptional<IFluidHandler> tankCap = LazyOptional.of(() -> tank);
    public LazyOptional<IItemHandler> itemHandlerCap = LazyOptional.of(() -> new InvWrapper(this));
    public static final int TANK_CAPACITY = 50000;

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> tank.getFluidAmount();
                case 1 -> tank.getCapacity();
                default -> throw new IllegalStateException("Unexpected value: " + pIndex);
            };
        }

        @Override
        public void set(int pIndex, int pValue) {

        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public FluidTankBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(NonDeferredBlockEntitiesTypes.FLUID_TANK_NON_DEFERRED, pWorldPosition, pBlockState);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!remove) {
            if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
                return this.tankCap.cast();
            } else if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                return this.itemHandlerCap.cast();
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @NotNull
    @Override
    public FluidTankMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new FluidTankMenu(pContainerId, pInventory, this, this.data);
    }

    @Override
    public Component getContainerDisplayName() {
        return NonDeferredBlocks.FLUID_TANK_BLOCK.getName();
    }

    public boolean canFilledFromBucket(Fluid fluid) {
        return this.tank.getFluidAmount() + 1000 <= this.tank.getCapacity() && fluid.isSame(this.getFluid());
    }

    public boolean isFluidSame(Fluid fluid) {
        return fluid.isSame(Fluids.LAVA);

    }

    public boolean canDrainToExternal(Fluid fluid) {
        return this.tank.getFluidAmount() >= 1000 && this.isFluidSame(fluid);
    }

    public Fluid getFluid() {
        return this.tank.getFluid().getFluid();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.itemHandlerCap.invalidate();
        this.tankCap.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.itemHandlerCap = LazyOptional.of(() -> new InvWrapper(this));
        this.tankCap = LazyOptional.of(() -> new FluidTank(50000));
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        ContainerHelper.loadAllItems(pTag, this.items);
        this.tank.readFromNBT(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        ContainerHelper.saveAllItems(pTag, this.items, false);
        this.tank.writeToNBT(pTag);
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        if (!getItem(0).isEmpty()) {
            interact(0, false);
        } else if (!getItem(1).isEmpty()) {
            interact(1, true);
        }

        int fluidStoreState = (this.tank.getFluid().getAmount() * 12) / this.tank.getCapacity();
        if (state.getValue(FluidTankBlock.FLUID_LEVEL) != fluidStoreState) {
            state = state.setValue(FluidTankBlock.FLUID_LEVEL, fluidStoreState);
            level.setBlock(pos, state, 3);
        }

        setChanged(level, pos, state);
    }

    private void interact(int itemIndex, boolean drainToItem) {
        ItemStack item = this.items.get(itemIndex);
        if (item.getItem() instanceof BucketItem bucketItem) {
            if (bucketItem.getFluid().isSame(Fluids.EMPTY) && drainToItem) {
                this.items.set(itemIndex, new ItemStack(Items.LAVA_BUCKET));
                this.tank.drain(new FluidStack(Fluids.LAVA, 1000), IFluidHandler.FluidAction.EXECUTE);
            } else if (bucketItem.getFluid().isSame(Fluids.LAVA) && !drainToItem) {
                this.items.set(itemIndex, new ItemStack(Items.BUCKET));
                this.tank.fill(new FluidStack(Fluids.LAVA, 1000), IFluidHandler.FluidAction.EXECUTE);
            }
        }
    }


    @Override
    public FluidTank getTank() {
        return this.tank;
    }
}
