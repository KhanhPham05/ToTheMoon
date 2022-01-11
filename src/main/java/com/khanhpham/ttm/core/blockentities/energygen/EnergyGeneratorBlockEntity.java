package com.khanhpham.ttm.core.blockentities.energygen;

import com.khanhpham.ttm.core.containers.EnergyGenMenu;
import com.khanhpham.ttm.core.energy.EnergyData;
import com.khanhpham.ttm.init.ModBlocks;
import com.khanhpham.ttm.init.ModMisc;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class EnergyGeneratorBlockEntity extends BaseGeneratorEntity {

    /**
     * Extends Block Entity should not use this constructor as it will cause crashes
     * @deprecated
     */
    public EnergyGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModMisc.ENERGY_GEN_ENTITY, pWorldPosition, pBlockState);
    }

    protected EnergyGeneratorBlockEntity(BlockEntityType<? extends  EnergyGeneratorBlockEntity> blockEntity, BlockPos pos, BlockState state) {
        super(blockEntity, pos, state);
    }

    private static final EnergyData ENERGY_DATA = new EnergyData(20000, 200) {
        @Override
        public void setChanged(Level level, BlockPos pos, BlockState state) {
            BaseGeneratorEntity.setChanged(level, pos, state);
        }
    };

    @Nonnull
    @Override
    protected EnergyData getEnergyData() {
        return ENERGY_DATA;
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent(ModBlocks.ENERGY_GEN.getDescriptionId());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new EnergyGenMenu(pContainerId, pInventory, this, DATA_SLOT);
    }
}
