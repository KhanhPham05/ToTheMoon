package com.khanhpham.tothemoon.datagen.recipes;

import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import mekanism.api.datagen.recipe.builder.ItemStackToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import org.apache.commons.compress.utils.Lists;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RecipeGeneratorHelper {
    protected final Consumer<FinishedRecipe> consumer;

    public RecipeGeneratorHelper(Consumer<FinishedRecipe> consumer) {
        this.consumer = consumer;
    }

    public static String extractTag(TagKey<?> tag) {
        return tag.location().getPath().replace('/', '_');
    }

    static String getId(ItemLike item) {
        return ModUtils.convertRlToPath(item.asItem());
    }

    static <T extends RecipeBuilder> void saveGlobal(Consumer<FinishedRecipe> consumer, T builder, String recipeType, List<String> inputs, String result) {
        unlockedBy(builder);
        StringBuilder recipeName = new StringBuilder(recipeType + '/' + result + "_from_" + inputs.get(0));

        if (inputs.size() > 1) {
            for (int i = 1; i < inputs.size(); i++) {
                recipeName.append("_and_").append(inputs.get(i));
            }
        }

        builder.save(consumer, ModUtils.modLoc(recipeName.toString()));
    }

    private static void unlockedBy(RecipeBuilder builder) {
        builder.unlockedBy("tick", ModRecipeProvider.tick());
    }

    protected void buildSlab(Supplier<SlabBlock> slabBlockSupplier, Supplier<Block> materialSupplier) {
        new Shaped(slabBlockSupplier.get(), 6, consumer).pattern("AAA").define('A', materialSupplier.get()).save();
    }

    protected void stoneCutting(Supplier<? extends ItemLike> result, int amount, Supplier<? extends ItemLike> from) {
        var builder = SingleItemRecipeBuilder.stonecutting(Ingredient.of(from.get()), result.get(), amount);
        saveGlobal(consumer, builder, "stonecutting", List.of(getId(from.get())), getId(result.get()));
    }

    protected Shaped shaped(ItemLike result, int amount) {
        return new Shaped(result, amount, consumer);
    }

    protected Shaped shaped(Supplier<? extends ItemLike> result) {
        return this.shaped(result.get(), 1);
    }

    protected void smelting(ItemLike ingredient, ItemLike result, boolean includeBlasting) {
        var smeltingRecipe = new Smelting(consumer, result, ingredient, 200, includeBlasting);
        smeltingRecipe.save();
    }

    protected void smelting(TagKey<Item> ingredient, ItemLike result, boolean includeBlasting) {
        var smeltingRecipe = new Smelting(consumer, result, ingredient, 200, includeBlasting);
        smeltingRecipe.save();
    }

    @SafeVarargs
    protected final void shapelessCrafting(ItemLike result, int amount, TagKey<Item>... require) {
        var shapelessRecipe = ShapelessRecipeBuilder.shapeless(result, amount);
        List<String> tagStrings = new ArrayList<>();
        for (TagKey<Item> itemTagKey : require) {
            shapelessRecipe.requires(itemTagKey);
            tagStrings.add(extractTag(itemTagKey));
        }


        saveGlobal(consumer, shapelessRecipe, "shapeless_crafting", tagStrings, getId(result));
    }

    protected void shapelessCrafting(ItemLike result, int amount, ItemLike... require) {
        var shapelessRecipe = ShapelessRecipeBuilder.shapeless(result, amount);
        List<String> itemNames = new ArrayList<>();
        for (ItemLike itemLike : require) {
            itemNames.add(ModUtils.getPath(itemLike.asItem()));
            shapelessRecipe.requires(itemLike);
        }
        saveGlobal(consumer, shapelessRecipe, "shapeless_crafting", itemNames, getId(result));
    }

    public void armor() {
        ModItems.ARMORS.stream().map(Supplier::get).forEach(armorItem -> {
            var builder = this.shaped(armorItem, 1);
            EquipmentSlot armorSlot = armorItem.getSlot();
            switch (armorSlot) {
                case FEET -> builder.pattern("A A").pattern("A A");
                case LEGS -> builder.pattern("AAA").pattern("A A").pattern("A A");
                case HEAD -> builder.pattern("AAA").pattern("A A");
                case CHEST -> builder.pattern("A A").pattern("AAA").pattern("AAA");
            }
            builder.define('A', armorItem.getCraftItem());
            builder.save();
        });
    }

    @SuppressWarnings("deprecation")
    public void tools() {
        ModItems.ALL_TOOLS.values().stream().map(Supplier::get).forEach(toolItem -> {
            StringBuilder item = new StringBuilder(toolItem.getRegistryName().getPath());
            item.delete(item.lastIndexOf("_"), item.length());
            String craftItemName = item.toString();
            Item craftItem = Registry.ITEM.get(ModUtils.modLoc(craftItemName + "_ingot"));

            var builder = shaped(toolItem, 1);

            @Nullable Shaped addition = null;
            if (toolItem instanceof PickaxeItem) {
                builder.pattern("AAA").pattern(" S ").pattern(" S ");
            } else if (toolItem instanceof HoeItem) {
                builder.pattern("AA ").pattern(" S ").pattern(" S ");
                addition = shaped(toolItem, 1).pattern(" AA").pattern(" S ").pattern(" S ");
            } else if (toolItem instanceof AxeItem) {
                builder.pattern("AA ").pattern("AS ").pattern(" S ");
                addition = shaped(toolItem, 1).pattern(" AA").pattern(" SA").pattern(" S ");
            } else if (toolItem instanceof SwordItem) {
                builder.pattern("A").pattern("A").pattern("S");
            } else if (toolItem instanceof ShovelItem) {
                builder.pattern("A").pattern("S").pattern("S");
            }

            if (addition != null) {
                addition.define('A', craftItem).define('S', Items.STICK).getBuilder().save(consumer, ModUtils.modLoc(toolItem.getRegistryName().getPath() + "_r"));
            }
            builder.define('A', craftItem).define('S', Items.STICK).save();
        });
    }

    protected static abstract class NamedRecipeBuilder<T extends RecipeBuilder> {

        protected final Consumer<FinishedRecipe> consumer;

        protected final T builder;

        final List<String> inputs = Lists.newArrayList();

        final ItemLike result;

        private final String recipeType;

        public NamedRecipeBuilder(Consumer<FinishedRecipe> consumer, T builder, ItemLike result, String recipeType) {
            this.consumer = consumer;
            this.builder = builder;
            this.result = result;
            this.recipeType = recipeType;
        }

        public T getBuilder() {
            unlockedBy(builder);
            return builder;
        }

        public void save() {
            saveGlobal(consumer, builder, recipeType, inputs, result.asItem().getRegistryName().getPath());
        }
    }

    protected static final class Shaped extends NamedRecipeBuilder<ShapedRecipeBuilder> {
        public Shaped(ItemLike result, int amount, Consumer<FinishedRecipe> consumer) {
            super(consumer, ShapedRecipeBuilder.shaped(result, amount), result, "crafting");
        }

        public Shaped pattern(String pattern) {
            builder.pattern(pattern);
            return this;
        }

        public Shaped define(char c, ItemLike item) {
            this.inputs.add(ModUtils.convertRlToPath(item.asItem()));
            builder.define(c, item);
            return this;
        }

        public Shaped define(char c, TagKey<Item> tag) {
            this.inputs.add(extractTag(tag));
            builder.define(c, tag);
            return this;
        }

    }

    protected static class Smelting extends NamedRecipeBuilder<SimpleCookingRecipeBuilder> {
        private final Ingredient ingredient;
        private final int cookTime;

        public Smelting(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike ingredient, int cookTime, boolean includeBlasting) {
            super(consumer, SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), result, 1.0f, cookTime), result, "smelting");
            this.ingredient = Ingredient.of(ingredient);
            super.inputs.add(getId(ingredient));
            this.cookTime = cookTime;
            if (includeBlasting) (new Blasting(consumer, result, ingredient, cookTime / 2)).save();
            ItemStackToItemStackRecipeBuilder.smelting(IngredientCreatorAccess.item().from(ingredient), new ItemStack(result.asItem())).build(consumer, ModUtils.modLoc("compat/mek/smelting/" + ModUtils.getPath(ingredient.asItem()) + "_to_" + ModUtils.getPath(result.asItem())));
        }

        public Smelting(Consumer<FinishedRecipe> consumer, ItemLike result, TagKey<Item> ingredient, int cookTime, boolean includeBlasting) {
            super(consumer, SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), result, 1.0f, cookTime), result, "smelting");
            this.ingredient = Ingredient.of(ingredient);
            this.cookTime = cookTime;
            super.inputs.add(extractTag(ingredient));
            if (includeBlasting) (new Blasting(consumer, result, ingredient, cookTime / 2)).save();
            ItemStackToItemStackRecipeBuilder.smelting(IngredientCreatorAccess.item().from(ingredient), new ItemStack(result.asItem())).build(consumer, ModUtils.modLoc("compat/mek/smelting/" + extractTag(ingredient) + "_to_" + ModUtils.getPath(result.asItem())));
        }

        @Deprecated
        public Smelting makeBlastingRecipe() {
            (new Blasting(consumer, result, ingredient, cookTime / 2)).save();
            return this;
        }
    }

    protected static class Blasting extends NamedRecipeBuilder<SimpleCookingRecipeBuilder> {
        public Blasting(Consumer<FinishedRecipe> consumer, ItemLike result, Ingredient ingredient, int cookTime) {
            super(consumer, SimpleCookingRecipeBuilder.smelting(ingredient, result, 1.0f, cookTime), result, "blasting");
        }

        public Blasting(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike ingredient, int cookTime) {
            this(consumer, result, Ingredient.of(ingredient), cookTime);
            super.inputs.add(getId(ingredient));
        }

        public Blasting(Consumer<FinishedRecipe> consumer, ItemLike result, TagKey<Item> ingredient, int cookTime) {
            this(consumer, result, Ingredient.of(ingredient), cookTime);
            super.inputs.add(extractTag(ingredient));
        }
    }
}
