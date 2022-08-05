package com.khanhpham.tothemoon.datagen.recipes.provider;

import com.khanhpham.tothemoon.datagen.recipes.AlloySmeltingRecipeBuilder;
import com.khanhpham.tothemoon.datagen.recipes.MetalPressRecipeBuilder;
import com.khanhpham.tothemoon.datagen.recipes.RecipeGeneratorHelper;
import com.khanhpham.tothemoon.datagen.recipes.TagTranslatingRecipeBuilder;
import com.khanhpham.tothemoon.datagen.tags.ModItemTags;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.datagen.recipe.builder.ItemStackToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.tags.MekanismTags;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.TickTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.khanhpham.tothemoon.datagen.tags.ModItemTags.*;
import static net.minecraftforge.common.Tags.Items.*;

public class ModRecipeProvider extends RecipeProvider {
    public static int RECIPE_CODE = 1;

    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    public static ResourceLocation createRecipeId() {
        ResourceLocation id = ModUtils.modLoc("recipe_" + RECIPE_CODE);
        RECIPE_CODE++;
        return id;
    }

    public static TickTrigger.TriggerInstance tick() {
        return new TickTrigger.TriggerInstance(EntityPredicate.Composite.ANY);
    }

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        final RecipeGeneratorHelper helper = new RecipeGeneratorHelper(consumer);
        buildSmeltingRecipes(helper);
        buildModdedRecipes(consumer);
        buildCraftingTableRecipes(helper);
        buildRecipeCompat(consumer, helper);
    }

    private void buildRecipeCompat(Consumer<FinishedRecipe> consumer, RecipeGeneratorHelper helper) {
        //30mB redstone + iron dust -> redstone metal dust
        ItemStackChemicalToItemStackRecipeBuilder.metallurgicInfusing(
                IngredientCreatorAccess.item().from(DUSTS_IRON),
                IngredientCreatorAccess.infusion().from(MekanismTags.InfuseTypes.REDSTONE, 30),
                new ItemStack(ModItems.REDSTONE_METAL_DUST.get())
        ).build(consumer, loc("compat/mek/metallurgic_infusing/iron_dust_with_redstone_to_redstone_metal_dust"));

        //30 redstone + iron INGOT -> redstone metal
        ItemStackChemicalToItemStackRecipeBuilder.metallurgicInfusing(
                IngredientCreatorAccess.item().from(INGOTS_IRON),
                IngredientCreatorAccess.infusion().from(MekanismTags.InfuseTypes.REDSTONE, 30),
                new ItemStack(ModItems.REDSTONE_METAL.get())
        ).build(consumer, loc("compat/mek/metallurgic_infusing/iron_with_redstone_to_redstone_metal"));

        //30mB redstone + steel dust -> redstone steel dust
        ItemStackChemicalToItemStackRecipeBuilder.metallurgicInfusing(
                IngredientCreatorAccess.item().from(DUSTS_STEEL),
                IngredientCreatorAccess.infusion().from(MekanismTags.InfuseTypes.REDSTONE, 30),
                new ItemStack(ModItems.REDSTONE_STEEL_ALLOY_DUST.get())
        ).build(consumer, loc("compat/mek/metallurgic_infusing/steel_dust_with_redstone_to_redstone_steel_dust"));

        //30 redstone + iron INGOT -> redstone metal
        ItemStackChemicalToItemStackRecipeBuilder.metallurgicInfusing(
                IngredientCreatorAccess.item().from(INGOTS_STEEL),
                IngredientCreatorAccess.infusion().from(MekanismTags.InfuseTypes.REDSTONE, 30),
                new ItemStack(ModItems.REDSTONE_STEEL_ALLOY.get())
        ).build(consumer, loc("compat/mek/metallurgic_infusing/steel_with_redstone_to_redstone_steel_alloy"));

        //ingots to dusts
        ItemStackToItemStackRecipeBuilder.crushing(IngredientCreatorAccess.item().from(ModItems.STEEL_INGOT.get()), new ItemStack(ModItems.STEEL_DUST.get()))
                .build(consumer, loc("compat/mek/crushing/steel_to_dust"));

        ItemStackToItemStackRecipeBuilder.crushing(IngredientCreatorAccess.item().from(ModItems.REDSTONE_METAL.get()), new ItemStack(ModItems.REDSTONE_METAL_DUST.get()))
                .build(consumer, loc("compat/mek/crushing/redstone_metal_to_dust"));

        ItemStackToItemStackRecipeBuilder.crushing(IngredientCreatorAccess.item().from(ModItems.REDSTONE_STEEL_ALLOY.get()), new ItemStack(ModItems.REDSTONE_STEEL_ALLOY_DUST.get()))
                .build(consumer, loc("compat/mek/crushing/redstone_steel_to_dust"));
    }

    private ResourceLocation loc(String saveLoc) {
        return ModUtils.modLoc(saveLoc);
    }

    private void buildCraftingTableRecipes(final RecipeGeneratorHelper helper) {
        buildStair(helper, ModBlocks.MOON_ROCK_STAIR, ModBlocks.MOON_ROCK);
        buildStair(helper, ModBlocks.MOON_ROCK_BRICK_STAIR, ModBlocks.MOON_ROCK_BRICK);
        buildStair(helper, ModBlocks.POLISHED_MOON_ROCK_STAIR, ModBlocks.POLISHED_MOON_ROCK);
        buildStair(helper, ModBlocks.COBBLED_MOON_ROCK_STAIR, ModBlocks.COBBLED_MOON_ROCK);
        buildSlab(helper, ModBlocks.COBBLED_MOON_ROCK_SLAB, ModBlocks.COBBLED_MOON_ROCK);
        buildSlab(helper, ModBlocks.MOON_ROCK_SLAB, ModBlocks.MOON_ROCK);
        buildSlab(helper, ModBlocks.MOON_ROCK_BRICK_SLAB, ModBlocks.MOON_ROCK_BRICK);
        buildSlab(helper, ModBlocks.POLISHED_MOON_ROCK_SLAB, ModBlocks.POLISHED_MOON_ROCK);

        storageBlock(helper, ModBlocks.STEEL_BLOCK, ModItems.STEEL_INGOT);
        storageBlock(helper, ModBlocks.REDSTONE_METAL_BLOCK, ModItems.REDSTONE_METAL);
        storageBlock(helper, ModBlocks.REDSTONE_STEEL_ALLOY_BLOCK, ModItems.REDSTONE_STEEL_ALLOY);
        storageBlock(helper, ModBlocks.RAW_URANIUM_BLOCK, ModItems.RAW_URANIUM_ORE);

        shapelessCrafting(helper, ModItems.COPPER_PLATE, GENERAL_TTM_HAMMERS, INGOTS_COPPER);
        shapelessCrafting(helper, ModItems.URANIUM_PLATE, GENERAL_TTM_HAMMERS, INGOTS_URANIUM);
        shapelessCrafting(helper, ModItems.STEEL_PLATE, GENERAL_TTM_HAMMERS, INGOTS_STEEL);
        shapelessCrafting(helper, ModItems.IRON_PLATE, GENERAL_TTM_HAMMERS, INGOTS_IRON);
        shapelessCrafting(helper, ModItems.GOLD_PLATE, GENERAL_TTM_HAMMERS, INGOTS_GOLD);
        shapelessCrafting(helper, ModItems.REDSTONE_METAL_PLATE, GENERAL_TTM_HAMMERS, INGOTS_REDSTONE_METAL);
        shapelessCrafting(helper, ModItems.REDSTONE_STEEL_ALLOY_PLATE, GENERAL_TTM_HAMMERS, INGOTS_REDSTONE_STEEL_ALLOY);

        shapelessCrafting(helper, ModItems.COPPER_ROD, GENERAL_TTM_HAMMERS, PLATES_COPPER);
        shapelessCrafting(helper, ModItems.URANIUM_ROD, GENERAL_TTM_HAMMERS, PLATES_URANIUM);
        shapelessCrafting(helper, ModItems.STEEL_ROD, GENERAL_TTM_HAMMERS, PLATES_STEEL);
        shapelessCrafting(helper, ModItems.IRON_ROD, GENERAL_TTM_HAMMERS, PLATES_IRON);
        shapelessCrafting(helper, ModItems.GOLD_ROD, GENERAL_TTM_HAMMERS, PLATES_GOLD);
        shapelessCrafting(helper, ModItems.REDSTONE_METAL_ROD, GENERAL_TTM_HAMMERS, PLATES_REDSTONE_METAL);
        shapelessCrafting(helper, ModItems.REDSTONE_STEEL_ALLOY_ROD, GENERAL_TTM_HAMMERS, PLATES_REDSTONE_STEEL_ALLOY);

        shapelessCrafting(helper, ModItems.COPPER_WIRE, GENERAL_TTM_HAMMERS, RODS_COPPER);
        shapelessCrafting(helper, ModItems.URANIUM_WIRE, GENERAL_TTM_HAMMERS, RODS_URANIUM);
        shapelessCrafting(helper, ModItems.STEEL_WIRE, GENERAL_TTM_HAMMERS, RODS_STEEL);
        shapelessCrafting(helper, ModItems.IRON_WIRE, GENERAL_TTM_HAMMERS, RODS_IRON);
        shapelessCrafting(helper, ModItems.GOLD_WIRE, GENERAL_TTM_HAMMERS, RODS_GOLD);
        shapelessCrafting(helper, ModItems.REDSTONE_METAL_WIRE, GENERAL_TTM_HAMMERS, RODS_REDSTONE_METAL);
        shapelessCrafting(helper, ModItems.REDSTONE_STEEL_ALLOY_WIRE, GENERAL_TTM_HAMMERS, RODS_REDSTONE_STEEL);

        shapelessCrafting(helper, ModItems.STEEL_DUST, DUSTS_HEATED_COAL, DUSTS_HEATED_COAL, DUSTS_HEATED_COAL, DUSTS_IRON);
        helper.shaped(ModItems.COPPER_GEAR).pattern(" C ").pattern("C C").pattern(" C ").define('C', INGOTS_COPPER).save();

        helper.shaped(ModItems.CPU_CHIP).pattern("RRR").pattern("DGD").pattern("SSS").define('R', PLATES_REDSTONE_METAL).define('D', DUSTS_REDSTONE).define('G', DUSTS_GOLD).define('S', PLATES_STEEL).save();
        helper.shaped(ModItems.CIRCUIT_BOARD).pattern("UUU").pattern("RCR").pattern("GGG").define('U', PLATES_URANIUM).define('R', DUSTS_REDSTONE_STEEL_ALLOY).define('G', PLATES_GOLD).define('C', ModItems.CPU_CHIP.get()).save();
        helper.shaped(ModBlocks.MOON_ROCK_BARREL).pattern("MMM").pattern("MCM").pattern("MMM").define('M', ModItemTags.MOON_ROCKS).define('C', CHESTS_WOODEN).save();

        helper.shaped(ModBlocks.ANTI_PRESSURE_GLASS.get(), 4).pattern("SGS").pattern("GQG").pattern("SGS").define('S', DUSTS_STEEL).define('G', Tags.Items.GLASS).define('Q', GEMS_QUARTZ).save();
        helper.shaped(ModBlocks.METAL_PRESS).pattern("SSS").pattern("WFW").pattern("SBS").define('S', PLATES_STEEL).define('W', WIRES_STEEL).define('F', ModBlocks.COPPER_MACHINE_FRAME.get()).define('B', STORAGE_BLOCKS_IRON);

        helper.shaped(ModBlocks.COPPER_ENERGY_GENERATOR).pattern("SGS").pattern("WFW").pattern("SBS").define('S', ModItemTags.PLATES_STEEL).define('G', ModItemTags.GEARS_COPPER).define('W', WIRES_REDSTONE_METAL).define('F', ModBlocks.REDSTONE_MACHINE_FRAME.get()).define('B', Blocks.BLAST_FURNACE).save();
        helper.shaped(ModBlocks.IRON_ENERGY_GENERATOR).pattern("SPS").pattern("WMW").pattern("SGS").define('S', ModItemTags.PLATES_STEEL).define('P', PLATES_IRON).define('W', WIRES_REDSTONE_METAL).define('M', ModBlocks.COPPER_ENERGY_GENERATOR.get()).define('G', ModItemTags.GEARS_STEEL).save();
        helper.shaped(ModBlocks.GOLD_ENERGY_GENERATOR).pattern("SPS").pattern("WMW").pattern("SGS").define('S', ModItemTags.PLATES_STEEL).define('P', PLATES_GOLD).define('W', WIRES_REDSTONE_STEEL_ALLOY).define('M', ModBlocks.IRON_ENERGY_GENERATOR.get()).define('G', ModItemTags.GEARS_GOLD).save();
        helper.shaped(ModBlocks.NETHER_BRICK_FURNACE_CONTROLLER).pattern("SSS").pattern("BFB").pattern("BAB").define('S', ModBlocks.SMOOTH_BLACKSTONE.get()).define('F', Items.FURNACE).define('B', Items.NETHER_BRICKS).define('A', Items.SOUL_SOIL).save();
        helper.shaped(ModBlocks.BLACKSTONE_FLUID_ACCEPTOR).pattern("A").pattern("B").define('A', ModBlocks.SMOOTH_BLACKSTONE.get()).define('B', Items.BUCKET).save();
        helper.shaped(ModBlocks.NETHER_BRICKS_FLUID_ACCEPTOR).pattern("A").pattern("B").define('A', Blocks.NETHER_BRICKS).define('B', Items.BUCKET).save();


        helper.shapelessCrafting(ModItems.REDSTONE_STEEL_ALLOY_DUST.get(), 1, ModItems.STEEL_DUST.get(), Items.REDSTONE, Items.REDSTONE, Items.REDSTONE);
        helper.shapelessCrafting(ModItems.REDSTONE_METAL_DUST.get(), 1, ModItems.IRON_DUST.get(), Items.REDSTONE, Items.REDSTONE, Items.REDSTONE);

        sheetBlock(helper, ModBlocks.COPPER_SHEET_BLOCK, PLATES_COPPER);
        sheetBlock(helper, ModBlocks.GOLD_SHEET_BLOCK, PLATES_GOLD);
        sheetBlock(helper, ModBlocks.STEEL_SHEET_BLOCK, PLATES_STEEL);
        sheetBlock(helper, ModBlocks.IRON_SHEET_BLOCK, PLATES_IRON);

        shapeless(helper, ModItems.COPPER_PLATE, 8, ModBlocks.COPPER_SHEET_BLOCK);
        shapeless(helper, ModItems.GOLD_PLATE, 8, ModBlocks.GOLD_SHEET_BLOCK);
        shapeless(helper, ModItems.STEEL_PLATE, 8, ModBlocks.STEEL_SHEET_BLOCK);
        shapeless(helper, ModItems.IRON_PLATE, 8, ModBlocks.IRON_SHEET_BLOCK);

        polished(helper, ModBlocks.SMOOTH_PURIFIED_QUARTZ_BLOCK, ModBlocks.PURIFIED_QUARTZ_BLOCK);
        polished(helper, ModBlocks.POLISHED_MOON_ROCK, ModBlocks.MOON_ROCK);

        helper.armor();
        helper.tools();
        helper.stoneCutting(ModBlocks.SMOOTH_BLACKSTONE, 1, () -> Items.BLACKSTONE);
    }

    private void sheetBlock(final RecipeGeneratorHelper helper, Supplier<? extends Block> block, TagKey<Item> material) {
        helper.shaped(block).pattern("SSS").pattern("S S").pattern("SSS").define('S', material).save();
    }

    private void storageBlock(final RecipeGeneratorHelper helper, Supplier<? extends Block> block, Supplier<? extends ItemLike> item) {
        helper.shaped(block).pattern("AAA").pattern("AAA").pattern("AAA").define('A', item.get()).save();
        helper.shapelessCrafting(item.get(), 9, block.get());
    }

    private void shapeless(final RecipeGeneratorHelper helper, Supplier<? extends ItemLike> result, int amount, Supplier<? extends ItemLike> from) {
        helper.shapelessCrafting(result.get(), amount, from.get());
    }

    @SafeVarargs
    private void shapelessCrafting(final RecipeGeneratorHelper helper, Supplier<? extends Item> result, TagKey<Item>... ingredients) {
        helper.shapelessCrafting(result.get(), 1, ingredients);
    }

    private void polished(final RecipeGeneratorHelper helper, Supplier<? extends Block> block, Supplier<? extends Block> material) {
        helper.shaped(block).pattern("AA").pattern("AA").define('A', material.get()).save();
        helper.stoneCutting(block, 1, material);
        //SingleItemRecipeBuilder.stonecutting(Ingredient.of(material.get()), block.get()).unlockedBy("tick", tick()).save(consumer, createRecipeId());
    }

    private void buildModdedRecipes(Consumer<FinishedRecipe> consumer) {
        alloy(consumer, ModItemTags.INGOTS_STEEL, 1, Tags.Items.DUSTS_REDSTONE, 3, ModItems.REDSTONE_STEEL_ALLOY, 1);
        alloy(consumer, ModItemTags.DUSTS_IRON, 1, DUSTS_REDSTONE, 3, ModItems.REDSTONE_METAL, 1);
        alloy(consumer, Tags.Items.INGOTS_IRON, 1, Tags.Items.DUSTS_REDSTONE, 3, ModItems.REDSTONE_METAL, 1);
        alloy(consumer, ModItemTags.DUSTS_COAL, 1, Tags.Items.INGOTS_IRON, 1, ModItems.STEEL_INGOT, 1);
        alloy(consumer, DUSTS_HEATED_COAL, 1, INGOTS_IRON, 1, ModItems.STEEL_INGOT, 1);

        metalPress(consumer, INGOTS_STEEL, PLATE_MOLD, ModItems.STEEL_PLATE);
        metalPress(consumer, INGOTS_STEEL, GEAR_MOLD, ModItems.STEEL_GEAR);
        metalPress(consumer, PLATES_STEEL, ROD_MOLD, ModItems.STEEL_ROD);

        metalPress(consumer, INGOTS_URANIUM, PLATE_MOLD, ModItems.URANIUM_PLATE);
        metalPress(consumer, INGOTS_URANIUM, GEAR_MOLD, ModItems.URANIUM_GEAR);
        metalPress(consumer, PLATES_URANIUM, ROD_MOLD, ModItems.URANIUM_ROD);

        metalPress(consumer, INGOTS_REDSTONE_METAL, PLATE_MOLD, ModItems.REDSTONE_METAL_PLATE);
        metalPress(consumer, PLATES_REDSTONE_METAL, ROD_MOLD, ModItems.REDSTONE_METAL_ROD);
        metalPress(consumer, INGOTS_REDSTONE_METAL, GEAR_MOLD, ModItems.REDSTONE_METAL_GEAR);

        metalPress(consumer, INGOTS_REDSTONE_STEEL_ALLOY, PLATE_MOLD, ModItems.REDSTONE_STEEL_ALLOY_PLATE);
        metalPress(consumer, PLATES_REDSTONE_STEEL_ALLOY, ROD_MOLD, ModItems.REDSTONE_STEEL_ALLOY_ROD);
        metalPress(consumer, INGOTS_REDSTONE_STEEL_ALLOY, GEAR_MOLD, ModItems.REDSTONE_STEEL_ALLOY_GEAR);

        metalPress(consumer, INGOTS_IRON, PLATE_MOLD, ModItems.IRON_PLATE);
        metalPress(consumer, PLATES_IRON, ROD_MOLD, ModItems.IRON_ROD);
        metalPress(consumer, INGOTS_IRON, GEAR_MOLD, ModItems.IRON_GEAR);

        metalPress(consumer, INGOTS_GOLD, GEAR_MOLD, ModItems.GOLD_GEAR);
        metalPress(consumer, INGOTS_GOLD, PLATE_MOLD, ModItems.GOLD_PLATE);
        metalPress(consumer, PLATES_GOLD, ROD_MOLD, ModItems.GOLD_ROD);

        metalPress(consumer, PLATES_COPPER, ROD_MOLD, ModItems.COPPER_ROD);
        metalPress(consumer, INGOTS_COPPER, PLATE_MOLD, ModItems.COPPER_PLATE);
        metalPress(consumer, INGOTS_COPPER, GEAR_MOLD, ModItems.COPPER_GEAR);

        TagToItemRecipeHelper provider = TagToItemRecipeHelper.create(consumer);

        provider.tag(DUSTS_COAL, DUSTS_HEATED_COAL, DUSTS_GOLD, DUSTS_REDSTONE_STEEL_ALLOY, DUSTS_URANIUM, DUSTS_STEEL, DUSTS_REDSTONE_METAL, DUSTS_IRON, DUSTS_COPPER);
        provider.tag(GENERAL_GEARS);
        provider.tag(GENERAL_RODS);
        provider.tag(GENERAL_SHEETMETALS);
        provider.tag(GENERAL_STORAGE_BLOCKS);

        provider.generateRecipe(this::translateTag);
    }

    private void translateTag(Consumer<FinishedRecipe> consumer, TagKey<Item> tag) {
        TagTranslatingRecipeBuilder builder = new TagTranslatingRecipeBuilder(tag);
        builder.save(consumer, "tag_translating/" + RecipeGeneratorHelper.extractTag(tag));
    }

    private void metalPress(Consumer<FinishedRecipe> consumer, TagKey<Item> ingredient, TagKey<Item> moldTag, Supplier<? extends Item> result) {
        MetalPressRecipeBuilder builder = MetalPressRecipeBuilder.press(ingredient, moldTag, result.get());
        RecipeGeneratorHelper.saveGlobal(consumer, builder, "metal_pressing", RecipeGeneratorHelper.getId(result.get()));
    }

    private void alloy(Consumer<FinishedRecipe> consumer, TagKey<Item> item1, int count1, TagKey<Item> item2, int count2, Supplier<? extends ItemLike> result, int amount) {
        var builder = AlloySmeltingRecipeBuilder.build(item1, count1, item2, count2, result.get(), amount);
        RecipeGeneratorHelper.saveGlobal(consumer, builder, "alloying", RecipeGeneratorHelper.getId(result.get()));
    }

    @SafeVarargs
    private List<String> getInputNames(TagKey<Item>... tags) {
        return Arrays.stream(tags).map(RecipeGeneratorHelper::extractTag).collect(Collectors.toList());
    }

    private void buildSlab(RecipeGeneratorHelper helper, Supplier<SlabBlock> slabBlockSupplier, Supplier<Block> materialSupplier) {
        helper.buildSlab(slabBlockSupplier, materialSupplier);
        helper.stoneCutting(slabBlockSupplier, 2, materialSupplier);
    }

    private void buildSmeltingRecipes(final RecipeGeneratorHelper helper) {
        this.oreSmelting(helper, ModBlocks.DEEPSLATE_URANIUM_ORE, ModItems.URANIUM_INGOT);
        this.oreSmelting(helper, ModBlocks.MOON_GOLD_ORE, Items.GOLD_INGOT);
        this.oreSmelting(helper, ModBlocks.MOON_IRON_ORE, Items.IRON_INGOT);
        this.oreSmelting(helper, ModBlocks.MOON_QUARTZ_ORE, ModItems.PURIFIED_QUARTZ);
        this.oreSmelting(helper, ModBlocks.MOON_URANIUM_ORE, ModItems.URANIUM_INGOT);
        this.oreSmelting(helper, URANIUM_RAW_MATERIAL, ModItems.URANIUM_INGOT);
        this.rawOreSmelting(helper, RAW_URANIUM_STORAGE_BLOCK, ModBlocks.URANIUM_BLOCK, 200 * 9);
        this.dustSmelting(helper);
    }

    private void dustSmelting(final RecipeGeneratorHelper helper) {
        GENERAL_DUSTS.getProcessMap().forEach((tag, item) -> this.oreSmelting(helper, tag, item));
    }

    private void rawOreSmelting(final RecipeGeneratorHelper helper, TagKey<Item> tag, Supplier<? extends ItemLike> result, int cookTime) {
        var smeltingRecipe = new RecipeGeneratorHelper.Smelting(helper.consumer, result.get(), tag, cookTime, true);
        smeltingRecipe.save();
    }

    private void oreSmelting(final RecipeGeneratorHelper helper, TagKey<Item> tag, Supplier<? extends ItemLike> sup) {
        helper.smelting(tag, sup.get(), true);
    }

    private void oreSmelting(final RecipeGeneratorHelper helper, Supplier<? extends ItemLike> ingredient, Supplier<? extends Item> resultSupplier) {
        this.oreSmelting(helper, ingredient.get(), resultSupplier.get());
    }

    private void oreSmelting(final RecipeGeneratorHelper helper, Supplier<? extends ItemLike> ingredient, Item result) {
        this.oreSmelting(helper, ingredient.get(), result);
    }

    private void oreSmelting(final RecipeGeneratorHelper helper, ItemLike ingredient, ItemLike result) {
        helper.smelting(ingredient, result, true);
        //SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), result, 1.0f, 200).unlockedBy("tick", tick()).save(consumer, createRecipeId());
        //SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredient), result, 1.0f, 100).unlockedBy("tick", tick()).save(consumer, createRecipeId());
    }

    private void buildStair(final RecipeGeneratorHelper helper, Supplier<? extends StairBlock> resultSupplier, Supplier<? extends Block> materialSupplier) {
        helper.shaped(resultSupplier.get(), 6).pattern("S  ").pattern("SS ").pattern("SSS").define('S', materialSupplier.get()).save();
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(materialSupplier.get()), resultSupplier.get());
    }
}

