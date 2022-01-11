package com.khanhpham.ttm.utils.block;

import com.khanhpham.ttm.core.ToolType;
import com.khanhpham.ttm.core.blockentities.energygen.BaseGeneratorEntity;
import com.khanhpham.ttm.core.blocks.MineableBlock;
import com.khanhpham.ttm.core.blocks.MiningLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;


public abstract class ModCapableBlock<TILE extends BlockEntity> extends BaseEntityBlock implements EntityBlock, MineableBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private final BlockEntityType.BlockEntitySupplier<TILE> blockEntitySup;
    private final ToolType toolType;
    private final MiningLevel miningLevel;

    public ModCapableBlock(Properties behaviour, ToolType toolType, MiningLevel miningLevel, BlockEntityType.BlockEntitySupplier<TILE> blockEntity) {
        super(behaviour);
        this.blockEntitySup = blockEntity;
        this.toolType = toolType;
        this.miningLevel = miningLevel;
    }

    @Override
    public ToolType getHarvestTool() {
        return toolType;
    }

    @Override
    public MiningLevel getMiningLevel() {
        return miningLevel;
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return blockEntitySup.create(pPos, pState);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity te = pLevel.getBlockEntity(pPos);
            if (te instanceof BaseGeneratorEntity) {
                if (pLevel instanceof ServerLevel) {
                    Containers.dropContents(pLevel, pPos, ((BaseGeneratorEntity) te).getStoredItems());
                }

                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }


    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            openMenu(pLevel, pPos, pPlayer);
            return InteractionResult.CONSUME;
        }
    }

    protected abstract void openMenu(Level level, BlockPos pos, Player player) ;
}
