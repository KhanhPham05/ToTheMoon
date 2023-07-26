package com.khanhtypo.tothemoon.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public final class MachineFrameShapes {
    public static final VoxelShape COPPER = Stream.of(
            Block.box(0, 0, 3, 16, 16, 3),
            Block.box(0, 0, 13, 16, 16, 13),
            Block.box(13, 0, 0, 13, 16, 16),
            Block.box(3, 0, 0, 3, 16, 16),
            Block.box(0, 3, 0, 16, 3, 16),
            Block.box(0, 13, 0, 16, 13, 16),
            Block.box(0, 0, 0, 16, 16, 16),
            Block.box(2.5, 2.5, 2.5, 13.5, 13.5, 13.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();;

    public static final VoxelShape REDSTONE = Stream.of(
            Block.box(3.5, 3.5, 3.5, 12.5, 12.5, 12.5),
            Block.box(0, 0, 0, 16, 16, 16),
            Block.box(0, 0, 3, 16, 16, 3),
            Block.box(0, 0, 13, 16, 16, 13),
            Block.box(13, 0, 0, 13, 16, 16),
            Block.box(3, 0, 0, 3, 16, 16),
            Block.box(0, 3, 0, 16, 3, 16),
            Block.box(0, 13, 0, 16, 13, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape STEEL = Stream.of(
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

    private MachineFrameShapes() {}
}
