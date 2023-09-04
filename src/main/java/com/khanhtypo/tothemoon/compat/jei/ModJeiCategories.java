package com.khanhtypo.tothemoon.compat.jei;

import com.google.common.collect.ImmutableSet;
import com.khanhtypo.tothemoon.common.block.WorkbenchBlock;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.workbench.WorkbenchMenu;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.workbench.WorkbenchScreen;
import com.khanhtypo.tothemoon.common.item.hammer.HammerLevel;
import com.khanhtypo.tothemoon.registration.ModMenuTypes;
import com.khanhtypo.tothemoon.registration.ModRecipeTypes;
import com.khanhtypo.tothemoon.serverdata.recipes.WorkbenchRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

final class ModJeiCategories {
    static final BaseRecipeCategory<WorkbenchRecipe> WORKBENCH_CRAFTING;
    private static final ImmutableSet<BaseRecipeCategory<?>> ALL_CATEGORY_CACHED;

    static {
        final ImmutableSet.Builder<BaseRecipeCategory<?>> setBuilder = ImmutableSet.builder();
        WORKBENCH_CRAFTING =
                BaseRecipeCategory.builder(ModRecipeTypes.WORKBENCH_RECIPE, ModMenuTypes.WORKBENCH)
                        .setRecipeScreenBuilder((builder, recipe) -> {
                            int index = 0;
                            int y = 5;
                            for (int i = 0; i < 5; i++) {
                                int x = 5;
                                for (int j = 0; j < 5; j++) {
                                    putIngredient(builder, x, y, recipe.getIngredients().get(index), true);
                                    x += 18;
                                    index++;
                                }
                                y += 18;
                            }

                            putIngredient(builder, 105, 9, HammerLevel.getHammersIngredient(recipe.getMinimumHammerLevel()), false);
                            putIngredient(builder, 105, 41, recipe.getIngredients().get(25), true);
                            putResult(builder, 105, 73, recipe);
                        })
                        .collectedRecipe(m -> m.getAllRecipesFor(ModRecipeTypes.WORKBENCH_RECIPE.get()))
                        .setExtraRenderer(null)
                        .setRecipeTransfer(WorkbenchMenu.class, 0, 27, 28)
                        .setClickableArea(WorkbenchScreen.class)
                        .build(setBuilder, 123, 98, List.of(WorkbenchBlock.getInstance()));

        ALL_CATEGORY_CACHED = setBuilder.build();
    }

    static void forEachTab(Consumer<BaseRecipeCategory<?>> categoryConsumer) {
        ALL_CATEGORY_CACHED.forEach(categoryConsumer);
    }

    private static RegistryAccess registryAccess() {
        return Objects.requireNonNull(Minecraft.getInstance().level).registryAccess();
    }

    private static void putIngredient(IRecipeLayoutBuilder builder, int x, int y, Ingredient ingredient, boolean isConsumed) {
        builder.addSlot(isConsumed ? RecipeIngredientRole.INPUT : RecipeIngredientRole.CATALYST ,x, y).addIngredients(ingredient);
    }

    @SuppressWarnings("SameParameterValue")
    private static void putResult(IRecipeLayoutBuilder builder, int x, int y, Recipe<?> recipe) {
        builder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addItemStack(recipe.getResultItem(registryAccess()));
    }
}
