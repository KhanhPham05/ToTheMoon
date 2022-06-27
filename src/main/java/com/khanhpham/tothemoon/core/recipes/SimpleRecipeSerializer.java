package com.khanhpham.tothemoon.core.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.Names;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class SimpleRecipeSerializer<T extends Recipe<?>> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
    public SimpleRecipeSerializer() {
        setRegistryName(Names.MOD_ID, getRecipeName());
    }

    protected abstract String getRecipeName();

    @SuppressWarnings("deprecation")
    protected ItemStack stackFromJson(JsonObject jsonObject) {
        if (jsonObject.has(JsonNames.RESULT)) {
            JsonElement resultElement = jsonObject.get(JsonNames.RESULT);
            if (resultElement.isJsonObject()) {
                return ShapedRecipe.itemStackFromJson((JsonObject) resultElement);
            } else {
                ResourceLocation itemId = new ResourceLocation(GsonHelper.getAsString(jsonObject, JsonNames.RESULT));
                return new ItemStack(Registry.ITEM.getOptional(itemId).orElseThrow(() -> new IllegalStateException("No item match [" + itemId + "]")));
            }
        }

        throw new JsonSyntaxException("No result was found");
    }
}
