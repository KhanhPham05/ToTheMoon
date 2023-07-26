package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.serverdata.WorkbenchRSerializer;
import com.khanhtypo.tothemoon.serverdata.WorkbenchRecipe;
import com.khanhtypo.tothemoon.serverdata.recipes.RecipeTypeObject;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {
    public static final RecipeTypeObject<WorkbenchRecipe> WORKBENCH_RECIPE = new RecipeTypeObject<>("workbench_recipe", WorkbenchRecipe.class, WorkbenchRSerializer::new);
    public static final RecipeType<WorkbenchRecipe> WORKBENCH_RECIPE_TYPE = WORKBENCH_RECIPE.get();
    static void staticInit() {}
}
