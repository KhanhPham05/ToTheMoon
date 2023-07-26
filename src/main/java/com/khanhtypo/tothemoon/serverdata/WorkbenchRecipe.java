package com.khanhtypo.tothemoon.serverdata;

import com.khanhtypo.tothemoon.common.item.hammer.HammerLevel;
import com.khanhtypo.tothemoon.registration.ModRecipeTypes;
import com.khanhtypo.tothemoon.serverdata.recipes.BaseRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class WorkbenchRecipe extends BaseRecipe<Container> {
    private final HammerLevel hammerLevel;

    protected WorkbenchRecipe(NonNullList<Ingredient> ingredients, ItemStack result, HammerLevel hammerLevel, ResourceLocation recipeId) {
        super(ingredients, result, recipeId, ModRecipeTypes.WORKBENCH_RECIPE);
        this.hammerLevel = hammerLevel;
    }

    @Override
    public boolean matches(Container p_44002_, Level p_44003_) {
        for (int i = 0; i < 25; i++) {
            if (!super.test(i, p_44002_.getItem(i))) return false;
        }

        return this.hammerLevel.testHammer(p_44002_.getItem(25)) && super.test(25, p_44002_.getItem(26));
    }

    public HammerLevel getMinimumHammerLevel() {
        return this.hammerLevel;
    }
}
