package com.khanhtypo.tothemoon.common.tank;

import com.khanhtypo.tothemoon.api.abstracts.IItemDropOnBreak;
import com.khanhtypo.tothemoon.api.helpers.NbtHelper;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.AbstractMachineBlockEntity;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.SavableSimpleContainer;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.TickableBlockEntity;
import com.khanhtypo.tothemoon.common.capability.SimpleFluidStorage;
import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import com.khanhtypo.tothemoon.registration.ModBlocks;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FluidTankBlockEntity extends BlockEntity implements TickableBlockEntity, MenuProvider, IItemDropOnBreak {
    public static final int TANK_CAPACITY = FluidType.BUCKET_VOLUME * 500;
    public static final int CONTAINER_SIZE = 2;
    public static final int DATA_SIZE = 5;
    //a small delay time of .5 seconds that needed for an item fluid handler transfer 1000mB.
    static final int bucketTransferDuration = 10;
    private static final int mbEachTick = FluidType.BUCKET_VOLUME / 20;
    private final SimpleFluidStorage fluidTank;
    private final SavableSimpleContainer container;
    private final ContainerData containerData;
    private LazyOptional<IFluidHandler> fluidTankHandler;
    private int bucketTransferTime;

    public FluidTankBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FLUID_TANK.get(), pPos, pBlockState);
        this.fluidTank = new SimpleFluidStorage(this, TANK_CAPACITY);
        this.fluidTankHandler = LazyOptional.of(() -> this.fluidTank);
        this.container = new SavableSimpleContainer(this, CONTAINER_SIZE);
        this.bucketTransferTime = 0;
        this.containerData = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> fluidTank.getFluidAmount();
                    case 1 -> fluidTank.getCapacity();
                    case 2 -> ModRegistries.getId(Registries.FLUID, fluidTank.isEmpty() ? Fluids.EMPTY : fluidTank.getFluid().getRawFluid());
                    case 3 -> bucketTransferTime;
                    case 4 -> bucketTransferDuration;
                    default -> throw new IllegalStateException("Unexpected value: " + pIndex);
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                throw new UnsupportedOperationException(FluidTankBlockEntity.this.getClass().getSimpleName() + " does not support data setting");
            }

            @Override
            public int getCount() {
                return DATA_SIZE;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!super.remove && cap == ForgeCapabilities.FLUID_HANDLER) {
            if (super.level != null) {
                if (side != null) {
                    Optional<?> tankBlockEntity = super.level.getBlockEntity(this.getBlockPos().relative(side), super.getType());
                    if (tankBlockEntity.isPresent()) {
                        return LazyOptional.empty();
                    }
                }
                return this.fluidTankHandler.cast();
            }
        }

        return LazyOptional.empty();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.fluidTankHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.fluidTankHandler = LazyOptional.of(() -> this.fluidTank);
    }

    private boolean dataChanged = false;

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState blockState) {
        //this.trySendFluid(level, pos);
        this.dataChanged = false;

        if (!this.fluidTank.isFull() && !this.container.isSlotEmpty(0)) {
            ItemStack input = this.container.getItem(0);

            if (input.getItem() instanceof BucketItem bucketItem) {
                Fluid bucketFluid = bucketItem.getFluid();
                FluidStack fluidStack = new FluidStack(bucketFluid, FluidType.BUCKET_VOLUME);
                if (this.fluidTank.fill(fluidStack, IFluidHandler.FluidAction.SIMULATE) >= FluidType.BUCKET_VOLUME) {
                    this.bucketTransferTime++;
                    if (this.bucketTransferTime >= bucketTransferDuration) {
                        ItemStack returnedItem = input.getCraftingRemainingItem();
                        this.container.setItem(0, returnedItem);
                        this.fluidTank.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                        this.bucketTransferTime = 0;
                        this.dataChanged = true;
                    }
                } else {
                    this.dataChanged = true;
                    this.bucketTransferTime = 0;
                }
            } else {
                Optional<IFluidHandlerItem> itemFluidHolder = FluidTankMenu.testInputSlot(input);
                if (itemFluidHolder.isPresent()) {
                    this.bucketTransferTime = 0;
                    input.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
                    this.tryExtractFromItem(itemFluidHolder.get());
                }
            }
        }

        if (this.container.isSlotPresent(1) && !this.fluidTank.isEmpty()) {
            ItemStack extractSlotItem = this.container.getItem(1);
            Optional<IFluidHandlerItem> extractTankHandler = FluidTankMenu.testExtractSlot(extractSlotItem);
            extractTankHandler.ifPresent(this::tryInsertToItem);
        }

        if (this.dataChanged) {
            setChanged(level, pos, blockState);
        }
    }

    private void tryInsertToItem(IFluidHandler tankToBeInserted) {
        FluidStack toBeExtracted = this.fluidTank.drain(tankToBeInserted.fill(this.fluidTank.getFluid(), IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.SIMULATE);
        if (!toBeExtracted.isEmpty()) {
            this.fluidTank.drain(tankToBeInserted.fill(toBeExtracted, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
            this.dataChanged = true;
        }
    }

    private void tryExtractFromItem(IFluidHandler fluidHandlerItem) {
        int transferAmount = Math.min(this.fluidTank.getSpace(), mbEachTick);
        FluidStack toBeFilled = this.fluidTank.isEmpty() ? fluidHandlerItem.drain(transferAmount, IFluidHandler.FluidAction.SIMULATE) : fluidHandlerItem.drain(ModUtils.copyFluidStackWithSize(this.fluidTank.getFluid(), transferAmount), IFluidHandler.FluidAction.SIMULATE);
        if (!toBeFilled.isEmpty()) {
            this.fluidTank.fill(
                    fluidHandlerItem.drain(toBeFilled, IFluidHandler.FluidAction.EXECUTE)
                    , IFluidHandler.FluidAction.EXECUTE
            );
            this.dataChanged = true;
        }
    }

    private void trySendFluid(Level level, BlockPos pos) {
        if (!this.fluidTank.isEmpty()) {
            for (Direction direction : AbstractMachineBlockEntity.allDirections) {
                ICapabilityProvider blockEntity = level.getBlockEntity(pos.relative(direction));
                if (blockEntity != null) {
                    blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, direction.getOpposite())
                            .ifPresent(this::trySendFluid);
                }
            }
        }
    }

    private void trySendFluid(IFluidHandler destination) {
        FluidUtil.tryFluidTransfer(destination, this.fluidTank, this.fluidTank.getSpace(), true);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("FluidTank", NbtHelper.createFluidTankTag(this.fluidTank));
        if (!this.container.isEmpty()) {
            this.container.saveContainer("FluidTankContainer", pTag);
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("FluidTank", Tag.TAG_COMPOUND)) {
            NbtHelper.loadFluidTankFrom(pTag, this.fluidTank);
        }
        if (pTag.contains("FluidTankContainer", CompoundTag.TAG_COMPOUND)) {
            this.container.loadContainer("FluidTankContainer", pTag);
        }
    }

    @Override
    public Component getDisplayName() {
        return ModBlocks.FLUID_TANK.getTranslationName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return super.level != null ? new FluidTankMenu(pContainerId, pPlayerInventory, ContainerLevelAccess.create(super.level, super.getBlockPos()), this.container, this.containerData) : null;
    }

    public SimpleFluidStorage getFluidTank() {
        return fluidTank;
    }

    @Override
    public SavableSimpleContainer getContainer() {
        return this.container;
    }
}