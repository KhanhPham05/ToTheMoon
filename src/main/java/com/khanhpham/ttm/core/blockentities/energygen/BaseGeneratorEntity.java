package com.khanhpham.ttm.core.blockentities.energygen;

import com.khanhpham.ttm.ToTheMoonMain;
import com.khanhpham.ttm.core.blockentities.core.BaseBlockEntity;
import com.khanhpham.ttm.core.blocks.BaseEnergyGenBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.Objects;

/**
 * @see Container
 * @see net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
 */
public abstract class BaseGeneratorEntity extends BaseBlockEntity implements MenuProvider, Container {
    public static final int STORAGE_CAPACITY = 1;
    public static final int FUEL_SLOT_INDEX = 0;
    public static final int DATA_SLOT_CAPACITY = 4;
    protected final NonNullList<ItemStack> STORED_ITEMS = NonNullList.withSize(STORAGE_CAPACITY, ItemStack.EMPTY);
    protected int burningTime;
    private int litDuration;
    private int isFull = 1;

    protected final ContainerData DATA_SLOT = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> burningTime;
                case 1 -> litDuration;
                case 2 -> getEnergyData().getEnergy();
                case 3 -> getEnergyData().getCapacity();
                default -> throw new ArrayIndexOutOfBoundsException();
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            throw new IllegalArgumentException("You Are Not Allowed To Change Anything In Here");
        }

        @Override
        public int getCount() {
            return DATA_SLOT_CAPACITY;
        }
    };

    public BaseGeneratorEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, BaseGeneratorEntity blockEntity) {
        if (blockEntity.level != null && !blockEntity.level.isClientSide) {

            //Do something when the generator is idle.
            if (blockEntity.burningTime <= 0) {
                if (!blockEntity.getEnergyData().isFull()) {
                    if (!blockEntity.STORED_ITEMS.get(FUEL_SLOT_INDEX).isEmpty()) {
                        if (ForgeHooks.getBurnTime(blockEntity.STORED_ITEMS.get(FUEL_SLOT_INDEX), null) > 0) {
                            level.setBlock(blockPos, blockState.setValue(BaseEnergyGenBlock.LIT, Boolean.TRUE), 2);
                            blockEntity.burningTime = ForgeHooks.getBurnTime(blockEntity.STORED_ITEMS.get(FUEL_SLOT_INDEX), null);
                            blockEntity.litDuration = blockEntity.burningTime;
                            blockEntity.STORED_ITEMS.get(FUEL_SLOT_INDEX).shrink(1);
                            markDirty(blockEntity.getEnergyData(), level, blockPos, blockState);
                            ToTheMoonMain.LOG.info("Detected fuel, processing now... burning time is now " + blockEntity.burningTime);
                        }
                    }
                } else {
                    level.setBlock(blockPos, blockState.setValue(BaseEnergyGenBlock.LIT, Boolean.FALSE), 2);
                }
            } else

            //Do something here when the generator is still not idle
            {
                if (Objects.requireNonNull(blockEntity.getEnergyData()).produceEnergy()) {
                    blockEntity.isFull = 0;
                    blockEntity.burningTime--;
                } else {
                    blockEntity.isFull = 1;
                    level.setBlock(blockPos, blockState.setValue(BaseEnergyGenBlock.LIT, Boolean.FALSE), 2);
                }
            }

            BlockEntity te = level.getBlockEntity(blockPos.above());
            if (te != null && te.getCapability(CapabilityEnergy.ENERGY, Direction.DOWN).isPresent() && !(te instanceof BaseGeneratorEntity)) {
                LazyOptional<IEnergyStorage> energy = te.getCapability(CapabilityEnergy.ENERGY, Direction.DOWN);
                energy.ifPresent(e -> {
                    if (e.canReceive()) {
                        e.receiveEnergy(blockEntity.getEnergyData().maxExtract, false);
                    }
                });
            }
        }
        markDirty(blockEntity.getEnergyData(), level, blockPos, blockState);
    }

    public NonNullList<ItemStack> getStoredItems() {
        return STORED_ITEMS;
    }

    @Override
    protected CompoundTag saveCustom(CompoundTag tag) {
        super.saveCustom(tag);
        tag.putInt("burningTime", burningTime);
        tag.putInt("burningDuration", litDuration);
        tag.putInt("isFull", isFull);
        return saveAllItems(tag, STORED_ITEMS);
    }

    @Override
    protected void loadCustom(CompoundTag tag) {
        super.loadCustom(tag);
        isFull = tag.getInt("isFull");
        burningTime = tag.getInt("burningTime");
        litDuration = tag.getInt("burningDuration");
        loadAllItems(tag, STORED_ITEMS);
    }

    @Override
    public int getContainerSize() {
        return STORAGE_CAPACITY;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack item : STORED_ITEMS) {
            if (item.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return STORED_ITEMS.get(pIndex);
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        return ContainerHelper.removeItem(STORED_ITEMS, pIndex, pCount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        return ContainerHelper.takeItem(STORED_ITEMS, pIndex);
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        STORED_ITEMS.set(pIndex, pStack);
    }


    @Override
    public void setChanged() {
        super.setChanged();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        assert this.level != null;
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return pPlayer.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void clearContent() {
        STORED_ITEMS.clear();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        energyOptional.invalidate();
    }
}