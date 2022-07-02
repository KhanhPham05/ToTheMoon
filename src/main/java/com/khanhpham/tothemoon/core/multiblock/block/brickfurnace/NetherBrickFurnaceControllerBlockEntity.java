package com.khanhpham.tothemoon.core.multiblock.block.brickfurnace;

import com.khanhpham.tothemoon.core.blockentities.TickableBlockEntity;
import com.khanhpham.tothemoon.core.recipes.HighHeatSmelting;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.DirectionUtils;
import com.khanhpham.tothemoon.utils.multiblock.Multiblock;
import com.khanhpham.tothemoon.utils.multiblock.MultiblockEntity;
import com.khanhpham.tothemoon.utils.multiblock.MultiblockManager;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.LinkedList;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class NetherBrickFurnaceControllerBlockEntity extends MultiblockEntity implements TickableBlockEntity {
    public static final int CONTAINER_SIZE = 4;
    public static final int MULTIBLOCK_SIZE = 27;
    public static final int DATA_COUNT = 4;
    final FluidTank fluid = new FluidTank(10000, (fluidStack -> fluidStack.getFluid().equals(Fluids.LAVA)));
    final LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> fluid);
    private final int smeltingDuration = 200;
    private final ArrayList<BlockPos> multiblockPartPositions = new ArrayList<>();
    private final LinkedList<Boolean> partDefinition = new LinkedList<>();
    private int smeltingTime;
    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> fluid.getFluidAmount();
                case 1 -> fluid.getCapacity();
                case 2 -> smeltingTime;
                case 3 -> smeltingDuration;
                default -> throw new ArrayIndexOutOfBoundsException();
            };
        }

        @Override
        public void set(int pIndex, int pValue) {

        }

        @Override
        public int getCount() {
            return DATA_COUNT;
        }
    };

    public NetherBrickFurnaceControllerBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntityTypes.BRICK_FURNACE.get(), pWorldPosition, pBlockState, CONTAINER_SIZE);
        Direction controllerFacing = pBlockState.getValue(NetherBrickFurnaceBlock.FACING);
        BlockPos worldPosition = new BlockPos(pWorldPosition);
        multiblockPartPositions.clear();

        for (int i = 0; i <= 2; i++) {
            multiblockPartPositions.add(worldPosition.below().relative(DirectionUtils.getBlockDirectionLeft(controllerFacing)));
            multiblockPartPositions.add(worldPosition.below());
            multiblockPartPositions.add(worldPosition.below().relative(DirectionUtils.getBlockDirectionRight(controllerFacing)));

            multiblockPartPositions.add(worldPosition.relative(DirectionUtils.getBlockDirectionLeft(controllerFacing)));
            multiblockPartPositions.add(worldPosition);
            multiblockPartPositions.add(worldPosition.relative(DirectionUtils.getBlockDirectionRight(controllerFacing)));

            multiblockPartPositions.add(worldPosition.above().relative(DirectionUtils.getBlockDirectionLeft(controllerFacing)));
            multiblockPartPositions.add(worldPosition.above());
            multiblockPartPositions.add(worldPosition.above().relative(DirectionUtils.getBlockDirectionRight(controllerFacing)));
            worldPosition = worldPosition.relative(controllerFacing.getOpposite());
        }
    }

    @Override
    protected Component getDefaultName() {
        return ModBlocks.NETHER_BRICK_FURNACE_CONTROLLER.get().getName();
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new NetherBrickFurnaceControllerMenu(pContainerId, pInventory, this, data);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!remove && cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return this.fluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        checkMultiblock(level);
        final Direction controllerFacing = state.getValue(NetherBrickFurnaceBlock.FACING);

        items.get(3).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(fluid -> {
            if (fluid.getFluidInTank(0).getFluid().isSame(Fluids.LAVA)) {
                this.fluid.fill(fluid.drain(this.fluid.getSpace(), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
            }
        });

        if (this.getMultiblock() != null) {
            @Nullable Recipe<Container> recipe = level.getRecipeManager().getRecipeFor(HighHeatSmelting.RECIPE_TYPE, this, level).orElse(null);
            if (recipe != null && this.smeltingTime >= this.smeltingDuration) {
                makeResult(recipe);
                state = state.setValue(NetherBrickFurnaceBlock.LIT, true);
            } else
                //Process starts
                if (recipe != null && this.fluid.getFluidAmount() > 0 && !items.get(0).isEmpty() && !items.get(1).isEmpty()) {
                    state = state.setValue(NetherBrickFurnaceBlock.LIT, true);
                    smeltingTime++;
                    fluid.drain(3, IFluidHandler.FluidAction.EXECUTE);

                } else {
                    if (this.smeltingTime > 0) this.smeltingTime = Mth.clamp(smeltingTime - 2, 0, smeltingDuration);
                    state = state.setValue(NetherBrickFurnaceBlock.LIT, false);
                }
        } else {
            // Drop all the items out.
            if (!this.items.isEmpty()) {
                Containers.dropContents(level, pos.relative(controllerFacing), this.items);
            }
            state = state.setValue(NetherBrickFurnaceBlock.LIT, false);
        }


        level.setBlock(pos, state, 3);
        setChanged(level, pos, state);
    }

    private void makeResult(@Nullable Recipe<Container> recipe) {
        if (recipe != null) {
            ItemStack result = recipe.assemble(this);
            if (items.get(2).isEmpty()) {
                items.set(2, result);
            } else if (items.get(2).sameItem(result) && items.get(2).getCount() + result.getCount() <= getMaxStackSize()) {
                items.get(2).grow(1);
            }
            items.get(0).shrink(1);
            items.get(1).shrink(1);
            this.smeltingTime = 0;
        }
    }

    private void checkMultiblock(Level level) {
        if (this.multiblock == null) {
            this.partDefinition.clear();
            Multiblock.Builder builder = Multiblock.Builder.setup(MULTIBLOCK_SIZE);

            for (int i = 0; i < multiblockPartPositions.size(); i++) {
                Block block = level.getBlockState(multiblockPartPositions.get(i)).getBlock();
                if (i == 4 && block.equals(ModBlocks.NETHER_BRICK_FURNACE_CONTROLLER.get())) {
                    partDefinition.add(4, true);
                    builder.addPart(multiblockPartPositions.get(i), Multiblock.PartType.CONTROLLER);
                } else if (i == 13 && block.equals(Blocks.BLAST_FURNACE)) {
                    partDefinition.add(13, true);
                    builder.addPart(multiblockPartPositions.get(i), Multiblock.PartType.PART);
                } else
                    switch (i) {
                        case 6, 7, 8, 15, 16, 17, 24, 25, 26 -> {
                            if (block.equals(ModBlocks.SMOOTH_BLACKSTONE.get())) {
                                partDefinition.add(i, true);
                                builder.addPart(multiblockPartPositions.get(i), Multiblock.PartType.PART);
                            } else partDefinition.add(i, false);
                        }
                        default -> {
                            if (block.equals(Blocks.NETHER_BRICKS)) {
                                builder.addPart(multiblockPartPositions.get(i), Multiblock.PartType.PART);
                                partDefinition.add(i, true);
                            } else partDefinition.add(i, false);
                        }
                    }
            }

            if (!partDefinition.contains(false)) {
                this.multiblock = MultiblockManager.INSTANCE.addMultiblock(builder.build());
                //ModUtils.log("Multiblock with controller at {} has formed", controllerPos);
                //ModdedTriggers.MULTIBLOCK_FORMED.trigger(ModUtils.getNearestPlayer(level, controllerPos, 15.0d), MultiblockFormedTrigger.MultiblockType.NETHER_BRICK_FURNACE);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        this.fluid.writeToNBT(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        fluid.readFromNBT(pTag);
    }

    @Override
    public Component getDisplayName() {
        return ModLanguage.NETHER_BRICK_FURNACE_CONTROLLER;
    }
}
