package com.khanhpham.tothemoon.compat.patchouli;

import com.khanhpham.tothemoon.core.recipes.WorkbenchCraftingRecipe;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WorkbenchTemplateProcessor implements IComponentProcessor {
    private WorkbenchCraftingRecipe workbenchCraftingRecipe;

    @Override
    public void setup(IVariableProvider variables) {
        this.workbenchCraftingRecipe = ModUtils.getResourceRecipe(Minecraft.getInstance().level, WorkbenchCraftingRecipe.RECIPE_TYPE, variables.get("recipe").asString());
    }


    @Override
    public IVariable process(String key) {
        if (workbenchCraftingRecipe != null) {
            return switch (key) {
                case "output" -> IVariable.from(workbenchCraftingRecipe.getResultItem());
                case "title" -> IVariable.from(new TranslatableComponent("book.tothemoon.title_crafting_smt", ModLanguage.getPureName(this.workbenchCraftingRecipe.getResultItem().getItem())));
                default -> null;
            };
        }

        return null;
    }
}
