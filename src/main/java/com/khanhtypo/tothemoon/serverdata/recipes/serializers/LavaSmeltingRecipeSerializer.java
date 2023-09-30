package com.khanhtypo.tothemoon.serverdata.recipes.serializers;

import com.google.gson.JsonObject;
import com.khanhtypo.tothemoon.serverdata.RecipeSerializerHelper;
import com.khanhtypo.tothemoon.serverdata.recipes.LavaSmeltingRecipe;
import com.khanhtypo.tothemoon.utls.JsonUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public class LavaSmeltingRecipeSerializer extends AbstractProcessSingleInputRecipeSerializer<LavaSmeltingRecipe> {
    public static final int DEFAULT_SMELTING_TICK = 30 * 20;

    @Override
    protected int defaultDuration() {
        return DEFAULT_SMELTING_TICK;
    }

    @Override
    protected LavaSmeltingRecipe createRecipe(Ingredient ingredient, ItemStack result, int duration, ResourceLocation recipeId) {
        return new LavaSmeltingRecipe(ingredient, result, duration, recipeId);
    }
}
