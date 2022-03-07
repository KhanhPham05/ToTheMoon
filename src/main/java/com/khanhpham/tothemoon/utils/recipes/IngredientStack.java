package com.khanhpham.tothemoon.utils.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.function.Predicate;

public class IngredientStack implements Predicate<ItemStack> {
    public final Ingredient baseIngredient;
    public final int amount;

    private IngredientStack(Ingredient baseIngredient, int amount) {
        this.baseIngredient = baseIngredient;
        this.amount = amount;
    }

    /**
     * @see net.minecraft.world.item.crafting.SimpleCookingSerializer
     */

    @SuppressWarnings("deprecation")
    public static IngredientStack fromJson(JsonElement ingredientElement) {
        if (ingredientElement.isJsonObject()) {
            Ingredient ingredient = null;
            JsonObject ingredientObject = ingredientElement.getAsJsonObject();
            if (ingredientObject.has(JsonNames.ITEM)) {

                JsonObject itemObject = ingredientObject.getAsJsonObject(JsonNames.ITEM);

                if (itemObject.has(JsonNames.TAG)) {
                    String tag = GsonHelper.getAsString(itemObject, JsonNames.TAG);
                    Tag.Named<Item> namedTag = ItemTags.bind(tag);
                    ingredient = Ingredient.of(namedTag);
                }

                if (itemObject.has(JsonNames.ITEM)) {
                    String itemName = GsonHelper.getAsString(itemObject, JsonNames.ITEM);
                    Item item = Registry.ITEM.getOptional(new ResourceLocation(itemName)).orElseThrow(() -> new IllegalStateException("Not a valid item id"));
                    ingredient = Ingredient.of(item);
                }

                int amount = GsonHelper.getAsInt(ingredientObject, JsonNames.COUNT, 1);

                return new IngredientStack(ingredient, amount);
            } else {
                throw new IllegalStateException("No item definition found");
            }
        }

        throw new IllegalStateException("Ingredient should follow the correct syntax");
    }

    public static IngredientStack fromJson(JsonObject recipeObject,String memberName ) {
        return fromJson(recipeObject.get(memberName));
    }

    public static IngredientStack create(Ingredient baseIngredient, int amount) {
        return new IngredientStack(baseIngredient, amount);
    }

    public static IngredientStack fromNetwork(FriendlyByteBuf buffer) {
        int amount = buffer.readInt();
        Ingredient baseIngredient = Ingredient.fromNetwork(buffer);
        return new IngredientStack(baseIngredient, amount);
    }

    @Override
    public boolean test(ItemStack stack) {
        if (stack != null) {
            return baseIngredient.test(stack) && stack.getCount() >= this.amount;
        }

        return false;
    }

    public void toNetwork(FriendlyByteBuf buffer) {
        buffer.writeInt(this.amount);
        CraftingHelper.write(buffer, this.baseIngredient);
    }

}
