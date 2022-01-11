package com.khanhpham.ttm.data.recipes;

import com.khanhpham.ttm.ToTheMoonMain;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.TickTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Objects;
import java.util.function.Consumer;

public record RecipeHelper(Consumer<FinishedRecipe> consumer) {
    public void craftFourSame(ItemLike result, int count, ItemLike ingredient, String saveName) {
        shaped(result, count).pattern("##").pattern("##").define('#', Ingredient.of(ingredient))
                .unlockedBy("unlock", new TickTrigger.TriggerInstance(EntityPredicate.Composite.ANY))
                .save(consumer, new ResourceLocation(ToTheMoonMain.MOD_ID, "crafting/" + saveName));
    }

    public void stonecutting(ItemLike baseIngredient, ItemLike... results) {
        for (ItemLike result : results) {
            stonecutter(baseIngredient, result).unlockedBy("tick", tick()).save(consumer, new ResourceLocation(Objects.requireNonNull(result.asItem().getRegistryName()).getPath()));
        }
    }

    public SingleItemRecipeBuilder stonecutter(ItemLike ingredient, ItemLike result) {
        return SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), result);
    }

    public ShapedRecipeBuilder shaped(ItemLike result, int count) {
        return ShapedRecipeBuilder.shaped(result, count);
    }

    public TickTrigger.TriggerInstance tick() {
        return new TickTrigger.TriggerInstance(EntityPredicate.Composite.ANY);
    }
}
