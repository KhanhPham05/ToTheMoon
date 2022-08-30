package com.khanhpham.tothemoon.datagen.recipes.builders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nonnull;

abstract class AbstractFinishedRecipe<T extends Recipe<?>> implements FinishedRecipe {
    protected final ResourceLocation recipeId;
    protected final RecipeSerializer<T> serializer;
    protected final Advancement.Builder advancementBuilder;
    protected final ResourceLocation advancementRecipeId;

    public AbstractFinishedRecipe(ResourceLocation recipeId, RecipeSerializer<T> serializer, Advancement.Builder advancementBuilder, ResourceLocation advancementRecipeId) {
        this.recipeId = recipeId;
        this.serializer = serializer;
        this.advancementBuilder = advancementBuilder;
        this.advancementRecipeId = advancementRecipeId;
    }

    @Nonnull
    @Override
    public final ResourceLocation getId() {
        return this.recipeId;
    }

    @Nonnull
    @Override
    public final RecipeSerializer<T> getType() {
        return this.serializer;
    }

    @Override
    public final JsonObject serializeAdvancement() {
        return advancementBuilder.serializeToJson();
    }

    @Override
    public final ResourceLocation getAdvancementId() {
        return advancementRecipeId;
    }

    protected JsonElement itemStackToJson(ItemStack itemStack) {
        String itemId = ModUtils.getFullName(itemStack.getItem());
        if (itemStack.getCount() == 1) {
            return new JsonPrimitive(itemId);
        } else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", itemId);
            jsonObject.addProperty("count", itemStack.getCount());
            return jsonObject;
        }
    }
}
