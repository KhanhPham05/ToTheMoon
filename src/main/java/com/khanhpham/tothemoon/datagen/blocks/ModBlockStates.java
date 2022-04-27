package com.khanhpham.tothemoon.datagen.blocks;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class ModBlockStates extends BlockStateProvider {
    public ModBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Names.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.SOLID_BLOCKS.forEach(this::simpleBlock);
        this.slabBlock(ModBlocks.MOON_ROCK_SLAB.get(), ModBlocks.MOON_ROCK.get());
        this.stairBlock(ModBlocks.MOON_ROCK_STAIR.get(), ModBlocks.MOON_ROCK.get());
        this.slabBlock(ModBlocks.POLISHED_MOON_ROCK_SLAB.get(), ModBlocks.POLISHED_MOON_ROCK.get());
        this.stairBlock(ModBlocks.POLISHED_MOON_ROCK_STAIR.get(), ModBlocks.POLISHED_MOON_ROCK.get());
        this.slabBlock(ModBlocks.MOON_ROCK_BRICK_SLAB.get(), ModBlocks.MOON_ROCK_BRICK.get());
        this.stairBlock(ModBlocks.MOON_ROCK_BRICK_STAIR.get(), ModBlocks.MOON_ROCK_BRICK.get());
    }

    private void simpleBlock(Supplier<? extends Block> supplier) {
        super.simpleBlock(supplier.get());
    }

    private void stairBlock(StairBlock block, Block materialBlock) {
        stairsBlock(block, modLoc("block/" + ModUtils.convertRlToPath(materialBlock)));
    }

    private void slabBlock(SlabBlock block, Block materialBlock) {
        ResourceLocation rl = modLoc("block/" + ModUtils.convertRlToPath(materialBlock));
        slabBlock(block, rl, rl);
    }
}
