package com.khanhpham.tothemoon.core.blocks.tanks;

import com.khanhpham.tothemoon.core.blockentities.FluidCapableBlockEntity;
import com.khanhpham.tothemoon.core.blockentities.ImplementedContainer;
import com.khanhpham.tothemoon.core.blockentities.TickableBlockEntity;
import com.khanhpham.tothemoon.init.nondeferred.NonDeferredBlockEntitiesTypes;
import com.khanhpham.tothemoon.init.nondeferred.NonDeferredBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
@SuppressWarnings("deprecation")
public class FluidTankBlockEntity extends BlockEntity implements ImplementedContainer<FluidTankMenu>, TickableBlockEntity, FluidCapableBlockEntity {
    public static final int TANK_CAPACITY = 50000;
    private final NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    public FluidTank tank = new FluidTank(TANK_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            fluidRegistryId = Registry.FLUID.getId(this.fluid.getFluid());
        }
    };
    public int fluidRegistryId = 0;

    final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> tank.getFluidAmount();
                case 1 -> tank.getCapacity();
                case 2 -> fluidRegistryId;
                default -> throw new IllegalStateException("Unexpected value: " + pIndex);
            };
        }

        @Override
        public void set(int pIndex, int pValue) {

        }

        @Override
        public int getCount() {
            return 3;
        }
    };
    public LazyOptional<IFluidHandler> tankCap = LazyOptional.of(() -> tank);
    public LazyOptional<IItemHandler> itemHandlerCap = LazyOptional.of(() -> new InvWrapper(this));

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
        this.fluidRegistryId = pTag.getInt("fluidId");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        ContainerHelper.saveAllItems(pTag, this.items, false);
        this.tank.writeToNBT(pTag);
        pTag.putInt("fluidId", fluidRegistryId);
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        /*.if (!this.items.get(0).isEmpty()) {
            this.items.get(0).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(fluidHandler -> {
                if (this.tank.isFluidValid(fluidHandler.getFluidInTank(1000)) && items.get(0).hasContainerItem()) {
                    this.tank.fill(fluidHandler.drain(1000, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                    setItem(0, items.get(0).getContainerItem());
                }
            });
        }
        */
        this.interact(0, true);
        this.interact(1, false);



        setChanged(level, pos, state);
    }

    private void interact(int index, boolean drainItem) {
        ItemStack item = items.get(index);
        if (!item.isEmpty() && item.hasContainerItem()) {
            item.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(fluidHandler -> {
                if (drainItem && this.tank.isFluidValid(fluidHandler.getFluidInTank(1000)) && item.hasContainerItem()) {
                    this.tank.fill(fluidHandler.drain(1000, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                    setItem(index, item.getContainerItem());
                } else if (!drainItem && fluidHandler.isFluidValid(1000, this.tank.getFluid())) {
                    this.tank.drain(fluidHandler.fill(this.tank.getFluidInTank(1000), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                }
            });
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::saveWithoutMetadata);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);

        CompoundTag tag = pkt.getTag();
        if (tag != null && tag.contains("FluidName", 10) && tag.contains("Amount", 3)) {
            this.tank.setFluid(FluidStack.loadFluidStackFromNBT(tag));
        }
    }

    @Override
    public FluidTank getTank() {
        return this.tank;
    }
}
