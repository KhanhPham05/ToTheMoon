package com.khanhpham.tothemoon.utils.recipes;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;

public class MultipleIngredient {
    final Ingredient ingredient;
    final int amount;

    MultipleIngredient(ItemLike ingredient, int amount) {
        this.ingredient = Ingredient.of(ingredient);
        this.amount = amount;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getAmount() {
        return amount;
    }

    public MultipleIngredient(Ingredient ingredient, int amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public boolean isMatch(ItemStack stack) {
        return this.ingredient.test(stack) && stack.getCount() >= this.amount;
    }

    static MultipleIngredient of(ItemLike ingredient, int amount) {
        return new MultipleIngredient(ingredient, amount);
    }

    static MultipleIngredient of(Ingredient ingredient, int amount) {
        return new MultipleIngredient(ingredient, amount);
    }

    static MultipleIngredient fromJsonToObject(JsonObject json) {
        ItemLike item = ShapedRecipe.itemFromJson(GsonHelper.convertToJsonObject(json, "item"));
        int amount = GsonHelper.getAsInt(json, "count");
        return of(item, amount);
    }

    MultipleIngredient fromNetwork(FriendlyByteBuf packetBuffer) {
        return of(Ingredient.fromNetwork(packetBuffer), packetBuffer.readInt());
    }
}
