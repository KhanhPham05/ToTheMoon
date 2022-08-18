package com.khanhpham.tothemoon.datagen.recipes;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.core.recipes.AlloySmeltingRecipe;
import com.khanhpham.tothemoon.core.recipes.IngredientStack;
import com.khanhpham.tothemoon.datagen.recipes.provider.ModRecipeProvider;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AlloySmeltingRecipeBuilder implements RecipeBuilder {

    private final IngredientStack base;
    private final IngredientStack secondary;
    private final Item result;
    private final int count;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();
    private String group;

    private AlloySmeltingRecipeBuilder(IngredientStack base, IngredientStack secondary, ItemLike result, int count) {
        this.base = base;
        this.secondary = secondary;
        this.result = result.asItem();
        this.count = count;
    }

    public static AlloySmeltingRecipeBuilder build(IngredientStack base, IngredientStack secondary, ItemLike result, int count) {
        return new AlloySmeltingRecipeBuilder(base, secondary, result, count).unlock();
    }

    public static AlloySmeltingRecipeBuilder build(TagKey<Item> baseTag, int count1, TagKey<Item> sec, int count2, ItemLike result, int count) {
        return build(IngredientStack.create(baseTag, count1), IngredientStack.create(sec, count2), result, count);
    }

    public static AlloySmeltingRecipeBuilder build(ItemLike item1, int count1, ItemLike item2, int count2, ItemLike result, int amount) {
        return build(IngredientStack.create(item1, count1), IngredientStack.create(item2, count2), result, amount);
    }

    public AlloySmeltingRecipeBuilder unlock() {
        this.unlockedBy("has_" + base.getIngredientName() + "_and_" + secondary.getIngredientName(), InventoryChangeTrigger.TriggerInstance.hasItems(base.getIngredientItem(), secondary.getIngredientItem()));
        return this;
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
        return result;
    }

    public void save(Consumer<FinishedRecipe> consumer) {
        save(consumer, ModRecipeProvider.createRecipeId());
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.advancementBuilder.parent(new ResourceLocation("recipes/root")).addCriterion("unlock_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.AND);
        pFinishedRecipeConsumer.accept(new Finished(pRecipeId, base, secondary, result, count, advancementBuilder, ModUtils.modLoc("recipes/" + pRecipeId.getPath()), group));
    }

    @ParametersAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    public static final class Finished extends AbstractFinishedRecipe<AlloySmeltingRecipe> {
        private final IngredientStack baseIngredient;
        private final IngredientStack secondaryIngredient;
        private final Item result;
        private final int count;
        private final String group;

        public Finished(ResourceLocation pRecipeId, IngredientStack base, IngredientStack secondary, Item result, int count, Advancement.Builder advancementBuilder, ResourceLocation advancementId, String group) {
            super(pRecipeId, ModRecipes.ALLOY_SMELTING_RECIPE_SERIALIZER, advancementBuilder, advancementId);
            this.baseIngredient = base;
            this.secondaryIngredient = secondary;
            this.result = result;
            this.count = count;
            this.group = group;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonObject base = baseIngredient.toJson();
            JsonObject secondary = secondaryIngredient.toJson();


            JsonObject result = new JsonObject();
            result.addProperty(JsonNames.ITEM, ModUtils.getFullItemName(this.result));
            result.addProperty(JsonNames.COUNT, this.count);

            json.addProperty(JsonNames.GROUP, this.group);
            json.add(JsonNames.BASE_INGREDIENT, base);
            json.add(JsonNames.SECONDARY_INGREDIENT, secondary);
            json.add(JsonNames.RESULT, result);
        }
    }


}
