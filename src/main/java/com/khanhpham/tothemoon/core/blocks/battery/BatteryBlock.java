package com.khanhpham.tothemoon.core.blocks.battery;

import com.khanhpham.tothemoon.core.blockentities.battery.AbstractBatteryBlockEntity;
import com.khanhpham.tothemoon.core.blockentities.battery.BatteryBlockEntity;
import com.khanhpham.tothemoon.core.blocks.BaseEntityBlock;
import com.khanhpham.tothemoon.core.blocks.HasCustomBlockItem;
import com.khanhpham.tothemoon.core.items.BatteryItem;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class BatteryBlock extends BaseEntityBlock<AbstractBatteryBlockEntity> implements HasCustomBlockItem {

    public static final IntegerProperty ENERGY_LEVEL = ModUtils.ENERGY_LEVEL;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final ResourceLocation LOOT_CONTENT = ModUtils.modLoc("energy");

    public BatteryBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<AbstractBatteryBlockEntity> supplier) {
        super(p_49224_, supplier);
        super.registerDefaultState(defaultBlockState().setValue(ENERGY_LEVEL, 0).setValue(FACING, Direction.NORTH));
    }

    @Override
    @Nonnull
    protected BlockEntityType<AbstractBatteryBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.BATTERY.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ENERGY_LEVEL, FACING);
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {
        BlockEntity blockEntity = pBuilder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof AbstractBatteryBlockEntity battery) {
            pBuilder = pBuilder.withParameter(LootContextParams.BLOCK_ENTITY, battery);
        }

        return super.getDrops(pState, pBuilder);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockItem getRawItem() {
        return new BatteryItem(this);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {

        if (pStack.hasTag()) {
            CompoundTag tag = Objects.requireNonNull(pStack.getTag());
            if (tag.contains("BlockEntityTag")) {
                CompoundTag blockEntityTag = tag.getCompound("BlockEntityTag");
                if (blockEntityTag.contains("energy", 3)) {
                    if (pLevel.getBlockEntity(pPos) instanceof BatteryBlockEntity battery) {
                        battery.energy.setEnergy(blockEntityTag.getInt("energy"));
                    }
                }
            }
        }

        /*LazyOptional<IEnergyStorage> energyStorageLazyOptional = pStack.getCapability(CapabilityEnergy.ENERGY);
        energyStorageLazyOptional.ifPresent(energyStorage -> {
            if (pLevel.getBlockEntity(pPos) instanceof BatteryBlockEntity battery) {
                battery.energy.setEnergy(energyStorage.getEnergyStored());
            }
        });*/
    }
}
