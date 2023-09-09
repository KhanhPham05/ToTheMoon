package com.khanhtypo.tothemoon.common.machine.powergenerator;

import com.khanhtypo.tothemoon.common.block.EnergyBlock;
import com.khanhtypo.tothemoon.common.block.FunctionalBlock;
import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PowerGeneratorBlock extends FunctionalBlock<PowerGeneratorBlockEntity> implements EnergyBlock {
    private final PowerGeneratorLevels generatorLevel;

    public PowerGeneratorBlock(Properties p_49795_, PowerGeneratorLevels generatorLevel) {
        super(p_49795_, ModBlockEntities.POWER_GENERATOR);
        this.generatorLevel = generatorLevel;
    }

    public PowerGeneratorLevels getGeneratorLevel() {
        return generatorLevel;
    }

    @Override
    public int getEnergyCapacity() {
        return this.getGeneratorLevel().capacity;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (pStack.hasTag()) {
            Optional<PowerGeneratorBlockEntity> powerGeneratorBlockEntity = pLevel.getBlockEntity(pPos, ModBlockEntities.POWER_GENERATOR.get());
            if (powerGeneratorBlockEntity.isPresent()) {
                var blockEntity = powerGeneratorBlockEntity.get();
                CompoundTag tag = pStack.getTag();
                if (tag != null && tag.contains("MachineData", CompoundTag.TAG_COMPOUND)) {
                    CompoundTag generatorTag = tag.getCompound("MachineData");
                    if (generatorTag.contains("Upgrades", CompoundTag.TAG_COMPOUND)) {
                        CompoundTag upgrades = generatorTag.getCompound("Upgrades");
                        for (int i = 0; i < 3; i++) {
                            if (upgrades.contains("Slot" + i, CompoundTag.TAG_STRING)) {
                                blockEntity.upgradeContainer.setItem(i, new ItemStack(ForgeRegistries.ITEMS.getDelegateOrThrow(new ResourceLocation(upgrades.getString("Slot" + i)))));
                            }
                        }
                    }

                    if (generatorTag.contains("Energy", CompoundTag.TAG_COMPOUND)) {
                        blockEntity.loadEnergyFrom(generatorTag.getCompound("Energy"));
                    }
                }
            }
        }
    }
}
