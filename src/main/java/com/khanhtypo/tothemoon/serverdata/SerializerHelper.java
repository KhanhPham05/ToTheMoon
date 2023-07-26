package com.khanhtypo.tothemoon.serverdata;

import com.khanhtypo.tothemoon.serverdata.recipes.BaseRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public interface SerializerHelper<T extends BaseRecipe<?>> extends RecipeSerializer<T> {
    String RESULT = "result";

    default NonNullList<Ingredient> ingredientsFromNetwork(FriendlyByteBuf buffer, int size) {
        final NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);

        for (int i = 0; i < size; i++) {
            ingredients.set(i, Ingredient.fromNetwork(buffer));
        }

        return ingredients;
    }

    default void ingredientsToNetwork(FriendlyByteBuf buffer, Recipe<?> recipe) {
        recipe.getIngredients().forEach(ingredient -> ingredient.toNetwork(buffer));
    }
}
