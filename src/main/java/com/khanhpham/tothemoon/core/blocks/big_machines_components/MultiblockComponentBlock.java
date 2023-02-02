package com.khanhpham.tothemoon.core.blocks.big_machines_components;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class MultiblockComponentBlock extends Block implements IMultiblockComponent {
    public static final BooleanProperty IS_FORMED = BooleanProperty.create("formed");
    public MultiblockComponentBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(IS_FORMED);
    }
}
