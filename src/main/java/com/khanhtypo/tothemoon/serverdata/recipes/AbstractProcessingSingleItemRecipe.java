package com.khanhtypo.tothemoon.serverdata.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class AbstractProcessingSingleItemRecipe<T extends AbstractProcessingSingleItemRecipe<T>> extends BaseSingleIngredientRecipe<T> {
    public final int processDuration;

    public AbstractProcessingSingleItemRecipe(Ingredient ingredient, ItemStack result, int processDuration, ResourceLocation recipeId, RecipeTypeObject<T> recipeTypeObject) {
        super(ingredient, result, recipeId, recipeTypeObject);
        this.processDuration = processDuration;
    }

}
