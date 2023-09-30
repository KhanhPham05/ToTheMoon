package com.khanhtypo.tothemoon.serverdata.recipes.serializers;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.khanhtypo.tothemoon.serverdata.RecipeSerializerHelper;
import com.khanhtypo.tothemoon.serverdata.recipes.WorkbenchRecipe;
import com.khanhtypo.tothemoon.utls.JsonUtils;
import com.khanhtypo.tothemoon.common.item.hammer.HammerLevel;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class WorkbenchRecipeSerializer implements RecipeSerializerHelper<WorkbenchRecipe> {
    public static final String HAMMER_LEVEL = "hammer_level";
    public static final String ADDITIONAL = "additional";
    public static final String PATTERN = "pattern";
    public static final String MAPPING = "mapping";
    public static final String KEY = "key";
    public static final String INGREDIENT = "ingredient";
    private static final int defaultRecipeListSize = 26;

    @Override
    public WorkbenchRecipe fromJson(ResourceLocation id, JsonObject jsonFile) {
        String patternDissolved = this.breakdownPattern(jsonFile);
        Map<Character, Ingredient> mapping = this.toMapping(jsonFile);
        NonNullList<Ingredient> ingredients = NonNullList.withSize(defaultRecipeListSize, Ingredient.EMPTY);
        for (int i = 0; i < 25; i++) {
            char c = patternDissolved.charAt(i);
            Preconditions.checkState(mapping.containsKey(c) || c == ' ', "Key %s doesn't defined.".formatted(Character.toString(c)));
            ingredients.set(i, c != ' ' ? mapping.get(c) : Ingredient.EMPTY);
        }
        HammerLevel hammerLevel = HammerLevel.byLevel(GsonHelper.getAsInt(jsonFile, HAMMER_LEVEL, 1));
        @Nullable JsonObject optionalIngredientObject = jsonFile.getAsJsonObject(ADDITIONAL);
        if (optionalIngredientObject != null) {
            ingredients.set(25, Ingredient.fromJson(optionalIngredientObject));
        }

        return new WorkbenchRecipe(
                ingredients,
                JsonUtils.jsonToItem(jsonFile, RESULT),
                hammerLevel,
                id
        );
    }

    private String breakdownPattern(JsonObject jsonFile) {
        JsonArray pattern = GsonHelper.getAsJsonArray(jsonFile, PATTERN);
        Preconditions.checkState(pattern.size() == 5);
        StringBuilder patternBuilder = new StringBuilder();
        for (JsonElement lineEl : pattern) {
            patternBuilder.append(lineEl.getAsString());
        }
        return patternBuilder.toString();
    }

    private Map<Character, Ingredient> toMapping(JsonObject jsonFile) {
        ImmutableMap.Builder<Character, Ingredient> mappingBuilder = new ImmutableMap.Builder<>();
        JsonObject mappings = GsonHelper.getAsJsonObject(jsonFile, MAPPING);
        mappings.asMap().forEach((key, ingredient) -> mappingBuilder.put(key.charAt(0), Ingredient.fromJson(ingredient)));
        return mappingBuilder.build();
    }

    @Override
    public WorkbenchRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf reader) {
        NonNullList<Ingredient> ingredients = this.ingredientsFromNetwork(reader, defaultRecipeListSize);
        HammerLevel hammerLevel = HammerLevel.readFromServer(reader);
        ItemStack result = reader.readItem();
        return new WorkbenchRecipe(ingredients, result, hammerLevel, recipeId);
    }

    @Override
    public void toNetwork(FriendlyByteBuf writer, WorkbenchRecipe recipe) {
        this.ingredientsToNetwork(writer, recipe);
        recipe.getMinimumHammerLevel().writeToServer(writer);
        writer.writeItem(recipe.copyResult());
    }
}
