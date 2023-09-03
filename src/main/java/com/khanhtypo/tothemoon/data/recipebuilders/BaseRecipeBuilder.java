package com.khanhtypo.tothemoon.data.recipebuilders;

import com.google.gson.JsonObject;
import com.khanhtypo.tothemoon.serverdata.recipes.RecipeTypeObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class BaseRecipeBuilder implements RecipeBuilder {
    protected static final Set<RecipeBuilder> UNSAVED_BUILDERS = new HashSet<>();
    protected static final Function<Character, String> CHAR_TO_STRING = c -> Character.toString(c);
    protected final ItemStack result;
    @Nullable
    private Advancement.Builder trigger;

    public BaseRecipeBuilder(ItemStack result) {
        this.result = result;
        UNSAVED_BUILDERS.add(this);
    }

    public BaseRecipeBuilder(ItemLike itemLike) {
        this(itemLike, 1);
    }

    public BaseRecipeBuilder(ItemLike itemLike, int count) {
        this(new ItemStack(itemLike, count));
    }

    public static Set<RecipeBuilder> getUnsavedBuilders() {
        return UNSAVED_BUILDERS;
    }

    @Override
    public RecipeBuilder unlockedBy(String p_176496_, CriterionTriggerInstance triggerInstance) {
        this.trigger = Advancement.Builder.recipeAdvancement().addCriterion(p_176496_, triggerInstance);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String p_176495_) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation recipeId) {
        @Nullable String message = this.shouldBuildOrThrow();
        if (message != null) {
            throw new IllegalStateException(message);
        }
        consumer.accept(
                new RecipeFinisher(
                        this.getRecipeType(),
                        this::writeToJson,
                        recipeId,
                        this.trigger != null ? this.trigger : Advancement.Builder.recipeAdvancement().display(this.result, Component.empty(), Component.empty(), null, FrameType.TASK, false, false, true).addCriterion("auto_trigger", PlayerTrigger.TriggerInstance.tick()),
                        recipeId,
                        this.result
                ));
        UNSAVED_BUILDERS.remove(this);
    }

    private void includeType(ResourceLocation ogRecipeId) {
        String recipeType = this.getRecipeType().getId().getPath() + "/";
        if (!ogRecipeId.getPath().contains(recipeType)) {
            ogRecipeId.withPrefix(recipeType);
        }
    }

    @Nullable
    protected String shouldBuildOrThrow() {
        return null;
    }

    @Override
    public String toString() {
        return "%s|%s".formatted(this.getRecipeType().getId(), ForgeRegistries.ITEMS.getKey(this.result.getItem()));
    }

    protected abstract RecipeTypeObject<?> getRecipeType();

    protected abstract void writeToJson(JsonObject writer);
}
