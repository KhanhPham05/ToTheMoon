package com.khanhpham.tothemoon.core.recipes;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.compat.jei.DisplayRecipe;
import com.khanhpham.tothemoon.core.blocks.machines.oreprocessor.OreProcessorBlockEntity;
import com.khanhpham.tothemoon.core.recipes.elements.ChancedResult;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Arrays;


public class OreProcessingRecipe implements DisplayRecipe<OreProcessorBlockEntity> {
    public static final RecipeType<OreProcessingRecipe> RECIPE_TYPE = ModUtils.registerRecipeType(ModUtils.modLoc("ore_processing"));
    private final ItemStack result;
    private final Ingredient ingredient;
    private final int processingTime;
    private final ResourceLocation recipeId;
    private final ChancedResult chancedResult;

    public OreProcessingRecipe(ResourceLocation recipeId, ItemStack result, Ingredient ingredient, int processingTime, ChancedResult chancedResult) {
        this.result = result;
        this.ingredient = ingredient;
        this.processingTime = processingTime;
        this.recipeId = recipeId;
        this.chancedResult = chancedResult;
    }

    @Override
    public boolean matches(OreProcessorBlockEntity pContainer, Level pLevel) {
        return pContainer.isResultFree(this) && ingredient.test(pContainer.getItem(0));
    }

    public ItemStack giveExtra(OreProcessorBlockEntity container) {
        ItemStack rolledItem = this.chancedResult.tryGiveExtra();
        return container.getItem(3).isEmpty() || ModUtils.isSlotFree(container, 3, rolledItem) ? rolledItem : ItemStack.EMPTY;
    }

    @Override
    public ItemStack assemble(OreProcessorBlockEntity pContainer) {
        return result.copy();
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
        return this.recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ORE_PROCESSING;
    }

    @Override
    public RecipeType<?> getType() {
        return RECIPE_TYPE;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    @Override
    public String toString() {
        return "OreProcessingRecipe{" +
                "result=" + result.toString() +
                ", ingredient=" + Arrays.toString(ingredient.getItems()) +
                ", processingTime=" + processingTime +
                ", recipeId=" + recipeId +
                ", chancedResult=" + chancedResult +
                '}';
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.withSize(1, Ingredient.EMPTY);
        ingredients.set(0, ingredient);
        return ingredients;
    }

    public ChancedResult getExtraResult() {
        return this.chancedResult;
    }

    public static final class Serializer extends SimpleRecipeSerializer<OreProcessingRecipe> {
        @Override
        protected String getRecipeName() {
            return "ore_processing";
        }

        @Override
        public OreProcessingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient ingredient = super.getShortenIngredient(pSerializedRecipe, JsonNames.INGREDIENT);
            ItemStack output = super.getShortenOutput(pSerializedRecipe);
            ChancedResult chancedResult = ChancedResult.fromJson(pSerializedRecipe);
            return new OreProcessingRecipe(pRecipeId, output, ingredient, GsonHelper.getAsInt(pSerializedRecipe, "processing_time", 100), chancedResult);
        }

        @Override
        public OreProcessingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new OreProcessingRecipe(pRecipeId, pBuffer.readItem(), Ingredient.fromNetwork(pBuffer), pBuffer.readInt(), ChancedResult.fromNetwork(pBuffer));
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, OreProcessingRecipe pRecipe) {
            pBuffer.writeItem(pRecipe.result);
            pRecipe.ingredient.toNetwork(pBuffer);
            pBuffer.writeInt(pRecipe.getProcessingTime());
            pRecipe.chancedResult.toNetwork(pBuffer);
        }
    }
}
