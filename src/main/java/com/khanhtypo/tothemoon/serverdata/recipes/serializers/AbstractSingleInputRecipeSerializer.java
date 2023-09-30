package com.khanhtypo.tothemoon.serverdata.recipes.serializers;

import com.google.gson.JsonObject;
import com.khanhtypo.tothemoon.serverdata.RecipeSerializerHelper;
import com.khanhtypo.tothemoon.serverdata.recipes.BaseSingleIngredientRecipe;
import com.khanhtypo.tothemoon.utls.JsonUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractSingleInputRecipeSerializer<T extends BaseSingleIngredientRecipe<T>> implements RecipeSerializerHelper<T> {
    @Override
    public T fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        return this.createRecipe(
                JsonUtils.jsonToIngredient(pSerializedRecipe, "ingredient", false),
                JsonUtils.jsonToItem(pSerializedRecipe, "result"),
                pRecipeId
        );
    }

    @Override
    public @Nullable T fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
        ItemStack result = pBuffer.readItem();
        return this.createRecipe(ingredient, result, pRecipeId);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, T pRecipe) {
        pRecipe.getIngredient().toNetwork(pBuffer);
        pBuffer.writeItem(pRecipe.result);
    }

    protected abstract T createRecipe(Ingredient ingredient, ItemStack result, ResourceLocation recipeId);
}
