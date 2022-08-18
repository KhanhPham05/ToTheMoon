package com.khanhpham.tothemoon.core.blocks.battery;

import com.khanhpham.tothemoon.core.blocks.BaseEntityBlock;
import com.khanhpham.tothemoon.core.blocks.HasCustomBlockItem;
import com.khanhpham.tothemoon.core.items.BatteryItem;
import com.khanhpham.tothemoon.datagen.loottable.LootUtils;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BatteryBlock extends BaseEntityBlock<AbstractBatteryBlockEntity> implements HasCustomBlockItem {

    public static final IntegerProperty ENERGY_LEVEL = ModUtils.ENERGY_LEVEL;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public BatteryBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<AbstractBatteryBlockEntity> supplier) {
        super(p_49224_);
        super.registerDefaultState(defaultBlockState().setValue(ENERGY_LEVEL, 0).setValue(FACING, Direction.NORTH));
    }

    @Override
    @Nonnull
    protected BlockEntityType<AbstractBatteryBlockEntity> getBlockEntityType() {
        return ModBlockEntities.BATTERY.get();
    }

    @Override
    public @Nullable AbstractBatteryBlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BatteryBlockEntity(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ENERGY_LEVEL, FACING);
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
        LootUtils.loadItemTagToBlockEntity(pStack, pLevel, pPos, getBlockEntityType(), (tag, be) -> {
            if (tag.contains(LootUtils.LOOT_DATA_ENERGY, LootUtils.TAG_TYPE_INT)) {
                int energy = tag.getInt(LootUtils.LOOT_DATA_ENERGY);
                be.energy.setEnergy(energy);
            }
        });
    }
}
