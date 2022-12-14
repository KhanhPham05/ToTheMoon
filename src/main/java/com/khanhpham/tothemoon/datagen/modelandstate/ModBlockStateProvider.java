package com.khanhpham.tothemoon.datagen.modelandstate;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.blocks.DecorationBlocks;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryBlock;
import com.khanhpham.tothemoon.core.multiblock.block.brickfurnace.NetherBrickFurnaceBlock;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.helpers.RegistryEntries;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class ModBlockStateProvider extends BlockStateProvider {
    private final Direction[] horizontalDirections = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ToTheMoon.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.SOLID_BLOCKS.stream().map(Supplier::get).forEach(this::simpleBlock);
        //this.simpleBlock(ModBlocks.ANTI_PRESSURE_GLASS.get());

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

        DecorationBlocks.ALL_DECORATION_BLOCKS.forEach((block) -> block.registerStateAndModels(this));
    }


    private void specialBlocks() {
        batteryBlockStates(ModBlocks.BATTERY.get());
        batteryBlockStates(ModBlocks.REDSTONE_BATTERY.get());
        batteryBlockStates(ModBlocks.STEEL_BATTERY.get());
        facingWithLitState(ModBlocks.NETHER_BRICK_FURNACE_CONTROLLER.get(), false);
        facingWithLitState(ModBlocks.GOLD_ENERGY_GENERATOR.get());
        facingWithLitState(ModBlocks.IRON_ENERGY_GENERATOR.get());
        facingWithLitState(ModBlocks.DIAMOND_ENERGY_GENERATOR.get());
        facingWithLitState(ModBlocks.NETHERITE_ENERGY_GENERATOR.get());
        facingWithLitState(ModBlocks.COPPER_ENERGY_GENERATOR.get());
        facingWithLitState(ModBlocks.CRUSHER.get());
    }

    static final List<Block> facingLitBlocks = new ArrayList<>();

    private void facingWithLitState(@NotNull Block block) {
        facingWithLitState(block, true);
    }

    private void facingWithLitState(@NotNull Block block, boolean addToList) {
        if (addToList) facingLitBlocks.add(block);
        VariantBlockStateBuilder variantBuilder = super.getVariantBuilder(block);
        for (Direction direction : HorizontalDirectionalBlock.FACING.getPossibleValues()) {
            for (Boolean lit : BlockStateProperties.LIT.getPossibleValues()) {
                variantBuilder.addModels(variantBuilder.partialState().with(HorizontalDirectionalBlock.FACING, direction).with(BlockStateProperties.LIT, lit), getModel(direction, "block/" + RegistryEntries.BLOCK.getPath(block) + (lit ? "_on" : "")));
            }
        }
    }


    private void stairBlock(StairBlock block, Block materialBlock) {
        stairsBlock(block, modLoc("block/" + RegistryEntries.getKeyFrom(materialBlock).getPath()));
    }

    private void slabBlock(SlabBlock block, Block materialBlock) {
        ResourceLocation rl = modLoc("block/" + RegistryEntries.getKeyFrom(materialBlock).getPath());
        slabBlock(block, rl, rl);
    }

    private void batteryBlockStates(BatteryBlock<?> batteryBlock) {
        facingLitBlocks.add(batteryBlock);
        var builder = super.getVariantBuilder(batteryBlock);
        String batteryName = ModUtils.getPath(batteryBlock);

        for (int i = 0; i <= 10; i++) {
            for (Direction direction : horizontalDirections) {
                builder.addModels(builder.partialState().with(BatteryBlock.FACING, direction).with(BatteryBlock.ENERGY_LEVEL, i), getModel(direction, "block/battery/" + batteryName + "_level_" + i));
            }
        }
    }

    private void horizontalFacingBlock(Block block) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        for (Direction direction : horizontalDirections)
            builder.addModels(builder.partialState().with(HorizontalDirectionalBlock.FACING, direction), getModel(direction, "block/creative_battery"));
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
