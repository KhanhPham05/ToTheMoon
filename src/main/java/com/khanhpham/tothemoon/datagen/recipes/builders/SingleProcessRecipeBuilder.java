package com.khanhpham.tothemoon.datagen.recipes.builders;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.core.recipes.SingleProcessRecipe;
import com.khanhpham.tothemoon.datagen.recipes.elements.ShortenIngredient;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SingleProcessRecipeBuilder implements RecipeBuilder {
    private final RecipeSerializer<SingleProcessRecipe> serializer;
    private final ResourceLocation recipeId;
    private final ShortenIngredient shortenIngredient;
    private final ItemStack result;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();

    private SingleProcessRecipeBuilder(RecipeSerializer<SingleProcessRecipe> recipeType, ResourceLocation recipeId, ShortenIngredient shortenIngredient, ItemStack result) {
        this.serializer = recipeType;
        this.recipeId = recipeId;
        this.shortenIngredient = shortenIngredient;
        this.result = result;
    }

    public static void metalCrushing(Consumer<FinishedRecipe> consumer, ShortenIngredient ingredient, ItemStack result, String recipeId) {
        SingleProcessRecipeBuilder builder = new SingleProcessRecipeBuilder(ModRecipes.METAL_CRUSHING, ModUtils.modLoc("metal_crushing/" + recipeId), ingredient, result);
        builder.unlockedBy("has_item", builder.shortenIngredient.getInventoryTrigger());
        builder.save(consumer, builder.recipeId);
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        advancementBuilder.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        pFinishedRecipeConsumer.accept(new Finished<>(pRecipeId, this.serializer, this));
    }

    private static final class Finished<T extends SingleProcessRecipe> extends AbstractFinishedRecipe<T> {
        private final SingleProcessRecipeBuilder builder;

        public Finished(ResourceLocation recipeId, RecipeSerializer<T> serializer, SingleProcessRecipeBuilder builder) {
            super(recipeId, serializer, builder.advancementBuilder);
            this.builder = builder;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("ingredient", builder.shortenIngredient.toShortenJson());
            pJson.add("result", super.itemStackToJson(builder.result));
        }
    }
}
