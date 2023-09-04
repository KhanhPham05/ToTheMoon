package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.serverdata.recipes.AnvilSmashingRecipe;
import com.khanhtypo.tothemoon.serverdata.recipes.LavaSmeltingRecipe;
import com.khanhtypo.tothemoon.serverdata.recipes.serializers.AnvilSmashingRecipeSerializer;
import com.khanhtypo.tothemoon.serverdata.recipes.serializers.LavaSmeltingRecipeSerializer;
import com.khanhtypo.tothemoon.serverdata.recipes.serializers.WorkbenchRecipeSerializer;
import com.khanhtypo.tothemoon.serverdata.recipes.WorkbenchRecipe;
import com.khanhtypo.tothemoon.serverdata.recipes.RecipeTypeObject;
import net.minecraft.world.level.block.Blocks;

public class ModRecipeTypes {
    public static final RecipeTypeObject<WorkbenchRecipe> WORKBENCH_RECIPE = new RecipeTypeObject<>("workbench_recipe", WorkbenchRecipe.class, WorkbenchRecipeSerializer::new, ModBlocks.WORKBENCH);
    public static final RecipeTypeObject<LavaSmeltingRecipe> LAVA_SMELTING = new RecipeTypeObject<>("lava_smelting", LavaSmeltingRecipe.class, LavaSmeltingRecipeSerializer::new, ModBlocks.BLACK_STONE_FURNACE_CONTROLLER);
    public static final RecipeTypeObject<AnvilSmashingRecipe> ANVIL_SMASHING = new RecipeTypeObject<>("anvil_crushing", AnvilSmashingRecipe.class, AnvilSmashingRecipeSerializer::new, Blocks.ANVIL);

    static void staticInit() {}
}
