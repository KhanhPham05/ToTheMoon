package com.khanhpham.tothemoon.core.blocks.workbench;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.blocks.HasCustomBlockItem;
import com.khanhpham.tothemoon.core.blocks.NoBlockItem;
import com.khanhpham.tothemoon.core.menus.SimpleAccessMenuProvider;
import com.khanhpham.tothemoon.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class WorkbenchBlock extends Block implements HasCustomBlockItem {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape NEW_SHAPE = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public WorkbenchBlock(Properties pProperties) {
        super(pProperties);
        super.registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public final InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide && !pPlayer.getMainHandItem().is(ModBlocks.WORKBENCH.get().asItem())) {
            pPlayer.openMenu(new SimpleAccessMenuProvider(WorkbenchMenu::new, this, pPos, ModBlocks.WORKBENCH.get().getName()));
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return NEW_SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction facing = pContext.getHorizontalDirection().getOpposite();
        return defaultBlockState().setValue(FACING, facing);
    }

    private Direction getLeftDirection(Direction facing) {
        return switch (facing) {
            case NORTH -> Direction.WEST;
            case SOUTH -> Direction.EAST;
            case EAST -> Direction.NORTH;
            case WEST -> Direction.SOUTH;
            default -> facing;
        };
    }

    public boolean canExtendsToLeft(BlockPlaceContext pContext, Level level, BlockPos pos, Direction facing) {
        BlockPos leftPos = pos.relative(getLeftDirection(facing));
        return this.isAirOrFluid(level.getBlockState(leftPos)) && level.getWorldBorder().isWithinBounds(leftPos) && level.getBlockState(leftPos).canBeReplaced(pContext);
    }

    private boolean isAirOrFluid(BlockState state) {
        return state.isAir() || state.getBlock() instanceof LiquidBlock;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        BlockPos leftPos = pPos.relative(this.getLeftDirection(pState.getValue(FACING).getOpposite()));
        BlockState state = ModBlocks.WORKBENCH_LEFT.get().transformState(pState.getValue(FACING));
        pLevel.setBlock(leftPos, state, 2);
        pLevel.blockUpdated(leftPos, state.getBlock());
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        BlockPos leftPos = pPos.relative(this.getLeftDirection(pState.getValue(FACING).getOpposite()));
        pLevel.destroyBlock(leftPos, false);
    }

    @Override
    public BlockItem getRawItem() {
        return new BlockItem(this, new Item.Properties().tab(ToTheMoon.TAB).stacksTo(1)) {
            @Override
            public InteractionResult place(BlockPlaceContext pContext) {
                return canExtendsToLeft(pContext, pContext.getLevel(), pContext.getClickedPos(), pContext.getHorizontalDirection()) ? super.place(pContext) : InteractionResult.FAIL;
            }
        };
    }

    public static final class LeftSide extends WorkbenchBlock implements NoBlockItem {
        public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

        public LeftSide(Properties pProperties) {
            super(pProperties);
            this.registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
        }

        @Override
        public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
            return NEW_SHAPE;
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
            pBuilder.add(FACING);
        }

        @Override
        public BlockState getStateForPlacement(BlockPlaceContext pContext) {
            return this.defaultBlockState();
        }

        public BlockState transformState(Direction rightFacing) {
            return this.defaultBlockState().setValue(FACING, rightFacing);
        }

        private Direction getRightDirection(Direction facing) {
            return switch (facing) {
                case NORTH -> Direction.EAST;
                case SOUTH -> Direction.WEST;
                case WEST -> Direction.NORTH;
                case EAST -> Direction.SOUTH;
                default -> facing;
            };
        }

        @Override
        public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
            BlockPos rightPos = pPos.relative(this.getRightDirection(pState.getValue(FACING).getOpposite()));
            pLevel.destroyBlock(rightPos, false);
        }

        @Override
        public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        }

        @Override
        public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
            return new ItemStack(ModBlocks.WORKBENCH.get());
        }
    }
}