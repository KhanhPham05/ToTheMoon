package com.khanhpham.tothemoon.data;

import com.khanhpham.tothemoon.ModUtils;
import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.blocks.AbstractEnergyGeneratorBlock;
import com.khanhpham.tothemoon.utils.blocks.MineableSlabBlocks;
import com.khanhpham.tothemoon.utils.blocks.MineableStairBlock;
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
            build(ModBlocks.GOLD_ENERGY_GENERATOR);
            build(ModBlocks.DIAMOND_ENERGY_GENERATOR);
            build(ModBlocks.COPPER_ENERGY_GENERATOR);
            build(ModBlocks.IRON_ENERGY_GENERATOR);
            build(ModBlocks.MOON_ROCK_STAIRS, ModBlocks.MOON_ROCK_BRICK_SLAB, ModBlocks.MOON_ROCK_BRICK_STAIR, ModBlocks.MOON_ROCK_SLAB);
            build(ModItems.COPPER_PLATE, ModItems.STEEL_PLATE, ModItems.IRON_PLATE, ModItems.STEEL_INGOT, ModItems.URANIUM_INGOT);
        }

        private void build(Block... blocks) {
            for (Block block : blocks) {
                this.build(block);
            }
        }

        private void build(Block block) {
            super.getBuilder(block.getRegistryName().getPath()).parent(getExistingFile(modLoc("block/" + block.getRegistryName().getPath())));
        }

        private void build(Item... items) {
            for (Item item : items) {
                this.build(item);
            }
        }

        private void build(Item item) {
            super.getBuilder(item.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + item.getRegistryName().getPath());
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
            generatorBlock(ModBlocks.GOLD_ENERGY_GENERATOR);
            generatorBlock(ModBlocks.DIAMOND_ENERGY_GENERATOR);
            generatorBlock(ModBlocks.COPPER_ENERGY_GENERATOR);
            generatorBlock(ModBlocks.IRON_ENERGY_GENERATOR);
        }

        private void cubeAll(Block block) {
            cubeAll(block.getRegistryName().getPath(), modLoc("block/" + block.getRegistryName().getPath()));
        }

        private void generatorBlock(Block block) {
            if (block instanceof AbstractEnergyGeneratorBlock) {
                String name = block.getRegistryName().getPath();
                super.orientableWithBottom(name, blockLoc(name + "_side"), blockLoc(name + "_front"), blockLoc(name + "_bottom"), blockLoc(name + "_top"));
                super.orientableWithBottom(name + "_on", blockLoc(name + "_side"), blockLoc(name + "_front_on"), blockLoc(name + "_bottom"), blockLoc(name + "_top"));
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
            generatorBlock(ModBlocks.GOLD_ENERGY_GENERATOR);
            generatorBlock(ModBlocks.DIAMOND_ENERGY_GENERATOR);
            generatorBlock(ModBlocks.COPPER_ENERGY_GENERATOR);
            generatorBlock(ModBlocks.IRON_ENERGY_GENERATOR);
            slabBlocks(ModBlocks.MOON_ROCK_SLAB, ModBlocks.MOON_ROCK_BRICK_SLAB);
            stairBlocks(ModBlocks.MOON_ROCK_BRICK_STAIR, ModBlocks.MOON_ROCK_STAIRS);
        }

        private void slabBlocks(Block... blocks) {
            for (Block block : blocks) {
                this.slabBlock(block);
            }
        }

        private void slabBlock(Block block) {
            if (block instanceof MineableSlabBlocks slabBlock) {
                super.slabBlock(slabBlock, ModUtils.modLoc("block/" + slabBlock.parentBlock().getRegistryName().getPath()), ModUtils.modLoc("block/" + slabBlock.parentBlock().getRegistryName().getPath()));
            }
        }

        private void stairBlocks(Block... blocks) {
            for (Block block : blocks) {
                this.stairBlock(block);
            }
        }

        private void stairBlock(Block block) {
            if (block instanceof MineableStairBlock stairBlock) {
                super.stairsBlock(stairBlock, ModUtils.modLoc("block/" + stairBlock.parentBlock().getRegistryName().getPath()));
            }
        }

        private void generatorBlock(Block block) {
            if (block instanceof AbstractEnergyGeneratorBlock generatorBlock) {
                for (Direction direction : Direction.values()) {
                    if (direction != Direction.UP && direction != Direction.DOWN) {
                        if (direction == Direction.EAST) {
                            addModel(generatorBlock, direction, 90);
                        } else if (direction == Direction.NORTH) {
                            addModel(generatorBlock, direction, 0);
                        } else if (direction == Direction.SOUTH) {
                            addModel(generatorBlock, direction, 180);
                        } else if (direction == Direction.WEST) {
                            addModel(generatorBlock, direction, 270);
                        }
                    }
                }
            }
        }

        private void addModel(VariantBlockStateBuilder.PartialBlockstate builder, Direction direction, Boolean lit, ConfiguredModel model) {
            builder.with(AbstractEnergyGeneratorBlock.FACING, direction).with(AbstractEnergyGeneratorBlock.LIT, lit)
                    .addModels(model);
        }

        private void addModel(Block generatorBlock, Direction direction, int y) {
            VariantBlockStateBuilder.PartialBlockstate builder = super.getVariantBuilder(generatorBlock).partialState();

            String blockName = generatorBlock.getRegistryName().getPath();
            final ResourceLocation locOn = new ResourceLocation(Names.MOD_ID, "block/" + blockName + "_on");
            final ResourceLocation locOff = new ResourceLocation(Names.MOD_ID, "block/" + blockName);


            ConfiguredModel on = new ConfiguredModel(new ModelFile.UncheckedModelFile(locOn), 0, y, false);
            ConfiguredModel off = new ConfiguredModel(new ModelFile.UncheckedModelFile(locOff), 0, y, false);

            this.addModel(builder, direction, Boolean.TRUE, on);
            this.addModel(builder, direction, Boolean.FALSE, off);
        }
    }
}
