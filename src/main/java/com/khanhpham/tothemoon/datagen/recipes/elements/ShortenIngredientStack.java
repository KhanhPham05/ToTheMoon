package com.khanhpham.tothemoon.datagen.recipes.elements;

import com.google.gson.JsonObject;

public class ShortenIngredientStack {
    private final ShortenIngredient ingredients;
    private final int ingredientCount;

    public ShortenIngredientStack(ShortenIngredient ingredients, int ingredientCount) {
        this.ingredients = ingredients;
        this.ingredientCount = ingredientCount;
    }

    public JsonObject toJson() {
        JsonObject object = new JsonObject();

        object.add("ingredient", ingredients.toShortenJson());
        if (ingredientCount > 1) object.addProperty("count", ingredientCount);

        return object;
    }
}
