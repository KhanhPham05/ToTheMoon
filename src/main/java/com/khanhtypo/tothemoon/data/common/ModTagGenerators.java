package com.khanhtypo.tothemoon.data.common;

import com.google.common.base.Preconditions;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.MultiblockBlackStoneFurnace;
import com.khanhtypo.tothemoon.utls.ModUtils;
import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.common.tag.TagFamily;
import com.khanhtypo.tothemoon.data.ModBlockItemTags;
import com.khanhtypo.tothemoon.data.ModBlockTags;
import com.khanhtypo.tothemoon.data.ModItemTags;
import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import com.khanhtypo.tothemoon.registration.elements.BlockObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.khanhtypo.tothemoon.registration.ModBlocks.*;

@ParametersAreNonnullByDefault
@SuppressWarnings("SameParameterValue")
public final class ModTagGenerators {

    @SuppressWarnings("unused")
    public static void addProviders(boolean includeServer, DataGenerator dataGenerator, PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, ExistingFileHelper fileHelper) {
        ModItemTags.staticInit();
        ModBlockTags.staticInit();
        ModBlockItemTags.staticInit();
        final BlockGenerators blockGeneratorsProvider = dataGenerator.addProvider(includeServer, new BlockGenerators(packOutput, lookup, ToTheMoon.MODID, fileHelper));
        final Item itemProvider = dataGenerator.addProvider(includeServer, new Item(packOutput, lookup, blockGeneratorsProvider.contentsGetter(), ToTheMoon.MODID, fileHelper));
    }

    public static final class BlockGenerators extends BlockTagsProvider {
        private final Set<? extends BlockObject<?>> modBlocks;

        BlockGenerators(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, modId, existingFileHelper);
            this.modBlocks = BlockObject.BLOCK_SET.stream().filter(block -> block.get().defaultBlockState().requiresCorrectToolForDrops()).collect(ModUtils::resourceSortedSet, Set::add, Set::addAll);
        }

        @Override
        @Nonnull
        public CompletableFuture<?> run(CachedOutput p_253684_) {
            CompletableFuture<?> completableFuture = super.run(p_253684_);
            if (!this.modBlocks.isEmpty()) {
                ToTheMoon.LOGGER.warn("There are blocks that are not defined a tool tag : %s".formatted(ModUtils.toArrayString(this.modBlocks, ObjectSupplier::getId)));
            }
            return completableFuture;
        }

        @Override
        protected void addTags(HolderLookup.Provider p_256380_) {
            TagFamily.generateToJson(Registries.BLOCK, super::tag);
            pickaxeMineable(METEORITE_LAMP, MOON_QUARTZ_ORE, MOON_ROCK_BARREL, PURIFIED_QUARTZ_BLOCK, SMOOTH_PURIFIED_QUARTZ_BLOCK, COPPER_POWER_GENERATOR, IRON_POWER_GENERATOR, GOLD_POWER_GENERATOR, DIAMOND_POWER_GENERATOR, NETHERITE_POWER_GENERATOR, BLACK_STONE_FURNACE_CONTROLLER, BLACKSTONE_EMPTY_ACCEPTOR, NETHER_BRICKS_EMPTY_ACCEPTOR, BLACKSTONE_ITEM_ACCEPTOR, NETHER_BRICKS_ITEM_ACCEPTOR);
            stonePickaxeMineable(COBBLED_METEORITE, COBBLED_METEORITE_STAIRS, COBBLED_METEORITE_SLAB, COBBLED_METEORITE_WALL, COBBLED_MOON_ROCK, COBBLED_MOON_ROCK_STAIRS, COBBLED_MOON_ROCK_SLAB, COBBLED_MOON_ROCK_WALL, ERODED_METEORITE, GILDED_METEORITE_BRICKS, METEORITE, METEORITE_SLAB, METEORITE_STAIR, METEORITE_WALL, METEORITE_BRICKS, METEORITE_BRICKS_SLAB, METEORITE_BRICKS_WALL, METEORITE_BRICKS_STAIRS, METEORITE_TILES, METEORITE_TILES_SLAB, METEORITE_TILES_WALL, METEORITE_TILES_STAIRS, MOON_ROCK, MOON_ROCK_SLAB, MOON_ROCK_WALL, MOON_ROCK_STAIRS, MOON_ROCK_BRICKS, MOON_ROCK_BRICKS_SLAB, MOON_ROCK_BRICKS_WALL, MOON_ROCK_BRICKS_STAIRS, POLISHED_METEORITE, POLISHED_METEORITE_SLAB, POLISHED_METEORITE_WALL, POLISHED_METEORITE_STAIRS, POLISHED_MOON_ROCK, POLISHED_MOON_ROCK_SLAB, POLISHED_MOON_ROCK_WALL, POLISHED_MOON_ROCK_STAIR, SMOOTH_BLACKSTONE, SMOOTH_BLACKSTONE_SLAB, SMOOTH_BLACKSTONE_WALL, SMOOTH_BLACKSTONE_STAIRS, SMOOTH_METEORITE, SMOOTH_METEORITE_SLAB, SMOOTH_METEORITE_WALL, SMOOTH_METEORITE_STAIRS, MOON_IRON_ORE);
            ironPickaxeMineable(COPPER_MACHINE_FRAME, DEEPSLATE_URANIUM_ORE, GOLD_SHEET_BLOCK, IRON_SHEET_BLOCK, MOON_URANIUM_ORE, METEORITE_ZIRCONIUM_ORE, MOON_GOLD_ORE, MOON_REDSTONE_ORE, PURE_ZIRCONIUM_BLOCK, REDSTONE_METAL_MACHINE_FRAME, REDSTONE_METAL_BLOCK, REDSTONE_STEEL_ALLOY_BLOCK, REDSTONE_STEEL_ALLOY_SHEET_BLOCK, REINFORCED_WOOD, STEEL_BLOCK, STEEL_MACHINE_FRAME, STEEL_SHEET_BLOCK, URANIUM_BLOCK, ZIRCONIUM_ALLOY_BLOCK, ZIRCONIUM_BLOCK, RAW_ZIRCONIUM_BLOCK, RAW_URANIUM_BLOCK, COPPER_SHEET_BLOCK);
            this.shovelMineable(MOON_DUST);
            this.axeMineable(PROCESSED_WOOD_PLANKS, WORKBENCH);
            super.tag(MultiblockBlackStoneFurnace.BLACKSTONE_ACCEPTABLE).add(SMOOTH_BLACKSTONE.get(), BLACKSTONE_EMPTY_ACCEPTOR.get());
            super.tag(MultiblockBlackStoneFurnace.NETHER_BRICK_ACCEPTABLE).add(NETHER_BRICKS_EMPTY_ACCEPTOR.get());
        }

        private void pickaxeMineable(BlockObject<?>... blocks) {
            defineTool(BlockTags.MINEABLE_WITH_PICKAXE, null, blocks);
        }

        private void stonePickaxeMineable(BlockObject<?>... blocks) {
            this.defineTool(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL, blocks);
        }

        private void ironPickaxeMineable(BlockObject<?>... blocks) {
            this.defineTool(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL, blocks);
        }

        private void axeMineable(BlockObject<?>... blocks) {
            this.defineTool(BlockTags.MINEABLE_WITH_AXE, null, blocks);
        }

        private void shovelMineable(BlockObject<?>... blocks) {
            this.defineTool(BlockTags.MINEABLE_WITH_SHOVEL, null, blocks);
        }

        private void defineTool(TagKey<Block> tagKey1, @Nullable TagKey<Block> tagKey2, @Nonnull BlockObject<?>[] blocks) {
            Preconditions.checkState(blocks.length > 0);
            Block[] blocks1 = Arrays.stream(blocks).map(BlockObject::get).toArray(Block[]::new);
            super.tag(tagKey1).add(blocks1);
            if (tagKey2 != null) super.tag(tagKey2).add(blocks1);
            Arrays.stream(blocks).toList().forEach(this.modBlocks::remove);
        }
    }

    public static final class Item extends ItemTagsProvider {

        Item(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, CompletableFuture<TagLookup<Block>> blockProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
            super(packOutput, lookup, blockProvider, modId, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider p_256380_) {
            TagFamily.generateToJson(Registries.ITEM, super::tag);
        }
    }
}
