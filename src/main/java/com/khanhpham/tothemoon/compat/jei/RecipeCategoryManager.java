package com.khanhpham.tothemoon.compat.jei;

import com.google.common.collect.ImmutableList;
import com.khanhpham.tothemoon.compat.jei.recipecategories.*;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeCategoryManager {
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
        List<RecipeCategory<?>> recipes = ImmutableList.of(
                new OreProcessingCategory(helper),
                new WorkbenchCraftingCategory(helper),
                new HighHeatSmeltingCategory(helper));
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
