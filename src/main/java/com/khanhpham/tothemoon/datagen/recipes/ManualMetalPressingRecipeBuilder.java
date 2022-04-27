package com.khanhpham.tothemoon.datagen.recipes;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.core.recipes.metalpressing.ManualMetalPressRecipe;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ManualMetalPressingRecipeBuilder implements RecipeBuilder {

    private final Ingredient ingredient;
    private final Ingredient mold;
    private final Item result;
    private final ResourceLocation recipeId;
    private final Advancement.Builder advancementBuilder;

    public ManualMetalPressingRecipeBuilder(Ingredient ingredient, Ingredient mold, Item result) {
        this.ingredient = ingredient;
        this.mold = mold;
        this.result = result;
        this.recipeId = ModRecipeProvider.createRecipeId();
        this.advancementBuilder = Advancement.Builder.advancement();
        this.unlockedBy("tick", ModRecipeProvider.tick());
    }

    public static void build(Consumer<FinishedRecipe> consumer, TagKey<Item> ingredient, TagKey<Item> mold, Item result) {
        ManualMetalPressingRecipeBuilder builder = new ManualMetalPressingRecipeBuilder(Ingredient.of(ingredient), Ingredient.of(mold), result);
        builder.save(consumer, ModRecipeProvider.createRecipeId());
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
        return result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        pFinishedRecipeConsumer.accept(new Finished(recipeId, advancementBuilder, recipeId, ingredient, mold, result));
    }

    private static final class Finished extends AbstractFinishedRecipe<ManualMetalPressRecipe> {
        private final Ingredient ingredient;
        private final Ingredient mold;
        private final Item result;

        public Finished(ResourceLocation recipeId, Advancement.Builder advancementBuilder, ResourceLocation advancementRecipeId, Ingredient ingredient, Ingredient mold, Item result) {
            super(recipeId, ModRecipes.MANUAL_METAL_PRESS_RECIPE_SERIALIZER, advancementBuilder, advancementRecipeId);
            this.ingredient = ingredient;
            this.mold = mold;
            this.result = result;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add(JsonNames.INGREDIENT, this.ingredient.toJson());
            json.add(JsonNames.MOLD, this.mold.toJson());
            final JsonObject object = new JsonObject();
            object.addProperty(JsonNames.ITEM, ModUtils.getNameFromItem(result));
            json.add(JsonNames.RESULT, object);
        }
    }
}
