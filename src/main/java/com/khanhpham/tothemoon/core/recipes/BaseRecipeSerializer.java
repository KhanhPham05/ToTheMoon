package com.khanhpham.tothemoon.core.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public abstract class BaseRecipeSerializer<T extends Recipe<?>> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
    protected abstract ItemLike getIcon();

    public ItemStack getDisplayIcon() {
        return new ItemStack(getIcon());
    }

    protected ItemStack resultFromJson(JsonObject json) {
        @Nullable JsonElement element = json.get(JsonNames.RESULT);
        if (element != null && element.isJsonObject() && element.getAsJsonObject().has(JsonNames.ITEM)) {
            JsonObject resultItemObject = element.getAsJsonObject().get(JsonNames.ITEM).getAsJsonObject();
            return ShapedRecipe.itemStackFromJson(resultItemObject);
        }

        throw new IllegalStateException("No result definition is found");
    }
}
