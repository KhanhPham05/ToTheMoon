package com.khanhpham.tothemoon.core.recipes;

import com.khanhpham.tothemoon.core.recipes.type.SingleProcessRecipeType;
import com.khanhpham.tothemoon.datagen.recipes.builders.AbstractFinishedRecipe;
import com.khanhpham.tothemoon.datagen.recipes.builders.base.SimpleRecipeBuilder;
import com.khanhpham.tothemoon.datagen.recipes.elements.ShortenIngredient;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class MetalCrushingRecipe extends SingleProcessRecipe {
    public static final ResourceLocation RECIPE_LOCATION = ModUtils.modLoc("metal_crushing");
    public static final SingleProcessRecipeType RECIPE_TYPE = new SingleProcessRecipeType(RECIPE_LOCATION, ModBlocks.CRUSHER);

    public MetalCrushingRecipe(ResourceLocation recipeId, Ingredient ingredient, ItemStack result) {
        super(recipeId, ingredient, result);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.METAL_CRUSHING;
    }

    @Override
    public SingleProcessRecipeType getType() {
        return RECIPE_TYPE;
    }

    public static final class Serializer extends SingleProcessRecipe.Serializer {
        public Serializer() {
            super(MetalCrushingRecipe::new);
        }

        @Override
        protected String getSerializerName() {
            return "metal_crushing";
        }
    }

    @SuppressWarnings("unused")
    public static final class Builder extends SimpleRecipeBuilder {

        private final ShortenIngredient ingredient;
        private final ItemStack result;

        Builder(ShortenIngredient ingredient, ItemStack result) {
            super(result.getItem(), RECIPE_LOCATION);
            this.ingredient = ingredient;
            this.result = result;
        }

        public static Builder build(ShortenIngredient ingredient, ItemStack result) {
            return new Builder(ingredient, result);
        }

        @Override
        protected FinishedRecipe saveRecipe(@NotNull ResourceLocation recipeId) {
            return AbstractFinishedRecipe.serialize(
                    recipeId,
                    ModRecipes.METAL_CRUSHING,
                    ingredient.getInventoryTrigger(),
                    json -> {
                        json.add("ingredient", ingredient.toShortenJson());
                        AbstractFinishedRecipe.toJsonResult(json, result);
                    }
            );
        }
    }
}
