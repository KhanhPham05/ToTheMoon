package com.khanhpham.tothemoon.core.energygenerator.tileentities;

import com.khanhpham.tothemoon.core.energygenerator.containers.EnergyGeneratorContainer;
import com.khanhpham.tothemoon.init.ModTileEntityTypes;
import com.khanhpham.tothemoon.utils.energy.Energy;
import com.khanhpham.tothemoon.utils.te.energygenerator.AbstractEnergyGeneratorTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;


public class CopperEnergyGeneratorTileEntity extends AbstractEnergyGeneratorTileEntity {

    public static final Component LABEL = new TranslatableComponent("gui.tothemoon.energy_generator.copper");

    public CopperEnergyGeneratorTileEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, @NotNull Component label) {
        super(pType, pWorldPosition, pBlockState, 100000, 50, 200, label);
    }

    public CopperEnergyGeneratorTileEntity(BlockPos pos, BlockState state) {
        this(ModTileEntityTypes.COPPER_ENERGY_GENERATOR_TE, pos, state, LABEL);
    }

    @Nonnull
    @Override
    protected EnergyGeneratorContainer createMenu(int containerId, Inventory playerInventory) {
        return new EnergyGeneratorContainer(this, playerInventory, containerId, super.data);
    }
}
