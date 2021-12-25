package com.khanhpham.ttm.data.client;

import com.khanhpham.ttm.ToTheMoonMain;
import com.khanhpham.ttm.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public final class BlockModelStates extends BlockStateProvider {
    public BlockModelStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ToTheMoonMain.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.MOON_SURFACE_STONE);
        //simpleBlock(ModBlocks.MOON_COBBLESTONE);
        simpleBlock(ModBlocks.MOON_SURFACE_STONE_BRICKS);
    }
}
