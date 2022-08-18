package com.khanhpham.tothemoon.compat.ct;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.khanhpham.tothemoon.core.recipes.WorkbenchCraftingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("com.khanhpham.tothemoon.compat.ct.WorkbenchCraftingTweaker")
@IRecipeHandler.For(WorkbenchCraftingRecipe.class)
public class WorkbenchCraftingTweaker implements IRecipeHandler<WorkbenchCraftingRecipe>, IRecipeManager<WorkbenchCraftingRecipe> {

    //name, result, hammer, extra, pattern
    @Override
    public String dumpToCommandString(IRecipeManager manager, WorkbenchCraftingRecipe recipe) {
        return String.format("Recipe: [%s] | <recipetype:tothemoon:workbench_crafting>.addRecipe(%s, %s, %s, %s, \n\t%s)",
                recipe.getId(),
                StringUtil.quoteAndEscape(recipe.getId().getPath()),

                ItemStackUtil.getCommandString(recipe.getResultItem()),
                IIngredient.fromIngredient(recipe.getHammerIngredient()).getCommandString(),
                IIngredient.fromIngredient(recipe.getExtraIngredient()).getCommandString(),
                this.patternList(recipe.getIngredients())
        );
    }

    private String patternList(NonNullList<Ingredient> ingredients) {
        StringBuilder builder = new StringBuilder("[");

        for (int i = 0; i < ingredients.size(); i++) {
            builder.append(IIngredient.fromIngredient(ingredients.get(i)).getCommandString()).append(i == ingredients.size() - 1 ? "" : ", ");

        }

        builder.append("]");

        return builder.toString();
    }

    @Override
    public RecipeType<WorkbenchCraftingRecipe> getRecipeType() {
        return WorkbenchCraftingRecipe.RECIPE_TYPE;
    }

    @ZenCodeType.Method
    public void addRecipe(String recipeName, IItemStack result, IIngredient hammer, IIngredient extra, IIngredient[] pattern) {
        recipeName = fixRecipeName(recipeName);
        ResourceLocation resourceLocation = new ResourceLocation(CraftTweakerConstants.MOD_ID, recipeName);
        NonNullList<Ingredient> ingredients = NonNullList.withSize(25, Ingredient.EMPTY);
        for (int i = 0; i < pattern.length; i++) {
            ingredients.set(i, pattern[i].asVanillaIngredient());
        }
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, new WorkbenchCraftingRecipe(resourceLocation, ingredients, result.getInternal(), hammer.asVanillaIngredient(), extra.asVanillaIngredient())));
    }
}
