package com.khanhtypo.tothemoon.common.block.machine.powergenerator;

import com.google.common.base.Preconditions;
import com.khanhtypo.tothemoon.common.block.FunctionalBlock;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.SingleItemPowerBlockEntity;
import com.khanhtypo.tothemoon.common.capability.GeneratableEnergyStorage;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public abstract class AbstractPowerGeneratorBlockEntity extends SingleItemPowerBlockEntity {
    public static final int DATA_SIZE = 8;
    protected final GeneratableEnergyStorage energyStorage;
    private final int generationPerTick;
    private int burningTime;
    private int burningDuration;
    private int fuelConsumeDuration;
    private int fuelConsumeTime;

    public AbstractPowerGeneratorBlockEntity(BlockEntityObject<? extends SingleItemPowerBlockEntity> blockEntity, BlockPos blockPos, BlockState blockState, int powerCapacity, int generationPerTick) {
        super(blockEntity, blockPos, blockState, new GeneratableEnergyStorage(powerCapacity),
                be -> new ContainerData() {
                    @Override
                    public int get(int pIndex) {
                        AbstractPowerGeneratorBlockEntity generator = ((AbstractPowerGeneratorBlockEntity) be);
                        return switch (pIndex) {
                            case 0 -> be.energyStorage.getEnergyStored();
                            case 1 -> be.energyStorage.getMaxEnergyStored();
                            case 2 -> generator.burningTime;
                            case 3 -> generator.burningDuration;
                            case 4 -> generator.generationPerTick;
                            case 5 -> generator.fuelConsumeTime;
                            case 6 -> generator.fuelConsumeDuration;
                            case 7 -> be.active;
                            default -> throw new ArrayIndexOutOfBoundsException();
                        };
                    }

                    @Override
                    public void set(int pIndex, int pValue) {
                        Preconditions.checkState(pIndex == 7);
                        if ((pValue == 0 || pValue == 1)) {
                            be.setActive(pValue);
                        }
                    }

                    @Override
                    public int getCount() {
                        return DATA_SIZE;
                    }
                }
        );
        this.generationPerTick = generationPerTick;
        this.energyStorage = ((GeneratableEnergyStorage) super.energyStorage);
    }

    @Override
    protected void tick(Level level, BlockPos pos, BlockState blockState) {
        boolean litState;
        boolean flag = litState = blockState.getValue(FunctionalBlock.LIT);
        if (this.burningTime > 0) {
            if (this.fuelConsumeTime >= this.fuelConsumeDuration) {
                this.burningTime -= this.fuelConsumeDuration;
                this.fuelConsumeTime = 0;
                this.energyStorage.generatePower(this.generationPerTick * this.fuelConsumeDuration);
            } else {
                this.fuelConsumeTime++;
            }
        } else if (super.getPowerSpace() > 0) {
            if (this.isSlotPresent(0) && super.canBurn(0)) {
                this.burningTime = this.burningDuration = super.getBurnTime(0);
                this.fuelConsumeDuration = 20;
                this.fuelConsumeTime = 0;
                super.shrinkItem(0, 1);
                litState = true;
            }
        } else litState = false;

        if (flag != litState) {
            blockState = blockState.setValue(FunctionalBlock.LIT, litState);
            level.setBlock(pos, blockState, 3);
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        Level level = Objects.requireNonNull(this.getLevel());
        return new PowerGeneratorMenu(pContainerId, pInventory, ContainerLevelAccess.create(level, this.getBlockPos()), this, super.getContainerData())
                .setTargetedBlock(super.getBlockState().getBlock());
    }

    @Override
    protected void saveAdditional(CompoundTag writer) {
        super.saveAdditional(writer);
        writer.putInt("BurningTime", this.burningTime);
        writer.putInt("BurningDuration", this.burningDuration);
        writer.putInt("FuelConsumeTime", this.fuelConsumeTime);
        writer.putInt("FuelConsumeDuration", this.fuelConsumeDuration);
    }

    @Override
    public void load(CompoundTag deserializedNBT) {
        super.load(deserializedNBT);
        this.burningTime = deserializedNBT.getInt("BurningTime");
        this.burningDuration = deserializedNBT.getInt("BurningDuration");
        this.fuelConsumeDuration = deserializedNBT.getInt("FuelConsumeDuration");
        this.fuelConsumeTime = deserializedNBT.getInt("FuelConsumeTime");
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        return super.canBurn(pStack);
    }
}
