package com.khanhtypo.tothemoon.serverdata.recipes;


import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class BaseSingleIngredientRecipe<T extends BaseSingleIngredientRecipe<T>> extends BaseRecipe<Container> {
    protected final Ingredient ingredient;

    public BaseSingleIngredientRecipe(Ingredient ingredient, ItemStack result, ResourceLocation recipeId, RecipeTypeObject<T> recipeTypeObject) {
        super(Util.make(NonNullList.withSize(1, Ingredient.EMPTY), list -> list.set(0, ingredient)), result, recipeId, recipeTypeObject);
        this.ingredient = ingredient;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
}
