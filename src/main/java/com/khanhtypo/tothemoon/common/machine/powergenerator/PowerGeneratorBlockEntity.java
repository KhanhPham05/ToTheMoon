package com.khanhtypo.tothemoon.common.machine.powergenerator;

import com.khanhtypo.tothemoon.common.block.FunctionalBlock;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.AbstractMachineBlockEntity;
import com.khanhtypo.tothemoon.common.capability.GeneratablePowerStorage;
import com.khanhtypo.tothemoon.common.machine.CompoundTagSerializable;
import com.khanhtypo.tothemoon.common.machine.MachineRedstoneMode;
import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.Optional;

public class PowerGeneratorBlockEntity extends AbstractMachineBlockEntity<PowerGeneratorBlockEntity> implements CompoundTagSerializable {
    public static final int DATA_SIZE = 9;
    public final GeneratablePowerStorage energyStorage;
    private final int generationPerTick;
    private int burningTime;
    private int burningDuration;

    public PowerGeneratorBlockEntity(BlockEntityObject<PowerGeneratorBlockEntity> blockEntity, BlockPos blockPos, BlockState blockState, PowerGeneratorLevels generatorLevel) {
        super(blockEntity, blockPos, blockState, 1, new GeneratablePowerStorage(generatorLevel.capacity),
                be -> new ContainerData() {
                    @Override
                    public int get(int pIndex) {
                        return switch (pIndex) {
                            case 0 -> be.energyStorage.getEnergyStored();
                            case 1 -> be.energyStorage.getMaxEnergyStored();
                            case 2 -> be.burningTime;
                            case 3 -> be.burningDuration;
                            case 4 -> be.getEnergyGenerated();
                            case 5 -> be.fuelConsumeTime;
                            case 6 -> be.fuelConsumeDuration;
                            case 7 -> be.redstoneMode.getIndex();
                            case 8 -> be.active ? 1 : 0;
                            default -> throw new ArrayIndexOutOfBoundsException();
                        };
                    }

                    @Override
                    public void set(int pIndex, int pValue) {
                        switch (pIndex) {
                            case 7 -> be.redstoneMode = MachineRedstoneMode.valueFromIndex(pValue);
                            case 8 -> be.active = pValue == 1;
                        }
                    }

                    @Override
                    public int getCount() {
                        return DATA_SIZE;
                    }
                }
        );
        this.generationPerTick = generatorLevel.generationPerTick;
        this.energyStorage = ((GeneratablePowerStorage) super.energyStorage);

    }

    public static BlockEntityType.BlockEntitySupplier<PowerGeneratorBlockEntity> createSupplier() {
        return (blockPos, blockState) -> new PowerGeneratorBlockEntity(ModBlockEntities.POWER_GENERATOR, blockPos, blockState, Optional.of(blockState.getBlock()).filter(block -> block instanceof PowerGeneratorBlock).map(g -> ((PowerGeneratorBlock) g).getGeneratorLevel()).orElseThrow());
    }

    @Override
    protected void tick(Level level, BlockPos pos, BlockState blockState) {
        boolean litState;
        boolean flag = litState = blockState.getValue(FunctionalBlock.LIT);
        if (this.burningTime > 0) {
            if (this.fuelConsumeTime >= this.fuelConsumeDuration) {
                this.burningTime -= this.fuelConsumeDuration;
                this.fuelConsumeTime = 0;
                this.energyStorage.generatePower(this.getEnergyGenerated());
            } else {
                if (this.energyStorage.isFull()) {
                    litState = false;
                } else {
                    this.fuelConsumeTime++;
                    litState = true;
                }
            }
        } else if (super.getPowerSpace() > 0) {
            if (super.isSlotPresent(0) && super.canBurn(0)) {
                this.burningTime = this.burningDuration = super.getBurnTime(0);
                this.fuelConsumeTime = 0;
                super.shrinkItem(0, 1);
                litState = true;
            } else litState = false;
        } else litState = false;

        if (flag != litState) {
            blockState = blockState.setValue(FunctionalBlock.LIT, litState);
            level.setBlock(pos, blockState, 3);
        }
    }

    public int getEnergyGenerated() {
        return this.generationPerTick * fuelConsumeDuration;
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        Level level = Objects.requireNonNull(this.getLevel());
        return new PowerGeneratorMenu(pContainerId, pInventory, ContainerLevelAccess.create(level, this.getBlockPos()), this, super.upgradeContainer, super.getContainerData())
                .setTargetedBlock(super.getBlockState().getBlock());
    }

    @Override
    protected void saveAdditional(CompoundTag writer) {
        super.saveAdditional(writer);
        writer.putInt("BurningTime", this.burningTime);
        writer.putInt("BurningDuration", this.burningDuration);
    }

    @Override
    public void load(CompoundTag deserializedNBT) {
        super.load(deserializedNBT);
        this.burningTime = deserializedNBT.getInt("BurningTime");
        this.burningDuration = deserializedNBT.getInt("BurningDuration");
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        return super.canBurn(pStack);
    }
}
