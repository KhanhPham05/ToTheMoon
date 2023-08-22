package com.khanhtypo.tothemoon.common.block;

import com.khanhtypo.tothemoon.registration.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.level.BlockEvent;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class WorkbenchBlock extends ContainerBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty IS_RIGHT = BooleanProperty.create("is_right");
    public static WorkbenchBlock INSTANCE;

    static {
        AllShapes.staticInit();
    }

    public WorkbenchBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_PLANKS).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY), ModMenuTypes.WORKBENCH);
        super.registerDefaultState(super.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(IS_RIGHT, true));
        INSTANCE = this;
    }

    public static WorkbenchBlock getInstance() {
        return INSTANCE;
    }

    private static Direction getLeftDirection(Direction original) {
        return switch (original) {
            case NORTH -> Direction.EAST;
            case EAST -> Direction.SOUTH;
            case SOUTH -> Direction.WEST;
            case WEST -> Direction.NORTH;
            default ->
                    throw new IllegalStateException("Can not find the right side direction for [%s]".formatted(original));
        };
    }

    public static void onBreak(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() instanceof WorkbenchBlock) {
            boolean isRightPart = event.getState().getValue(IS_RIGHT);
            Direction blockFacing = event.getState().getValue(FACING);
            BlockPos blockPos = event.getPos();
            event.getLevel().destroyBlock(isRightPart ? blockPos.relative(getLeftDirection(blockFacing)) : blockPos.relative(getLeftDirection(blockFacing).getOpposite()), false, event.getPlayer());
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING, IS_RIGHT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
        Direction facingDirection = p_49820_.getHorizontalDirection().getOpposite();
        BlockPos leftPos = p_49820_.getClickedPos().relative(getLeftDirection(facingDirection));
        return p_49820_.getLevel().getBlockState(leftPos).canBeReplaced() && p_49820_.getLevel().isInWorldBounds(p_49820_.getClickedPos()) ? super.defaultBlockState().setValue(FACING, facingDirection).setValue(IS_RIGHT, true) : null;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(Level p_49847_, BlockPos p_49848_, BlockState p_49849_, @Nullable LivingEntity p_49850_, ItemStack p_49851_) {
        if (!p_49847_.isClientSide) {
            BlockPos leftPartPlacement = p_49848_.relative(getLeftDirection(p_49849_.getValue(FACING)));
            p_49847_.setBlock(leftPartPlacement, p_49849_.setValue(IS_RIGHT, false), 3);
        }
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        Direction blockFacing = p_60555_.getValue(FACING);
        boolean isRightPart = p_60555_.getValue(IS_RIGHT);
        return switch (blockFacing) {
            case NORTH -> isRightPart ? AllShapes.RIGHT_NORTH : AllShapes.LEFT_NORTH;
            case EAST -> isRightPart ? AllShapes.RIGHT_EAST : AllShapes.LEFT_EAST;
            case SOUTH -> isRightPart ? AllShapes.RIGHT_SOUTH : AllShapes.LEFT_SOUTH;
            case WEST -> isRightPart ? AllShapes.RIGHT_WEST : AllShapes.LEFT_WEST;
            default -> throw new IllegalStateException("%s is not a block direction".formatted(blockFacing));
        };
    }

    public static final class AllShapes {
        public static final VoxelShape RIGHT_NORTH = Stream.of(
                Block.box(0, 13, 0, 16, 16, 16),
                Block.box(0, 10, 0, 0.01, 13, 16),
                Block.box(0.5, 0, 0.5, 2.5, 13, 2.5),
                Block.box(0.5, 0, 13.5, 2.5, 13, 15.5)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        public static final VoxelShape RIGHT_EAST = Stream.of(
                Block.box(0, 13, 0, 16, 16, 16),
                Block.box(0, 10, 0, 16, 13, 0.009999999999999787),
                Block.box(13.5, 0, 0.5, 15.5, 13, 2.5),
                Block.box(0.5, 0, 0.5, 2.5, 13, 2.5)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        public static final VoxelShape RIGHT_SOUTH = Stream.of(
                Block.box(0, 13, 0, 16, 16, 16),
                Block.box(15.99, 10, 0, 16, 13, 16),
                Block.box(13.5, 0, 13.5, 15.5, 13, 15.5),
                Block.box(13.5, 0, 0.5, 15.5, 13, 2.5)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        public static final VoxelShape RIGHT_WEST = Stream.of(
                Block.box(0, 13, 0, 16, 16, 16),
                Block.box(0, 10, 15.99, 16, 13, 16),
                Block.box(0.5, 0, 13.5, 2.5, 13, 15.5),
                Block.box(13.5, 0, 13.5, 15.5, 13, 15.5)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        public static final VoxelShape LEFT_NORTH = Stream.of(
                Block.box(13.5, 0, 0.5, 15.5, 14, 2.5),
                Block.box(13.5, 0, 13.5, 15.5, 14, 15.5),
                Block.box(0, 14, 0, 16, 16, 16),
                Block.box(15.99, 10, 0, 16, 14, 16)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        public static final VoxelShape LEFT_EAST = Stream.of(
                Block.box(13.5, 0, 13.5, 15.5, 14, 15.5),
                Block.box(0, 14, 0, 16, 16, 16),
                Block.box(0, 10, 15.99, 16, 14, 16),
                Block.box(0.5, 0, 13.5, 2.5, 14, 15.5)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        public static final VoxelShape LEFT_SOUTH = Stream.of(
                Block.box(0.5, 0, 13.5, 2.5, 14, 15.5),
                Block.box(0, 14, 0, 16, 16, 16),
                Block.box(0, 10, 0, 0.009999999999999787, 14, 16),
                Block.box(0.5, 0, 0.5, 2.5, 14, 2.5)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

        public static final VoxelShape LEFT_WEST = Stream.of(
                Block.box(0.5, 0, 0.5, 2.5, 14, 2.5),
                Block.box(0, 14, 0, 16, 16, 16),
                Block.box(0, 10, 0, 16, 14, 0.009999999999999787),
                Block.box(13.5, 0, 0.5, 15.5, 14, 2.5)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

        private static void staticInit() {
        }
    }
}
