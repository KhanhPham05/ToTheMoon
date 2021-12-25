package com.khanhpham.ttm.data.recipes;

import com.khanhpham.ttm.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;

public class ModRecipes extends RecipeProvider {
    public ModRecipes(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        final RecipeHelper helper = new RecipeHelper(pFinishedRecipeConsumer);
        helper.craftFourSame(ModBlocks.MOON_SURFACE_STONE_BRICKS, 4, ModBlocks.MOON_SURFACE_STONE, "moon_rock_bricks");
    }
}
