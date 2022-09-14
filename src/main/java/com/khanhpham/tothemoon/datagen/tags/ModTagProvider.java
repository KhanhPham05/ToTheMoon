package com.khanhpham.tothemoon.datagen.tags;

import com.google.common.base.Preconditions;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.items.CraftingMaterial;
import com.khanhpham.tothemoon.init.ModItems;
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

import static com.khanhpham.tothemoon.datagen.tags.ModBlockTags.BLOCK_SHEETMETALS;
import static com.khanhpham.tothemoon.datagen.tags.ModItemTags.*;
import static com.khanhpham.tothemoon.init.ModBlocks.*;
import static com.khanhpham.tothemoon.init.ModItems.PURIFIED_QUARTZ;
import static com.khanhpham.tothemoon.init.ModItems.RAW_URANIUM_ORE;
import static net.minecraftforge.common.Tags.Items.*;

public class ModTagProvider {
    public ModTagProvider(DataGenerator data, ExistingFileHelper fileHelper) {
        ModBlockTagsProvider provider = new ModBlockTagsProvider(data, fileHelper);
        data.addProvider(true, new ModItemTagsProvider(data, provider, fileHelper));
        data.addProvider(true, provider);
    }

    public static final class ModItemTagsProvider extends ItemTagsProvider {
        public ModItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, ExistingFileHelper existingFileHelper) {
            super(pGenerator, pBlockTagsProvider, ToTheMoon.MOD_ID, existingFileHelper);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void addTags() {

            //this.add(Tags.Items.INGOTS, ModItems.STEEL_INGOT, ModItems.URANIUM_INGOT, ModItems.REDSTONE_METAL, ModItems.REDSTONE_STEEL_ALLOY);
            this.tag(ModItemTags.GENERAL_PLATES).addTags(ModItemTags.PLATES_STEEL, ModItemTags.PLATES_IRON, PLATES_COPPER, PLATES_GOLD, PLATES_REDSTONE_STEEL_ALLOY, PLATES_REDSTONE_METAL, PLATES_URANIUM);

            add(GENERAL_HAMMERS, ModItems.DIAMOND_HAMMER, ModItems.NETHERITE_HAMMER, ModItems.STEEL_HAMMER, ModItems.WOODEN_HAMMER);
            add(Tags.Items.STONE, MOON_ROCK);
            add(URANIUM_RAW_MATERIAL, RAW_URANIUM_ORE);
            add(GEMS_PURIFIED_QUARTZ, PURIFIED_QUARTZ);
            add(GLASS_DARK, ANTI_PRESSURE_GLASS);
            add(TREATED_WOOD, PROCESSED_WOOD);
            add(ModItemTags.ORES_URANIUM, DEEPSLATE_URANIUM_ORE);
            add(ORE_BEARING_GROUND_STONE, MOON_ROCK);
            add(ORE_RATES_SINGULAR, MOON_GOLD_ORE, MOON_QUARTZ_ORE, MOON_IRON_ORE, MOON_URANIUM_ORE, DEEPSLATE_URANIUM_ORE);
            add(ORE_RATES_DENSE, MOON_REDSTONE_ORE);
            add(ORES_IN_MOON_ROCK, MOON_REDSTONE_ORE, MOON_URANIUM_ORE, MOON_IRON_ORE, MOON_GOLD_ORE);
            add(ORES_IN_MOON_DUST, MOON_QUARTZ_ORE);

            add(GENERAL_DUSTS);
            add(GENERAL_PRESS_MOLDS);

            for (CraftingMaterial craftingMaterial : CraftingMaterial.ALL_MATERIALS) {
                craftingMaterial.addTags(this);
            }
        }

        @SafeVarargs
        public final void add(TagKey<Item> tag, Supplier<? extends ItemLike>... suppliers) {
            Preconditions.checkState(suppliers.length > 0, "Items are missing");
            var appender = super.tag(tag);
            for (Supplier<? extends ItemLike> supplier : suppliers) {
                appender.add(supplier.get().asItem());
            }
        }

        @Override
        public TagAppender<Item> tag(TagKey<Item> pTag) {
            return super.tag(pTag);
        }

        @SuppressWarnings("unchecked")
        private void add(AppendableItemTagKey tag) {
            TagKey<Item> main = tag.getMainTag();
            super.tag(main).addTags(tag.getMap().keySet().toArray(TagKey[]::new));
            tag.getMap().forEach((tagC, item) -> {
                if (item != null) {
                    super.tag(tagC).add(item);
                }
            });
        }

        @Override
        public String getName() {
            return "TTM Item Tags";
        }
    }

    public static final class ModBlockTagsProvider extends BlockTagsProvider {

        public ModBlockTagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
            super(pGenerator, ToTheMoon.MOD_ID, existingFileHelper);
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
            tag(ModBlockTags.ORES_URANIUM, DEEPSLATE_URANIUM_ORE, MOON_URANIUM_ORE);
            joinTags(BLOCK_SHEETMETALS);
            super.tag(ModToolTags.NEEDS_STEEL_TOOLS).addTags(ModBlockTags.ORES_URANIUM);
            tag(Tags.Blocks.STONE, MOON_ROCK);
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
