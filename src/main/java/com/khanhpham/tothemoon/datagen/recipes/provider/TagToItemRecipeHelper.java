package com.khanhpham.tothemoon.datagen.recipes.provider;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TagToItemRecipeHelper {
    private static final Map<TagKey<Item>, List<Item>> TAG_DEFINITION;

    static {
        TAG_DEFINITION = new HashMap<>();
    }

    private final Consumer<FinishedRecipe> consumer;
    @Nullable
    private TagKey<Item> itemTag;

    public TagToItemRecipeHelper(Consumer<FinishedRecipe> consumer) {
        this.consumer = consumer;
    }

    public static TagToItemRecipeHelper create(Consumer<FinishedRecipe> consumer) {
        return new TagToItemRecipeHelper(consumer);
    }

    public TagToItemRecipeHelper tag(TagKey<Item> tag) {
        this.itemTag = tag;
        return this;
    }

    public TagToItemRecipeHelper put(TagKey<Item> tag, ItemLike... item) {
        TAG_DEFINITION.put(tag, Arrays.stream(item).map(ItemLike::asItem).toList());
        return this;
    }

    public void generateRecipe(TagWriter writer) {
        if (this.itemTag != null && TAG_DEFINITION.containsKey(this.itemTag)) {
            TAG_DEFINITION.get(this.itemTag).forEach(item -> writer.generateRecipe(this.consumer, itemTag, item));
        }
    }

    @FunctionalInterface
    public interface TagWriter {
        void generateRecipe(Consumer<FinishedRecipe> consumer, TagKey<Item> tag, Item item);
    }
}
