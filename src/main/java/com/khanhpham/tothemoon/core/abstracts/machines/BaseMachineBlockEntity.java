package com.khanhpham.tothemoon.core.abstracts.machines;

import com.khanhpham.tothemoon.core.abstracts.EnergyProcessBlockEntity;
import com.khanhpham.tothemoon.core.items.UpgradeItem;
import com.khanhpham.tothemoon.core.energy.Energy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Deprecated
public abstract class BaseMachineBlockEntity extends EnergyProcessBlockEntity implements UpgradableContainer {
    public static final int UPGRADE_SLOTS_INDEX_SIZE = 4;
    protected final int normalContainerSize;

    protected final UpgradeItem[] upgrades = new UpgradeItem[UPGRADE_SLOTS_INDEX_SIZE];

    public BaseMachineBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label, int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy, label, containerSize + UPGRADE_SLOTS_INDEX_SIZE);
        normalContainerSize = containerSize;
    }

    public static <T extends BaseMachineBlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        //Collecting upgrades from upgrade slots
        int j = 0;
        for (int i = blockEntity.normalContainerSize; i <= blockEntity.normalContainerSize + 3; i++) {
            if (!blockEntity.items.get(i).isEmpty()) {
                blockEntity.upgrades[j] = (UpgradeItem) blockEntity.items.get(i).getItem();
            } else {
                blockEntity.upgrades[j] = null;
            }
            j++;
        }

        blockEntity.workingSpeedModify = UpgradesManager.getTotalSpeedModifiers(blockEntity.upgrades);

        blockEntity.serverTick(level, pos, state);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return super.items;
    }

    private static final class UpgradesManager {


        private static int getTotalSpeedModifiers(UpgradeItem... upgrades) {
            int speedModifiers = 1;
            for (UpgradeItem upgrade : upgrades) {
                if (upgrade instanceof UpgradeItem.SpeedUpgrade) {
                    int tier = upgrade.tier;
                    speedModifiers += tier;
                }
            }

            return speedModifiers;
        }


    }
}
