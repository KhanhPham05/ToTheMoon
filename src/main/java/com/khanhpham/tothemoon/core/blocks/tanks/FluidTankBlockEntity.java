package com.khanhpham.tothemoon.core.blocks.tanks;

import com.khanhpham.tothemoon.core.blockentities.FluidCapableBlockEntity;
import com.khanhpham.tothemoon.core.blockentities.ImplementedContainer;
import com.khanhpham.tothemoon.core.blockentities.TickableBlockEntity;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
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
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class FluidTankBlockEntity extends BlockEntity implements ImplementedContainer<FluidTankMenu>, TickableBlockEntity, FluidCapableBlockEntity {
    public static final int TANK_CAPACITY = 50000;
    private final NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    public int fluidRegistryId = 0;
    public FluidTank tank = new FluidTank(TANK_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            fluidRegistryId = Registry.FLUID.getId(this.fluid.getFluid());
        }

        @Override
        public void setFluid(FluidStack stack) {
            super.setFluid(stack);
            onContentsChanged();
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.isEmpty() || this.fluid.isEmpty() || stack.getFluid().isSame(fluid.getFluid());
        }
    };
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
        super(ModBlockEntities.FLUID_TANK.get(), pWorldPosition, pBlockState);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!remove) {
            if (cap == ForgeCapabilities.FLUID_HANDLER) {
                return this.tankCap.cast();
            } else if (cap == ForgeCapabilities.FLUID_HANDLER_ITEM) {
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
        return ModBlocks.FLUID_TANK.get().getName();
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
        ItemStack item = getItem(0);
        if (item.getItem() instanceof BucketItem bucketItem && this.isFluidSame(bucketItem.getFluid())) {
            Fluid fluid = bucketItem.getFluid();
            this.tank.fill(new FluidStack(fluid, FluidType.BUCKET_VOLUME), IFluidHandler.FluidAction.EXECUTE);
            setItem(0, new ItemStack(Items.BUCKET));
        } else {
            LazyOptional<IFluidHandlerItem> cap = item.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
            if (cap.isPresent()) {
                this.tank.fill(
                        cap.map(fluid -> {
                            if (this.tank.isEmpty())
                                return fluid.drain(this.tank.getSpace(), IFluidHandler.FluidAction.EXECUTE);
                            else
                                return fluid.drain(new FluidStack(this.tank.getFluid().getFluid(), this.tank.getSpace()), IFluidHandler.FluidAction.EXECUTE);
                        }).get()
                        , IFluidHandler.FluidAction.EXECUTE);
            }
        }

        ItemStack stack1 = getItem(1);
        if (stack1.is(Items.BUCKET)) {
            setItem(1, ModUtils.getBucketItem(this.tank.getFluid().getFluid()));
            this.tank.drain(FluidType.BUCKET_VOLUME, IFluidHandler.FluidAction.EXECUTE);
        } else {
            LazyOptional<IFluidHandlerItem> tankCap = stack1.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
            if (tankCap.isPresent()) {
                this.tank.drain(tankCap.map(fluid -> {
                    FluidStack fluidInTank = fluid.getFluidInTank(this.tank.getSpace());
                    if (!this.tank.isEmpty() && this.tank.isFluidValid(fluidInTank)) {
                        return this.tank.fill(fluidInTank, IFluidHandler.FluidAction.EXECUTE);
                    } else return 0;
                }).get(), IFluidHandler.FluidAction.EXECUTE);
            }
        }

        setChanged(level, pos, state);
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

    public boolean isFluidSame(Fluid fluid) {
        return fluid.isSame(Fluids.EMPTY) && this.tank.getFluid().getFluid().isSame(fluid);
    }

    public static final class Renderer implements BlockEntityRenderer<FluidTankBlockEntity> {
        public Renderer(BlockEntityRendererProvider.Context ignored) {
        }

        @Override
        public void render(FluidTankBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

        }
    }
}
