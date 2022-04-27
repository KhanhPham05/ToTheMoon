package com.khanhpham.tothemoon.core.recipes.metalpressing;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.core.blocks.processblocks.metalpressingboard.MetalPressingPlateBlockEntity;
import com.khanhpham.tothemoon.core.recipes.ModRecipeLocations;
import com.khanhpham.tothemoon.core.recipes.SimpleRecipeSerializer;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ManualMetalPressRecipe implements Recipe<MetalPressingPlateBlockEntity> {
    public static final RecipeType<ManualMetalPressRecipe> RECIPE_TYPE = ModUtils.registerRecipeType(ModRecipeLocations.MANUAL_METAL_PRESSING);

    public final Ingredient ingredient;
    public final Ingredient mold;
    public final ItemStack result;
    public final ResourceLocation recipeId;

    public ManualMetalPressRecipe(ResourceLocation recipeId, Ingredient ingredient, Ingredient mold, ItemStack result) {
        this.ingredient = ingredient;
        this.mold = mold;
        this.result = result;
        this.recipeId = recipeId;
    }

    @Override
    public boolean matches(MetalPressingPlateBlockEntity container, Level pLevel) {
        return (ingredient.test(container.items.get(0)) && mold.test(container.items.get(1))) || (ingredient.test(container.items.get(1)) && mold.test(container.items.get(0)));
    }

    @Override
    public ItemStack assemble(MetalPressingPlateBlockEntity pContainer) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.MANUAL_METAL_PRESS_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return RECIPE_TYPE;
    }

    public static final class Serializer extends SimpleRecipeSerializer<ManualMetalPressRecipe> {
        public Serializer() {
            setRegistryName(ModRecipeLocations.MANUAL_METAL_PRESSING);
        }

        @Override
        public ManualMetalPressRecipe fromJson(ResourceLocation pRecipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(json.getAsJsonObject(JsonNames.INGREDIENT));
            Ingredient mold = Ingredient.fromJson(json.getAsJsonObject(JsonNames.MOLD));
            ItemStack result = ShapedRecipe.itemStackFromJson(json.getAsJsonObject(JsonNames.RESULT));
            return new ManualMetalPressRecipe(pRecipeId, ingredient, mold, result);
        }

        @Override
        public ManualMetalPressRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            Ingredient mold = Ingredient.fromNetwork(pBuffer);
            ItemStack stack = pBuffer.readItem();
            return new ManualMetalPressRecipe(pRecipeId, ingredient, mold, stack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ManualMetalPressRecipe pRecipe) {
            pRecipe.ingredient.toNetwork(pBuffer);
            pRecipe.mold.toNetwork(pBuffer);
            pBuffer.writeItemStack(pRecipe.result, false);
        }
    }
}
