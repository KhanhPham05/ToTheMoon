package com.khanhtypo.tothemoon.serverdata.recipes.serializers;

import com.google.gson.JsonObject;
import com.khanhtypo.tothemoon.serverdata.SerializerHelper;
import com.khanhtypo.tothemoon.serverdata.recipes.AnvilSmashingRecipe;
import com.khanhtypo.tothemoon.utls.JsonUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public class AnvilSmashingRecipeSerializer implements SerializerHelper<AnvilSmashingRecipe> {
    @Override
    public AnvilSmashingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        return new AnvilSmashingRecipe(JsonUtils.jsonToIngredient(pSerializedRecipe, "ingredient", false), JsonUtils.jsonToItem(pSerializedRecipe, "result"), pRecipeId);
    }

    @Override
    public @Nullable AnvilSmashingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
        ItemStack result = pBuffer.readItem();
        return new AnvilSmashingRecipe(ingredient, result, pRecipeId);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, AnvilSmashingRecipe pRecipe) {
        pRecipe.getIngredient().toNetwork(pBuffer);
        pBuffer.writeItem(pRecipe.getResultItem(null));
    }
}
