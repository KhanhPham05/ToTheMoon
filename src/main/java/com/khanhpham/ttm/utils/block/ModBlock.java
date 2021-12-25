package com.khanhpham.ttm.utils.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.DirectionProperty;


public class ModBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public ModBlock(final Properties behaviour) {
        super(behaviour);
    }
}
