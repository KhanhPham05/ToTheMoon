package com.khanhpham.tothemoon.utils.recipes;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.crafting.CraftingHelper;

public class MultipleInputIngredient {
    private final MultipleIngredient[] ingredients;

    public MultipleInputIngredient(MultipleIngredient... ingredients) {
        this.ingredients = ingredients;
        ensureValid(ingredients);
    }
    public MultipleIngredient getFirstIngredient() {
        return ingredients[0];
    }

    public MultipleIngredient getSecondIngredient() {
        return ingredients[1];
    }

    public static MultipleInputIngredient fromNetwork(FriendlyByteBuf buffer) {
        return new MultipleInputIngredient(new MultipleIngredient(CraftingHelper.getIngredient(buffer.readResourceLocation(), buffer), buffer.readInt()), new MultipleIngredient(CraftingHelper.getIngredient(buffer.readResourceLocation(), buffer), buffer.readInt()));
    }

    public void toNetwork(FriendlyByteBuf buffer) {
        for (MultipleIngredient ingredient : ingredients) {
            CraftingHelper.write(buffer, ingredient.getIngredient());
            buffer.writeInt(ingredient.getAmount());
        }
    }

    private void ensureValid(MultipleIngredient... ingredients) {
        if (ingredients.length != 2) {
            throw new IllegalStateException();
        }
    }
}
