package com.khanhtypo.tothemoon.serverdata;

import com.google.gson.JsonObject;
import com.khanhtypo.tothemoon.serverdata.recipes.BaseRecipe;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.conditions.ICondition;
import org.jetbrains.annotations.Nullable;

public interface RecipeSerializerHelper<T extends BaseRecipe<?>> extends RecipeSerializer<T> {
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

    @Override
    @Nullable
    default T fromJson(ResourceLocation recipeLoc, JsonObject recipeJson, ICondition.IContext context) {
        T recipe = null;
        try {
            recipe = fromJson(recipeLoc, recipeJson);
        } catch (Exception exception) {
            throw ModUtils.fillCrashReport(
                    exception,
                    "Reading Recipe",
                    exception.getClass().getSimpleName(),
                    category -> category.setDetail("Recipe ID ", recipeLoc.toString())
            );
        }
        return recipe;
    }
}
