package com.khanhpham.tothemoon.datagen.recipes.elements;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.LinkedList;
import java.util.List;

public class ShortenIngredient {
    private final LinkedList<Item> items = new LinkedList<>();
    private final LinkedList<TagKey<Item>> tags = new LinkedList<>();


    private ShortenIngredient() {
    }

    public static ShortenIngredient create() {
        return new ShortenIngredient();
    }

    public ShortenIngredient add(ItemLike... items) {
        Preconditions.checkState(items.length > 0);
        for (ItemLike item : items) {
            this.items.add(item.asItem());
        }
        return this;
    }

    public ShortenIngredient add(TagKey<Item> tag) {
        this.tags.add(tag);
        return this;
    }

    public JsonElement toShortenJson() {
        JsonArray jsonArray = new JsonArray();
        if (!items.isEmpty()) {
            this.items.forEach(item -> jsonArray.add(ModUtils.getFullName(item)));
        }

        if (!tags.isEmpty()) {
            this.tags.forEach(tag -> jsonArray.add(this.serializeTagToString(tag)));
        }

        Preconditions.checkState(!jsonArray.isEmpty());
        return jsonArray.size() > 1 ? jsonArray : jsonArray.get(0).getAsJsonPrimitive();
    }

    private String serializeTagToString(TagKey<Item> tag) {
        return "tag:" + tag.location();
    }

    public Ingredient getIngredient() {
        List<Ingredient.Value> values = new LinkedList<>();

        for (TagKey<Item> tagKey : this.tags) {
            values.add(new Ingredient.TagValue(tagKey));
        }

        for (Item item : this.items) {
            values.add(new Ingredient.ItemValue(new ItemStack(item)));
        }

        return Ingredient.fromValues(values.stream());
    }

    public InventoryChangeTrigger.TriggerInstance getInventoryTrigger() {
        Preconditions.checkState(this.items.size() > 0 || this.tags.size() > 0);
        final ItemPredicate.Builder predicate = ItemPredicate.Builder.item();

        if (!this.items.isEmpty()) {
            predicate.of(this.items.toArray(ItemLike[]::new));
        }

        if (!this.tags.isEmpty()) {
            for (TagKey<Item> tag : this.tags) {
                predicate.of(tag);
            }
        }

        return InventoryChangeTrigger.TriggerInstance.hasItems(predicate.build());
    }

    public final ShortenIngredientStack stack() {
        return stack(1);
    }

    public final ShortenIngredientStack stack(int count) {
        if (this.items.isEmpty() && this.tags.isEmpty()) throw new IllegalStateException("Ingredient is empty, can not stack");
        return new ShortenIngredientStack(this, count);
    }
}
