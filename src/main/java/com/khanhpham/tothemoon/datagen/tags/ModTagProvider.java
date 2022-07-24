package com.khanhpham.tothemoon.datagen.tags;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.init.nondeferred.NonDeferredBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Set;
import java.util.function.Supplier;

import static com.khanhpham.tothemoon.init.ModBlocks.*;
import static com.khanhpham.tothemoon.datagen.tags.ModItemTags.*;
import static com.khanhpham.tothemoon.datagen.tags.ModBlockTags.*;
import static com.khanhpham.tothemoon.init.ModItems.*;
import static net.minecraftforge.common.Tags.Items.*;

public class ModTagProvider {
    public ModTagProvider(DataGenerator data, ExistingFileHelper fileHelper) {
        ModBlockTagsProvider provider = new ModBlockTagsProvider(data, fileHelper);
        data.addProvider(new ModItemTagsProvider(data, provider, fileHelper));
        data.addProvider(provider);
    }

    /**
     * @see Tags.Items
     */
    public static final class ModItemTagsProvider extends ItemTagsProvider {
        public ModItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, ExistingFileHelper existingFileHelper) {
            super(pGenerator, pBlockTagsProvider, Names.MOD_ID, existingFileHelper);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void addTags() {
            tag(ModItemTags.GENERAL_PRESS_MOLDS.getMainTag()).addTags(ModItemTags.PLATE_MOLD, ModItemTags.ROD_MOLD, ModItemTags.GEAR_MOLD);
            tag(ModItemTags.GEAR_MOLD).add(ModItems.GEAR_MOLD.get());
            tag(ModItemTags.ROD_MOLD).add(ModItems.ROD_MOLD.get());
            tag(ModItemTags.PLATE_MOLD).add(ModItems.PLATE_MOLD.get());

            this.add(Tags.Items.INGOTS, ModItems.STEEL_INGOT, ModItems.URANIUM_INGOT, ModItems.REDSTONE_METAL, ModItems.REDSTONE_STEEL_ALLOY);
            this.add(ModItemTags.INGOTS_URANIUM, ModItems.URANIUM_INGOT);
            this.add(ModItemTags.INGOTS_STEEL, ModItems.STEEL_INGOT);
            this.add(ModItemTags.INGOTS_REDSTONE_METAL, ModItems.REDSTONE_METAL);
            this.add(ModItemTags.INGOTS_REDSTONE_STEEL_ALLOY, ModItems.REDSTONE_STEEL_ALLOY);
            this.add(ModItemTags.DUSTS_COAL, ModItems.COAL_DUST);
            this.add(DUSTS_HEATED_COAL, HEATED_COAL_DUST);

            tag(ModItemTags.GENERAL_PLATES).addTags(ModItemTags.PLATES_STEEL, ModItemTags.PLATES_IRON, PLATES_COPPER, PLATES_GOLD, PLATES_REDSTONE_STEEL_ALLOY, PLATES_REDSTONE_METAL, PLATES_URANIUM);
            add(ModItemTags.PLATES_STEEL, ModItems.STEEL_PLATE);
            add(ModItemTags.PLATES_URANIUM, ModItems.URANIUM_PLATE);
            add(ModItemTags.PLATES_REDSTONE_METAL, ModItems.REDSTONE_METAL_PLATE);
            add(ModItemTags.PLATES_REDSTONE_STEEL_ALLOY, ModItems.REDSTONE_STEEL_ALLOY_PLATE);
            add(PLATES_COPPER, ModItems.COPPER_PLATE);
            add(PLATES_IRON, ModItems.IRON_PLATE);
            add(PLATES_GOLD, ModItems.GOLD_PLATE);
            add(GENERAL_TTM_HAMMERS, ModItems.DIAMOND_HAMMER, ModItems.NETHERITE_HAMMER, ModItems.STEEL_HAMMER, ModItems.WOODEN_HAMMER);
            add(DUSTS_GOLD, ModItems.GOLD_DUST);
            add(DUSTS_REDSTONE_STEEL_ALLOY, ModItems.REDSTONE_STEEL_ALLOY_DUST);
            add(Tags.Items.ORES, DEEPSLATE_URANIUM_ORE, MOON_GOLD_ORE, MOON_IRON_ORE, MOON_QUARTZ_ORE, MOON_REDSTONE_ORE, MOON_URANIUM_ORE);
            tag(SHEETMETALS).addTags(SHEETMETAL_COPPER, SHEETMETAL_GOLD, SHEETMETAL_IRON, SHEETMETAL_STEEL);
            add(SHEETMETAL_COPPER, COPPER_SHEET_BLOCK);
            add(SHEETMETAL_IRON, IRON_SHEET_BLOCK);
            add(SHEETMETAL_GOLD, GOLD_SHEET_BLOCK);
            add(SHEETMETAL_STEEL, STEEL_SHEET_BLOCK);
            add(Tags.Items.STONE, MOON_ROCK);
            add(URANIUM_RAW_MATERIAL, RAW_URANIUM_ORE);
            add(DUSTS_URANIUM, URANIUM_DUST);
            add(DUSTS_STEEL, STEEL_DUST);
            add(DUSTS_REDSTONE_METAL, REDSTONE_METAL);
            add(DUSTS_IRON, IRON_DUST);
            add(DUSTS_COPPER, COPPER_DUST);
            add(STEEL_STORAGE_BLOCK, STEEL_BLOCK);
            add(REDSTONE_METAL_STORAGE_BLOCK, REDSTONE_METAL_BLOCK);
            add(REDSTONE_STEEL_STORAGE_BLOCK, REDSTONE_STEEL_ALLOY_BLOCK);
            add(RAW_URANIUM_STORAGE_BLOCK, RAW_URANIUM_BLOCK);
            add(URANIUM_STORAGE_BLOCK, URANIUM_BLOCK);
            add(GEMS_PURIFIED_QUARTZ, PURIFIED_QUARTZ);
            add(PURIFIED_QUARTZ_STORAGE_BLOCK, PURIFIED_QUARTZ_BLOCK);
            add(GLASS_DARK, ANTI_PRESSURE_GLASS);
            tag(WIRES).addTags(WIRES_COPPER, WIRES_GOLD, WIRES_IRON, WIRES_STEEL, WIRES_REDSTONE_STEEL_ALLOY, WIRES_REDSTONE_METAL, WIRES_URANIUM);
            add(WIRES_COPPER, COPPER_WIRE);
            add(WIRES_GOLD, COPPER_WIRE);
            add(WIRES_IRON, COPPER_WIRE);
            add(WIRES_REDSTONE_METAL, COPPER_WIRE);
            add(WIRES_REDSTONE_STEEL_ALLOY, COPPER_WIRE);
            add(WIRES_STEEL, COPPER_WIRE);
            add(WIRES_URANIUM, COPPER_WIRE);
            add(TREATED_WOOD, PROCESSED_WOOD);
            add(ORE_BEARING_GROUND_STONE, MOON_ROCK);
            add(ORE_RATES_SINGULAR, MOON_GOLD_ORE, MOON_QUARTZ_ORE, MOON_IRON_ORE, MOON_URANIUM_ORE, DEEPSLATE_URANIUM_ORE);
            add(ORE_RATES_DENSE, MOON_REDSTONE_ORE);
            add(ORES_IN_MOON_ROCK, MOON_REDSTONE_ORE, MOON_URANIUM_ORE, MOON_IRON_ORE, MOON_GOLD_ORE);
            add(ORES_IN_MOON_DUST, MOON_QUARTZ_ORE);
        }

        @SafeVarargs
        private void add(TagKey<Item> tag, Supplier<? extends ItemLike>... suppliers) {
            var appender = super.tag(tag);
            for (Supplier<? extends ItemLike> supplier : suppliers) {
                appender.add(supplier.get().asItem());
            }
        }

        private void add(AppendableItemTagKey tag) {
            for (Supplier<? extends Item> perspectiveItem : tag.perspectiveItems) {
                this.add(tag.getMainTag(), perspectiveItem);
            }
        }
    }

    public static final class ModBlockTagsProvider extends BlockTagsProvider {

        public ModBlockTagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
            super(pGenerator, Names.MOD_ID, existingFileHelper);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void addTags() {
            TagsProvider.TagAppender<Block> blockTagAppender = tag(BlockTags.MINEABLE_WITH_PICKAXE);
            BLOCK_DEFERRED_REGISTER.getEntries().stream().map(Supplier::get).filter(b -> !b.equals(MOON_DUST.get())).forEach(blockTagAppender::add);

            //tag(BlockTags.MINEABLE_WITH_PICKAXE, POLISHED_MOON_ROCK_SLAB, POLISHED_MOON_ROCK_STAIR, POLISHED_MOON_ROCK, MOON_ROCK, MOON_ROCK_BRICK, MOON_ROCK_STAIR, MOON_ROCK_BRICK_STAIR, MOON_ROCK_BRICK_SLAB, MOON_ROCK, MOON_IRON_ORE, MOON_QUARTZ_ORE, COPPER_MACHINE_FRAME, STEEL_MACHINE_FRAME, REDSTONE_MACHINE_FRAME, DEEPSLATE_URANIUM_ORE, MOON_GOLD_ORE, MOON_REDSTONE_ORE, MOON_URANIUM_ORE);
            tag(BlockTags.NEEDS_STONE_TOOL, POLISHED_MOON_ROCK_SLAB, POLISHED_MOON_ROCK_STAIR, POLISHED_MOON_ROCK, MOON_ROCK, MOON_ROCK_BRICK, MOON_ROCK_STAIR, MOON_ROCK_BRICK_STAIR, MOON_ROCK_BRICK_SLAB, MOON_ROCK, MOON_IRON_ORE, MOON_QUARTZ_ORE, MOON_DUST);
            tag(BlockTags.NEEDS_IRON_TOOL, COPPER_MACHINE_FRAME, STEEL_MACHINE_FRAME, REDSTONE_MACHINE_FRAME, DEEPSLATE_URANIUM_ORE, MOON_GOLD_ORE, MOON_REDSTONE_ORE, MOON_URANIUM_ORE);
            tag(BlockTags.MINEABLE_WITH_SHOVEL, MOON_DUST);
            tag(Tags.Blocks.ORE_BEARING_GROUND_STONE, MOON_ROCK);
            tag(Tags.Blocks.ORE_RATES_SINGULAR, MOON_GOLD_ORE, MOON_QUARTZ_ORE, MOON_IRON_ORE, MOON_URANIUM_ORE, DEEPSLATE_URANIUM_ORE);
            tag(Tags.Blocks.ORE_RATES_DENSE, MOON_REDSTONE_ORE);
            tag(Tags.Blocks.ORES_QUARTZ, MOON_QUARTZ_ORE);
            tag(ORES_URANIUM, DEEPSLATE_URANIUM_ORE, MOON_URANIUM_ORE);
            joinTags(BLOCK_SHEETMETALS);
            super.tag(ModToolTags.NEEDS_STEEL_TOOLS).addTags(ORES_URANIUM);
            tag(Tags.Blocks.STONE, MOON_ROCK);

            super.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(NonDeferredBlocks.FLUID_TANK_BLOCK);
            super.tag(BlockTags.NEEDS_STONE_TOOL).add(NonDeferredBlocks.FLUID_TANK_BLOCK);
        }

        @SafeVarargs
        public final void tag(TagKey<Block> tag, Supplier<? extends Block>... blockSupplier) {
            var tags = super.tag(tag);
            for (Supplier<? extends Block> supplier : blockSupplier) {
                tags.add(supplier.get());
            }
        }

        @SuppressWarnings("unchecked")
        public void joinTags(AppendableBlockTagKey tag) {
            Set<TagKey<Block>> tags = tag.getMap().keySet();
            TagAppender<Block> appender = super.tag(tag.getMainTag());
            for (TagKey<Block> blockTagKey : tags) {
                appender.addTags(blockTagKey);
            }

            for (TagKey<Block> blockTagKey : tag.getMap().keySet()) {
                tag(blockTagKey).add(tag.getMap().get(blockTagKey));
            }
        }
    }

}
