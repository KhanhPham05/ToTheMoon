package com.khanhpham.tothemoon.datagen.recipes.builders;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.core.items.HammerItem;
import com.khanhpham.tothemoon.core.recipes.WorkbenchCraftingRecipe;
import com.khanhpham.tothemoon.datagen.recipes.builders.base.SimpleRecipeBuilder;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.khanhpham.tothemoon.utils.helpers.RegistryEntries;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Supplier;

public class WorkbenchRecipeBuilder extends SimpleRecipeBuilder {
    private final LinkedList<String> items = new LinkedList<>();
    private final Map<Character, String> patternMapping = new HashMap<>();
    private String pattern = "";
    private ItemLike extraInput = Items.AIR;
    private @Nullable Supplier<HammerItem> hammerItem;

    protected WorkbenchRecipeBuilder(ItemLike result) {
        super(result, WorkbenchCraftingRecipe.LOCATION);
    }

    public static WorkbenchRecipeBuilder craft(Supplier<? extends ItemLike> supplier) {
        return craft(supplier.get());
    }

    public static WorkbenchRecipeBuilder craft(ItemLike result) {
        return new WorkbenchRecipeBuilder(result);
    }

    public WorkbenchRecipeBuilder extra(ItemLike extraInput) {
        this.extraInput = extraInput;
        return this;
    }

    public WorkbenchRecipeBuilder extra(Supplier<? extends ItemLike> supplier) {
        return this.extra(supplier.get());
    }

    public WorkbenchRecipeBuilder pattern(String... patterns) {
        for (String pattern : patterns) {
            Preconditions.checkState(pattern.length() == 5, "");
            this.pattern = this.pattern.concat(pattern);
        }
        return this;
    }

    public WorkbenchRecipeBuilder setHammerItem(Supplier<HammerItem> hammerItem) {
        this.hammerItem = hammerItem;
        return this;
    }

    public WorkbenchRecipeBuilder define(char c, TagKey<Item> ingredient) {
        Preconditions.checkState(!patternMapping.containsKey(c), "Key" + c + " has already been assigned");
        this.patternMapping.put(c, "tag:" + ingredient.location());
        return this;
    }

    public WorkbenchRecipeBuilder define(char c, ItemLike ingredient) {
        Preconditions.checkState(!patternMapping.containsKey(c), "Key" + c + " has already been assigned");
        this.patternMapping.put(c, RegistryEntries.ITEM.getKey(ingredient.asItem()).toString());
        return this;
    }

    @Override
    protected FinishedRecipe saveRecipe(ResourceLocation recipeId) {
        Preconditions.checkState(this.pattern.length() == 25, "Pattern should only consists of 25 characters");
        for (char c : pattern.toCharArray()) {
            if (c != ' ') {
                Preconditions.checkState(patternMapping.containsKey(c), "No mapping for key [" + c + "]");
                this.items.add(this.patternMapping.get(c));
            } else {
                this.items.add("");
            }
        }

        Preconditions.checkState(this.items.size() == 25);
        return new Finished(recipeId, this.getResult(), this.hammerItem, this.extraInput, this.advancementBuilder, this.items);
    }

    private static final class Finished extends AbstractFinishedRecipe<WorkbenchCraftingRecipe> {

        private final Item result;
        @Nullable
        private final Supplier<HammerItem> hammerItem;
        private final ItemLike extraInput;
        private final LinkedList<String> items;

        public Finished(ResourceLocation recipeId, Item result, @Nullable Supplier<HammerItem> hammerItem, ItemLike extraInput, Advancement.Builder advancementBuilder, LinkedList<String> items) {
            super(recipeId, ModRecipes.WORKBENCH_CRAFTING, advancementBuilder, recipeId);
            this.result = result;
            this.hammerItem = hammerItem;
            this.extraInput = extraInput;
            this.items = items;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonObject resultObject = new JsonObject();
            resultObject.addProperty("item", RegistryEntries.ITEM.getKey(this.result).toString());
            pJson.add("result", resultObject);
            if (this.extraInput != Items.AIR)
                pJson.addProperty("extra", RegistryEntries.ITEM.getKey(this.extraInput.asItem()).toString());
            JsonArray jsonArray = new JsonArray();
            for (String item : items) {
                jsonArray.add(item);
            }
            pJson.add("craftingPattern", jsonArray);
            if (hammerItem != null) {
                pJson.addProperty("hammer", RegistryEntries.ITEM.getKey(this.hammerItem.get()).toString());
            }
        }
    }
}
