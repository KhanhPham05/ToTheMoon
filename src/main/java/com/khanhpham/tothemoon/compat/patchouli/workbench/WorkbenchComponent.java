package com.khanhpham.tothemoon.compat.patchouli.workbench;

import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.SerializedName;
import com.khanhpham.tothemoon.core.recipes.WorkbenchCraftingRecipe;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;

import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public class WorkbenchComponent implements ICustomComponent {
    @SerializedName("recipe_id")
    public String recipeName;
    private transient
    List<Ingredient> craftingIngredients;

    private transient ItemStack result;
    private transient int x, y;

    private List<Ingredient> getCraftingIngredients() {
        Level clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null) {
            if (ResourceLocation.isValidResourceLocation(recipeName)) {
                ResourceLocation recipeLocation = new ResourceLocation(recipeName);
                Map<ResourceLocation, WorkbenchCraftingRecipe> map = ModUtils.getResourceRecipes(clientLevel, WorkbenchCraftingRecipe.RECIPE_TYPE, recipeLocation);
                if (map.get(recipeLocation) != null) {
                    final WorkbenchCraftingRecipe recipe = map.get(recipeLocation);
                    this.result = recipe.getResultItem();
                    return recipe.getIngredients();
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
    public void render(PoseStack poseStack, IComponentRenderContext context, float pTicks, int mouseX, int mouseY) {
        if (!this.craftingIngredients.isEmpty()) {
            //render hammer
            context.renderIngredient(poseStack, x + 7, y + 4, mouseX, mouseY, this.craftingIngredients.get(0));
            //render extra
            context.renderIngredient(poseStack, x + 41, y + 4, mouseX, mouseY, this.craftingIngredients.get(1));

            //crafting grid
            int index = 2;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    context.renderIngredient(poseStack, x + 6 + (j * 18), y + 31 + (i * 18), mouseX, mouseY, craftingIngredients.get(index));
                    index++;
                }
            }

            context.renderItemStack(poseStack, x + 76, y + 4, mouseX, mouseY, this.result);
        }
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> lookup) {
        this.recipeName = lookup.apply(IVariable.wrap(recipeName)).asString();
    }
}
