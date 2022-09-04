package com.khanhpham.tothemoon.datagen.recipes.builders;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.core.recipes.metalpressing.MetalPressingRecipe;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MetalPressRecipeBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final Ingredient press;
    private final ItemStack result;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();
    private String group;

    public MetalPressRecipeBuilder(Ingredient ingredient, Ingredient press, ItemStack result) {
        this.ingredient = ingredient;
        this.press = press;
        this.result = result;
    }

    public static MetalPressRecipeBuilder press(TagKey<Item> ingredientTag, TagKey<Item> moldTag, ItemStack result) {
        return new MetalPressRecipeBuilder(Ingredient.of(ingredientTag), Ingredient.of(moldTag), result);
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancementBuilder.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        this.save(pFinishedRecipeConsumer, ModUtils.modLoc(ModUtils.getPath(this.getResult())));
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        pFinishedRecipeConsumer.accept(new Finished(pRecipeId, ingredient, press, result, pRecipeId, advancementBuilder, group));
    }

    private static final class Finished extends AbstractFinishedRecipe<MetalPressingRecipe> {

        private final Ingredient ingredient;
        private final Ingredient press;
        private final ItemStack result;
        private final String group;

        public Finished(ResourceLocation recipeId, Ingredient ingredient, Ingredient press, ItemStack result, ResourceLocation advancementRecipeId, Advancement.Builder advancementBuilder, String group) {
            super(recipeId, ModRecipes.METAL_PRESSING_RECIPE_SERIALIZER, advancementBuilder, advancementRecipeId);
            this.press = press;
            this.ingredient = ingredient;
            this.result = result;
            this.group = group;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty(JsonNames.GROUP, this.group);
            json.add(JsonNames.INGREDIENT, ingredient.toJson());
            json.add(JsonNames.MOLD, press.toJson());
            final JsonObject object = new JsonObject();
                object.addProperty(JsonNames.ITEM, ModUtils.getFullName(result.getItem()));
                object.addProperty("count", result.getCount());
            json.add(JsonNames.RESULT, object);
        }
    }
}
