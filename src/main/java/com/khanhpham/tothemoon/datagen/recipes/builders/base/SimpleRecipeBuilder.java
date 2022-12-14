package com.khanhpham.tothemoon.datagen.recipes.builders.base;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.helpers.RegistryEntries;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class SimpleRecipeBuilder implements RecipeBuilder {
    protected final Advancement.Builder advancementBuilder;
    private final Item result;
    private final String recipeTypeName;

    protected SimpleRecipeBuilder(ItemLike result, ResourceLocation recipeType) {
        this.advancementBuilder = Advancement.Builder.advancement();
        this.result = result.asItem();
        this.recipeTypeName =  recipeType.getPath();
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancementBuilder.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
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
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, String pRecipeId) {
        this.save(pFinishedRecipeConsumer, ModUtils.modLoc(pRecipeId));
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        this.save(pFinishedRecipeConsumer, RegistryEntries.ITEM.getPath(this.result));
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        pFinishedRecipeConsumer.accept(this.saveRecipe(ModUtils.modLoc(recipeTypeName + "/" + pRecipeId.getPath())));
    }

    protected abstract FinishedRecipe saveRecipe(@Nonnull ResourceLocation recipeId);
}
