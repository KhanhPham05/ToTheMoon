package com.khanhpham.tothemoon.datagen.recipes.elements;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.LinkedList;

public class ShortenIngredient {
    private final LinkedList<Item> items = new LinkedList<>();

    private final LinkedList<TagKey<Item>> tag = new LinkedList<>();

    private ShortenIngredient() {
    }

    public static ShortenIngredient create() {
        return new ShortenIngredient();
    }

    public ShortenIngredient add(ItemLike... items) {
        for (ItemLike item : items) {
            this.items.add(item.asItem());
        }
        return this;
    }

    public ShortenIngredient add(TagKey<Item> tag) {
        this.tag.add(tag);
        return this;
    }

    public JsonElement toShortenJson() {
        JsonArray jsonArray = new JsonArray();
        if (!items.isEmpty()) {
            this.items.forEach(item -> jsonArray.add(ModUtils.getFullName(item)));
        }

        if (!tag.isEmpty()) {
            this.tag.forEach(tag -> jsonArray.add(this.serializeTagToString(tag)));
        }

        Preconditions.checkState(!jsonArray.isEmpty());
        return jsonArray.size() > 1 ? jsonArray : jsonArray.get(0).getAsJsonPrimitive();
    }

    private String serializeTagToString(TagKey<Item> tag) {
        return "tag:" + tag.location();
    }
}
