package com.khanhpham.tothemoon.core.blocks.machine.crusher;

import com.khanhpham.tothemoon.core.processes.single.SingleProcessBlockEntity;
import com.khanhpham.tothemoon.core.recipes.MetalCrushingRecipe;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CrusherBlockEntity extends SingleProcessBlockEntity {
    public CrusherBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.CRUSHER.get(), pWorldPosition, pBlockState, ModBlocks.CRUSHER, MetalCrushingRecipe.RECIPE_TYPE);
    }
}
