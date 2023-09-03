package com.khanhtypo.tothemoon.serverdata.recipes.serializers;

import com.google.gson.JsonObject;
import com.khanhtypo.tothemoon.serverdata.SerializerHelper;
import com.khanhtypo.tothemoon.serverdata.recipes.LavaSmeltingRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.jetbrains.annotations.Nullable;

public class LavaSmeltingRecipeSerializer implements SerializerHelper<LavaSmeltingRecipe> {
    public static final int DEFAULT_SMELTING_TICK = 30 * 20;

    @Override
    public LavaSmeltingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        final Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "ingredient"), false);
        final ItemStack result = ShapedRecipe.itemStackFromJson(pSerializedRecipe.getAsJsonObject("result"));
        final int smeltingTick = GsonHelper.getAsInt(pSerializedRecipe, "smeltingTick", DEFAULT_SMELTING_TICK);
        return new LavaSmeltingRecipe(ingredient, result, smeltingTick, pRecipeId);
    }

    @Override
    public @Nullable LavaSmeltingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        final Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
        final ItemStack result = pBuffer.readItem();
        final int smeltingTick = pBuffer.readInt();
        return new LavaSmeltingRecipe(ingredient, result, smeltingTick, pRecipeId);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, LavaSmeltingRecipe pRecipe) {
        pRecipe.getIngredient().toNetwork(pBuffer);
        pBuffer.writeItem(pRecipe.getResultItem(null));
        pBuffer.writeInt(pRecipe.getSmeltingTick());
    }
}
