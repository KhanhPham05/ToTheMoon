package com.khanhtypo.tothemoon.serverdata.recipes;

import com.khanhtypo.tothemoon.registration.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class AnvilSmashingRecipe extends BaseSingleIngredientRecipe<Container> {
    public AnvilSmashingRecipe(Ingredient ingredient, ItemStack result, ResourceLocation recipeId) {
        super(ingredient, result, recipeId, ModRecipeTypes.ANVIL_SMASHING);
    }
}
