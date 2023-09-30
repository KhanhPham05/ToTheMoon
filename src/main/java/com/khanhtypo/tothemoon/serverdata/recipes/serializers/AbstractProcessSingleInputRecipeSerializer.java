package com.khanhtypo.tothemoon.serverdata.recipes.serializers;

import com.google.gson.JsonObject;
import com.khanhtypo.tothemoon.serverdata.RecipeSerializerHelper;
import com.khanhtypo.tothemoon.serverdata.recipes.AbstractProcessingSingleItemRecipe;
import com.khanhtypo.tothemoon.utls.JsonUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractProcessSingleInputRecipeSerializer<T extends AbstractProcessingSingleItemRecipe<T>> implements RecipeSerializerHelper<T> {
    @Override
    public T fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        final Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "ingredient"), false);
        final ItemStack result = JsonUtils.jsonToItem(pSerializedRecipe, "result");
        final int duration = GsonHelper.getAsInt(pSerializedRecipe, "duration", this.defaultDuration());
        return this.createRecipe(ingredient, result, duration, pRecipeId);
    }

    @Override
    public @Nullable T fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        final Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
        final ItemStack result = pBuffer.readItem();
        final int duration = pBuffer.readInt();
        return this.createRecipe(ingredient, result, duration, pRecipeId);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, T pRecipe) {
        pRecipe.getIngredient().toNetwork(pBuffer);
        pBuffer.writeItem(pRecipe.result);
        pBuffer.writeInt(pRecipe.processDuration);
    }

    protected abstract int defaultDuration();

    protected abstract T createRecipe(Ingredient ingredient, ItemStack result, int duration, ResourceLocation recipeId);
}
