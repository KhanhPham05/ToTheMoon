package com.khanhtypo.tothemoon.common.block;

import com.khanhtypo.tothemoon.registration.elements.BlockObject;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FrameBlockObject extends BlockObject<FrameBlockObject.BlockInstance> {
    public FrameBlockObject(String name, VoxelShape shape, MapColor mapColor) {
        super(name, () -> new BlockInstance(createProperties(mapColor), shape));
    }

    private static BlockBehaviour.Properties createProperties(MapColor mapColor) {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).mapColor(mapColor).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL).noOcclusion();
    }

    public static final class BlockInstance extends Block {
        private final VoxelShape shape;

        public BlockInstance(Properties p_49795_, VoxelShape shape) {
            super(p_49795_);
            this.shape = shape;
        }

        @Override
        @SuppressWarnings("deprecation")
        public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
            return this.shape;
        }
    }
}
