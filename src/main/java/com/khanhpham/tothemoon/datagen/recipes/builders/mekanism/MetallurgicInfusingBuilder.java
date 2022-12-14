package com.khanhpham.tothemoon.datagen.recipes.builders.mekanism;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.datagen.recipes.builders.CompatRecipeBuilder;
import com.khanhpham.tothemoon.datagen.recipes.builders.CompatRecipeType;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Locale;
import java.util.function.Consumer;

public class MetallurgicInfusingBuilder extends CompatRecipeBuilder {
    private Ingredient ingredient;
    private InfuseType infuseType;
    private int mb;

    public MetallurgicInfusingBuilder(Consumer<FinishedRecipe> consumer, Item result) {
        super(result,consumer, CompatRecipeType.METALLURGIC_INFUSING);
    }

    public static MetallurgicInfusingBuilder of(Consumer<FinishedRecipe> consumer, ItemLike item) {
        return new MetallurgicInfusingBuilder(consumer, item.asItem());
    }

    @Override
    protected void serializeToJson(JsonObject object) {
        JsonObject ingredient = new JsonObject();
        ingredient.add("ingredient", this.ingredient.toJson());

        object.add("itemInput", ingredient);

        JsonObject chemicalInput = new JsonObject();
        chemicalInput.addProperty("amount", this.mb);
        chemicalInput.addProperty("tag", "mekanism:" + this.infuseType.toString().toLowerCase(Locale.ROOT));

        object.add("chemicalInput", chemicalInput);

        JsonObject output = new JsonObject();
        output.addProperty("item", ModUtils.getFullName(this.getResult()));

        object.add("output", output);
    }

    public void from(Ingredient ingredient, InfuseType infuseType, int mb) {
        this.ingredient = ingredient;
        this.infuseType = infuseType;
        this.mb = mb;
        this.save();
    }

    public enum InfuseType {
        REDSTONE,
        GOLD
    }
}
