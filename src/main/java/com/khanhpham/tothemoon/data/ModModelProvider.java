package com.khanhpham.tothemoon.data;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.blocks.AbstractEnergyGeneratorBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModModelProvider {
    private final ItemProvider items;
    private final BlockProvider blocks;
    private final BlockState blockStates;

    public ModModelProvider(DataGenerator dataGenerator, ExistingFileHelper fileHelper) {
        this.items = new ItemProvider(dataGenerator, fileHelper);
        this.blocks = new BlockProvider(dataGenerator, fileHelper);
        this.blockStates = new BlockState(dataGenerator, fileHelper);
    }

    public ItemProvider itemModels() {
        return items;
    }

    public BlockProvider blockModels() {
        return blocks;
    }

    public BlockState blockStates() {
        return this.blockStates;
    }

    private static final class ItemProvider extends ItemModelProvider {
        public ItemProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, Names.MOD_ID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            build(ModBlocks.MOON_ROCK);
            build(ModBlocks.MOON_ROCK_BRICKS);
            build(ModBlocks.MOON_ROCK_BARREL);
            build(ModBlocks.COPPER_ENERGY_GENERATOR);
        }

        private void build(Block block) {
            super.getBuilder(block.getRegistryName().getPath()).parent(getExistingFile(modLoc("block/" + block.getRegistryName().getPath())));
        }

        private void build(Item item) {
            super.getBuilder(item.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/generated"))).texture("leyer0", "item/" + item.getRegistryName().getPath());
        }
    }

    private static final class BlockProvider extends BlockModelProvider {

        public BlockProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, Names.MOD_ID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            cubeAll(ModBlocks.MOON_ROCK);
            cubeAll(ModBlocks.MOON_ROCK_BRICKS);
            cubeAll(ModBlocks.MOON_ROCK_BARREL);
            generatorBlock(ModBlocks.COPPER_ENERGY_GENERATOR);
        }

        private void cubeAll(Block block) {
            cubeAll(block.getRegistryName().getPath(), modLoc("block/" + block.getRegistryName().getPath()));
        }

        private void generatorBlock(Block block) {
            if (block instanceof AbstractEnergyGeneratorBlock) {
                String name = block.getRegistryName().getPath();
                super.orientableWithBottom(name, blockLoc(name + "_bottom"), blockLoc(name + "_front"), blockLoc(name + "_bottom"), blockLoc(name + "_top"));
                super.orientableWithBottom(name + "_on", blockLoc(name + "_bottom"), blockLoc(name + "_front_on"), blockLoc(name + "_bottom"), blockLoc(name + "_top"));
            }
        }

        private ResourceLocation blockLoc(String name) {
            return modLoc("block/" + name);
        }

    }

    private static final class BlockState extends BlockStateProvider {
        public BlockState(DataGenerator gen, ExistingFileHelper exFileHelper) {
            super(gen, Names.MOD_ID, exFileHelper);
        }

        @Override
        protected void registerStatesAndModels() {
            simpleBlock(ModBlocks.MOON_ROCK);
            simpleBlock(ModBlocks.MOON_ROCK_BRICKS);
            simpleBlock(ModBlocks.MOON_ROCK_BARREL);
            generatorBlock(ModBlocks.COPPER_ENERGY_GENERATOR);
        }

        private void generatorBlock(Block block) {
            if (block instanceof AbstractEnergyGeneratorBlock generatorBlock) {
                VariantBlockStateBuilder.PartialBlockstate builder = super.getVariantBuilder(generatorBlock).partialState();

                String blockName = generatorBlock.getRegistryName().getPath();
                ConfiguredModel on = new ConfiguredModel(new ModelFile.UncheckedModelFile(new ResourceLocation(Names.MOD_ID, "block/" + blockName + "_on")));
                ConfiguredModel off = new ConfiguredModel(new ModelFile.UncheckedModelFile(new ResourceLocation(Names.MOD_ID, "block/" + blockName)));

                for (Direction direction : Direction.values()) {
                    if (direction != Direction.UP && direction != Direction.DOWN) {
                        this.addModel(builder, direction, Boolean.TRUE, on);
                        this.addModel(builder, direction, Boolean.FALSE, off);
                    }
                }
            }
        }

        private void addModel(VariantBlockStateBuilder.PartialBlockstate builder, Direction direction, Boolean lit, ConfiguredModel model) {
            builder.with(AbstractEnergyGeneratorBlock.FACING, direction).with(AbstractEnergyGeneratorBlock.LIT, lit)
                    .addModels(model);
        }
    }
}
