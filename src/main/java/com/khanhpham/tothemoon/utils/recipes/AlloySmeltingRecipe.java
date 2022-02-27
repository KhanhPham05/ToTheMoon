package com.khanhpham.tothemoon.utils.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.ModUtils;
import com.khanhpham.tothemoon.core.alloysmelter.AlloySmelterBlockEntity;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModRecipes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.ParametersAreNonnullByDefault;


@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public final class AlloySmeltingRecipe implements Recipe<AlloySmelterBlockEntity> {

    public static final ResourceLocation RECIPE_ID = ModUtils.modLoc("alloy_smelting");

    private final ResourceLocation recipeId;
    private final MultipleInputIngredient ingredients;
    private final ItemStack result;

    public AlloySmeltingRecipe(ResourceLocation recipeId, MultipleInputIngredient ingredients, ItemStack result) {
        this.recipeId = recipeId;
        this.ingredients = ingredients;
        this.result = result;
    }

    public MultipleIngredient getFirstIngredient() {
        return ingredients.getFirstIngredient();
    }

    public MultipleIngredient getSecondIngredient() {
        return ingredients.getSecondIngredient();
    }

    @Override
    public boolean matches(AlloySmelterBlockEntity pContainer, Level pLevel) {
        return test(pContainer, ingredients);
    }

    private boolean test(Container container, MultipleInputIngredient stack) {
        ItemStack input = container.getItem(0);
        ItemStack input2 = container.getItem(1);
        if (input.isEmpty() || input2.isEmpty()) {
            return false;
        } else {
            boolean flag = false;

            if (container.getItem(2).isEmpty()) {
                flag = true;
            } else if (container.getItem(2).is(result.getItem())) {
                flag = container.getItem(2).getCount() <= container.getContainerSize() - result.getCount();
            }

            return stack.getFirstIngredient().isMatch(input) && stack.getSecondIngredient().isMatch(input2) && flag;
        }
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
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return new Serializer();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ALLOY_SMELTING;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.ALLOY_SMELTER);
    }

    @MethodsReturnNonnullByDefault
    @ParametersAreNonnullByDefault
    public static final class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<AlloySmeltingRecipe> {

        public Serializer() {
            setRegistryName(RECIPE_ID);
        }

        @Override
        public AlloySmeltingRecipe fromJson(ResourceLocation id, JsonObject json) {
            MultipleInputIngredient ingredients = getIngredientFromJson(json, JsonNames.ALLOY_INGREDIENTS);
            ItemStack result = getStackFromJson(json, JsonNames.CRAFTING_RESULT);
            return new AlloySmeltingRecipe(id, ingredients, result);
        }

        @SuppressWarnings("deprecation")
        private ItemStack getStackFromJson(JsonObject json, String memberName) {
            if (json.get(memberName).isJsonObject()) {
                return ShapedRecipe.itemStackFromJson(json);
            } else {
                String s = GsonHelper.getAsString(json, memberName);
                return new ItemStack(Registry.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> new IllegalStateException("Item: " + s + " doesn't exist")));
            }
        }

        @SuppressWarnings("deprecation")
        private MultipleInputIngredient getIngredientFromJson(JsonObject json, String memberName) {
            if (json.has(memberName)) {
                JsonElement element = json.get(memberName);
                if (element.isJsonArray()) {
                    if (element.getAsJsonArray().size() >= 2) {
                        JsonElement element1 = element.getAsJsonArray().get(0);
                        JsonElement element2 = element.getAsJsonArray().get(1);

                        if (element1.isJsonObject() && element2.isJsonObject()) {
                            JsonObject object1 = (JsonObject) element1;
                            JsonObject object2 = (JsonObject) element2;
                            int amount1 = GsonHelper.getAsInt(object1, JsonNames.COUNT, 1);
                            int amount2 = GsonHelper.getAsInt(object2, JsonNames.COUNT, 1);

                            MultipleIngredient ingredient1 = null;
                            MultipleIngredient ingredient2 = null;
                            if (object1.has(JsonNames.ITEM_NAME)) {
                                ingredient1 = MultipleIngredient.of(Ingredient.of(Registry.ITEM.get(new ResourceLocation(object1.get(JsonNames.ITEM_NAME).getAsString()))), amount1);
                            } else if (object1.has(JsonNames.ITEM_TAG)) {
                                ingredient1 = MultipleIngredient.of(Ingredient.of(ItemTags.bind(object1.get(JsonNames.ITEM_TAG).getAsString())), amount1);
                            }

                            if (object2.has(JsonNames.ITEM_NAME)) {
                                ingredient2 = MultipleIngredient.of(Ingredient.of(Registry.ITEM.get(new ResourceLocation(object2.get(JsonNames.ITEM_NAME).getAsString()))), amount2);
                            } else if (object2.has(JsonNames.ITEM_TAG)) {
                                ingredient2 = MultipleIngredient.of(Ingredient.of(ItemTags.bind(object2.get(JsonNames.ITEM_TAG).getAsString())), amount2);
                            }

                            return new MultipleInputIngredient(ingredient1, ingredient2);
                        } else throw new JsonSyntaxException("");

                    } else throw new JsonSyntaxException("");
                } else throw new JsonSyntaxException("");
            } else throw new JsonSyntaxException("");
        }

        /**
         * @see SimpleCookingSerializer
         */
        @Override
        public AlloySmeltingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            MultipleInputIngredient ingredient = MultipleInputIngredient.fromNetwork(buffer);
            ItemStack stack = buffer.readItem();
            return new AlloySmeltingRecipe(id, ingredient, stack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AlloySmeltingRecipe recipe) {
            recipe.ingredients.toNetwork(buffer);
            buffer.writeItemStack(recipe.result, false);
        }
    }
}
