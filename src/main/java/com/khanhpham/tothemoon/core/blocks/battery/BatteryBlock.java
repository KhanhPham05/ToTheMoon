package com.khanhpham.tothemoon.core.blocks.battery;

import com.khanhpham.tothemoon.core.blocks.BaseEntityBlock;
import com.khanhpham.tothemoon.core.blocks.HasCustomBlockItem;
import com.khanhpham.tothemoon.core.items.BatteryItem;
import com.khanhpham.tothemoon.datagen.loottable.LootUtils;
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
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class BatteryBlock<T extends AbstractBatteryBlockEntity> extends BaseEntityBlock<T> implements HasCustomBlockItem {

    public static final IntegerProperty ENERGY_LEVEL = ModUtils.ENERGY_LEVEL;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private final int energyCapacity;
    private final Supplier<BlockEntityType<T>> blockEntity;
    private final BlockEntityType.BlockEntitySupplier<T> batterySupplier;

    public BatteryBlock(Properties p_49224_, int energyCapacity, Supplier<BlockEntityType<T>> blockEntity, BlockEntityType.BlockEntitySupplier<T> batterySupplier) {
        super(p_49224_);
        this.energyCapacity = energyCapacity;
        this.blockEntity = blockEntity;
        this.batterySupplier = batterySupplier;
        super.registerDefaultState(defaultBlockState().setValue(ENERGY_LEVEL, 0).setValue(FACING, Direction.NORTH));
    }

    @Override
    @Nonnull
    protected final BlockEntityType<T> getBlockEntityType() {
        return this.blockEntity.get();
    }

    @Override
    public final @Nonnull T newBlockEntity(BlockPos pPos, BlockState pState) {
        return this.batterySupplier.create(pPos, pState);
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
    public final BlockItem getRawItem() {
        return new BatteryItem(this, this.energyCapacity);
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
