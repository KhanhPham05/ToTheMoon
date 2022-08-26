package com.khanhpham.tothemoon.datagen.recipes.builders;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.core.recipes.OreProcessingRecipe;
import com.khanhpham.tothemoon.core.recipes.elements.ChancedResult;
import com.khanhpham.tothemoon.datagen.recipes.elements.ShortenIngredient;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.TickTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class OreProcessingBuilder implements RecipeBuilder {
    public static final int DEFAULT_RAW_ORE_DOUBLE_CHANCE = 30;
    public static final int DEFAULT_ORE_DOUBLE_CHANCE = 50;
    private final ItemStack result;
    private final ShortenIngredient ingredient;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();
    private ChancedResult chancedResult = ChancedResult.NO_EXTRA;
    private int processingTime = 100;

    private OreProcessingBuilder(ItemStack result, ShortenIngredient ingredient) {
        this.result = result;
        this.ingredient = ingredient;
    }

    public static OreProcessingBuilder process(ItemStack result, ShortenIngredient ingredient) {
        return new OreProcessingBuilder(result, ingredient);
    }

    public static OreProcessingBuilder process(Item result, ShortenIngredient ingredient) {
        return process(new ItemStack(result), ingredient);
    }

    public static OreProcessingBuilder process(Item result, int count, ShortenIngredient ingredient) {
        return process(new ItemStack(result, count), ingredient);
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    public OreProcessingBuilder extraOutput(ItemStack output, int percentChance) {
        this.chancedResult = ChancedResult.of(output, percentChance);
        return this;
    }

    public OreProcessingBuilder extraOutput(Item item, int percentChance) {
        return this.extraOutput(new ItemStack(item), percentChance);
    }

    public OreProcessingBuilder doubleChance(int percentChance) {
        return this.extraOutput(this.result.getItem(), percentChance);
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
        if (this.advancementBuilder.getCriteria().isEmpty()) {
            this.advancementBuilder.addCriterion("auto_trigger", new TickTrigger.TriggerInstance(EntityPredicate.Composite.ANY));
        }
        pFinishedRecipeConsumer.accept(new Finished(pRecipeId, this.result, this.ingredient, this.chancedResult, this.processingTime, this.advancementBuilder, pRecipeId));
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, String pRecipeId) {
        this.save(pFinishedRecipeConsumer, ModUtils.modLoc("ore_processing/" + pRecipeId));
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        this.save(pFinishedRecipeConsumer, ModUtils.getPath(this.getResult()));
    }

    public static final class Finished extends AbstractFinishedRecipe<OreProcessingRecipe> {

        private final ItemStack result;
        private final ShortenIngredient ingredient;
        private final ChancedResult chancedResult;
        private final int processingTime;

        public Finished(ResourceLocation recipeId, ItemStack result, ShortenIngredient ingredient, ChancedResult chancedResult, int processingTime, Advancement.Builder advancementBuilder, ResourceLocation advancementRecipeId) {
            super(recipeId, ModRecipes.ORE_PROCESSING, advancementBuilder, advancementRecipeId);
            this.result = result;
            this.ingredient = ingredient;
            this.chancedResult = chancedResult;
            this.processingTime = processingTime;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("ingredient", this.ingredient.toShortenJson());
            pJson.add("result", super.itemStackToJson(this.result));
            pJson.addProperty("processing_time", this.processingTime);
            if (this.chancedResult != ChancedResult.NO_EXTRA) pJson.add(ChancedResult.JSON_OBJECT_NAME, this.chancedResult.toJson());
        }
    }
}
