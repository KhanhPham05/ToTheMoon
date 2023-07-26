package com.khanhtypo.tothemoon.compat.jei;

import com.google.common.collect.ImmutableSet;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.workbench.WorkbenchMenu;
import com.khanhtypo.tothemoon.common.item.hammer.HammerLevel;
import com.khanhtypo.tothemoon.registration.ModMenus;
import com.khanhtypo.tothemoon.registration.ModRecipeTypes;
import com.khanhtypo.tothemoon.serverdata.WorkbenchRecipe;
import mezz.jei.api.recipe.RecipeIngredientRole;

import java.util.function.Consumer;

final class ModCategories {
    static final BaseRecipeCategory<WorkbenchRecipe> WORKBENCH_CRAFTING;
    private static final ImmutableSet<BaseRecipeCategory<?>> ALL_CATEGORY_CACHED;

    static {
        final ImmutableSet.Builder<BaseRecipeCategory<?>> setBuilder = ImmutableSet.builder();
        WORKBENCH_CRAFTING =
                BaseRecipeCategory.builder(ModRecipeTypes.WORKBENCH_RECIPE, ModMenus.WORKBENCH)
                        .setRecipeScreenBuilder((builder, recipe) -> {
                            int index = 0;
                            int y = 5;
                            for (int i = 0; i < 5; i++) {
                                int x = 5;
                                for (int j = 0; j < 5; j++) {
                                    builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(recipe.getIngredients().get(index));
                                    x += 18;
                                    index++;
                                }
                                y += 18;
                            }

                            builder.addSlot(RecipeIngredientRole.INPUT, 105, 10).addIngredients(HammerLevel.getHammersIngredient(recipe.getMinimumHammerLevel()));
                            builder.addSlot(RecipeIngredientRole.INPUT, 105, 41).addIngredients(recipe.getIngredients().get(25));
                        })
                        .collectedRecipe(BaseRecipeCategory.clientRecipeManager().getAllRecipesFor(ModRecipeTypes.WORKBENCH_RECIPE_TYPE))
                        .setExtraRenderer(null)
                        .setRecipeTransfer(WorkbenchMenu.class, 0, 27, 28)
                        .build(setBuilder, 122 + 1, 98);


        ALL_CATEGORY_CACHED = setBuilder.build();
    }

    static void forEachTab(Consumer<BaseRecipeCategory<?>> categoryConsumer) {
        ALL_CATEGORY_CACHED.forEach(categoryConsumer);
    }
}
