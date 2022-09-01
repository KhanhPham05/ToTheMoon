package com.khanhpham.tothemoon.core.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.helpers.RegistryEntries;
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

import java.util.LinkedList;

public abstract class SimpleRecipeSerializer<T extends Recipe<?>> implements RecipeSerializer<T> {
    public SimpleRecipeSerializer() {
        ModRecipes.ALL_SERIALIZERS.put(this.getSerializerName(), this);
    }

    public static Ingredient getShortenIngredient(String name) {
        if (name.equalsIgnoreCase("")) {
            return Ingredient.EMPTY;
        } else if (name.contains("tag:")) {
            String tagName = name.replace("tag:", "");
            TagKey<Item> tag = ItemTags.create(new ResourceLocation(clearRedundantSpaces(tagName)));
            return Ingredient.of(tag);
        } else {
            return Ingredient.of(ModUtils.getItemFromName(name));
        }
    }

    public static Ingredient getIngredientsFromArray(JsonArray array) {
        LinkedList<Ingredient.Value> values = new LinkedList<>();
        for (JsonElement jsonElement : array) {
            if (jsonElement.isJsonPrimitive()) {
                String name = jsonElement.getAsString();
                if (isTag(name)) {
                    TagKey<Item> tag = ItemTags.create(new ResourceLocation(clearRedundantSpaces(name.replace("tag:", ""))));
                    //ModUtils.log("Tag [{}]", tag.location());
                    values.add(new Ingredient.TagValue(tag));
                } else {
                    values.add(new Ingredient.ItemValue(ModUtils.getItemStackFromName(name)));
                }
            }
        }

        return Ingredient.fromValues(values.stream());
    }

    private static String clearRedundantSpaces(String string) {
        return string.replace(" ", "");
    }

    protected static boolean isTag(String value) {
        return value.contains("tag:");
    }

    protected abstract String getSerializerName();

    protected ItemStack stackFromJson(JsonObject jsonObject) {
        if (jsonObject.has(JsonNames.RESULT)) {
            JsonElement resultElement = jsonObject.get(JsonNames.RESULT);
            if (resultElement.isJsonObject()) {
                return ShapedRecipe.itemStackFromJson((JsonObject) resultElement);
            } else {
                ResourceLocation itemId = new ResourceLocation(GsonHelper.getAsString(jsonObject, JsonNames.RESULT));
                return new ItemStack(RegistryEntries.ITEM.getFromKey(itemId));
            }
        }

        throw new JsonSyntaxException("No result was found");
    }

    protected Ingredient getShortenIngredient(JsonObject json, String name) {
        if (json.has(name)) {
            JsonElement jsonElement = json.get(name);
            if (jsonElement.isJsonPrimitive()) {
                return getShortenIngredient(jsonElement.getAsString());
            } else if (jsonElement.isJsonArray()) {
                return getIngredientsFromArray(jsonElement.getAsJsonArray());
            } else Ingredient.fromJson(jsonElement);
        }

        throw new JsonSyntaxException("Missing ingredient");
    }

    protected ItemStack getShortenOutput(JsonObject pSerializedRecipe) {
        if (pSerializedRecipe.has(JsonNames.RESULT)) {
            JsonElement resultElement = pSerializedRecipe.get(JsonNames.RESULT);
            if (resultElement.isJsonObject()) {
                return ShapedRecipe.itemStackFromJson(resultElement.getAsJsonObject());
            } else if (resultElement.isJsonPrimitive()) {
                return new ItemStack(ModUtils.getItemFromName(resultElement.getAsJsonPrimitive().getAsString()));
            } else
                throw new JsonSyntaxException("result JsonObject or JsonPrimitive is expected, but " + resultElement.getClass().getSimpleName() + " was found.");
        }
        throw new JsonSyntaxException("Result is expected, but missing");
    }
}
