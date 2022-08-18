package com.khanhpham.tothemoon.core.recipes.metalpressing;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.compat.jei.DisplayRecipe;
import com.khanhpham.tothemoon.core.blockentities.others.MetalPressBlockEntity;
import com.khanhpham.tothemoon.core.recipes.SimpleRecipeSerializer;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MetalPressingRecipe implements DisplayRecipe<MetalPressBlockEntity> {
    public static final RecipeType<MetalPressingRecipe> RECIPE_TYPE = ModUtils.registerRecipeType(ModUtils.modLoc("metal_pressing"));
    public final Ingredient ingredient;
    public final Ingredient moldIngredient;
    public final ItemStack result;
    private final NonNullList<Ingredient> ingredients;
    private final boolean consumeMold;
    private final int pressingTime;
    private final ResourceLocation recipeId;


    public MetalPressingRecipe(ResourceLocation recipeId, Ingredient ingredient, Ingredient moldIngredient, boolean consumeMold, int pressingTime, ItemStack result) {
        this.ingredient = ingredient;
        this.moldIngredient = moldIngredient;
        this.consumeMold = consumeMold;
        this.result = result;
        this.pressingTime = pressingTime;
        this.recipeId = recipeId;
        this.ingredients = NonNullList.withSize(2, Ingredient.EMPTY);

        ingredients.set(0, this.ingredient);
        ingredients.set(1, this.moldIngredient);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public int getPressingTime() {
        return pressingTime;
    }

    @Override
    public boolean matches(MetalPressBlockEntity pContainer, Level pLevel) {
        return this.ingredient.test(pContainer.items.get(0)) && this.moldIngredient.test(pContainer.items.get(1));
    }

    @Override
    public ItemStack assemble(MetalPressBlockEntity pContainer) {
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
        return ModRecipes.METAL_PRESSING_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return RECIPE_TYPE;
    }

    public final boolean isConsumeMold() {
        return this.consumeMold;
    }

    public static final class Serializer extends SimpleRecipeSerializer<MetalPressingRecipe> {
        @Override
        public MetalPressingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient ingredient = Ingredient.fromJson(pSerializedRecipe.getAsJsonObject(JsonNames.INGREDIENT));
            Ingredient mold = pSerializedRecipe.has(JsonNames.MOLD) ? Ingredient.fromJson(pSerializedRecipe.getAsJsonObject(JsonNames.MOLD)) : Ingredient.EMPTY;
            ItemStack result = ShapedRecipe.itemStackFromJson(pSerializedRecipe.getAsJsonObject(JsonNames.RESULT));
            int pressingTime = GsonHelper.getAsInt(pSerializedRecipe, JsonNames.PROCESS_TIME, 100);
            return new MetalPressingRecipe(pRecipeId, ingredient, mold, GsonHelper.getAsBoolean(pSerializedRecipe, "consume_mold", false), pressingTime, result);
        }

        @Override
        public MetalPressingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            Ingredient press = Ingredient.fromNetwork(pBuffer);
            boolean consumeMold = pBuffer.readBoolean();
            ItemStack result = pBuffer.readItem();
            int pressingTime = pBuffer.readInt();
            return new MetalPressingRecipe(pRecipeId, ingredient, press, consumeMold, pressingTime, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, MetalPressingRecipe pRecipe) {
            pRecipe.ingredient.toNetwork(pBuffer);
            pRecipe.moldIngredient.toNetwork(pBuffer);
            pBuffer.writeBoolean(pRecipe.isConsumeMold());
            pBuffer.writeItemStack(pRecipe.result, false);
            pBuffer.writeInt(pRecipe.pressingTime);
        }

        @Override
        protected String getRecipeName() {
            return "metal_pressing";
        }
    }
}
