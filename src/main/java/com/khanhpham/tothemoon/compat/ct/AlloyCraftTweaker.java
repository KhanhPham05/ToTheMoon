package com.khanhpham.tothemoon.compat.ct;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandlerRegistry;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.handler.helper.ReplacementHandlerHelper;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.khanhpham.tothemoon.core.recipes.AlloySmeltingRecipe;
import com.khanhpham.tothemoon.core.recipes.IngredientStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name("com.khanhpham.tothemoon.compat.ct.AlloyCraftTweaker")
@IRecipeHandler.For(AlloySmeltingRecipe.class)
public class AlloyCraftTweaker implements IRecipeHandler<AlloySmeltingRecipe>, IRecipeManager<AlloySmeltingRecipe> {

    //result, base, extra, alloyTime
    @Override
    public String dumpToCommandString(IRecipeManager manager, AlloySmeltingRecipe recipe) {
        return String.format("Recipe: [%s] | <recipetype:tothemoon:alloy_smelting>.addRecipe(%s, %s, %s, %s, %d);",
                recipe.getId(),
                StringUtil.quoteAndEscape(recipe.getId().getPath()),
                ItemStackUtil.getCommandString(recipe.getResultItem()),
                fromIngredientWithAmount(recipe.baseIngredient),
                fromIngredientWithAmount(recipe.secondaryIngredient),
                recipe.getAlloyingTime()
        );
    }

    @Override
    public RecipeType<AlloySmeltingRecipe> getRecipeType() {
        return AlloySmeltingRecipe.RECIPE_TYPE;
    }

    private String fromIngredientWithAmount(IngredientStack ingredient) {
        return String.format("(%s) * %d", IIngredient.fromIngredient(ingredient.ingredient).getCommandString(), ingredient.amount);
    }

    /*@ZenCodeType.Method
    public void addRecipe(String recipeName, IItemStack result, IIngredient base, int baseAmount, IIngredient extra, int alloyTime) {
        addRecipe(recipeName, result, base, baseAmount, extra, 1, alloyTime);
    }

    @ZenCodeType.Method
    public void addRecipe(String recipeName, IItemStack result, IIngredient base, IIngredient extra, int extraAmount, int alloyTime) {
        addRecipe(recipeName, result, base, 0, extra, extraAmount, alloyTime);
    }

    @ZenCodeType.Method
    public void addRecipe(String recipeName, IItemStack result, IIngredient base, IIngredient extra, int alloyTime) {
        addRecipe(recipeName, result, base, 1, extra, 1, alloyTime);
    }*/

    @ZenCodeType.Method
    public void addRecipe(String recipeName, IItemStack result, IIngredientWithAmount baseIngredient, IIngredientWithAmount extraIngredient, int alloyTime) {
        recipeName = this.fixRecipeName(recipeName);
        ResourceLocation resourceLocation = new ResourceLocation(CraftTweakerConstants.MOD_ID, recipeName);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, new AlloySmeltingRecipe(IngredientStack.create(baseIngredient.getIngredient().asVanillaIngredient(), baseIngredient.getAmount()), IngredientStack.create(extraIngredient.getIngredient().asVanillaIngredient(), extraIngredient.getAmount()), result.getInternal(), alloyTime, resourceLocation)));
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager manager, AlloySmeltingRecipe firstRecipe, U secondRecipe) {
        if (secondRecipe instanceof AlloySmeltingRecipe) {
            return IngredientUtil.canConflict(firstRecipe.baseIngredient.ingredient, ((AlloySmeltingRecipe) secondRecipe).baseIngredient.ingredient)
                    || IngredientUtil.canConflict(firstRecipe.secondaryIngredient.ingredient, ((AlloySmeltingRecipe) secondRecipe).secondaryIngredient.ingredient)
                    || IRecipeHandlerRegistry.getHandlerFor(secondRecipe).doesConflict(manager, secondRecipe, firstRecipe);
        }
        return false;
    }

    @Override
    public Optional<Function<ResourceLocation, AlloySmeltingRecipe>> replaceIngredients(IRecipeManager manager, AlloySmeltingRecipe recipe, List<IReplacementRule> rules) throws ReplacementNotSupportedException {
        return ReplacementHandlerHelper.replaceNonNullIngredientList(
                recipe.getIngredients(),
                Ingredient.class,
                recipe,
                rules,
                newIngredients -> id -> new AlloySmeltingRecipe(recipe.baseIngredient, recipe.secondaryIngredient, recipe.result, recipe.getAlloyingTime(), id)
        );
    }
}
