package com.khanhpham.tothemoon.utils.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class MachineFrameBlock extends Block {
    public MachineFrameBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}
