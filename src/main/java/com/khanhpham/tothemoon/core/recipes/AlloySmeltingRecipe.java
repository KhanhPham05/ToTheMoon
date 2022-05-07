package com.khanhpham.tothemoon.core.recipes;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.core.blockentities.others.AlloySmelterBlockEntity;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class AlloySmeltingRecipe implements Recipe<AlloySmelterBlockEntity> {
    public static RecipeType<AlloySmeltingRecipe> RECIPE_TYPE = ModUtils.registerRecipeType(ModRecipeLocations.ALLOY_SMELTING);

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


    public int getAlloyingTime() {
        return alloyingTime;
    }

    @Override
    public boolean matches(AlloySmelterBlockEntity container, @Nonnull Level pLevel) {
        return this.baseIngredient.test(container.items.get(0)) && this.secondaryIngredient.test(container.items.get(1));
    }

    @Override
    public ItemStack assemble(AlloySmelterBlockEntity pContainer) {
        return this.result.copy();
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

    public static final class Serializer extends SimpleRecipeSerializer<AlloySmeltingRecipe> {

        public Serializer() {
            super.setRegistryName(ModRecipeLocations.ALLOY_SMELTING);
        }

        @Override
        public AlloySmeltingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pSerializedRecipe, JsonNames.RESULT), false);
            IngredientStack baseIngredient = IngredientStack.fromJson(pSerializedRecipe.get(JsonNames.BASE_INGREDIENT));
            IngredientStack secondaryIngredient = IngredientStack.fromJson(pSerializedRecipe.get(JsonNames.SECONDARY_INGREDIENT));
            int processTime = GsonHelper.getAsInt(pSerializedRecipe, JsonNames.PROCESS_TIME, 200);
            return new AlloySmeltingRecipe(baseIngredient, secondaryIngredient, result, processTime, pRecipeId);
        }


        @Override
        public @NotNull AlloySmeltingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            IngredientStack baseIngredient = IngredientStack.fromNetwork(pBuffer);
            IngredientStack secondaryIngredient = IngredientStack.fromNetwork(pBuffer);
            ItemStack result = pBuffer.readItem();
            int processTime = pBuffer.readInt();
            return new AlloySmeltingRecipe(baseIngredient, secondaryIngredient, result, processTime, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, AlloySmeltingRecipe pRecipe) {
            pRecipe.baseIngredient.toNetwork(pBuffer);
            pRecipe.secondaryIngredient.toNetwork(pBuffer);
            pBuffer.writeItemStack(pRecipe.result, false);
            pBuffer.writeInt(pRecipe.alloyingTime);
        }
    }

}
