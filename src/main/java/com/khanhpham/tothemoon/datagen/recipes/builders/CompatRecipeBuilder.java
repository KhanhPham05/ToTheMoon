package com.khanhpham.tothemoon.datagen.recipes.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.datagen.recipes.provider.ModRecipeProvider;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public abstract class CompatRecipeBuilder implements RecipeBuilder {
    private final Item result;
    protected final Consumer<FinishedRecipe> consumer;
    private final CompatRecipeType recipeType;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();

    public CompatRecipeBuilder(Item result, Consumer<FinishedRecipe> consumer, CompatRecipeType recipeType) {
        this.result = result;
        this.consumer = consumer;
        this.recipeType = recipeType;
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        advancementBuilder.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    public final RecipeBuilder tickUnlock() {
        return this.unlockedBy("tick", ModRecipeProvider.tick());
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        this.save(pFinishedRecipeConsumer, ModUtils.getPath(this.result));
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, String pRecipeId) {
        this.tickUnlock();
        this.save(pFinishedRecipeConsumer, ModUtils.modLoc("compat/" + this.recipeType.getRecipeTypeId().getNamespace() + "/" + this.recipeType.getRecipeTypeId().getPath() + "/" + pRecipeId));
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        pFinishedRecipeConsumer.accept(new FakeFinishedRecipe(this, pRecipeId, this.recipeType, this.advancementBuilder));
    }

    protected final void save() {
        this.save(consumer);
    }

    protected abstract void serializeToJson(JsonObject object);

    private static final class FakeFinishedRecipe implements FinishedRecipe {
        private final CompatRecipeBuilder combatBuilder;
        private final ResourceLocation recipeId;
        private final ResourceLocation recipeTypeId;
        private final Advancement.Builder advancementBuilder;

        public FakeFinishedRecipe(CompatRecipeBuilder combatBuilder, ResourceLocation recipeId, CompatRecipeType recipeTypeId, Advancement.Builder builder) {
            this.combatBuilder = combatBuilder;
            this.recipeId = recipeId;
            this.recipeTypeId = recipeTypeId.getRecipeTypeId();
            this.advancementBuilder = builder;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            this.combatBuilder.serializeToJson(pJson);
        }

        @Override
        public JsonObject serializeRecipe() {
            JsonObject recipeObject = new JsonObject();

            recipeObject.addProperty("type", this.recipeTypeId.toString());

            this.serializeRecipeData(recipeObject);

            final JsonArray conditions = new JsonArray();
            final JsonObject condition = new JsonObject();
            condition.addProperty("modid", this.recipeTypeId.getNamespace());
            condition.addProperty("type", "forge:mod_loaded");
            conditions.add(condition);

            recipeObject.add("conditions", conditions);

            return recipeObject;
        }

        @Override
        public ResourceLocation getId() {
            return this.recipeId;
        }

        @Override
        public RecipeSerializer<?> getType() {
            //noinspection ConstantConditions
            return null;
        }

        @Override
        public JsonObject serializeAdvancement() {
            return this.advancementBuilder.serializeToJson();
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return this.recipeId;
        }
    }
}
