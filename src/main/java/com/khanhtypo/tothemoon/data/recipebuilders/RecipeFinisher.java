package com.khanhtypo.tothemoon.data.recipebuilders;

import com.google.gson.JsonObject;
import com.khanhtypo.tothemoon.utls.JsonUtils;
import com.khanhtypo.tothemoon.serverdata.SerializerHelper;
import com.khanhtypo.tothemoon.serverdata.recipes.RecipeTypeObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Consumer;

public final class RecipeFinisher implements FinishedRecipe {

    private final RecipeTypeObject<?> recipeTypeObject;
    private final Consumer<JsonObject> serializer;
    private final ResourceLocation recipeId;
    private final Advancement.Builder unlockAdvancement;
    private final ResourceLocation advancementSaveId;
    private final ItemStack result;

    public RecipeFinisher(RecipeTypeObject<?> recipeTypeObject, Consumer<JsonObject> serializer, ResourceLocation recipeId, Advancement.Builder unlockAdvancement, ResourceLocation advancementSaveId, ItemStack result) {
        this.recipeTypeObject = recipeTypeObject;
        this.serializer = serializer;
        this.recipeId = recipeId;
        this.unlockAdvancement = unlockAdvancement;
        this.advancementSaveId = advancementSaveId;
        this.result = result;
    }

    @Override
    public void serializeRecipeData(JsonObject jsonObject) {
        this.serializer.accept(jsonObject);
        jsonObject.add(SerializerHelper.RESULT, JsonUtils.itemToJson(this.result));
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public RecipeSerializer<?> getType() {
        return this.recipeTypeObject.getSerializer();
    }

    @Override
    public JsonObject serializeAdvancement() {
        return this.unlockAdvancement.serializeToJson();
    }

    @Override
    public ResourceLocation getAdvancementId() {
        return this.advancementSaveId;
    }
}
