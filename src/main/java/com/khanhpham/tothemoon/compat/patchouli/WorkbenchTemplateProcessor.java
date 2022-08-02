package com.khanhpham.tothemoon.compat.patchouli;

import com.khanhpham.tothemoon.core.recipes.WorkbenchCraftingRecipe;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.client.Minecraft;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class WorkbenchTemplateProcessor implements IComponentProcessor {
    private WorkbenchCraftingRecipe workbenchCraftingRecipe;

    @Override
    public void setup(IVariableProvider variables) {
        this.workbenchCraftingRecipe = ModUtils.getResourceRecipe(Minecraft.getInstance().level, WorkbenchCraftingRecipe.RECIPE_TYPE, variables.get("recipe").asString());
    }

    @Override
    public IVariable process(String key) {
        if (workbenchCraftingRecipe != null) {
            if (key.equals("output")) {
                return IVariable.from(workbenchCraftingRecipe.getResultItem());
            }
        }

        return null;
    }
}
