package com.khanhpham.tothemoon.utils.recipes;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.ModUtils;
import com.khanhpham.tothemoon.core.alloysmelter.AlloySmelterBlockEntity;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModRecipes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class AlloySmeltingRecipe implements Recipe<AlloySmelterBlockEntity> {
    public static RecipeType<AlloySmeltingRecipe> RECIPE_TYPE = ModUtils.registerRecipeType("alloy_smelting");

    public final IngredientStack baseIngredient;
    public final IngredientStack secondaryIngredient;
    public final ItemStack result;
    public final int alloyingTime;
    public final ResourceLocation id;

    public AlloySmeltingRecipe(IngredientStack baseIngredient, IngredientStack secondaryIngredient, ItemStack result, int alloyingTime, ResourceLocation id) {
        this.baseIngredient = baseIngredient;
        this.secondaryIngredient = secondaryIngredient;
        this.result = result;
        this.alloyingTime = alloyingTime;
        this.id = id;
    }

    @Override
    public boolean matches(AlloySmelterBlockEntity pContainer, Level pLevel) {
        ItemStack outputSlot = pContainer.getItem(2);
        if (outputSlot.isEmpty()) {
            return true;
        } else return outputSlot.is(getResultItem().getItem()) && outputSlot.getCount() < pContainer.getMaxStackSize() - getResultItem().getCount();
    }

    @Override
    public ItemStack assemble(AlloySmelterBlockEntity pContainer) {
        return this.result;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ALLOY_SMELTING_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return RECIPE_TYPE;
    }

    public static final class Serializer extends BaseRecipeSerializer<AlloySmeltingRecipe> {
        @Override
        public AlloySmeltingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack result = resultFromJson(pSerializedRecipe);
            IngredientStack baseIngredient = IngredientStack.fromJson(pSerializedRecipe.get(JsonNames.BASE_INGREDIENT));
            IngredientStack secondaryIngredient = IngredientStack.fromJson(pSerializedRecipe.get(JsonNames.SECONDARY_INGREDIENT));
            int processTime = GsonHelper.getAsInt(pSerializedRecipe, JsonNames.PROCESS_TIME);
            return new AlloySmeltingRecipe(baseIngredient, secondaryIngredient, result, processTime, pRecipeId);
        }



        @Nullable
        @Override
        public AlloySmeltingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return null;
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, AlloySmeltingRecipe pRecipe) {

        }

        @Override
        protected ItemLike getIcon() {
            return ModBlocks.ALLOY_SMELTER;
        }
    }

}
