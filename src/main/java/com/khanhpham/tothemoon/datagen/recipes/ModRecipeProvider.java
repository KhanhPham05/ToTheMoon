package com.khanhpham.tothemoon.datagen.recipes;

import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.datagen.tags.ModItemTags;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.TickTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.khanhpham.tothemoon.datagen.tags.ModItemTags.*;
import static net.minecraftforge.common.Tags.Items.*;

public class ModRecipeProvider extends RecipeProvider {
    public static int RECIPE_CODE = 1;

    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    public static ResourceLocation createRecipeId() {
        ResourceLocation id = ModUtils.modLoc("recipe_" + RECIPE_CODE);
        RECIPE_CODE++;
        return id;
    }

    static TickTrigger.TriggerInstance tick() {
        return new TickTrigger.TriggerInstance(EntityPredicate.Composite.ANY);
    }

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        buildSmeltingRecipes(consumer);
        buildModdedRecipes(consumer);
        buildCraftingTableRecipes(consumer);
    }

    private void buildCraftingTableRecipes(Consumer<FinishedRecipe> consumer) {
        buildStair(consumer, ModBlocks.MOON_ROCK_STAIR, ModBlocks.MOON_ROCK);
        buildStair(consumer, ModBlocks.MOON_ROCK_BRICK_STAIR, ModBlocks.MOON_ROCK_BRICK);
        buildStair(consumer, ModBlocks.POLISHED_MOON_ROCK_STAIR, ModBlocks.POLISHED_MOON_ROCK);
        buildStair(consumer, ModBlocks.COBBLED_MOON_ROCK_STAIR, ModBlocks.COBBLED_MOON_ROCK);
        buildSlab(consumer, ModBlocks.COBBLED_MOON_ROCK_SLAB, ModBlocks.COBBLED_MOON_ROCK);
        buildSlab(consumer, ModBlocks.MOON_ROCK_SLAB, ModBlocks.MOON_ROCK);
        buildSlab(consumer, ModBlocks.MOON_ROCK_BRICK_SLAB, ModBlocks.MOON_ROCK_BRICK);
        buildSlab(consumer, ModBlocks.POLISHED_MOON_ROCK_SLAB, ModBlocks.POLISHED_MOON_ROCK);

        storageBlock(consumer, ModBlocks.STEEL_BLOCK, ModItems.STEEL_INGOT);
        storageBlock(consumer, ModBlocks.REDSTONE_METAL_BLOCK, ModItems.REDSTONE_METAL);
        storageBlock(consumer, ModBlocks.REDSTONE_STEEL_ALLOY_BLOCK, ModItems.REDSTONE_STEEL_ALLOY);
        storageBlock(consumer, ModBlocks.RAW_URANIUM_BLOCK, ModItems.RAW_URANIUM_ORE);

        shapelessCrafting(consumer, ModItems.COPPER_PLATE, GENERAL_TTM_HAMMERS, INGOTS_COPPER);
        shapelessCrafting(consumer, ModItems.URANIUM_PLATE, GENERAL_TTM_HAMMERS, INGOTS_URANIUM);
        shapelessCrafting(consumer, ModItems.STEEL_PLATE, GENERAL_TTM_HAMMERS, INGOTS_STEEL);
        shapelessCrafting(consumer, ModItems.IRON_PLATE, GENERAL_TTM_HAMMERS, INGOTS_IRON);
        shapelessCrafting(consumer, ModItems.GOLD_PLATE, GENERAL_TTM_HAMMERS, INGOTS_GOLD);
        shapelessCrafting(consumer, ModItems.REDSTONE_METAL_PLATE, GENERAL_TTM_HAMMERS, INGOTS_REDSTONE_METAL);
        shapelessCrafting(consumer, ModItems.REDSTONE_STEEL_ALLOY_PLATE, GENERAL_TTM_HAMMERS, INGOTS_REDSTONE_STEEL_ALLOY);

        shaped(ModItems.CPU_CHIP).pattern("RRR").pattern("DGD").pattern("SSS").define('R', PLATES_REDSTONE_METAL).define('D', DUSTS_REDSTONE).define('G', DUSTS_GOLD).define('S', PLATES_STEEL).save(consumer, createRecipeId());
        shaped(ModItems.CIRCUIT_BOARD).pattern("UUU").pattern("RCR").pattern("GGG").define('U', PLATES_URANIUM).define('R', DUSTS_REDSTONE_STEEL_ALLOY).define('G', PLATES_GOLD).define('C', ModItems.CPU_CHIP.get());
        shaped(ModBlocks.METAL_PRESSING_PLATE).pattern("AAA").define('A', Blocks.SMOOTH_STONE_SLAB).save(consumer, createRecipeId());
        shaped(ModBlocks.MOON_ROCK_BARREL).pattern(" M ").pattern("MCM").pattern(" M ").define('M', ModItemTags.MOON_ROCKS).define('C', CHESTS_WOODEN);

        sheetBlock(consumer, ModBlocks.COPPER_SHEET_BLOCK, PLATES_COPPER);
        sheetBlock(consumer, ModBlocks.GOLD_SHEET_BLOCK, PLATES_GOLD);
        sheetBlock(consumer, ModBlocks.STEEL_SHEET_BLOCK, PLATES_STEEL);
        sheetBlock(consumer, ModBlocks.IRON_SHEET_BLOCK, PLATES_IRON);

        shapeless(consumer, ModItems.COPPER_PLATE, 8, ModBlocks.COPPER_SHEET_BLOCK);
        shapeless(consumer, ModItems.GOLD_PLATE, 8, ModBlocks.GOLD_SHEET_BLOCK);
        shapeless(consumer, ModItems.STEEL_PLATE, 8, ModBlocks.STEEL_SHEET_BLOCK);
        shapeless(consumer, ModItems.IRON_PLATE, 8, ModBlocks.IRON_SHEET_BLOCK);

        polished(consumer, ModBlocks.SMOOTH_PURIFIED_QUARTZ_BLOCK, ModBlocks.PURIFIED_QUARTZ_BLOCK);
        polished(consumer, ModBlocks.POLISHED_MOON_ROCK, ModBlocks.MOON_ROCK);
    }

    private void sheetBlock(Consumer<FinishedRecipe> consumer, Supplier<? extends Block> block, TagKey<Item> material) {
        this.shaped(block).pattern("SSS").pattern("S S").pattern("SSS").define('S', material).save(consumer, createRecipeId());
    }

    private void storageBlock(Consumer<FinishedRecipe> consumer, Supplier<? extends Block> block, Supplier<? extends Item> item) {
        this.shaped(block).pattern("AAA").pattern("AAA").pattern("AAA").define('A', item.get()).save(consumer, createRecipeId());
        this.shapeless(consumer, item, 9, block);
    }

    @SafeVarargs
    private void shapeless(Consumer<FinishedRecipe> consumer, Supplier<? extends ItemLike> result, int amount, Supplier<? extends ItemLike>... from) {
        var builder = ShapelessRecipeBuilder.shapeless(result.get(), amount).unlockedBy("tick", tick());
        for (Supplier<? extends ItemLike> supplier : from) {
            builder.requires(supplier.get());
        }

        builder.save(consumer, createRecipeId());
    }

    @SafeVarargs
    private void shapelessCrafting(Consumer<FinishedRecipe> consumer, Supplier<? extends Item> result, TagKey<Item>... ingredients) {
        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(result.get(), 1);
        for (TagKey<Item> ingredient : ingredients) {
            builder.requires(ingredient);
        }

        builder.unlockedBy("tick", tick());
        builder.save(consumer, createRecipeId());
    }

    private void polished(Consumer<FinishedRecipe> consumer, Supplier<? extends Block> block, Supplier<? extends Block> material) {
        shaped(block).pattern("AA").pattern("AA").define('A', material.get()).save(consumer, createRecipeId());
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(material.get()), block.get()).unlockedBy("tick", tick()).save(consumer, createRecipeId());
    }

    private void buildModdedRecipes(Consumer<FinishedRecipe> consumer) {
        alloy(ModItemTags.INGOTS_STEEL, 1, Tags.Items.DUSTS_REDSTONE, 3, ModItems.REDSTONE_STEEL_ALLOY, 1).save(consumer);
        alloy(Tags.Items.INGOTS_IRON, 1, Tags.Items.DUSTS_REDSTONE, 3, ModItems.REDSTONE_METAL, 1).save(consumer);
        alloy(ModItemTags.DUSTS_COAL, 2, Tags.Items.INGOTS_IRON, 1, ModItems.STEEL_INGOT, 1).save(consumer);

        metalPress(consumer, INGOTS_STEEL, PLATE_MOLD, ModItems.STEEL_PLATE);
        metalPress(consumer, INGOTS_STEEL, GEAR_MOLD, ModItems.STEEL_GEAR);
        metalPress(consumer, PLATES_STEEL, ROD_MOLD, ModItems.STEEL_ROD);

        metalPress(consumer, INGOTS_URANIUM, PLATE_MOLD, ModItems.URANIUM_PLATE);
        metalPress(consumer, INGOTS_URANIUM, GEAR_MOLD, ModItems.URANIUM_GEAR);
        metalPress(consumer, PLATES_URANIUM, ROD_MOLD, ModItems.URANIUM_ROD);

        metalPress(consumer, INGOTS_REDSTONE_METAL, PLATE_MOLD, ModItems.REDSTONE_METAL_PLATE);
        metalPress(consumer, PLATES_REDSTONE_METAL, ROD_MOLD, ModItems.REDSTONE_METAL_ROD);
        metalPress(consumer, INGOTS_REDSTONE_METAL, GEAR_MOLD, ModItems.REDSTONE_METAL_GEAR);

        metalPress(consumer, INGOTS_REDSTONE_STEEL_ALLOY, PLATE_MOLD, ModItems.REDSTONE_STEEL_ALLOY_PLATE);
        metalPress(consumer, PLATES_REDSTONE_STEEL_ALLOY, ROD_MOLD, ModItems.REDSTONE_STEEL_ALLOY_ROD);
        metalPress(consumer, INGOTS_REDSTONE_STEEL_ALLOY, GEAR_MOLD, ModItems.REDSTONE_STEEL_ALLOY_GEAR);

        metalPress(consumer, INGOTS_IRON, PLATE_MOLD, ModItems.IRON_PLATE);
        metalPress(consumer, PLATES_IRON, ROD_MOLD, ModItems.IRON_ROD);
        metalPress(consumer, INGOTS_IRON, GEAR_MOLD, ModItems.IRON_GEAR);

        metalPress(consumer, INGOTS_GOLD, GEAR_MOLD, ModItems.GOLD_GEAR);
        metalPress(consumer, INGOTS_GOLD, PLATE_MOLD, ModItems.GOLD_PLATE);
        metalPress(consumer, PLATES_GOLD, ROD_MOLD, ModItems.GOLD_ROD);

        metalPress(consumer, PLATES_COPPER, ROD_MOLD, ModItems.COPPER_ROD);
        metalPress(consumer, INGOTS_COPPER, PLATE_MOLD, ModItems.COPPER_PLATE);
        metalPress(consumer, INGOTS_COPPER, GEAR_MOLD, ModItems.COPPER_GEAR);
    }

    private void metalPress(Consumer<FinishedRecipe> consumer, TagKey<Item> ingredient, TagKey<Item> moldTag, Supplier<? extends Item> result) {
        MetalPressRecipeBuilder builder = MetalPressRecipeBuilder.press(ingredient, moldTag, result.get());
        builder.save(consumer);
        ManualMetalPressingRecipeBuilder.build(consumer, ingredient, moldTag, result.get());
    }

    private AlloySmeltingRecipeBuilder alloy(TagKey<Item> item1, int count1, TagKey<Item> item2, int count2, Supplier<? extends ItemLike> result, int amount) {
        return AlloySmeltingRecipeBuilder.build(item1, count1, item2, count2, result.get(), amount);
    }

    private void buildSlab(Consumer<FinishedRecipe> consumer, Supplier<SlabBlock> slabBlockSupplier, Supplier<Block> materialSupplier) {
        slabBuilder(slabBlockSupplier.get(), Ingredient.of(materialSupplier.get())).unlockedBy("tick", tick()).save(consumer, createRecipeId());
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(materialSupplier.get()), slabBlockSupplier.get(), 2).unlockedBy("tick", tick()).save(consumer, createRecipeId());
    }

    private void buildSmeltingRecipes(Consumer<FinishedRecipe> consumer) {
        this.oreSmelting(consumer, ModBlocks.DEEPSLATE_URANIUM_ORE, ModItems.URANIUM_INGOT);
        this.oreSmelting(consumer, ModBlocks.MOON_GOLD_ORE, Items.GOLD_INGOT);
        this.oreSmelting(consumer, ModBlocks.MOON_IRON_ORE, Items.IRON_INGOT);
        this.oreSmelting(consumer, ModBlocks.MOON_QUARTZ_ORE, ModItems.PURIFIED_QUARTZ);
        this.oreSmelting(consumer, ModBlocks.MOON_URANIUM_ORE, ModItems.URANIUM_INGOT);
        this.oreSmelting(consumer, URANIUM_RAW_MATERIAL, ModItems.URANIUM_INGOT, 1.0f, 200);
        this.oreSmelting(consumer, RAW_URANIUM_STORAGE_BLOCK, ModBlocks.URANIUM_BLOCK, 3.0f, 200 * 9);
        this.dustSmelting(consumer);
    }

    private void dustSmelting(Consumer<FinishedRecipe> consumer) {
        GENERAL_DUSTS.getProcessMap().forEach((tag, item) -> this.oreSmelting(consumer, tag, item, 1.0f, 200));
    }


    private void oreSmelting(Consumer<FinishedRecipe> consumer, TagKey<Item> tag, Supplier<? extends ItemLike> sup, float exp, int defaultFurnaceTime) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(tag), sup.get(), exp, defaultFurnaceTime).unlockedBy("tick", tick()).save(consumer, createRecipeId());
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(tag), sup.get(), exp, defaultFurnaceTime / 2).unlockedBy("tick", tick()).save(consumer, createRecipeId());
    }

    private void oreSmelting(Consumer<FinishedRecipe> consumer, Supplier<? extends ItemLike> ingredient, Supplier<? extends Item> resultSupplier) {
        this.oreSmelting(consumer, ingredient.get(), resultSupplier.get());
    }

    private void oreSmelting(Consumer<FinishedRecipe> consumer, Supplier<? extends ItemLike> ingredient, Item result) {
        this.oreSmelting(consumer, ingredient.get(), result);
    }

    private void oreSmelting(Consumer<FinishedRecipe> consumer, ItemLike ingredient, ItemLike result) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), result, 1.0f, 200).unlockedBy("tick", tick()).save(consumer, createRecipeId());
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredient), result, 1.0f, 100).unlockedBy("tick", tick()).save(consumer, createRecipeId());
    }

    private ShapedRecipeBuilder shaped(Supplier<? extends ItemLike> item) {
        return ShapedRecipeBuilder.shaped(item.get()).unlockedBy("tick", tick());
    }

    private void buildStair(Consumer<FinishedRecipe> consumer, Supplier<? extends StairBlock> resultSupplier, Supplier<? extends Block> materialSupplier) {
        stairBuilder(resultSupplier.get(), Ingredient.of(materialSupplier.get())).unlockedBy("has_" + materialSupplier.get().getRegistryName().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(materialSupplier.get())).save(consumer, createRecipeId());
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(materialSupplier.get()), resultSupplier.get());
    }
}

