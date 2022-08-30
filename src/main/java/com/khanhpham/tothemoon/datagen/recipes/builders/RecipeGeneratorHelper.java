package com.khanhpham.tothemoon.datagen.recipes.builders;

import com.khanhpham.tothemoon.datagen.recipes.provider.ModRecipeProvider;
import com.khanhpham.tothemoon.datagen.tags.ModItemTags;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public record RecipeGeneratorHelper(Consumer<FinishedRecipe> consumer) {
    private static final ArrayList<String> RECIPES = new ArrayList<>();
    private static int recipeDuplications = 0;

    public static String extractTag(TagKey<?> tag) {
        return tag.location().getPath().replace('/', '_');
    }

    public static String getId(ItemLike item) {
        return ModUtils.registryToPath(item.asItem());
    }

    public static <T extends RecipeBuilder> void saveGlobal(Consumer<FinishedRecipe> consumer, T builder, String recipeType, String result) {
        unlock(builder);
        String recipeName = recipeType + '/' + result;

        if (RECIPES.contains(recipeName)) {
            recipeName = recipeName + "_" + recipeDuplications;
            recipeDuplications++;
        }

        RECIPES.add(recipeName);
        builder.save(consumer, ModUtils.modLoc(recipeName));
    }

    private static void unlock(RecipeBuilder builder) {
        builder.unlockedBy("tick", ModRecipeProvider.tick());
    }

    public void buildSlab(Supplier<SlabBlock> slabBlockSupplier, Supplier<Block> materialSupplier) {
        new Shaped(slabBlockSupplier.get(), 6, consumer).pattern("AAA").define('A', materialSupplier.get()).save();
    }

    public void stoneCutting(Supplier<? extends ItemLike> result, int amount, Supplier<? extends ItemLike> from) {
        var builder = SingleItemRecipeBuilder.stonecutting(Ingredient.of(from.get()), result.get(), amount);
        saveGlobal(consumer, builder, "stonecutting", getId(result.get()));
    }

    public Shaped shaped(ItemLike result, int amount) {
        return new Shaped(result, amount, consumer);
    }

    public Shaped shaped(Supplier<? extends ItemLike> result) {
        return this.shaped(result.get(), 1);
    }

    public void smelting(ItemLike ingredient, ItemLike result, boolean includeBlasting) {
        var smeltingRecipe = new Smelting(consumer, result, ingredient, 200, includeBlasting);
        smeltingRecipe.save();
    }

    public void smelting(TagKey<Item> ingredient, ItemLike result, boolean includeBlasting) {
        var smeltingRecipe = new Smelting(consumer, result, ingredient, 200, includeBlasting);
        smeltingRecipe.save();
    }

    @SafeVarargs
    public final void shapelessCrafting(ItemLike result, int amount, TagKey<Item>... require) {
        var shapelessRecipe = ShapelessRecipeBuilder.shapeless(result, amount);
        Set<String> tagStrings = new HashSet<>();
        for (TagKey<Item> itemTagKey : require) {
            shapelessRecipe.requires(itemTagKey);
            tagStrings.add(extractTag(itemTagKey));
        }


        saveGlobal(consumer, shapelessRecipe, "shapeless_crafting", getId(result));
    }

    public void shapelessCrafting(ItemLike result, int amount, ItemLike... require) {
        var shapelessRecipe = ShapelessRecipeBuilder.shapeless(result, amount);
        List<String> itemNames = new ArrayList<>();
        for (ItemLike itemLike : require) {
            itemNames.add(ModUtils.getPath(itemLike.asItem()));
            shapelessRecipe.requires(itemLike);
        }
        saveGlobal(consumer, shapelessRecipe, "shapeless_crafting", getId(result));
    }

    public void armor() {
        ModItems.ALL_ARMORS.values().stream().map(Supplier::get).forEach(armorItem -> {
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
                addition.define('A', craftItem).define('S', ModItemTags.RODS_STEEL).getBuilder().unlockedBy("tick", ModRecipeProvider.tick()).save(consumer, ModUtils.modLoc("crafting/" + toolItem.getRegistryName().getPath() + "_r"));
            }
            builder.define('A', craftItem).define('S', ModItemTags.RODS_STEEL).save();
        });
    }

    public static abstract class NamedRecipeBuilder<T extends RecipeBuilder> {

        public final Consumer<FinishedRecipe> consumer;

        public final T builder;

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
            return builder;
        }

        public void save() {
            unlock(this.getBuilder());
            this.builder.save(consumer, "tothemoon:" + recipeType + '/' + ModUtils.getPath(result.asItem()));
        }
    }

    public static final class Shaped extends NamedRecipeBuilder<ShapedRecipeBuilder> {
        public Shaped(ItemLike result, int amount, Consumer<FinishedRecipe> consumer) {
            super(consumer, ShapedRecipeBuilder.shaped(result, amount), result, "crafting");
        }

        public Shaped pattern(String pattern) {
            builder.pattern(pattern);
            return this;
        }

        public Shaped define(char c, ItemLike item) {
            this.inputs.add(ModUtils.registryToPath(item.asItem()));
            builder.define(c, item);
            return this;
        }

        public Shaped define(char c, TagKey<Item> tag) {
            this.inputs.add(extractTag(tag));
            builder.define(c, tag);
            return this;
        }
    }

    public static class Smelting extends NamedRecipeBuilder<SimpleCookingRecipeBuilder> {
        private final Ingredient ingredient;
        private final int cookTime;

        public Smelting(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike ingredient, int cookTime, boolean includeBlasting) {
            super(consumer, SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), result, 1.0f, cookTime), result, "smelting");
            this.ingredient = Ingredient.of(ingredient);
            super.inputs.add(getId(ingredient));
            this.cookTime = cookTime;
            if (includeBlasting) (new Blasting(consumer, result, ingredient, cookTime / 2)).save();
        }

        public Smelting(Consumer<FinishedRecipe> consumer, ItemLike result, TagKey<Item> ingredient, int cookTime, boolean includeBlasting) {
            super(consumer, SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), result, 1.0f, cookTime), result, "smelting");
            this.ingredient = Ingredient.of(ingredient);
            this.cookTime = cookTime;
            super.inputs.add(extractTag(ingredient));
            if (includeBlasting) (new Blasting(consumer, result, ingredient, cookTime / 2)).save();
        }

        @Deprecated
        public Smelting makeBlastingRecipe() {
            (new Blasting(consumer, result, ingredient, cookTime / 2)).save();
            return this;
        }
    }

    public static class Blasting extends NamedRecipeBuilder<SimpleCookingRecipeBuilder> {
        public Blasting(Consumer<FinishedRecipe> consumer, ItemLike result, Ingredient ingredient, int cookTime) {
            super(consumer, SimpleCookingRecipeBuilder.blasting(ingredient, result, 1.0f, cookTime), result, "blasting");
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
