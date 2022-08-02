package com.khanhpham.tothemoon.compat.patchouli;

import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.SerializedName;
import com.khanhpham.tothemoon.core.recipes.WorkbenchCraftingRecipe;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;

import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

public class WorkbenchComponent implements ICustomComponent {
    @SerializedName("recipe_id")
    public String recipeName;
    private transient List<Ingredient> craftingIngredients;
    private transient int x, y;

    private List<Ingredient> getCraftingIngredients() {
        Level clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null) {
            //Optional<WorkbenchCraftingRecipe> workbenchCraftingRecipe = clientLevel.getRecipeManager().getAllRecipesFor()
            if (ResourceLocation.isValidResourceLocation(recipeName)) {
                ResourceLocation recipeLocation = new ResourceLocation(recipeName);
                Map<ResourceLocation, WorkbenchCraftingRecipe> recipe = ModUtils.getResourceRecipes(clientLevel, WorkbenchCraftingRecipe.RECIPE_TYPE, recipeLocation);
                if (recipe.get(recipeLocation) != null) {
                    return recipe.get(recipeLocation).getIngredients();
                } else {
                    ModUtils.log("No Workbench Crafting recipe with id  : " + recipeName);
                    return ImmutableList.of();
                }
            } else throw new IllegalStateException("Invalid ID : " + recipeName);
        }
        return ImmutableList.of();
    }

    @Override
    public void build(int componentX, int componentY, int pageNum) {
        this.craftingIngredients = this.getCraftingIngredients();
        this.x = componentX;
        this.y = componentY;
    }

    @Override
    public void render(PoseStack ms, IComponentRenderContext context, float pticks, int mouseX, int mouseY) {
        if (!this.craftingIngredients.isEmpty()) {
            //render hammer
            context.renderIngredient(ms, x + 1, y + 7, mouseX, mouseY, this.craftingIngredients.get(0));
            //render extra
            context.renderIngredient(ms, x + 35, y + 7, mouseX, mouseY, this.craftingIngredients.get(1));

            //crafting grid
            int index = 2;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    context.renderIngredient(ms, x + 1 + j * 17, y + 36 + i * 17, mouseX, mouseY, craftingIngredients.get(index));
                    index++;
                }
            }
        }
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> lookup) {
        this.recipeName = lookup.apply(IVariable.wrap(recipeName)).asString();
    }
}
