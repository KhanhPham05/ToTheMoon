package com.khanhpham.tothemoon.data.recipe;

import com.khanhpham.tothemoon.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements RecipeGeneratorHelper {

    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }


    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        stairBlocks(consumer, ModBlocks.MOON_ROCK_STAIRS, ModBlocks.MOON_ROCK_BRICK_STAIR);
        slabBlocks(consumer, ModBlocks.MOON_ROCK_SLAB, ModBlocks.MOON_ROCK_BRICK_SLAB);
    }
}
