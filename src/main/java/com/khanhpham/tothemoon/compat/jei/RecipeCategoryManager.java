package com.khanhpham.tothemoon.compat.jei;

import com.google.common.collect.ImmutableList;
import com.khanhpham.tothemoon.compat.jei.recipecategories.*;
import com.khanhpham.tothemoon.core.recipes.type.SingleProcessRecipeType;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class RecipeCategoryManager {
    private final Set<RecipeCategory<?>> recipeCategories = new HashSet<>();

    RecipeCategoryManager() {
    }

    public void registerCatalysts(IRecipeCatalystRegistration registration) {
        for (RecipeCategory<?> category : recipeCategories) {
            registration.addRecipeCatalyst(category.getCatalystIcon(), category.getRecipeType());
        }
    }

    public void registerRecipes(IRecipeRegistration registration, RecipeManager manager) {
        for (RecipeCategory<?> recipeCategory : recipeCategories) {
            recipeCategory.registerRecipes(registration, manager);
        }
    }

    public void registerAllCategories(IRecipeCategoryRegistration registration, IGuiHelper helper) {
        this.recipeCategories.clear();
        ArrayList<RecipeCategory<?>> recipes = new ArrayList<>();
        recipes.add(new OreProcessingCategory(helper));
        recipes.add(new WorkbenchCraftingCategory(helper));
        recipes.add(new HighHeatSmeltingCategory(helper));
        recipes.addAll(SingleProcessRecipeType.ALL_TYPES.stream().map(recipeType -> new SingleProcessCategory(helper, recipeType)).toList());
        registration.addRecipeCategories(recipes.toArray(new RecipeCategory[0]));
        this.recipeCategories.addAll(recipes);
    }

    public void registerGuiHandler(IGuiHandlerRegistration registration) {
        for (RecipeCategory<?> recipeCategory : recipeCategories) {
            recipeCategory.addShowRecipeZone(registration);
        }
    }

    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        for (RecipeCategory<?> recipeCategory : this.recipeCategories) {
            recipeCategory.addRecipeTransfer(registration);
        }
    }
}
