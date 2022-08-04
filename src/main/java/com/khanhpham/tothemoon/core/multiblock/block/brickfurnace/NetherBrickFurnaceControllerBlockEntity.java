package com.khanhpham.tothemoon.core.multiblock.block.brickfurnace;

import com.khanhpham.tothemoon.advancements.ModdedTriggers;
import com.khanhpham.tothemoon.advancements.MultiblockFormedTrigger;
import com.khanhpham.tothemoon.core.blockentities.FluidCapableBlockEntity;
import com.khanhpham.tothemoon.core.blockentities.TickableBlockEntity;
import com.khanhpham.tothemoon.core.blocks.workbench.WorkbenchBlock;
import com.khanhpham.tothemoon.core.recipes.HighHeatSmeltingRecipe;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.DirectionUtils;
import com.khanhpham.tothemoon.utils.multiblock.Multiblock;
import com.khanhpham.tothemoon.utils.multiblock.MultiblockEntity;
import com.khanhpham.tothemoon.utils.multiblock.MultiblockManager;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class NetherBrickFurnaceControllerBlockEntity extends MultiblockEntity implements TickableBlockEntity, FluidCapableBlockEntity {
    public static final int CONTAINER_SIZE = 4;
    public static final int MULTIBLOCK_SIZE = 27;
    public static final int DATA_COUNT = 4;
    public static final int TANK_CAPACITY = 10000;
    final FluidTank tank = new FluidTank(TANK_CAPACITY, (fluidStack -> fluidStack.getFluid().equals(Fluids.LAVA)));
    final LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> tank);
    private final int smeltingDuration = 200;
    private final ArrayList<BlockPos> multiblockPartPositions = new ArrayList<>();
    private final NonNullList<Boolean> partDefinition = NonNullList.withSize(MULTIBLOCK_SIZE, false);
    private final Multiblock.Builder builder = Multiblock.Builder.setup(MULTIBLOCK_SIZE);
    private int smeltingTime;
    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> tank.getFluidAmount();
                case 1 -> tank.getCapacity();
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
        super(ModBlockEntities.BRICK_FURNACE.get(), pWorldPosition, pBlockState, CONTAINER_SIZE);
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

    /**
     * Multiblock checking is now moved to {@link NetherBrickFurnaceBlock#use(BlockState, Level, BlockPos, Player, InteractionHand, BlockHitResult)}
     */
    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        //checkMultiblock(level);
        final Direction controllerFacing = state.getValue(NetherBrickFurnaceBlock.FACING);
        if (this.getMultiblock() != null) {
            if (!getItem(3).isEmpty()) {
                ItemStack tankItem = items.get(3);
                if (tankItem.is(Items.LAVA_BUCKET) && this.tank.getSpace() >= 1000) {
                    setItem(3, new ItemStack(Items.BUCKET));
                    this.tank.fill(new FluidStack(Fluids.LAVA, 1000), IFluidHandler.FluidAction.EXECUTE);
                } else if (this.tank.getSpace() > 0) {
                    LazyOptional<IFluidHandlerItem> cap = tankItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
                    if (cap.isPresent())
                        this.tank.fill(cap.map(fluid -> fluid.drain(new FluidStack(Fluids.LAVA, this.tank.getSpace()), IFluidHandler.FluidAction.EXECUTE)).get(), IFluidHandler.FluidAction.EXECUTE);

                }
            }

            @Nullable Recipe<Container> recipe = level.getRecipeManager().getRecipeFor(HighHeatSmeltingRecipe.RECIPE_TYPE, this, level).orElse(null);
            if (recipe != null && this.smeltingTime >= this.smeltingDuration) {
                makeResult(recipe);
                state = state.setValue(NetherBrickFurnaceBlock.LIT, true);
            } else
                //Process starts
                if (recipe != null && this.tank.getFluidAmount() > 0 && !items.get(0).isEmpty() && !items.get(1).isEmpty()) {
                    state = state.setValue(NetherBrickFurnaceBlock.LIT, true);
                    smeltingTime++;
                    tank.drain(3, IFluidHandler.FluidAction.EXECUTE);

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


    public void checkMultiblock(Level level) {
        if (this.getMultiblock() == null) {
            this.partDefinition.clear();

            for (int i = 0; i < multiblockPartPositions.size(); i++) {
                Block block = level.getBlockState(multiblockPartPositions.get(i)).getBlock();
                if (i == 4 && block.equals(ModBlocks.NETHER_BRICK_FURNACE_CONTROLLER.get())) {
                    partDefinition.set(4, true);
                    builder.addPart(multiblockPartPositions.get(i), Multiblock.PartType.CONTROLLER);
                } else if (i == 13 && block.equals(Blocks.BLAST_FURNACE)) {
                    partDefinition.set(13, true);
                    builder.addPart(multiblockPartPositions.get(i), Multiblock.PartType.PART);
                } else
                    switch (i) {
                        case 6, 8,
                                15, 16, 17,
                                24, 25, 26 -> check(block.equals(ModBlocks.SMOOTH_BLACKSTONE.get()), i);
                        case 7 ->
                                check((block.equals(ModBlocks.BLACKSTONE_FLUID_ACCEPTOR.get()) && ((FluidAcceptorBlock) block).isNotObstructed()) || block.equals(ModBlocks.SMOOTH_BLACKSTONE.get()), i);

                        case 1, 3, 5 ->
                                check((block.equals(ModBlocks.NETHER_BRICKS_FLUID_ACCEPTOR.get()) && ((FluidAcceptorBlock) block).isNotObstructed()) || block.equals(Blocks.NETHER_BRICKS), i);
                        default -> this.check(block.equals(Blocks.NETHER_BRICKS), i);
                    }
            }

            //Make sure the furnace controller is facing outside
            if (!partDefinition.contains(false) && level.getBlockState(this.worldPosition.relative(this.getBlockState().getValue(NetherBrickFurnaceBlock.FACING))).isAir()) {
                this.setMultiblock(MultiblockManager.INSTANCE.addMultiblock(builder.build()));
                if (level.getNearestPlayer(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 10d, (entity) -> entity instanceof ServerPlayer) instanceof ServerPlayer serverPlayer) {
                    ModdedTriggers.MULTIBLOCK_FORMED.trigger(serverPlayer, MultiblockFormedTrigger.MultiblockType.NETHER_BRICK_FURNACE);
                }
                this.setAllAcceptor(this);
            } else {
                this.builder.clearAll();
            }
        }
    }

    private void check(boolean condition, int i) {
        if (condition) {
            partDefinition.set(i, true);
            builder.addPart(multiblockPartPositions.get(i), Multiblock.PartType.PART);
        } else partDefinition.set(i, false);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        this.tank.writeToNBT(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        tank.readFromNBT(pTag);
    }

    @Override
    public Component getDisplayName() {
        return ModLanguage.NETHER_BRICK_FURNACE_CONTROLLER;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::saveWithoutMetadata);
    }

    @Override
    public FluidTank getTank() {
        return this.tank;
    }

    public void fillFromBucket(Player pPlayer) {
        if (this.tank.getSpace() >= FluidAttributes.BUCKET_VOLUME) {
            this.tank.fill(new FluidStack(Fluids.LAVA, FluidAttributes.BUCKET_VOLUME), IFluidHandler.FluidAction.EXECUTE);
            pPlayer.playSound(SoundEvents.BUCKET_EMPTY_LAVA, 1.0f, 1.0f);
        }
    }

    @Override
    public void onRemove() {
        this.setAllAcceptor(null);
    }

    private BlockPos[] getAcceptorDirections() {
        BlockPos[] pos = new BlockPos[4];
        Direction facingDirection = this.getBlockState().getValue(NetherBrickFurnaceBlock.FACING).getOpposite();
        pos[0] = super.worldPosition.above();
        pos[1] = super.worldPosition.relative(WorkbenchBlock.getRightDirection(facingDirection));
        pos[2] = super.worldPosition.below();
        pos[3] = super.worldPosition.relative(WorkbenchBlock.getLeftDirection(facingDirection));
        return pos;
    }

    private void setAllAcceptor(@Nullable NetherBrickFurnaceControllerBlockEntity be) {
        for (BlockPos acceptorDirection : this.getAcceptorDirections()) {
            if (this.level != null && this.level.getBlockState(acceptorDirection).getBlock() instanceof FluidAcceptorBlock fluidAcceptorBlock) {
                fluidAcceptorBlock.setControllerBe(be);
            }
        }
    }
}