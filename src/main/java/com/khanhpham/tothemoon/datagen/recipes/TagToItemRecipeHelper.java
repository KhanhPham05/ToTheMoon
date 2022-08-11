package com.khanhpham.tothemoon.datagen.recipes;

import com.khanhpham.tothemoon.datagen.tags.AppendableItemTagKey;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class TagToItemRecipeHelper {
    private static final Set<TagKey<Item>> TAGS = new HashSet<>();
    private final Consumer<FinishedRecipe> consumer;

    public TagToItemRecipeHelper(Consumer<FinishedRecipe> consumer) {
        this.consumer = consumer;
    }

    public static TagToItemRecipeHelper create(Consumer<FinishedRecipe> consumer) {
        return new TagToItemRecipeHelper(consumer);
    }

    @SafeVarargs
    public final TagToItemRecipeHelper tag(TagKey<Item>... tag) {
        TAGS.addAll(Arrays.asList(tag));
        return this;
    }

    private TagToItemRecipeHelper tag(Collection<TagKey<Item>> tags) {
        TAGS.addAll(tags);
        return this;
    }

    public final TagToItemRecipeHelper tag(AppendableItemTagKey appendableItemTagKey) {
        return this.tag(appendableItemTagKey.getAllChild());
    }

    public void generateRecipe(TagWriter writer) {
        for (TagKey<Item> tag : TAGS) {
            writer.generateRecipe(this.consumer, tag);
        }
    }

    @FunctionalInterface
    public interface TagWriter {
        void generateRecipe(Consumer<FinishedRecipe> consumer, TagKey<Item> tag);
    }
}
