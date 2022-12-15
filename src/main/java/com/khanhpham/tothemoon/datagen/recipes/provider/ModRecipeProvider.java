package com.khanhpham.tothemoon.datagen.recipes.provider;

import com.khanhpham.tothemoon.core.blocks.DecorationBlocks;
import com.khanhpham.tothemoon.core.items.CraftingMaterial;
import com.khanhpham.tothemoon.core.items.HammerItem;
import com.khanhpham.tothemoon.datagen.recipes.builders.*;
import com.khanhpham.tothemoon.datagen.recipes.elements.ShortenIngredient;
import com.khanhpham.tothemoon.datagen.recipes.elements.ShortenIngredientStack;
import com.khanhpham.tothemoon.datagen.tags.ModItemTags;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
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
import net.minecraft.world.level.block.Blocks;
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


    public static PlayerTrigger.TriggerInstance tick() {
        return PlayerTrigger.TriggerInstance.located(LocationPredicate.ANY);
    }

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        CraftingMaterial.ALL_MATERIALS.forEach(material -> material.generateRecipes(consumer));
        final RecipeGeneratorHelper helper = new RecipeGeneratorHelper(consumer);
        buildModdedRecipes(consumer);
        buildVanillaRecipes(consumer, helper);
        buildRecipeCompat(consumer);
    }

    private void buildRecipeCompat(Consumer<FinishedRecipe> consumer) {
        new MekanismCompatProvider(consumer).run();
    }

    private void buildVanillaRecipes(Consumer<FinishedRecipe> consumer, final RecipeGeneratorHelper helper) {
        buildStair(helper, ModBlocks.MOON_ROCK_STAIR, ModBlocks.MOON_ROCK);
        buildStair(helper, ModBlocks.MOON_ROCK_BRICK_STAIR, ModBlocks.MOON_ROCK_BRICK);
        buildStair(helper, ModBlocks.POLISHED_MOON_ROCK_STAIR, ModBlocks.POLISHED_MOON_ROCK);
        buildStair(helper, ModBlocks.COBBLED_MOON_ROCK_STAIR, ModBlocks.COBBLED_MOON_ROCK);
        buildSlab(helper, ModBlocks.COBBLED_MOON_ROCK_SLAB, ModBlocks.COBBLED_MOON_ROCK);
        buildSlab(helper, ModBlocks.MOON_ROCK_SLAB, ModBlocks.MOON_ROCK);
        buildSlab(helper, ModBlocks.MOON_ROCK_BRICK_SLAB, ModBlocks.MOON_ROCK_BRICK);
        buildSlab(helper, ModBlocks.POLISHED_MOON_ROCK_SLAB, ModBlocks.POLISHED_MOON_ROCK);

        shapelessCrafting(helper, ModItems.STEEL_MATERIAL::getDust, DUSTS_HEATED_COAL, DUSTS_HEATED_COAL, DUSTS_HEATED_COAL, DUSTS_IRON);
        helper.shaped(ModItems.COPPER_MATERIAL::getGear).pattern(" C ").pattern("C C").pattern(" C ").define('C', INGOTS_COPPER).save();

        helper.shaped(ModItems.CPU_CHIP).pattern("RRR").pattern("DGD").pattern("SSS").define('R', PLATES_REDSTONE_METAL).define('D', DUSTS_REDSTONE).define('G', DUSTS_GOLD).define('S', PLATES_STEEL).save();
        helper.shaped(ModItems.CIRCUIT_BOARD).pattern("UUU").pattern("RCR").pattern("GGG").define('U', PLATES_URANIUM).define('R', DUSTS_REDSTONE_STEEL_ALLOY).define('G', PLATES_GOLD).define('C', ModItems.CPU_CHIP.get()).save();
        helper.shaped(ModBlocks.MOON_ROCK_BARREL).pattern("MMM").pattern("MCM").pattern("MMM").define('M', ModItemTags.MOON_ROCKS).define('C', CHESTS_WOODEN).save();

        helper.shaped(ModItems.URANIUM_ARMOR_PLATING).pattern("PS").pattern("SP").define('P', ModItems.URANIUM_MATERIAL.getPlateTag()).define('S', ModItems.STEEL_MATERIAL.getPlateTag()).save();
        helper.shaped(ModItems.URANIUM_ARMOR_PLATING).pattern("SP").pattern("PS").define('P', ModItems.URANIUM_MATERIAL.getPlateTag()).define('S', ModItems.STEEL_MATERIAL.getPlateTag()).save("uranium_armor_plate_1");

        helper.shaped(ModBlocks.ANTI_PRESSURE_GLASS.get(), 4).pattern("SGS").pattern("GQG").pattern("SGS").define('S', DUSTS_STEEL).define('G', Tags.Items.GLASS).define('Q', GEMS_QUARTZ).save();
        helper.shaped(ModBlocks.METAL_PRESS).pattern("SSS").pattern("WFW").pattern("SBS").define('S', PLATES_STEEL).define('W', WIRES_STEEL).define('F', ModBlocks.COPPER_MACHINE_FRAME.get()).define('B', STORAGE_BLOCKS_IRON);

        helper.shaped(ModBlocks.WORKBENCH).pattern("PPH").pattern("WWW").pattern("F F").define('F', FENCES).define('P', Items.PAPER).define('W', ItemTags.PLANKS).define('H', ModItems.WOODEN_HAMMER.get()).save();

        helper.smelting(ModBlocks.PURIFIED_QUARTZ_BLOCK.get(), ModBlocks.SMOOTH_PURIFIED_QUARTZ_BLOCK.get(), false);
        helper.smelting(ModBlocks.METEORITE.get(), ModBlocks.POLISHED_METEORITE.get(), false);
        helper.smelting(ModBlocks.COBBLED_METEORITE.get(), ModBlocks.METEORITE.get(), false);

        //hammers
        this.hammer(helper, ModItems.WOODEN_HAMMER, ItemTags.PLANKS);
        this.hammer(helper, ModItems.STEEL_HAMMER, PLATES_STEEL);
        this.hammer(helper, ModItems.DIAMOND_HAMMER, GEMS_DIAMOND);
        UpgradeRecipeBuilder.smithing(Ingredient.of(ModItems.DIAMOND_HAMMER.get()), Ingredient.of(INGOTS_NETHERITE), ModItems.NETHERITE_HAMMER.get()).unlocks("unlock", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.DIAMOND_HAMMER.get())).save(consumer, ModUtils.modLoc("upgrading/netherite_hammer"));

        polished(helper, ModBlocks.POLISHED_METEORITE, ModBlocks.METEORITE);
        polished(helper, ModBlocks.POLISHED_MOON_ROCK, ModBlocks.MOON_ROCK);

        helper.tools();
        helper.stoneCutting(ModBlocks.SMOOTH_BLACKSTONE, 1, () -> Items.BLACKSTONE);
        helper.smelting(Items.BLACKSTONE, ModBlocks.SMOOTH_BLACKSTONE.get(), false);

        helper.shaped(ModBlocks.METEORITE_TILES).define('A', ModBlocks.METEORITE.get()).pattern("AA").pattern("AA");
        helper.shaped(ModItems.COPPER_MATERIAL::getGear).pattern(" P ").pattern("PIP").pattern(" P ").define('P', PLATES_COPPER).define('I', INGOTS_COPPER);
        helper.shaped(ModItems.IRON_MATERIAL::getGear).pattern(" P ").pattern("PIP").pattern(" P ").define('P', PLATES_IRON).define('I', INGOTS_IRON);
        this.oreSmelting(consumer, Ingredient.of(ModBlocks.MOON_QUARTZ_ORE.get()), ModItems.PURIFIED_QUARTZ.get(), "purified_quartz");

        helper.shapelessCrafting(ModItems.URANIUM_ARMOR_PLATING.get(), 2, ModItems.URANIUM_MATERIAL.getPlateTag(), ModItems.URANIUM_MATERIAL.getPlateTag(), ModItems.STEEL_MATERIAL.getPlateTag());
        helper.stoneCutting(ModBlocks.METEORITE_TILES, 1, ModBlocks.METEORITE);
        helper.stoneCutting(ModBlocks.POLISHED_METEORITE, 1, ModBlocks.METEORITE);

        DecorationBlocks.ALL_DECORATION_BLOCKS.forEach(d -> d.generateCraftingRecipes(consumer));
    }

    private void hammer(RecipeGeneratorHelper helper, Supplier<HammerItem> result, TagKey<Item> from) {
        helper.shaped(result).pattern(" F ").pattern(" SF").pattern("S  ").define('S', RODS_WOODEN).define('F', from).save();
    }

    private void storageBlock(final RecipeGeneratorHelper helper, Supplier<? extends Block> block, Supplier<? extends ItemLike> item) {
        helper.shaped(block).pattern("AAA").pattern("AAA").pattern("AAA").define('A', item.get()).save();
        helper.shapelessCrafting(item.get(), 9, block.get());
    }

    @SafeVarargs
    private void shapelessCrafting(final RecipeGeneratorHelper helper, Supplier<? extends Item> result, TagKey<Item>... ingredients) {
        helper.shapelessCrafting(result.get(), 1, ingredients);
    }

    private void polished(final RecipeGeneratorHelper helper, Supplier<? extends Block> block, Supplier<? extends Block> material) {
        helper.shaped(block).pattern("AA").pattern("AA").define('A', material.get()).save();
        helper.stoneCutting(block, 1, material);
    }

    private void buildModdedRecipes(Consumer<FinishedRecipe> consumer) {
        WorkbenchRecipeBuilder.craft(ModBlocks.DIAMOND_ENERGY_GENERATOR)
                .extra(ModBlocks.REDSTONE_MACHINE_FRAME)
                .pattern(
                        "DSSSD",
                        "DWWWD",
                        "DWBWD",
                        "DWWWD",
                        "DSSSD")
                .define('D', ModItems.DIAMOND_MATERIAL.getPlateTag())
                .define('S', ModItems.REDSTONE_STEEL_MATERIAL.getPlateTag())
                .define('B', Blocks.BLAST_FURNACE)
                .define('W', ModItems.REDSTONE_METAL_MATERIAL.getWireTag())
                .save(consumer);

        AlloySmeltingRecipeBuilder.build(
                new ShortenIngredientStack(ShortenIngredient.create().add(DUSTS_IRON).add(INGOTS_IRON), 1),
                new ShortenIngredientStack(ShortenIngredient.create().add(DUSTS_REDSTONE), 3),
                ModItems.REDSTONE_METAL_MATERIAL.getIngot(), 1
        ).save(consumer, "redstone_metal");

        AlloySmeltingRecipeBuilder.build(
                new ShortenIngredientStack(ShortenIngredient.create().add(DUSTS_HEATED_COAL).add(ItemTags.COALS).add(DUSTS_COAL), 1),
                new ShortenIngredientStack(ShortenIngredient.create().add(INGOTS_IRON).add(DUSTS_IRON), 1),
                ModItems.STEEL_MATERIAL.getIngot(), 1
        ).save(consumer, "steel_ingot");

        AlloySmeltingRecipeBuilder.build(
                ShortenIngredient.create().add(ModItems.STEEL_MATERIAL.getDustTag()).add(ModItems.STEEL_MATERIAL.getIngotTag()).stack(),
                ShortenIngredient.create().add(DUSTS_REDSTONE).stack(3),
                ModItems.REDSTONE_STEEL_MATERIAL.getIngot(), 1
        ).save(consumer, "redstone_steel");

        TagToItemRecipeHelper provider = TagToItemRecipeHelper.create(consumer);

        provider.tag(DUSTS_COAL, DUSTS_HEATED_COAL, DUSTS_GOLD, DUSTS_REDSTONE_STEEL_ALLOY, DUSTS_URANIUM, DUSTS_STEEL, DUSTS_REDSTONE_METAL, DUSTS_IRON, DUSTS_COPPER);
        provider.tag(GENERAL_GEARS);
        provider.tag(GENERAL_RODS);
        provider.tag(GENERAL_SHEETMETALS);
        provider.tag(GENERAL_STORAGE_BLOCKS);

        provider.generateRecipe(this::translateTag);

        OreProcessingBuilder.process(ModItems.IRON_MATERIAL.getDust(), ShortenIngredient.create().add(RAW_MATERIALS_IRON)).doubleChance(OreProcessingBuilder.DEFAULT_RAW_ORE_DOUBLE_CHANCE).save(consumer, "iron_from_raw");
        OreProcessingBuilder.process(ModItems.COPPER_MATERIAL.getDust(), ShortenIngredient.create().add(RAW_MATERIALS_COPPER)).doubleChance(OreProcessingBuilder.DEFAULT_RAW_ORE_DOUBLE_CHANCE).save(consumer, "copper_from_raw");
        OreProcessingBuilder.process(ModItems.GOLD_MATERIAL.getDust(), ShortenIngredient.create().add(RAW_MATERIALS_GOLD)).doubleChance(OreProcessingBuilder.DEFAULT_RAW_ORE_DOUBLE_CHANCE).save(consumer, "gold_from_raw");

        OreProcessingBuilder.process(ModItems.IRON_MATERIAL.getDust(), 2, ShortenIngredient.create().add(ORES_IRON)).doubleChance(OreProcessingBuilder.DEFAULT_ORE_DOUBLE_CHANCE).save(consumer, "iron_from_ore");
        OreProcessingBuilder.process(ModItems.COPPER_MATERIAL.getDust(), 2, ShortenIngredient.create().add(ORES_COPPER)).doubleChance(OreProcessingBuilder.DEFAULT_ORE_DOUBLE_CHANCE).save(consumer, "copper_from_ore");
        OreProcessingBuilder.process(ModItems.GOLD_MATERIAL.getDust(), 2, ShortenIngredient.create().add(ORES_GOLD)).doubleChance(OreProcessingBuilder.DEFAULT_ORE_DOUBLE_CHANCE).save(consumer, "gold_from_ore");

        OreProcessingBuilder.process(ModItems.COAL_DUST.get(), 2, ShortenIngredient.create().add(Items.COAL)).doubleChance(OreProcessingBuilder.DEFAULT_ORE_DOUBLE_CHANCE).save(consumer);
        OreProcessingBuilder.process(Items.REDSTONE, 10, ShortenIngredient.create().add(ORES_REDSTONE)).save(consumer);
        OreProcessingBuilder.process(Items.LAPIS_LAZULI, 12, ShortenIngredient.create().add(ORES_LAPIS)).save(consumer);
        OreProcessingBuilder.process(Items.QUARTZ, 4, ShortenIngredient.create().add(ORES_QUARTZ)).extraOutput(new ItemStack(ModItems.PURIFIED_QUARTZ.get(), 3), 10).save(consumer);
        OreProcessingBuilder.process(Items.NETHERITE_SCRAP, 1, ShortenIngredient.create().add(ORES_NETHERITE_SCRAP)).doubleChance(40).save(consumer);

    }

    private void translateTag(Consumer<FinishedRecipe> consumer, TagKey<Item> tag) {
        TagTranslatingRecipeBuilder builder = new TagTranslatingRecipeBuilder(tag);
        builder.save(consumer, "tag_translating/" + RecipeGeneratorHelper.extractTag(tag));
    }

    private void buildSlab(RecipeGeneratorHelper helper, Supplier<SlabBlock> slabBlockSupplier, Supplier<Block> materialSupplier) {
        helper.buildSlab(slabBlockSupplier, materialSupplier);
        helper.stoneCutting(slabBlockSupplier, 2, materialSupplier);
    }

    private void buildSmeltingRecipes(final RecipeGeneratorHelper helper) {
        Consumer<FinishedRecipe> consumer = helper.consumer();
        this.oreSmelting(consumer, ModUtils.multiTagsIngredient(ORES_URANIUM, URANIUM_RAW_MATERIAL, DUSTS_URANIUM), ModItems.URANIUM_MATERIAL.getIngot(), "uranium_ingot");
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

