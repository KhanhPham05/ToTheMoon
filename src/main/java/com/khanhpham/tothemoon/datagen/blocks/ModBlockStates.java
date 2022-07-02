package com.khanhpham.tothemoon.datagen.blocks;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryBlock;
import com.khanhpham.tothemoon.core.multiblock.block.brickfurnace.NetherBrickFurnaceBlock;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class ModBlockStates extends BlockStateProvider {
    private final Direction[] horizontalDirections = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

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
        this.horizontalFacingBlock(ModBlocks.CREATIVE_BATTERY.get());
        specialBlocks();
    }

    private void specialBlocks() {
        batteryBlockStates();

        VariantBlockStateBuilder netherbrickFurnace = super.getVariantBuilder(ModBlocks.NETHER_BRICK_FURNACE_CONTROLLER.get());
        for (Direction direction : horizontalDirections) {
            netherbrickFurnace.addModels(netherbrickFurnace.partialState().with(NetherBrickFurnaceBlock.FACING, direction).with(NetherBrickFurnaceBlock.LIT, false), getModel(direction, "block/netherbrick_furnace_controller"));
            netherbrickFurnace.addModels(netherbrickFurnace.partialState().with(NetherBrickFurnaceBlock.FACING, direction).with(NetherBrickFurnaceBlock.LIT, true), getModel(direction, "block/nether_brick_furnace_controller_on"));
        }
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


        for (int i = 0; i <= 10; i++) {
            for (Direction direction : horizontalDirections) {
                builder.addModels(builder.partialState().with(BatteryBlock.FACING, direction).with(BatteryBlock.ENERGY_LEVEL, i), getModel(direction, "block/battery_level_" + i));
            }
        }

    }

    private void horizontalFacingBlock(Block block) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        for (Direction direction : horizontalDirections) builder.addModels(builder.partialState().with(HorizontalDirectionalBlock.FACING, direction), getModel(direction, "block/creative_battery"));
    }

    private ConfiguredModel getModel(Direction direction, String name) {
        ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(new ModelFile.UncheckedModelFile(ModUtils.modLoc(name)));
        rotateModel(builder, direction);
        return builder.buildLast();
    }

    private void rotateModel(ConfiguredModel.Builder<?> builder, Direction direction) {
        switch (direction) {
            case SOUTH -> builder.rotationY(180);
            case WEST -> builder.rotationY(270);
            case EAST -> builder.rotationY(90);
        }
    }
}
