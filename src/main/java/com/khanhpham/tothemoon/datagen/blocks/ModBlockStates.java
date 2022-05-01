package com.khanhpham.tothemoon.datagen.blocks;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.core.blockentities.bettery.BatteryConnectionMode;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

import static net.minecraft.core.Direction.*;
import static com.khanhpham.tothemoon.core.blockentities.bettery.BatteryBlock.*;

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
        this.stairBlock(ModBlocks.COBBLED_MOON_ROCK_STAIR.get(), ModBlocks.COBBLED_MOON_ROCK.get());
        this.slabBlock(ModBlocks.COBBLED_MOON_ROCK_SLAB.get(), ModBlocks.COBBLED_MOON_ROCK.get());
        this.batteryBlockStates();
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

    private void batteryBlockStates() {
        var builder = super.getVariantBuilder(ModBlocks.BATTERY.get());
        Direction[] directions = new Direction[]{NORTH, SOUTH, WEST, EAST};

        for (BatteryConnectionMode mode : BatteryConnectionMode.values()) {
            for (Direction direction : directions) {
                for (int i = 0; i <= 10; i++) {
                    builder.addModels(builder.partialState().with(CONNECTION_MODE, mode).with(FACING, direction).with(ENERGY_LEVEL, i),getModel(direction, mode, i));
                }
            }
        }

    }

    private ConfiguredModel getModel(Direction direction, BatteryConnectionMode mode, int powerLevel) {
        ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(new ModelFile.UncheckedModelFile(ModUtils.modLoc("block/battery_connect_" + mode.getSerializedName() + "_level_" + powerLevel)));
        switch (direction) {
            case SOUTH -> builder.rotationY(180);
            case WEST -> builder.rotationY(270);
            case EAST -> builder.rotationY(90);
        }

        return builder.buildLast();
    }
}
