package com.khanhpham.tothemoon.datagen.recipes.provider;

import com.khanhpham.tothemoon.core.items.HammerItem;
import com.khanhpham.tothemoon.datagen.recipes.builders.*;
import com.khanhpham.tothemoon.datagen.recipes.elements.ShortenIngredient;
import com.khanhpham.tothemoon.datagen.recipes.elements.ShortenIngredientStack;
import com.khanhpham.tothemoon.datagen.tags.ModItemTags;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.TickTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.khanhpham.tothemoon.datagen.tags.ModItemTags.*;
import static net.minecraftforge.common.Tags.Items.*;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }


    public static TickTrigger.TriggerInstance tick() {
        return new TickTrigger.TriggerInstance(EntityPredicate.Composite.ANY);
    }

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        final RecipeGeneratorHelper helper = new RecipeGeneratorHelper(consumer);
        buildSmeltingRecipes(helper);
        buildModdedRecipes(consumer);
        buildCraftingTableRecipes(consumer, helper);
        buildRecipeCompat(consumer);
    }

    private void buildRecipeCompat(Consumer<FinishedRecipe> consumer) {
        new MekanismCompatProvider(consumer).run();
    }

    private void buildCraftingTableRecipes(Consumer<FinishedRecipe> consumer, final RecipeGeneratorHelper helper) {
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

        //helper.shaped(ModBlocks.COPPER_ENERGY_GENERATOR).pattern("SGS").pattern("WFW").pattern("SBS").define('S', ModItemTags.PLATES_STEEL).define('G', ModItemTags.GEARS_COPPER).define('W', WIRES_REDSTONE_METAL).define('F', ModBlocks.REDSTONE_MACHINE_FRAME.get()).define('B', Blocks.BLAST_FURNACE).save();
        //helper.shaped(ModBlocks.IRON_ENERGY_GENERATOR).pattern("SPS").pattern("WMW").pattern("SGS").define('S', ModItemTags.PLATES_STEEL).define('P', PLATES_IRON).define('W', WIRES_REDSTONE_METAL).define('M', ModBlocks.COPPER_ENERGY_GENERATOR.get()).define('G', ModItemTags.GEARS_STEEL).save();
        //helper.shaped(ModBlocks.GOLD_ENERGY_GENERATOR).pattern("SPS").pattern("WMW").pattern("SGS").define('S', ModItemTags.PLATES_STEEL).define('P', PLATES_GOLD).define('W', WIRES_REDSTONE_STEEL_ALLOY).define('M', ModBlocks.IRON_ENERGY_GENERATOR.get()).define('G', ModItemTags.GEARS_GOLD).save();
        //helper.shaped(ModBlocks.NETHER_BRICK_FURNACE_CONTROLLER).pattern("SSS").pattern("BFB").pattern("BAB").define('S', ModBlocks.SMOOTH_BLACKSTONE.get()).define('F', Items.FURNACE).define('B', Items.NETHER_BRICKS).define('A', Items.SOUL_SOIL).save();
        helper.shaped(ModBlocks.WORKBENCH).pattern("PPH").pattern("WWW").pattern("F F").define('F', FENCES).define('P', Items.PAPER).define('W', ItemTags.PLANKS).define('H', ModItems.WOODEN_HAMMER.get()).save();

        //hammers
        this.hammer(helper, ModItems.WOODEN_HAMMER, ItemTags.PLANKS);
        this.hammer(helper, ModItems.STEEL_HAMMER, PLATES_STEEL);
        this.hammer(helper, ModItems.DIAMOND_HAMMER, GEMS_DIAMOND);
        UpgradeRecipeBuilder.smithing(Ingredient.of(ModItems.DIAMOND_HAMMER.get()), Ingredient.of(INGOTS_NETHERITE), ModItems.NETHERITE_HAMMER.get()).unlocks("unlock", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.DIAMOND_HAMMER.get())).save(consumer, ModUtils.modLoc("upgrading/netherite_hammer"));

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
        helper.stoneCutting(ModBlocks.POLISHED_MOON_ROCK, 1, ModBlocks.MOON_ROCK);
        helper.stoneCutting(ModBlocks.SMOOTH_BLACKSTONE, 1, () -> Items.BLACKSTONE);
        helper.smelting(Items.BLACKSTONE, ModBlocks.SMOOTH_BLACKSTONE.get(), false);

        helper.shaped(ModItems.COPPER_GEAR).pattern(" P ").pattern("PIP").pattern(" P ").define('P', PLATES_COPPER).define('I', INGOTS_COPPER);
        helper.shaped(ModItems.IRON_GEAR).pattern(" P ").pattern("PIP").pattern(" P ").define('P', PLATES_IRON).define('I', INGOTS_IRON);
    }

    private void hammer(RecipeGeneratorHelper helper, Supplier<HammerItem> result, TagKey<Item> from) {
        helper.shaped(result).pattern(" F ").pattern(" SF").pattern("S  ").define('S', RODS_WOODEN).define('F', from).save();
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
        AlloySmeltingRecipeBuilder.build(
                new ShortenIngredientStack(ShortenIngredient.create().add(DUSTS_IRON).add(INGOTS_IRON), 1),
                new ShortenIngredientStack(ShortenIngredient.create().add(DUSTS_REDSTONE), 3),
                ModItems.REDSTONE_METAL.get(), 1
        ).save(consumer, "redstone_metal");

        AlloySmeltingRecipeBuilder.build(
                new ShortenIngredientStack(ShortenIngredient.create().add(DUSTS_HEATED_COAL).add(ItemTags.COALS).add(DUSTS_COAL), 1),
                new ShortenIngredientStack(ShortenIngredient.create().add(INGOTS_IRON).add(DUSTS_IRON), 1),
                ModItems.STEEL_INGOT.get(), 1
        ).save(consumer, "steel_ingot");


        metalPress(consumer, INGOTS_STEEL, PLATE_MOLD, ModItems.STEEL_PLATE);
        metalPress(consumer, INGOTS_STEEL, GEAR_MOLD, ModItems.STEEL_GEAR);
        metalPress(consumer, INGOTS_STEEL, ROD_MOLD, new ItemStack(ModItems.STEEL_ROD.get(), 2));

        metalPress(consumer, INGOTS_URANIUM, PLATE_MOLD, ModItems.URANIUM_PLATE);
        metalPress(consumer, INGOTS_URANIUM, GEAR_MOLD, ModItems.URANIUM_GEAR);
        metalPress(consumer, INGOTS_URANIUM, ROD_MOLD, new ItemStack(ModItems.URANIUM_ROD.get(), 2));


        metalPress(consumer, INGOTS_REDSTONE_METAL, PLATE_MOLD, ModItems.REDSTONE_METAL_PLATE);
        metalPress(consumer, INGOTS_REDSTONE_METAL, GEAR_MOLD, ModItems.REDSTONE_METAL_GEAR);
        metalPress(consumer, INGOTS_REDSTONE_METAL, ROD_MOLD, new ItemStack(ModItems.REDSTONE_METAL_ROD.get(), 2));


        metalPress(consumer, INGOTS_REDSTONE_STEEL_ALLOY, PLATE_MOLD, ModItems.REDSTONE_STEEL_ALLOY_PLATE);
        metalPress(consumer, INGOTS_REDSTONE_STEEL_ALLOY, GEAR_MOLD, ModItems.REDSTONE_STEEL_ALLOY_GEAR);
        metalPress(consumer, INGOTS_REDSTONE_STEEL_ALLOY, ROD_MOLD, new ItemStack(ModItems.REDSTONE_STEEL_ALLOY_ROD.get(), 2));


        metalPress(consumer, INGOTS_IRON, PLATE_MOLD, ModItems.IRON_PLATE);
        metalPress(consumer, INGOTS_IRON, GEAR_MOLD, ModItems.IRON_GEAR);
        metalPress(consumer, INGOTS_IRON, ROD_MOLD, new ItemStack(ModItems.IRON_ROD.get(), 2));


        metalPress(consumer, INGOTS_GOLD, GEAR_MOLD, ModItems.GOLD_GEAR);
        metalPress(consumer, INGOTS_GOLD, PLATE_MOLD, ModItems.GOLD_PLATE);
        metalPress(consumer, INGOTS_GOLD, ROD_MOLD, new ItemStack(ModItems.GOLD_ROD.get(), 2));

        metalPress(consumer, INGOTS_COPPER, PLATE_MOLD, ModItems.COPPER_PLATE);
        metalPress(consumer, INGOTS_COPPER, GEAR_MOLD, ModItems.COPPER_GEAR);
        metalPress(consumer, INGOTS_COPPER, ROD_MOLD, new ItemStack(ModItems.COPPER_ROD.get(), 2));


        TagToItemRecipeHelper provider = TagToItemRecipeHelper.create(consumer);

        provider.tag(DUSTS_COAL, DUSTS_HEATED_COAL, DUSTS_GOLD, DUSTS_REDSTONE_STEEL_ALLOY, DUSTS_URANIUM, DUSTS_STEEL, DUSTS_REDSTONE_METAL, DUSTS_IRON, DUSTS_COPPER);
        provider.tag(GENERAL_GEARS);
        provider.tag(GENERAL_RODS);
        provider.tag(GENERAL_SHEETMETALS);
        provider.tag(GENERAL_STORAGE_BLOCKS);

        provider.generateRecipe(this::translateTag);

        OreProcessingBuilder.process(ModItems.IRON_DUST.get(), ShortenIngredient.create().add(RAW_MATERIALS_IRON)).doubleChance(OreProcessingBuilder.DEFAULT_RAW_ORE_DOUBLE_CHANCE).save(consumer, "iron_from_raw");
        OreProcessingBuilder.process(ModItems.COPPER_DUST.get(), ShortenIngredient.create().add(RAW_MATERIALS_COPPER)).doubleChance(OreProcessingBuilder.DEFAULT_RAW_ORE_DOUBLE_CHANCE).save(consumer, "copper_from_raw");
        OreProcessingBuilder.process(ModItems.GOLD_DUST.get(), ShortenIngredient.create().add(RAW_MATERIALS_GOLD)).doubleChance(OreProcessingBuilder.DEFAULT_RAW_ORE_DOUBLE_CHANCE).save(consumer, "gold_from_raw");

        OreProcessingBuilder.process(ModItems.IRON_DUST.get(), 2, ShortenIngredient.create().add(ORES_IRON)).doubleChance(OreProcessingBuilder.DEFAULT_ORE_DOUBLE_CHANCE).save(consumer, "iron_from_ore");
        OreProcessingBuilder.process(ModItems.COPPER_DUST.get(), 2, ShortenIngredient.create().add(ORES_COPPER)).doubleChance(OreProcessingBuilder.DEFAULT_ORE_DOUBLE_CHANCE).save(consumer, "copper_from_ore");
        OreProcessingBuilder.process(ModItems.GOLD_DUST.get(), 2, ShortenIngredient.create().add(ORES_GOLD)).doubleChance(OreProcessingBuilder.DEFAULT_ORE_DOUBLE_CHANCE).save(consumer, "gold_from_ore");

        OreProcessingBuilder.process(ModItems.COAL_DUST.get(), 2, ShortenIngredient.create().add(Items.COAL)).doubleChance(OreProcessingBuilder.DEFAULT_ORE_DOUBLE_CHANCE).save(consumer);
        OreProcessingBuilder.process(Items.REDSTONE, 10, ShortenIngredient.create().add(ORES_REDSTONE)).save(consumer);
        OreProcessingBuilder.process(Items.LAPIS_LAZULI, 12, ShortenIngredient.create().add(ORES_LAPIS)).save(consumer);
        OreProcessingBuilder.process(Items.QUARTZ, 4, ShortenIngredient.create().add(ORES_QUARTZ)).extraOutput(new ItemStack(ModItems.PURIFIED_QUARTZ.get(), 3), 10).save(consumer);
        OreProcessingBuilder.process(Items.NETHERITE_SCRAP, 1, ShortenIngredient.create().add(ORES_NETHERITE_SCRAP)).doubleChance(40).save(consumer);

     }

    private void metalPress(Consumer<FinishedRecipe> consumer, TagKey<Item> ingredient, TagKey<Item> mold, ItemStack result) {
        MetalPressRecipeBuilder builder = MetalPressRecipeBuilder.press(ingredient, mold, result);
        RecipeGeneratorHelper.saveGlobal(consumer, builder, "metal_pressing", RecipeGeneratorHelper.getId(result.getItem()));
    }

    private void translateTag(Consumer<FinishedRecipe> consumer, TagKey<Item> tag) {
        TagTranslatingRecipeBuilder builder = new TagTranslatingRecipeBuilder(tag);
        builder.save(consumer, "tag_translating/" + RecipeGeneratorHelper.extractTag(tag));
    }

    private void metalPress(Consumer<FinishedRecipe> consumer, TagKey<Item> ingredient, TagKey<Item> moldTag, Supplier<? extends Item> result) {
        MetalPressRecipeBuilder builder = MetalPressRecipeBuilder.press(ingredient, moldTag, new ItemStack(result.get()));
        RecipeGeneratorHelper.saveGlobal(consumer, builder, "metal_pressing", RecipeGeneratorHelper.getId(result.get()));
    }

    private void buildSlab(RecipeGeneratorHelper helper, Supplier<SlabBlock> slabBlockSupplier, Supplier<Block> materialSupplier) {
        helper.buildSlab(slabBlockSupplier, materialSupplier);
        helper.stoneCutting(slabBlockSupplier, 2, materialSupplier);
    }

    private void buildSmeltingRecipes(final RecipeGeneratorHelper helper) {
        Consumer<FinishedRecipe> consumer = helper.consumer();
        this.oreSmelting(consumer, ModUtils.multiTagsIngredient(ORES_URANIUM, URANIUM_RAW_MATERIAL, DUSTS_URANIUM), ModItems.URANIUM_INGOT.get(), "uranium_ingot");
        this.oreSmelting(consumer, Ingredient.of(ModBlocks.MOON_QUARTZ_ORE.get()), ModItems.PURIFIED_QUARTZ.get(), "purified_quartz");
        this.oreSmelting(consumer, Ingredient.of(DUSTS_IRON), Items.IRON_INGOT, "iron_from_dust");
        this.oreSmelting(consumer, Ingredient.of(DUSTS_GOLD), Items.GOLD_INGOT, "gold_from_dust");
    }

    private void oreSmelting(Consumer<FinishedRecipe> finishedRecipe, Ingredient ingredient, ItemLike result, String recipeId) {
        SimpleCookingRecipeBuilder.smelting(ingredient, result, 1.0f, 200).unlockedBy("tick", tick()).save(finishedRecipe, "tothemoon:smelting/" + recipeId);
        SimpleCookingRecipeBuilder.blasting(ingredient, result, 1.0f, 100).unlockedBy("tick", tick()).save(finishedRecipe, "tothemoon:blasting/" + recipeId);

    }

    private void buildStair(final RecipeGeneratorHelper helper, Supplier<? extends StairBlock> resultSupplier, Supplier<? extends Block> materialSupplier) {
        helper.shaped(resultSupplier.get(), 6).pattern("S  ").pattern("SS ").pattern("SSS").define('S', materialSupplier.get()).save();
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(materialSupplier.get()), resultSupplier.get());
    }
}

