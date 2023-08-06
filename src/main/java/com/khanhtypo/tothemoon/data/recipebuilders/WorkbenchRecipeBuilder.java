package com.khanhtypo.tothemoon.data.recipebuilders;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.khanhtypo.tothemoon.utls.JsonUtils;
import com.khanhtypo.tothemoon.common.item.hammer.HammerLevel;
import com.khanhtypo.tothemoon.registration.ModRecipeTypes;
import com.khanhtypo.tothemoon.serverdata.recipes.RecipeTypeObject;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Map;

import static com.khanhtypo.tothemoon.serverdata.WorkbenchRSerializer.*;

public final class WorkbenchRecipeBuilder extends BaseRecipeBuilder {
    private final Map<Character, Ingredient> mapping = Maps.newTreeMap();
    private @Nullable String[] pattern = null;
    private @Nullable HammerLevel hammerLevel;
    private @Nullable Ingredient additionalIngredient;

    public WorkbenchRecipeBuilder(ItemLike itemLike) {
        this(itemLike, 1);
    }

    public WorkbenchRecipeBuilder(ItemLike itemLike, int count) {
        super(itemLike, count);
    }

    public WorkbenchRecipeBuilder pattern(@Nonnull String... pattern) {
        Preconditions.checkState(pattern.length == 5);
        Arrays.stream(pattern).forEach(s -> Preconditions.checkState(checkLine(s)));
        Preconditions.checkState(this.pattern == null, "Pattern for %s has already set".formatted(this.toString()));
        this.pattern = pattern;
        return this;
    }

    public WorkbenchRecipeBuilder mapping(char c, Ingredient ingredient) {
        Preconditions.checkNotNull(this.pattern, "Pattern must be defined first.");
        this.mapping.put(c, ingredient);
        return this;
    }

    public WorkbenchRecipeBuilder minimumHammerLevel(HammerLevel hammerLevel) {
        this.hammerLevel = hammerLevel;
        return this;
    }

    public WorkbenchRecipeBuilder additionalIngredient(Ingredient additionalIngredient) {
        this.additionalIngredient = additionalIngredient;
        return this;
    }

    private boolean checkLine(String line) {
        return !line.isEmpty() && !line.isBlank() && line.length() == 5;
    }

    @Override
    protected RecipeTypeObject<?> getRecipeType() {
        return ModRecipeTypes.WORKBENCH_RECIPE;
    }

    @Override
    protected void writeToJson(JsonObject writer) {
        JsonUtils.addArrayTo(writer, PATTERN, this.pattern);
        JsonUtils.mapToObject(writer, MAPPING, this.mapping, CHAR_TO_STRING, Ingredient::toJson);
        JsonUtils.putNumberIfNonNull(writer, HAMMER_LEVEL, this.hammerLevel, HammerLevel::getHammerLevel);
        JsonUtils.putIngredientIfNonNull(writer, ADDITIONAL, this.additionalIngredient);
    }

    public static WorkbenchRecipeBuilder builder(ItemLike itemLike) {
        return new WorkbenchRecipeBuilder(itemLike);
    }
}
