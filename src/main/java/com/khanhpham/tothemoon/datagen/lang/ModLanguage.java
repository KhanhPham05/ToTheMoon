package com.khanhpham.tothemoon.datagen.lang;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.datagen.advancement.AdvancementComponent;
import com.khanhpham.tothemoon.datagen.sounds.ModSoundsProvider;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.text.TextUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.function.Supplier;

public class ModLanguage extends LanguageProvider {
    //JEI
    public static final TranslatableComponent METAL_PRESS_RECIPE_CATEGORY = create("jei", "metal_press_category");
    public static final TranslatableComponent ALLOY_SMELTER_RECIPE_CATEGORY = create("jei", "alloy_smelter_category");
    public static final TranslatableComponent MANUAL_METAL_PRESSING_CATEGORY = create("jei", "manual_metal_pressing");

    //GUI
    public static final TranslatableComponent MACHINE_UPGRADE_LABELS = create("gui", "insert_upgrades");
    public static final TranslatableComponent NETHER_BRICK_FURNACE_CONTROLLER = create("gui", "nether_brick_furnace");

    //PATCHOULI
    public static final TranslatableComponent BOOK_NAME = create("book", "header");
    public static final TranslatableComponent BOOK_LANDING = create("book", "header.landing_text");
    public static final TranslatableComponent THE_BASICS = create("book", "the_basics");
    public static final TranslatableComponent THE_BASICS_DESCRIPTION = create("book", "the_basics.description");
    public static final TranslatableComponent MANUAL_CRUSHING = create("book", "manual_crushing");
    public static final TranslatableComponent MANUAL_CRUSHING_PAGE_1 = create("book", "manual_crushing.page_one");
    public static final TranslatableComponent MANUAL_CRUSHING_CRAFTING_PAGE = create("book", "manual_crushing.crafting");
    public static final TranslatableComponent MANUAL_CRUSHING_CRAFTING_TITLE = create("book", "manual_crushing.crafting.title");

    public static final TranslatableComponent MAKING_STEEL = create("book", "making_steel");
    public static final TranslatableComponent MAKING_STEEL_PAGE_ONE = create("book", "making_steel.page_one");
    //ADVANCEMENTS
    public static final TranslatableComponent ROOT = create("advancement", "root");
    public static final TranslatableComponent ROOT_DESCRIPTION = create("advancement", "root.description");
    private static final AdvancementComponent ADVANCEMENT_COMPONENT = new AdvancementComponent();
    public static final TranslatableComponent HEAVY_CRUSHING = createAdvancement("heavy_crushing", "Heavy Crushing", "Crush some ores and coals into dusts");
    public static final TranslatableComponent A_HEATED_TOPIC = createAdvancement("a_heated_topic", "A Heated Topic", "Craft a Nether Brick Furnace Controller");
    public static final TranslatableComponent HIGH_HEAT_SMELTING = createAdvancement("high_heat_smelting", "High Heat Smelting", "Construct A Nether Brick Furnace, see Guide Book for more information");
    public static final TranslatableComponent GETTING_A_TRUE_UPGRADE = createAdvancement("getting_a_true_upgrade", "Getting A True Upgrade", "Obtain Steel Ingot");
    public static final TranslatableComponent COVER_ME_WITH_CARBONIZED_IRON = createAdvancement("cover_me_with_carbonized_iron", "Cover Me With Carbonized Carbon", "Wear a full set of Steel Armor with all the steel tools");

    public ModLanguage(DataGenerator gen) {
        super(gen, Names.MOD_ID, "en_us");
    }

    public static TranslatableComponent create(String pre, String suf) {
        return new TranslatableComponent(pre + '.' + Names.MOD_ID + '.' + suf);
    }

    public static TranslatableComponent createAdvancement(String suf, String title, String description) {
        final String component = TextUtils.translateFormat("advancement", suf);
        return ADVANCEMENT_COMPONENT.add(component, title, description);
    }

    @Override
    protected void addTranslations() {
        ModBlocks.BLOCK_DEFERRED_REGISTER.getEntries().forEach(this::addBlocks);
        ModItems.ITEM_DEFERRED_REGISTER.getEntries().forEach(this::addItems);

        //GUI
        add("gui.tothemoon.alloy_smelter", "Alloy Smelter");
        add("gui.tothemoon.metal_press", "Metal Press");
        add("tooltip.tothemoon.energy", "Energy %s %s / %s");
        add("tooltip.tothemoon.fluid_fuel_tank", "Fuel : %s / %s");
        //JEI
        add(METAL_PRESS_RECIPE_CATEGORY, "Metal Pressing");
        add(ALLOY_SMELTER_RECIPE_CATEGORY, "Alloy Smelting");
        add(MANUAL_METAL_PRESSING_CATEGORY, "Manual Metal Pressing");
        add(MACHINE_UPGRADE_LABELS, "Upgrades");
        add(NETHER_BRICK_FURNACE_CONTROLLER, "Nether Bricks Furnace");

        //sound
        ModSoundsProvider.soundLanguages.forEach(lang -> lang.addTranslation(this));


        //ADVANCEMENT
        add(ROOT_DESCRIPTION, "Welcome To The Moon, this advancement will show you al the things that are in this mod");
        add(ROOT, "TTM Project");
        ADVANCEMENT_COMPONENT.startTranslating(this);

        //PATCHOULI
        add(BOOK_NAME, "TTM Guide Book");
        add(BOOK_LANDING, "This guide book will show you all the features that are in this mod.");
        add(THE_BASICS, "The Basics");
        add(THE_BASICS_DESCRIPTION, "This category will show you all $(o)the basics $()as the first steps in the mod.");
        add(MANUAL_CRUSHING, "Manual Crushing");
        add(MANUAL_CRUSHING_PAGE_1, "Vanilla Minecraft's anvil is used to smash the raw ores and coals in to dusts (this will work for all the mods such as Mekanism or Immersive Engineering, and of course, this mod) $(br2) This method of crushing will give you 15% of getting an extra dust");
        add(MANUAL_CRUSHING_CRAFTING_PAGE, "The Crafting recipe of Anvil is shown up here.");
        add(MANUAL_CRUSHING_CRAFTING_TITLE, "Crafting Anvil");
        add(MAKING_STEEL, "Making Steel");
    }

    private void add(TranslatableComponent component, String trans) {
        super.add(component.getKey(), trans);
    }

    private void addBlocks(Supplier<Block> supplier) {
        Block block = supplier.get();
        super.add(block, this.convertToTranslatedText(block));
    }

    private void addItems(Supplier<Item> supplier) {
        Item item = supplier.get();
        super.add(item, this.convertToTranslatedText(item));
    }

    private <T extends ForgeRegistryEntry<T>> String convertToTranslatedText(T entry) {
        StringBuilder buffer = new StringBuilder();
        String id = entry.getRegistryName().getPath();
        buffer.append(Character.toUpperCase(id.charAt(0)));

        for (int i = 1; i < id.length(); i++) {
            char c = id.charAt(i);

            if (c == '_') {
                buffer.append(' ');
            } else if (id.charAt(i - 1) == ' ' || id.charAt(i - 1) == '_') {
                buffer.append(Character.toUpperCase(id.charAt(i)));
            } else {
                buffer.append(c);
            }
        }

        return buffer.toString();
    }
}
