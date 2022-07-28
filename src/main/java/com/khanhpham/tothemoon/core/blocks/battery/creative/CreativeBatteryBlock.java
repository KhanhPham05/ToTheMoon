package com.khanhpham.tothemoon.core.blocks.battery.creative;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.blockentities.TickableBlockEntity;
import com.khanhpham.tothemoon.core.blockentities.battery.creative.CreativeBatteryBlockEntity;
import com.khanhpham.tothemoon.core.blocks.HasCustomBlockItem;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class CreativeBatteryBlock extends BaseEntityBlock implements EntityBlock, HasCustomBlockItem {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public CreativeBatteryBlock(Properties p_49224_) {
        super(p_49224_);
        super.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockItem getRawItem() {
        return new BlockItem(this, new Item.Properties().stacksTo(1).tab(ToTheMoon.TAB));
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CreativeBatteryBlockEntity(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, ModBlockEntities.CREATIVE_BATTERY.get(), TickableBlockEntity::staticServerTick);
    }

    @Override
    @SuppressWarnings("deprecation")
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}
