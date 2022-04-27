package com.khanhpham.tothemoon.core.blocks;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.stream.Stream;

@SuppressWarnings("deprecation")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MachineFrameBlock extends Block {
    public MachineFrameBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public static final class Redstone extends MachineFrameBlock {
        public Redstone() {
            super(Properties.copy(Blocks.REDSTONE_BLOCK).noOcclusion());
        }

        public static final VoxelShape SHAPE = Stream.of(
                Block.box(0, 0, 3, 16, 16, 3),
                Block.box(0, 0, 13, 16, 16, 13),
                Block.box(13, 0, 0, 13, 16, 16),
                Block.box(3, 0, 0, 3, 16, 16),
                Block.box(0, 3, 0, 16, 3, 16),
                Block.box(0, 13, 0, 16, 13, 16)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }

    public static final class Steel extends MachineFrameBlock {

        public Steel() {
            super(Properties.copy(Blocks.IRON_BLOCK).noOcclusion());
        }

        public static final VoxelShape SHAPE = Stream.of(
                Block.box(0, 0, 0, 16, 16, 16),
                Block.box(3, 3, 3, 13, 13, 13),
                Block.box(0, 0, 3, 16, 16, 3),
                Block.box(0, 0, 13, 16, 16, 13),
                Block.box(13, 0, 0, 13, 16, 16),
                Block.box(3, 0, 0, 3, 16, 16),
                Block.box(0, 3, 0, 16, 3, 16),
                Block.box(0, 13, 0, 16, 13, 16),
                Block.box(3, 2, 3, 4, 3, 12),
                Block.box(4, 2, 3, 13, 3, 4),
                Block.box(3, 2, 12, 12, 3, 13),
                Block.box(12, 2, 4, 13, 3, 13),
                Block.box(3, 13, 3, 4, 14, 12),
                Block.box(4, 13, 3, 13, 14, 4),
                Block.box(3, 13, 12, 12, 14, 13),
                Block.box(12, 13, 4, 13, 14, 13),
                Block.box(2, 12, 3, 3, 13, 13),
                Block.box(3, 12, 2, 13, 13, 3),
                Block.box(3, 3, 2, 13, 4, 3),
                Block.box(2, 3, 3, 3, 4, 13),
                Block.box(13, 12, 3, 14, 13, 13),
                Block.box(3, 12, 13, 13, 13, 14),
                Block.box(13, 3, 3, 14, 4, 13),
                Block.box(3, 3, 13, 13, 4, 14)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

        @Override
        public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
            return SHAPE;
        }

    }

    public static final class Copper extends MachineFrameBlock {
        public Copper() {
            super(Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.COPPER).noOcclusion());
        }

        /*public static final VoxelShape SHAPE = Stream.of(
                Block.box(0, 0, 3, 16, 16, 3),
                Block.box(0, 0, 13, 16, 16, 13),
                Block.box(13, 0, 0, 13, 16, 16),
                Block.box(3, 0, 0, 3, 16, 16),
                Block.box(0, 3, 0, 16, 3, 16),
                Block.box(0, 13, 0, 16, 13, 16)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

        @Override
        public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
            return SHAPE;
        }*/
    }
}
