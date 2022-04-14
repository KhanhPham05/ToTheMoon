package com.khanhpham.tothemoon.datagen.recipes;

import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.ModTags;
import com.khanhpham.tothemoon.utils.ModUtils;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.TickTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }
    public static int RECIPE_CODE = 1;

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        buildSmeltingRecipes(consumer);
        buildModdedRecipes(consumer);
        buildStair(consumer, ModBlocks.MOON_ROCK_STAIR, ModBlocks.MOON_ROCK);
        buildStair(consumer, ModBlocks.MOON_ROCK_BRICK_STAIR, ModBlocks.MOON_ROCK_BRICK);
        buildSlab(consumer, ModBlocks.MOON_ROCK_SLAB, ModBlocks.MOON_ROCK);
        buildSlab(consumer, ModBlocks.MOON_ROCK_BRICK_SLAB, ModBlocks.MOON_ROCK_BRICK);
    }

    private void buildModdedRecipes(Consumer<FinishedRecipe> consumer) {
        alloy(ModTags.INGOTS_STEEL, 1, Tags.Items.DUSTS_REDSTONE, 3, ModItems.REDSTONE_STEEL_ALLOY, 1).save(consumer);
        alloy(Tags.Items.INGOTS_IRON, 1, Tags.Items.DUSTS_REDSTONE ,3, ModItems.REDSTONE_METAL, 1).save(consumer);
        alloy(ModTags.DUSTS_COAL, 2, Tags.Items.INGOTS_IRON, 1, ModItems.STEEL_INGOT, 1).save(consumer);

        metalPress(consumer, ModTags.INGOTS_STEEL, ModTags.PLATES_MOLD, ModItems.STEEL_PLATE);
        metalPress(consumer, ModTags.INGOTS_STEEL, ModTags.GEAR_MOLD, ModItems.STEEL_GEAR);
        metalPress(consumer, ModTags.INGOTS_STEEL, ModTags.ROD_MOLD, ModItems.STEEL_ROD);

        metalPress(consumer, ModTags.INGOTS_URANIUM, ModTags.PLATES_MOLD, ModItems.URANIUM_PLATE);
        metalPress(consumer, ModTags.INGOTS_URANIUM, ModTags.GEAR_MOLD, ModItems.URANIUM_GEAR);
        metalPress(consumer, ModTags.INGOTS_URANIUM, ModTags.ROD_MOLD, ModItems.URANIUM_ROD);

        metalPress(consumer, ModTags.INGOTS_REDSTONE_METAL, ModTags.PLATES_MOLD, ModItems.REDSTONE_METAL_PLATE);
        metalPress(consumer, ModTags.INGOTS_REDSTONE_METAL, ModTags.ROD_MOLD, ModItems.REDSTONE_METAL_ROD);
        metalPress(consumer, ModTags.INGOTS_REDSTONE_METAL, ModTags.GEAR_MOLD, ModItems.REDSTONE_METAL_GEAR);

        metalPress(consumer, ModTags.INGOTS_REDSTONE_STEEL_ALLOY, ModTags.PLATES_MOLD, ModItems.REDSTONE_STEEL_ALLOY_PLATE);
        metalPress(consumer, ModTags.INGOTS_REDSTONE_STEEL_ALLOY, ModTags.ROD_MOLD, ModItems.REDSTONE_STEEL_ALLOY_ROD);
        metalPress(consumer, ModTags.INGOTS_REDSTONE_STEEL_ALLOY, ModTags.GEAR_MOLD, ModItems.REDSTONE_STEEL_ALLOY_GEAR);

    }

    private void metalPress(Consumer<FinishedRecipe> consumer, TagKey<Item> ingredient, TagKey<Item> moldTag, Supplier<? extends Item> result) {
        MetalPressRecipeBuilder builder = MetalPressRecipeBuilder.press(ingredient, moldTag, result.get());
        builder.save(consumer);

    }

    private AlloySmeltingRecipeBuilder alloy(TagKey<Item> item1, int count1, TagKey<Item> item2, int count2, Supplier<? extends ItemLike> result, int amount) {
        return AlloySmeltingRecipeBuilder.build(item1, count1, item2, count2, result.get(), amount);
    }

    private void buildSlab(Consumer<FinishedRecipe> consumer, Supplier<SlabBlock> slabBlockSupplier, Supplier<Block> materialSupplier) {
        slabBuilder(slabBlockSupplier.get(), Ingredient.of(materialSupplier.get())).unlockedBy("tick" , tick()).save(consumer, createRecipeId());
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(materialSupplier.get()), slabBlockSupplier.get(), 2).unlockedBy("tick",tick()).save(consumer, createRecipeId());
    }

    public static ResourceLocation createRecipeId() {
        ResourceLocation id = ModUtils.modLoc("recipe_" + RECIPE_CODE);
        RECIPE_CODE++;
        return id;
    }

    private void buildSmeltingRecipes(Consumer<FinishedRecipe> consumer) {
        this.oreSmelting(consumer, ModBlocks.DEEPSLATE_URANIUM_ORE, ModItems.URANIUM_INGOT);
        this.oreSmelting(consumer, ModBlocks.MOON_GOLD_ORE, Items.GOLD_INGOT);
        this.oreSmelting(consumer, ModBlocks.MOON_IRON_ORE, Items.IRON_INGOT);
        this.oreSmelting(consumer, ModBlocks.MOON_URANIUM_ORE, ModItems.URANIUM_INGOT);
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
    private TickTrigger.TriggerInstance tick() {
        return new TickTrigger.TriggerInstance(EntityPredicate.Composite.ANY);
    }

    private void buildStair(Consumer<FinishedRecipe> consumer, Supplier<? extends StairBlock> resultSupplier, Supplier<? extends Block> materialSupplier) {
        stairBuilder(resultSupplier.get(), Ingredient.of(materialSupplier.get())).unlockedBy("has_" + materialSupplier.get().getRegistryName().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(materialSupplier.get())).save(consumer, createRecipeId());
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(materialSupplier.get()), resultSupplier.get());
    }
}

