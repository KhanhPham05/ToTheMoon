package com.khanhpham.tothemoon.datagen.recipes;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.core.recipes.metalpressing.MetalPressingRecipe;
import com.khanhpham.tothemoon.datagen.recipes.provider.ModRecipeProvider;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MetalPressRecipeBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final Ingredient press;
    private final Item result;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();
    private final ResourceLocation recipeId = ModRecipeProvider.createRecipeId();
    private String group;

    public MetalPressRecipeBuilder(Ingredient ingredient, Ingredient press, Item result) {
        this.ingredient = ingredient;
        this.press = press;
        this.result = result;
        this.unlock();
    }

    public static MetalPressRecipeBuilder press(TagKey<Item> ingredientTag, TagKey<Item> moldTag, ItemLike result) {
        return new MetalPressRecipeBuilder(Ingredient.of(ingredientTag), Ingredient.of(moldTag), result.asItem());
    }

    public void unlock() {
        Item item = this.ingredient.getItems()[0].getItem();
        this.unlockedBy("unlock_recipe_" + ModRecipeProvider.RECIPE_CODE, InventoryChangeTrigger.TriggerInstance.hasItems(item));

    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancementBuilder.parent(new ResourceLocation("recipes/root")).addCriterion(pCriterionName, pCriterionTrigger).rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.AND);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        this.save(pFinishedRecipeConsumer, recipeId);
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        pFinishedRecipeConsumer.accept(new Finished(pRecipeId, ingredient, press, result, ModUtils.modLoc("recipe_" + ModRecipeProvider.RECIPE_CODE), advancementBuilder, group));
    }

    private static final class Finished extends AbstractFinishedRecipe<MetalPressingRecipe> {

        private final Ingredient ingredient;
        private final Ingredient press;
        private final Item result;
        private final String group;

        public Finished(ResourceLocation recipeId, Ingredient ingredient, Ingredient press, Item result, ResourceLocation advancementRecipeId, Advancement.Builder advancementBuilder, String group) {
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
            object.addProperty(JsonNames.ITEM, ModUtils.getNameFromItem(result));
            json.add(JsonNames.RESULT, object);
        }
    }
}
