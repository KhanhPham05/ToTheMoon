package com.khanhpham.tothemoon.core.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class IngredientStack implements Predicate<ItemStack> {
    public final Ingredient ingredient;
    public final int amount;


    private IngredientStack(Ingredient ingredient, int amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }

    private IngredientStack(ItemLike item, int amount) {
        this.ingredient = Ingredient.of(new ItemStack(item, amount));
        this.amount = amount;
    }

    public static IngredientStack fromJson(JsonElement ingredientElement) {
        if (ingredientElement.isJsonObject()) {

            JsonObject ingredientObject = ingredientElement.getAsJsonObject();
            if (ingredientObject.has(JsonNames.ITEM)) {

                Ingredient item = Ingredient.fromJson(ingredientObject.get(JsonNames.ITEM));
                int amount = GsonHelper.getAsInt(ingredientObject, JsonNames.COUNT, 1);

                return new IngredientStack(item, amount);
            } else {
                throw new IllegalStateException("No item definition found");
            }
        }

        throw new IllegalStateException("Ingredient should follow the correct syntax");
    }

    public static IngredientStack create(ItemLike item, int count) {
        return new IngredientStack(item, count);
    }

    public static IngredientStack create(TagKey<Item> tag, int count) {
        return new IngredientStack(Ingredient.of(tag), count);
    }

    public static IngredientStack fromNetwork(FriendlyByteBuf buffer) {
        int amount = buffer.readInt();
        Ingredient baseIngredient = Ingredient.fromNetwork(buffer);
        return new IngredientStack(baseIngredient, amount);
    }

    public List<ItemStack> getIngredientStacks() {
        ItemStack[] stack = ingredient.getItems();
        ItemStack[] stack1 = new ItemStack[stack.length];

        for (int i = 0; i < stack.length; i++) {
            stack1[i] = ItemHandlerHelper.copyStackWithSize(stack[i], this.amount);
        }

        return Arrays.asList(stack1);
    }

    public ItemStack getStack() {
        return new ItemStack(this.ingredient.getItems()[0].getItem(), amount);
    }

    public Item getIngredientItem() {
        return ingredient.getItems()[0].getItem();
    }

    public String getIngredientName() {
        return Objects.requireNonNull(getIngredientItem().getRegistryName()).getPath();
    }

    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.add("item", this.ingredient.toJson());
        object.addProperty("count", this.amount);

        return object;
    }

    @Override
    public boolean test(ItemStack stack) {
        if (stack != null) {
            return ingredient.test(stack) && stack.getCount() >= this.amount;
        }

        return false;
    }

    public void toNetwork(FriendlyByteBuf buffer) {
        buffer.writeInt(this.amount);
        CraftingHelper.write(buffer, this.ingredient);
    }

}
