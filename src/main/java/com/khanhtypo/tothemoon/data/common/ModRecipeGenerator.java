package com.khanhtypo.tothemoon.data.common;

import com.khanhtypo.tothemoon.ModUtils;
import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.common.item.BasicArmorItem;
import com.khanhtypo.tothemoon.common.item.ModArmorMaterials;
import com.khanhtypo.tothemoon.common.item.ModToolTiers;
import com.khanhtypo.tothemoon.common.item.ToolItem;
import com.khanhtypo.tothemoon.data.recipebuilders.BaseRecipeBuilder;
import com.khanhtypo.tothemoon.data.recipebuilders.WorkbenchRecipeBuilder;
import com.khanhtypo.tothemoon.registration.ModBlocks;
import com.khanhtypo.tothemoon.registration.bases.IngredientProvider;
import com.khanhtypo.tothemoon.registration.elements.ChildBlockObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.khanhtypo.tothemoon.registration.ModItems.*;
import static com.khanhtypo.tothemoon.data.ModItemTags.*;
import static com.khanhtypo.tothemoon.data.ModBlockItemTags.*;

@SuppressWarnings("SameParameterValue")
public class ModRecipeGenerator extends RecipeProvider {
    private Consumer<FinishedRecipe> consumer;

    public ModRecipeGenerator(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    @Nonnull
    public CompletableFuture<?> run(@Nonnull CachedOutput output) {
        CompletableFuture<?> completableFuture = super.run(output);
        var unsavedBuilders = BaseRecipeBuilder.getUnsavedBuilders();
        if (!unsavedBuilders.isEmpty()) {
            ToTheMoon.LOGGER.warn("There are recipes that unsaved: %s".formatted(ModUtils.toArrayString(unsavedBuilders)));
        }
        return completableFuture;
    }

    @Override
    @SuppressWarnings("SpellCheckingInspection")
    protected void buildRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        this.consumer = consumer;
        this.armor();
        ChildBlockObject.generateRecipe(this::slab, this::wall, this::stair);
        this.tool(STEEL_SWORD);
        this.tool(STEEL_SHOVEL);
        this.tool(STEEL_PICKAXE);
        this.tool(STEEL_AXE);
        this.tool(STEEL_HOE);
        this.tool(URANIUM_AXE);
        this.tool(URANIUM_HOE);
        this.tool(URANIUM_PICKAXE);
        this.tool(URANIUM_SHOVEL);
        this.tool(URANIUM_SWORD);
        WorkbenchRecipeBuilder.builder(ModBlocks.COPPER_MACHINE_FRAME)
                .pattern(" PPP ", "RSDSR", "RDMDR", "RSDSR", " PPP ")
                .mapping('P', Ingredient.of(PLATE_COPPER))
                .mapping('M', Ingredient.of(SHEETMETAL_COPPER.getItemTag()))
                .mapping('R', Ingredient.of(ROD_COPPER))
                .mapping('S', Ingredient.of(ItemTags.STONE_CRAFTING_MATERIALS))
                .mapping('D', Ingredient.of(Tags.Items.DUSTS_REDSTONE))
                .save(consumer);
    }

    private void wall(IngredientProvider provider) {
        wall(this.consumer, RecipeCategory.BUILDING_BLOCKS, provider, provider.getParent());
        SingleItemRecipeBuilder.stonecutting(provider.toIngredient(), RecipeCategory.BUILDING_BLOCKS, provider).unlockedBy("has", has(provider.getParent())).save(this.consumer, provider.getId().withPrefix("stonecutting_"));
    }

    private void slab(IngredientProvider provider) {
        slab(this.consumer, RecipeCategory.BUILDING_BLOCKS, provider, provider.getParent());
        SingleItemRecipeBuilder.stonecutting(provider.toIngredient(), RecipeCategory.BUILDING_BLOCKS, provider, 2).unlockedBy("has", has(provider.getParent())).save(this.consumer, provider.getId().withPrefix("stonecutting_"));
    }

    private void stair(IngredientProvider provider) {
        stairBuilder(provider, provider.toIngredient()).unlockedBy("has_item", provider.trigger()).save(this.consumer, provider.getId());
        SingleItemRecipeBuilder.stonecutting(provider.toIngredient(), RecipeCategory.BUILDING_BLOCKS, provider).unlockedBy("has", has(provider.getParent())).save(this.consumer, provider.getId().withPrefix("stonecutting_"));
    }

    private void armor() {
        final BiConsumer<ArmorItem.Type, BasicArmorItem> factory = this::armorRecipe;
        Stream.of(ModArmorMaterials.URANIUM, ModArmorMaterials.STEEL, ModArmorMaterials.REDSTONE_STEEL)
                .forEach(materials -> materials.forEachArmorPiece(factory));
    }

    private void armorRecipe(ArmorItem.Type type, BasicArmorItem item) {
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, item).define('A', item.repairIngredient());

        switch (type) {
            case HELMET -> builder.pattern("AAA").pattern("A A");
            case CHESTPLATE -> builder.pattern("A A").pattern("AAA").pattern("AAA");
            case LEGGINGS -> builder.pattern("AAA").pattern("A A").pattern("A A");
            case BOOTS -> builder.pattern("A A").pattern("A A");
        }

        builder.showNotification(false).unlockedBy("has_item", inventoryTrigger(item.getCheckPredicate())).save(this.consumer, item.getId());
    }

    private void tool(ToolItem toolItem) {
        ShapedRecipeBuilder builder = this.createToolBuilder(toolItem);
        Item item = toolItem.get();

        if (item instanceof SwordItem) {
            builder.pattern("A").pattern("A").pattern("B");
        } else {
            if (item instanceof ShovelItem) {
                builder.pattern("A").pattern("B").pattern("B");
            } else if (item instanceof PickaxeItem) {
                builder.pattern("AAA").pattern(" B ").pattern(" B ");
            } else if (item instanceof AxeItem) {
                builder.pattern("AA").pattern("AB").pattern(" B");
            } else if (item instanceof HoeItem) {
                builder.pattern("AA").pattern(" B").pattern(" B");
            }
        }

        builder.unlockedBy("has_item", has(ModToolTiers.TAG_MAPPING.get(toolItem.getTier()))).save(this.consumer, toolItem.getId());
    }

    private ShapedRecipeBuilder createToolBuilder(ToolItem toolItem) {
        RecipeCategory category = toolItem.get() instanceof SwordItem ? RecipeCategory.COMBAT : RecipeCategory.TOOLS;
        return ShapedRecipeBuilder.shaped(category, toolItem)
                .showNotification(false)
                .define('A', toolItem.getIngredient())
                .define('B', Tags.Items.RODS_WOODEN);
    }

    private ResourceLocation modLoc(String path) {
        return ModUtils.location(path);
    }
}
