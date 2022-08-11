package com.khanhpham.tothemoon.core.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Optional;

@SuppressWarnings("deprecation")

public abstract class SimpleRecipeSerializer<T extends Recipe<?>> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
    public SimpleRecipeSerializer() {
        setRegistryName(Names.MOD_ID, getRecipeName());
    }

    protected abstract String getRecipeName();

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

    protected Ingredient getIngredientSpecial(String name) {
        if (name.equalsIgnoreCase("")) {
            return Ingredient.EMPTY;
        } else if (name.contains("tag:")) {
            String tagName = name.replace("tag:", "");
            TagKey<Item> tag = ItemTags.create(new ResourceLocation(tagName));
            ModUtils.log("{}", tag.toString());
            return Ingredient.of(tag);
        } else {
            Optional<Item> item = Registry.ITEM.getOptional(new ResourceLocation(name));
            if (item.isPresent()) {
                return Ingredient.of(item.get());
            }
        }

        throw new IllegalStateException("Unknown tag or item | " + name);
    }

    protected Ingredient getIngredientSpecial(JsonObject json, String name) {
        return getIngredientSpecial(GsonHelper.getAsString(json, name));
    }
}
