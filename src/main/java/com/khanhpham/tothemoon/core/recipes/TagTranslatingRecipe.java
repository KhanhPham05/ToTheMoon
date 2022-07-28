package com.khanhpham.tothemoon.core.recipes;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class TagTranslatingRecipe implements Recipe<Container> {
    public static final RecipeType<TagTranslatingRecipe> RECIPE_TYPE = ModUtils.registerRecipeType(ModUtils.modLoc("tag_translating"));
    private final ResourceLocation recipeId;
    private final TagKey<Item> tag;

    private final ItemStack resultItem;

    public TagTranslatingRecipe(ResourceLocation recipeId, TagKey<Item> tag, ItemStack resultItem) {
        this.recipeId = recipeId;
        this.tag = tag;
        this.resultItem = resultItem;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return pContainer.getItem(0).is(this.tag);
    }

    @Override
    public ItemStack assemble(Container pContainer) {
        return this.resultItem.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.resultItem;
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.TAG_TRANSLATING;
    }

    @Override
    public RecipeType<?> getType() {
        return RECIPE_TYPE;
    }

    public TagKey<Item> getTag() {
        return tag;
    }

    public static final class Serializer extends SimpleRecipeSerializer<TagTranslatingRecipe> {

        @Override
        protected String getRecipeName() {
            return "tag_translating";
        }

        @Override
        public TagTranslatingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            String tag = GsonHelper.getAsString(pSerializedRecipe, JsonNames.TAG);
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, JsonNames.RESULT));
            return new TagTranslatingRecipe(pRecipeId, ItemTags.create(new ResourceLocation(tag)), result);
        }

        @Override
        public TagTranslatingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            TagKey<Item> tag = ItemTags.create(pBuffer.readResourceLocation());
            ItemStack result = pBuffer.readItem();
            return new TagTranslatingRecipe(pRecipeId, tag, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, TagTranslatingRecipe pRecipe) {
            pBuffer.writeResourceLocation(pRecipe.tag.location());
            pBuffer.writeItem(pRecipe.resultItem);
        }
    }
}
