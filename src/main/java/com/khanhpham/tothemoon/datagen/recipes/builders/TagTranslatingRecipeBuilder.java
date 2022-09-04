package com.khanhpham.tothemoon.datagen.recipes.builders;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.core.recipes.TagTranslatingRecipe;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class TagTranslatingRecipeBuilder implements RecipeBuilder {
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();
    private final TagKey<Item> itemTag;

    public TagTranslatingRecipeBuilder(TagKey<Item> itemTag) {
        this.itemTag = itemTag;
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        return this;
    }

    private RecipeBuilder setUnlockCondition() {
        this.advancementBuilder.addCriterion("has_tag", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemTag).build()));
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return Items.AIR;
    }

    public TagKey<Item> getItemTag() {
        return itemTag;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.setUnlockCondition();
        pFinishedRecipeConsumer.accept(new Finished(pRecipeId, this, ModUtils.modLoc("recipes/" + pRecipeId.getPath())));
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, String pRecipeId) {
        this.save(pFinishedRecipeConsumer, ModUtils.modLoc(pRecipeId));
    }

    private static final class Finished extends AbstractFinishedRecipe<TagTranslatingRecipe> {
        private final TagTranslatingRecipeBuilder recipeBuilder;
        public Finished(ResourceLocation recipeId, TagTranslatingRecipeBuilder builder, ResourceLocation advancementRecipeId) {
            super(recipeId, ModRecipes.TAG_TRANSLATING, builder.advancementBuilder, advancementRecipeId);
            this.recipeBuilder = builder;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.addProperty("tag", recipeBuilder.getItemTag().location().toString());
        }
    }
}
