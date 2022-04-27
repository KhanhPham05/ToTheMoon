package com.khanhpham.tothemoon.core.blocks.cable;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.stream.Stream;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class EnergyCableBlock extends Block {
    private final int type;

    public EnergyCableBlock(int type) {
        super(Properties.of(Material.STONE).requiresCorrectToolForDrops().noOcclusion().strength(3.5f));
        this.type = type;
    }

    public static final VoxelShape TYPE_0 = Shapes.join(Block.box(4, 4, 4, 12, 12, 12), Shapes.join(Block.box(5.25, 4, 4, 5.25, 12, 12), Shapes.join(Block.box(10.85, 4, 4, 10.85, 12, 12), Shapes.join(Block.box(4.05, 10.8, 4, 12.05, 10.8, 12), Shapes.join(Block.box(4.05, 5.2, 4, 12.05, 5.2, 12), Shapes.join(Block.box(4.05, 4, 10.8, 12.05, 12, 10.8), Shapes.join(Block.box(4.05, 4, 5.2, 12.05, 12, 5.2), Shapes.join(Block.box(10.75, 10.75, 3.75, 12.25, 12.25, 5.25), Shapes.join(Block.box(3.85, 10.75, 3.75, 5.35, 12.25, 5.25), Shapes.join(Block.box(3.85, 10.75, 10.75, 5.35, 12.25, 12.25), Shapes.join(Block.box(10.75, 10.75, 10.75, 12.25, 12.25, 12.25), Shapes.join(Block.box(3.85, 3.75, 10.75, 5.35, 5.25, 12.25), Shapes.join(Block.box(10.75, 3.75, 10.75, 12.25, 5.25, 12.25), Shapes.join(Block.box(3.85, 3.75, 3.75, 5.35, 5.25, 5.25), Block.box(10.75, 3.75, 3.75, 12.25, 5.25, 5.25), BooleanOp.TRUE), BooleanOp.TRUE),  BooleanOp.TRUE),  BooleanOp.TRUE),  BooleanOp.TRUE),  BooleanOp.TRUE),  BooleanOp.TRUE), BooleanOp.TRUE),  BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE);
    

    public static final VoxelShape TYPE_1 = Shapes.join(Shapes.join(Block.box(4, 4, 4, 12, 12, 12), Shapes.join(Block.box(5.25, 4, 4, 5.25, 12, 12), Shapes.join(Block.box(10.85, 4, 4, 10.85, 12, 12), Shapes.join(Block.box(4.05, 10.8, 4, 12.05, 10.8, 12), Shapes.join(Block.box(4.05, 5.2, 4, 12.05, 5.2, 12), Shapes.join(Block.box(4.05, 4, 10.8, 12.05, 12, 10.8), Shapes.join(Block.box(4.05, 4, 5.2, 12.05, 12, 5.2), Shapes.join(Block.box(10.75, 10.75, 3.75, 12.25, 12.25, 5.25), Shapes.join(Block.box(3.85, 10.75, 3.75, 5.35, 12.25, 5.25), Shapes.join(Block.box(3.85, 10.75, 10.75, 5.35, 12.25, 12.25), Shapes.join(Block.box(10.75, 10.75, 10.75, 12.25, 12.25, 12.25), Shapes.join(Block.box(3.85, 3.75, 10.75, 5.35, 5.25, 12.25), Shapes.join(Block.box(10.75, 3.75, 10.75, 12.25, 5.25, 12.25), Shapes.join(Block.box(3.85, 3.75, 3.75, 5.35, 5.25, 5.25), Block.box(10.75, 3.75, 3.75, 12.25, 5.25, 5.25), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), Block.box(5, 0, 5, 11, 4, 11), BooleanOp.OR);

    public static final VoxelShape TYPE_2 = Stream.of(
            Shapes.join(Block.box(4, 4, 4, 12, 12, 12), Shapes.join(Block.box(5.25, 4, 4, 5.25, 12, 12), Shapes.join(Block.box(10.85, 4, 4, 10.85, 12, 12), Shapes.join(Block.box(4.05, 10.8, 4, 12.05, 10.8, 12), Shapes.join(Block.box(4.05, 5.2, 4, 12.05, 5.2, 12), Shapes.join(Block.box(4.05, 4, 10.8, 12.05, 12, 10.8), Shapes.join(Block.box(4.05, 4, 5.2, 12.05, 12, 5.2), Shapes.join(Block.box(10.75, 10.75, 3.75, 12.25, 12.25, 5.25), Shapes.join(Block.box(3.85, 10.75, 3.75, 5.35, 12.25, 5.25), Shapes.join(Block.box(3.85, 10.75, 10.75, 5.35, 12.25, 12.25), Shapes.join(Block.box(10.75, 10.75, 10.75, 12.25, 12.25, 12.25), Shapes.join(Block.box(3.85, 3.75, 10.75, 5.35, 5.25, 12.25), Shapes.join(Block.box(10.75, 3.75, 10.75, 12.25, 5.25, 12.25), Shapes.join(Block.box(3.85, 3.75, 3.75, 5.35, 5.25, 5.25), Block.box(10.75, 3.75, 3.75, 12.25, 5.25, 5.25), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE),
            Block.box(5, 0, 5, 11, 4, 11),
            Block.box(5, 12, 5, 11, 16, 11)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    
    public static final VoxelShape TYPE_3 = Stream.of(
            Block.box(5, 0, 5, 11, 4, 11),
            Block.box(5, 12, 5, 11, 16, 11),
            Block.box(0, 5, 5, 4, 11, 11),
            Shapes.join(Block.box(4, 4, 4, 12, 12, 12), Shapes.join(Block.box(5.25, 4, 4, 5.25, 12, 12), Shapes.join(Block.box(10.85, 4, 4, 10.85, 12, 12), Shapes.join(Block.box(4.05, 10.8, 4, 12.05, 10.8, 12), Shapes.join(Block.box(4.05, 5.2, 4, 12.05, 5.2, 12), Shapes.join(Block.box(4.05, 4, 10.8, 12.05, 12, 10.8), Shapes.join(Block.box(4.05, 4, 5.2, 12.05, 12, 5.2), Shapes.join(Block.box(10.75, 10.75, 3.75, 12.25, 12.25, 5.25), Shapes.join(Block.box(3.85, 10.75, 3.75, 5.35, 12.25, 5.25), Shapes.join(Block.box(3.85, 10.75, 10.75, 5.35, 12.25, 12.25), Shapes.join(Block.box(10.75, 10.75, 10.75, 12.25, 12.25, 12.25), Shapes.join(Block.box(3.85, 3.75, 10.75, 5.35, 5.25, 12.25), Shapes.join(Block.box(10.75, 3.75, 10.75, 12.25, 5.25, 12.25), Shapes.join(Block.box(3.85, 3.75, 3.75, 5.35, 5.25, 5.25), Block.box(10.75, 3.75, 3.75, 12.25, 5.25, 5.25), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    
    public static final VoxelShape TYPE_4 = Stream.of(
            Block.box(12, 5, 5, 16, 11, 11),
            Block.box(5, 0, 5, 11, 4, 11),
            Shapes.join(Block.box(5, 12, 5, 11, 16, 11), Block.box(0, 5, 5, 4, 11, 11), BooleanOp.TRUE),
            Shapes.join(Block.box(4, 4, 4, 12, 12, 12), Shapes.join(Block.box(5.25, 4, 4, 5.25, 12, 12), Shapes.join(Block.box(10.85, 4, 4, 10.85, 12, 12), Shapes.join(Block.box(4.05, 10.8, 4, 12.05, 10.8, 12), Shapes.join(Block.box(4.05, 5.2, 4, 12.05, 5.2, 12), Shapes.join(Block.box(4.05, 4, 10.8, 12.05, 12, 10.8), Shapes.join(Block.box(4.05, 4, 5.2, 12.05, 12, 5.2), Shapes.join(Block.box(10.75, 10.75, 3.75, 12.25, 12.25, 5.25), Shapes.join(Block.box(3.85, 10.75, 3.75, 5.35, 12.25, 5.25), Shapes.join(Block.box(3.85, 10.75, 10.75, 5.35, 12.25, 12.25), Shapes.join(Block.box(10.75, 10.75, 10.75, 12.25, 12.25, 12.25), Shapes.join(Block.box(3.85, 3.75, 10.75, 5.35, 5.25, 12.25), Shapes.join(Block.box(10.75, 3.75, 10.75, 12.25, 5.25, 12.25), Shapes.join(Block.box(3.85, 3.75, 3.75, 5.35, 5.25, 5.25), Block.box(10.75, 3.75, 3.75, 12.25, 5.25, 5.25), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape TYPE_5 = Stream.of(
            Shapes.join(Block.box(4, 4, 4, 12, 12, 12), Shapes.join(Block.box(5.25, 4, 4, 5.25, 12, 12), Shapes.join(Block.box(10.85, 4, 4, 10.85, 12, 12), Shapes.join(Block.box(4.05, 10.8, 4, 12.05, 10.8, 12), Shapes.join(Block.box(4.05, 5.2, 4, 12.05, 5.2, 12), Shapes.join(Block.box(4.05, 4, 10.8, 12.05, 12, 10.8), Shapes.join(Block.box(4.05, 4, 5.2, 12.05, 12, 5.2), Shapes.join(Block.box(10.75, 10.75, 3.75, 12.25, 12.25, 5.25), Shapes.join(Block.box(3.85, 10.75, 3.75, 5.35, 12.25, 5.25), Shapes.join(Block.box(3.85, 10.75, 10.75, 5.35, 12.25, 12.25), Shapes.join(Block.box(10.75, 10.75, 10.75, 12.25, 12.25, 12.25), Shapes.join(Block.box(3.85, 3.75, 10.75, 5.35, 5.25, 12.25), Shapes.join(Block.box(10.75, 3.75, 10.75, 12.25, 5.25, 12.25), Shapes.join(Block.box(3.85, 3.75, 3.75, 5.35, 5.25, 5.25), Block.box(10.75, 3.75, 3.75, 12.25, 5.25, 5.25), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE),
            Block.box(12, 5, 5, 16, 11, 11),
            Block.box(0, 5, 5, 4, 11, 11),
            Block.box(5, 5, 12, 11, 11, 16),
            Block.box(5, 12, 5, 11, 16, 11),
            Block.box(5, 0, 5, 11, 4, 11)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape TYPE_6 = Stream.of(
            Block.box(5, 0, 5, 11, 4, 11),
            Block.box(5, 12, 5, 11, 16, 11),
            Block.box(0, 5, 5, 4, 11, 11),
            Block.box(5, 5, 12, 11, 11, 16),
            Block.box(12, 5, 5, 16, 11, 11),
            Block.box(5, 5, 0, 11, 11, 4),
            Shapes.join(Block.box(4, 4, 4, 12, 12, 12), Shapes.join(Block.box(5.25, 4, 4, 5.25, 12, 12), Shapes.join(Block.box(10.85, 4, 4, 10.85, 12, 12), Shapes.join(Block.box(4.05, 10.8, 4, 12.05, 10.8, 12), Shapes.join(Block.box(4.05, 5.2, 4, 12.05, 5.2, 12), Shapes.join(Block.box(4.05, 4, 10.8, 12.05, 12, 10.8), Shapes.join(Block.box(4.05, 4, 5.2, 12.05, 12, 5.2), Shapes.join(Block.box(10.75, 10.75, 3.75, 12.25, 12.25, 5.25), Shapes.join(Block.box(3.85, 10.75, 3.75, 5.35, 12.25, 5.25), Shapes.join(Block.box(3.85, 10.75, 10.75, 5.35, 12.25, 12.25), Shapes.join(Block.box(10.75, 10.75, 10.75, 12.25, 12.25, 12.25), Shapes.join(Block.box(3.85, 3.75, 10.75, 5.35, 5.25, 12.25), Shapes.join(Block.box(10.75, 3.75, 10.75, 12.25, 5.25, 12.25), Shapes.join(Block.box(3.85, 3.75, 3.75, 5.35, 5.25, 5.25), Block.box(10.75, 3.75, 3.75, 12.25, 5.25, 5.25), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE), BooleanOp.TRUE)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch(type) {
            case 1 -> TYPE_1;
            case 2 -> TYPE_2;
            case 3 -> TYPE_3;
            case 4 -> TYPE_4;
            case 5 -> TYPE_5;
            case 6 -> TYPE_6;
            default -> TYPE_0;
        };
    }
}
