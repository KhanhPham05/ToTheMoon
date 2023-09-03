package com.khanhtypo.tothemoon.multiblock.blackstonefurnace;

import com.google.common.base.Preconditions;
import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.SavableSimpleContainer;
import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import com.khanhtypo.tothemoon.multiblock.IItemCapableMultiblockController;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.display.BlackStoneFurnaceMenu;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.ioparts.entity.BlackStoneFurnaceItemAcceptorPartEntity;
import com.khanhtypo.tothemoon.registration.ModBlocks;
import com.khanhtypo.tothemoon.registration.ModRecipeTypes;
import com.khanhtypo.tothemoon.serverdata.recipes.LavaSmeltingRecipe;
import com.khanhtypo.tothemoon.serverdata.recipes.serializers.LavaSmeltingRecipeSerializer;
import com.khanhtypo.tothemoon.utls.ModUtils;
import it.zerono.mods.zerocore.lib.fluid.FluidTank;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockController;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.AbstractCuboidMultiblockController;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class MultiblockBlackStoneFurnace extends AbstractCuboidMultiblockController<MultiblockBlackStoneFurnace> implements ContainerData, IItemCapableMultiblockController {
    public static final TagKey<Block> BLACKSTONE_ACCEPTABLE = BlockTags.create(ModUtils.location("multiblock_blackstone_furnace_acceptable/blackstone"));
    public static final TagKey<Block> NETHER_BRICK_ACCEPTABLE = BlockTags.create(ModUtils.location("multiblock_blackstone_furnace_acceptable/nether_bricks"));
    public static final BiPredicate<Integer, FluidStack> FLUID_CHECKER = (i, fluid) -> fluid.getFluid().isSame(Fluids.LAVA);
    public static final int DEFAULT_BURNING_DURATION = LavaSmeltingRecipeSerializer.DEFAULT_SMELTING_TICK;
    public static final Predicate<IMultiblockPart<MultiblockBlackStoneFurnace>> ITEM_ACCEPTOR_FILTER = p -> p instanceof BlackStoneFurnaceItemAcceptorPartEntity;
    private static final Predicate<IMultiblockPart<MultiblockBlackStoneFurnace>> CONTROLLER_FILTER = p -> p instanceof ControllerPartEntity;
    private final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
    private final FluidTank lavaHolder;
    SavableSimpleContainer itemStackHolder;
    private List<BlackStoneFurnaceItemAcceptorPartEntity> itemAcceptors;
    private ControllerPartEntity controllerPart;
    private int burningTime = 0;
    private int burningDuration = DEFAULT_BURNING_DURATION;
    private boolean serverUpdated = false;
    private BlockPos lastAddedPartPos;

    public MultiblockBlackStoneFurnace(Level world) {
        super(world);
        this.lavaHolder = new FluidTank(15000, FLUID_CHECKER);
        this.itemAcceptors = new LinkedList<>();
        this.itemStackHolder = new SavableSimpleContainer(null, 3) {
            @Override
            public boolean canPlaceItem(int pIndex, ItemStack pStack) {
                return pIndex != 1;
            }

            @Override
            public boolean canTakeItem(Container pTarget, int pIndex, ItemStack pStack) {
                if (pIndex == 2 && BlackStoneFurnaceMenu.isBucketOrHandler(pStack)) {
                    return true;
                }

                return pIndex == 1;
            }

            @Override
            public void setChanged() {
                MultiblockBlackStoneFurnace.this.markDirty();
            }
        };
    }

    public SavableSimpleContainer getItemStackHolder() {
        return itemStackHolder;
    }

    @Override
    protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
        int i = super.getPartsCount(CONTROLLER_FILTER);
        if (i == 0) {
            validatorCallback.setLastError(ModLanguageGenerator.NO_CONTROLLER);
            return false;
        } else if (i > 1) {
            validatorCallback.setLastError(ModLanguageGenerator.FURNACE_TOO_MUCH_CONTROLLERS);
            return false;
        }

        boolean machineWhole = super.isMachineWhole(validatorCallback);

        if (machineWhole) {
            this.itemAcceptors = super.getConnectedParts(ITEM_ACCEPTOR_FILTER).map(p -> ((BlackStoneFurnaceItemAcceptorPartEntity) p)).collect(LinkedList::new, List::add, List::addAll);
        } else {
            this.itemAcceptors.clear();
        }

        return machineWhole;
    }

    @Override
    protected void onPartAdded(IMultiblockPart<MultiblockBlackStoneFurnace> iMultiblockPart) {
        if (CONTROLLER_FILTER.test(iMultiblockPart)) {
            this.controllerPart = ((ControllerPartEntity) iMultiblockPart);
        }
        this.lastAddedPartPos = iMultiblockPart.getWorldPosition();
    }

    @Override
    protected void onPartRemoved(IMultiblockPart<MultiblockBlackStoneFurnace> iMultiblockPart) {
        if (CONTROLLER_FILTER.test(iMultiblockPart)) {
            ToTheMoon.LOGGER.info("Controller at %s removed".formatted(iMultiblockPart.getWorldPosition()));
            //if (!this.itemStackHolder.isEmpty()) {
            //    super.callOnLogicalServer(() -> Containers.dropContents(getWorld(), iMultiblockPart.getWorldPosition(), itemStackHolder));
            //    itemStackHolder.setAllEmpty();
            //    this.lavaHolder.setContent(FluidStack.EMPTY);
            //}
            this.controllerPart = null;
        }
        if (iMultiblockPart instanceof BlackStoneFurnaceItemAcceptorPartEntity itemAcceptorPart) {
            this.itemAcceptors.remove(itemAcceptorPart);
        }
    }

    @Override
    protected void onMachineAssembled() {
    }

    @Override
    protected void onMachineRestored() {
    }

    @Override
    protected void onMachinePaused() {
        this.turnControllerState(false);
    }

    @Override
    protected void onMachineDisassembled() {
        this.turnControllerState(false);
    }

    @Override
    protected int getMinimumNumberOfPartsForAssembledMachine() {
        return 26;
    }

    @Override
    protected int getMaximumXSize() {
        return 3;
    }

    @Override
    protected int getMaximumZSize() {
        return 3;
    }

    @Override
    protected int getMaximumYSize() {
        return 3;
    }

    @Override
    protected int getMinimumXSize() {
        return 3;
    }

    @Override
    protected int getMinimumYSize() {
        return 3;
    }

    @Override
    protected int getMinimumZSize() {
        return 3;
    }

    @Override
    protected void onAssimilate(IMultiblockController<MultiblockBlackStoneFurnace> toMerged) {
        if (toMerged instanceof MultiblockBlackStoneFurnace otherFurnace) {
            FluidUtil.tryFluidTransfer(this.lavaHolder, otherFurnace.lavaHolder, this.lavaHolder.getFreeSpace(), true);
            for (int i = 0; i < this.getSlots(); i++) {
                ItemStack spareItem = this.insertItem(i, otherFurnace.getStackInSlot(i), false);
                if (!spareItem.isEmpty()) {
                    BlockPos pos = this.controllerPart != null ? this.controllerPart.getWorldPosition() : this.lastAddedPartPos;
                    Containers.dropItemStack(this.getWorld(), pos.getX(), pos.getY(), pos.getZ(), spareItem);
                }
            }
        } else {
            String mergeFrom = toMerged.getClass().getSimpleName();
            ToTheMoon.LOGGER.warn("[{} -> {}] Trying to assimilate unmatched multiblock. The data of {} may be deleted", mergeFrom, getClass().getSimpleName(), mergeFrom);
        }
        this.resetTime();
        this.turnControllerState(false);
    }

    @Override
    protected void onAssimilated(IMultiblockController<MultiblockBlackStoneFurnace> iMultiblockController) {

    }

    @Override
    public CompoundTag syncDataTo(CompoundTag data, SyncReason syncReason) {
        super.syncDataTo(data, syncReason);
        this.itemStackHolder.saveContainer("BLFurnace", data);
        this.lavaHolder.syncDataTo(data, syncReason);
        data.putInt("BurningTime", this.burningTime);
        return data;
    }

    @Override
    public void syncDataFrom(CompoundTag data, SyncReason syncReason) {
        super.syncDataFrom(data, syncReason);
        this.itemStackHolder.loadContainer("BLFurnace", data);
        this.lavaHolder.syncDataFrom(data, syncReason);
        this.burningTime = data.getInt("BurningTime");
    }

    @Override
    protected boolean updateServer() {
        ItemStack item;
        if (!this.itemStackHolder.isSlotEmpty(2) && this.lavaHolder.getFreeSpace() > 0) {
            item = this.itemStackHolder.getItem(2);
            if (item.is(Items.LAVA_BUCKET) && this.lavaHolder.getFreeSpace() >= FluidType.BUCKET_VOLUME) {
                this.itemStackHolder.setItem(2, item.getCraftingRemainingItem());
                this.lavaHolder.fill(new FluidStack(Fluids.LAVA, FluidType.BUCKET_VOLUME), IFluidHandler.FluidAction.EXECUTE);
            } else {
                this.lavaHolder.fill(item.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(fluid -> FluidUtil.tryFluidTransfer(this.lavaHolder, fluid, Math.min(this.lavaHolder.getFreeSpace(), fluid.getTankCapacity(0) - fluid.getFluidInTank(0).getAmount()), true)).orElse(FluidStack.EMPTY), IFluidHandler.FluidAction.EXECUTE);
            }
            markDirty();
        }

        if (!this.lavaHolder.isEmpty()) {
            if (!this.itemStackHolder.isSlotEmpty(0) && this.itemStackHolder.isSlotAvailable(1)) {
                Optional<LavaSmeltingRecipe> optional = ModUtils.getRecipeFor(super.getWorld(), ModRecipeTypes.LAVA_SMELTING, this.getItemStackHolder());
                if (optional.isPresent()) {
                    if (this.burningTime < (this.burningDuration = optional.get().getSmeltingTick())) {
                        this.burningTime++;
                        if (Math.floorMod(this.burningTime, 2) == 0)
                            this.lavaHolder.drain(1, IFluidHandler.FluidAction.EXECUTE);
                    } else {
                        this.burningTime = 0;
                        this.emptyOrGrowResultSlot(optional.get());
                    }
                    this.turnControllerState(true);
                } else {
                    this.resetTime();
                }
            } else {
                this.resetTime();
            }
        } else if (this.burningTime > 0) {
            this.burningTime = Math.max(0, this.burningTime - 3);
            this.markDirty();
        }

        boolean flag = this.serverUpdated;
        this.serverUpdated = false;
        return flag;
    }

    private void emptyOrGrowResultSlot(LavaSmeltingRecipe recipe) {
        ItemStack resultSlot = this.itemStackHolder.getItem(1);
        if (resultSlot.isEmpty()) {
            itemStackHolder.setItem(1, recipe.getResultItem(super.getWorld().registryAccess()));
        } else {
            resultSlot.grow(1);
            this.itemStackHolder.setItem(1, resultSlot);
        }
        this.itemStackHolder.getItem(0).shrink(1);
        markDirty();
    }

    private void resetTime() {
        if (this.burningTime > 0) {
            this.burningTime = 0;
        }
        this.burningDuration = DEFAULT_BURNING_DURATION;
        this.markDirty();

        this.turnControllerState(false);
    }

    private void turnControllerState(boolean lit) {
        if (this.controllerPart != null && lit != super.getWorld().getBlockState(this.controllerPart.getWorldPosition()).getValue(BlockStateProperties.LIT))
            callOnLogicalServer(() -> ModUtils.changeBlockState(super.getWorld(), this.controllerPart, BlockStateProperties.LIT, lit, true));
    }

    private void markDirty() {
        if (!serverUpdated)
            this.serverUpdated = true;
    }

    @Override
    protected void updateClient() {

    }

    @Override
    protected boolean isBlockGoodForFrame(Level level, int x, int y, int z, IMultiblockValidator iMultiblockValidator) {
        if (!level.getBlockState(this.mutableBlockPos.set(x, y, z)).is(BLACKSTONE_ACCEPTABLE)) {
            iMultiblockValidator.setLastError(
                    this.mutableBlockPos,
                    ModLanguageGenerator.FRAME_MUST_BE_BLACKSTONE_ACCEPTABLE,
                    ModBlocks.SMOOTH_BLACKSTONE.getTranslationName(),
                    ModBlocks.BLACKSTONE_EMPTY_ACCEPTOR.getTranslationName()
            );
            return false;
        }


        return true;
    }

    @Override
    protected boolean isBlockGoodForTop(Level level, int x, int y, int z, IMultiblockValidator iMultiblockValidator) {
        BlockPos pos = this.mutableBlockPos.set(x, y, z);
        BlockState blockAt = level.getBlockState(pos);
        if (this.isBlockSurroundedByBlackStone(level, x, y, z, Direction.Plane.HORIZONTAL)) {
            if (blockAt.is(NETHER_BRICK_ACCEPTABLE)) {
                return true;
            } else {
                iMultiblockValidator.setLastError(pos, ModLanguageGenerator.NETHER_BRICK_MISPLACED);
                return false;
            }
        }

        if (blockAt.is(BLACKSTONE_ACCEPTABLE)) {
            return true;
        }

        iMultiblockValidator.setLastError(pos, ModLanguageGenerator.BLOCK_MISMATCHED);
        return false;
    }

    @Override
    protected boolean isBlockGoodForBottom(Level level, int x, int y, int z, IMultiblockValidator iMultiblockValidator) {
        return this.isBlockGoodForTop(level, x, y, z, iMultiblockValidator);
    }

    @Override
    protected boolean isBlockGoodForSides(Level level, int x, int y, int z, IMultiblockValidator iMultiblockValidator) {
        BlockPos blockPos = this.mutableBlockPos.set(x, y, z);
        BlockState state = level.getBlockState(blockPos);
        if (state.is(NETHER_BRICK_ACCEPTABLE)) {
            if (this.isBlockSurroundedByBlackStone(level, x, y, z, Direction.Plane.VERTICAL)) {
                return true;
            } else {
                iMultiblockValidator.setLastError(blockPos, ModLanguageGenerator.NETHER_BRICK_MISPLACED);
                return false;
            }
        } else if (ModBlocks.BLACK_STONE_FURNACE_CONTROLLER.isSame(state)) {
            if (level.getBlockState(blockPos.relative(state.getValue(HorizontalDirectionalBlock.FACING).getOpposite())).is(Tags.Blocks.STORAGE_BLOCKS_COAL)) {
                return true;
            } else {
                iMultiblockValidator.setLastError(blockPos, ModLanguageGenerator.CONTROLLER_FACING_ERROR);
                return false;
            }
        } else if (state.is(BLACKSTONE_ACCEPTABLE)) {
            return true;
        }

        iMultiblockValidator.setLastError(blockPos, ModLanguageGenerator.BLOCK_MISMATCHED);
        return false;
    }

    @Override
    protected boolean isBlockGoodForInterior(Level level, int x, int y, int z, IMultiblockValidator iMultiblockValidator) {
        if (!level.getBlockState(this.mutableBlockPos.set(x, y, z)).is(Tags.Blocks.STORAGE_BLOCKS_COAL)) {
            iMultiblockValidator.setLastError(this.mutableBlockPos, ModLanguageGenerator.BLOCK_MISMATCHED);
            return false;
        }

        return true;
    }

    private boolean isBlockSurroundedByBlackStone(Level level, int x, int y, int z, Direction.Plane type) {
        BlockPos blockPos = new BlockPos(x, y, z);
        for (Direction direction : type) {
            if (!level.getBlockState(blockPos.relative(direction)).is(BLACKSTONE_ACCEPTABLE)) {
                return false;
            }
        }

        return true;
    }

    //CONTAINER DATA

    @Override
    @SuppressWarnings("deprecation")
    public int get(int pIndex) {
        return switch (pIndex) {
            case 0 -> BuiltInRegistries.FLUID.getId(this.lavaHolder.getFluid().getFluid());
            case 1 -> this.lavaHolder.getFluidAmount();
            case 2 -> this.lavaHolder.getCapacity();
            case 3 -> this.burningTime;
            case 4 -> this.burningDuration;
            default -> throw new IllegalStateException("Unexpected value: " + pIndex);
        };
    }

    @Override
    public void set(int pIndex, int pValue) {
    }

    @Override
    public int getCount() {
        return BlackStoneFurnaceMenu.CONTAINER_DATA_SIZE;
    }

    //ITEM HANDLER
    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        this.itemStackHolder.setItem(slot, stack);
    }

    @Override
    public int getSlots() {
        return this.itemStackHolder.getContainerSize();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return this.itemStackHolder.getItem(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        Preconditions.checkState(slot >= 0 && slot <= 2, "Unknown slot " + slot);
        if (slot != 1) {
            if (BlackStoneFurnaceMenu.isBucketOrHandler(stack)) {
                if (slot == 2) {
                    ItemStack fluidSlot = this.itemStackHolder.getItem(slot);
                    if (fluidSlot.isEmpty()) {
                        if (!simulate) {
                            this.itemStackHolder.setItem(slot, stack);
                        }
                        return ItemStack.EMPTY;
                    }
                }

                return stack;
            } else if (slot == 0) {
                ItemStack ingredientSlot = this.itemStackHolder.getItem(slot);
                if (ingredientSlot.isEmpty()) {
                    if (!simulate) {
                        this.itemStackHolder.setItem(slot, stack);
                    }
                    return ItemStack.EMPTY;
                } else if (this.itemStackHolder.getSlotSpace(slot) == 0) {
                    return stack;
                } else if (ingredientSlot.is(stack.getItem())) {
                    int ingredientSpace = this.itemStackHolder.getSlotSpace(slot);
                    if (ingredientSpace >= stack.getCount()) {
                        if (!simulate) {
                            ingredientSlot.grow(stack.getCount());
                        }
                        return ItemStack.EMPTY;
                    } else {
                        int remaining = stack.getCount() - ingredientSpace;
                        if (!simulate) {
                            ingredientSlot.grow(ingredientSpace);
                        }
                        return stack.copyWithCount(remaining);
                    }
                }
            }
        }
        return stack;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        Preconditions.checkState(slot >= 0 && slot <= 2, "Unknown slot " + slot);
        if (slot > 0) {
            if (slot == 1) {
                ItemStack resultSlot = this.itemStackHolder.getItem(slot);
                if (!resultSlot.isEmpty()) {
                    if (amount >= resultSlot.getCount()) {
                        if (!simulate) {
                            this.itemStackHolder.setItem(slot, ItemStack.EMPTY);
                        }
                        return resultSlot.copy();
                    } else {
                        if (!simulate) {
                            resultSlot.shrink(amount);
                        }
                        return resultSlot.copyWithCount(amount);
                    }
                } else {
                    return ItemStack.EMPTY;
                }
            } else {
                ItemStack fluidSlot = this.itemStackHolder.getItem(slot);
                if (!fluidSlot.isEmpty()) {
                    if (fluidSlot.is(Items.BUCKET)) {
                        if (!simulate) {
                            this.itemStackHolder.setItem(slot, ItemStack.EMPTY);
                        }
                        return fluidSlot.copy();
                    } else {
                        var fluidItemHolder = fluidSlot.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
                        if (fluidItemHolder.isPresent()) {
                            return fluidItemHolder.map(handler -> {
                                if (handler.getFluidInTank(0).isEmpty()) {
                                    if (!simulate) {
                                        this.itemStackHolder.setItem(slot, ItemStack.EMPTY);
                                    }
                                    return fluidSlot.copy();
                                }
                                return ItemStack.EMPTY;
                            }).orElse(ItemStack.EMPTY);
                        }
                    }
                }
                return ItemStack.EMPTY;
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return slot == 2 ? 1 : 64;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return slot != 2 || BlackStoneFurnaceMenu.isBucketOrHandler(stack);
    }
}
