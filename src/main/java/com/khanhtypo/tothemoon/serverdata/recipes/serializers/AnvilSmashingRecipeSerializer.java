package com.khanhtypo.tothemoon.serverdata.recipes.serializers;

import com.khanhtypo.tothemoon.serverdata.recipes.AnvilSmashingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class AnvilSmashingRecipeSerializer extends AbstractSingleInputRecipeSerializer<AnvilSmashingRecipe> {
    @Override
    protected AnvilSmashingRecipe createRecipe(Ingredient ingredient, ItemStack result, ResourceLocation recipeId) {
        return new AnvilSmashingRecipe(ingredient, result, recipeId);
    }
}
