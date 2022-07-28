package com.khanhpham.tothemoon.datagen.recipes;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

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

    protected JsonObject convertItemToJson(Item item) {
        JsonObject object = new JsonObject();
        object.addProperty(JsonNames.ITEM, item.getRegistryName().getPath());
        return object;
    }
}
