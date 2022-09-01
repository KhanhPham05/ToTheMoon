package com.khanhpham.tothemoon.datagen.lang;

import com.google.common.base.Preconditions;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.datagen.advancement.AdvancementComponent;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.helpers.RegistryEntries;
import com.khanhpham.tothemoon.utils.text.TextUtils;
import com.khanhpham.tothemoon.worldgen.OreGenValues;
import com.khanhpham.tothemoon.worldgen.OreVeins;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@SuppressWarnings("unused")
public class ModLanguage extends LanguageProvider {
    public static final MutableComponent TAG_TRANSLATOR = create("info", "tag_translator");
    //UTILS
    public static final MutableComponent CAP_UNKNOWN = create("command", "cap_unknown");
    public static final MutableComponent CAP_FOUND = create("command", "cap_found");
    //JEI
    public static final MutableComponent JEI_METAL_PRESS = create("jei", "metal_press_category");
    public static final MutableComponent JEI_ALLOY_SMELTING = create("jei", "alloy_smelter_category");
    @Deprecated
    public static final MutableComponent MANUAL_METAL_PRESSING_CATEGORY = create("jei", "manual_metal_pressing");
    public static final MutableComponent JEI_WORKBENCH_CRAFTING = create("jei", "workbench_crafting");
    public static final MutableComponent JEI_METAL_PRESS_CONSUME_MOLD = create("jei", "recipe_consume_mold");

    //GUI
    public static final MutableComponent MACHINE_UPGRADE_LABELS = create("gui", "insert_upgrades");
    public static final MutableComponent NETHER_BRICK_FURNACE_CONTROLLER = create("gui", "nether_brick_furnace");
    public static final MutableComponent NEXT = create("container", "next");
    public static final MutableComponent PREV = create("container", "prev");
    public static final MutableComponent HOVER_TO_SHOW_TAG = create("button", "hover_show_tag");
    public static final MutableComponent NO_ITEM = create("button", "no_button");
    public static final MutableComponent JEI_HIGH_HEAT_SMELTING = create("jei", "high_heat_smelting");
    public static final MutableComponent MESSAGE_NBF_FORM_ERROR = create("message", "nbf_form_fail");
    public static final MutableComponent FILL_TANK = create("gui", "fill_tank");
    public static final MutableComponent EMPTY_TANK = create("gui", "empty_tank");
    //TOOLTIP
    public static final MutableComponent TANK_ONLY_SUPPORTS_LAVA = create("tooltip", "lava_support_only");
    public static final MutableComponent TANK_AMOUNT = create("tooltip", "amount");
    public static final MutableComponent ANVIL_DESCRIPTION = create("tooltip", "minecraft_anvil");
    //PATCHOULI
    public static final MutableComponent BOOK_NAME = create("book", "header");
    public static final MutableComponent BOOK_LANDING = create("book", "header.landing_text");
    public static final MutableComponent THE_BASICS = create("book", "the_basics");
    public static final MutableComponent THE_BASICS_DESCRIPTION = create("book", "the_basics.description");
    public static final MutableComponent MANUAL_CRUSHING = create("book", "manual_crushing");
    public static final MutableComponent MANUAL_CRUSHING_PAGE_1 = create("book", "manual_crushing.page_one");
    public static final MutableComponent MANUAL_CRUSHING_CRAFTING_PAGE = create("book", "manual_crushing.crafting");
    public static final MutableComponent MANUAL_CRUSHING_CRAFTING_TITLE = create("book", "manual_crushing.crafting.title");
    public static final MutableComponent MAKING_STEEL = create("book", "making_steel");
    public static final MutableComponent BENCH_WORKING_TITLE = create("book", "bench_working.title");
    public static final MutableComponent TEXT_HOW_TO_USE = create("book", "how_to_use");
    public static final MutableComponent BASIC_MATERIALS_CATEGORY = create("book", "category_basic_materials");
    public static final MutableComponent BASIC_MATERIAL_CATEGORY_DESCRIPTION = create("book", "category_basic_materials.description");
    //ADVANCEMENTS
    public static final MutableComponent ROOT = create("advancement", "root");
    public static final MutableComponent ROOT_DESCRIPTION = create("advancement", "root.description");
    public static final MutableComponent HIDDEN_ADVANCEMENT = create("advancement", "hidden");
    public static final MutableComponent ORE_PROCESSING = create("jei", "ore_processing");
    //ITEM / BLOCK /INVENTORY
    private static final AdvancementComponent ADVANCEMENT_COMPONENT = new AdvancementComponent();
    public static final MutableComponent HARDEN_FRAMED = createAdvancement("harden_framed", "Craft a higher tier of machine frame - Steel Frame.");
    public static final MutableComponent AUTOMATE_THE_FUEL = createAdvancement("automate_the_fuel", "Automate The Fuel", "Craft any type of Fluid Acceptor for Nether Brick Furnace");
    public static final MutableComponent HEAVY_CRUSHING = createAdvancement("heavy_crushing", "Heavy Crushing", "Crush some ores and coals into dusts");
    public static final MutableComponent A_HEATED_TOPIC = createAdvancement("a_heated_topic", "A Heated Topic", "Craft the Nether Brick Furnace Controller");
    public static final MutableComponent HIGH_HEAT_SMELTING = createAdvancement("high_heat_smelting", "High Heat Smelting", "Construct A Nether Brick Furnace, see Guide Book for more information");
    public static final MutableComponent GETTING_A_TRUE_UPGRADE = createAdvancement("getting_a_true_upgrade", "Getting A True Upgrade", "Obtain Steel Ingot");
    public static final MutableComponent COVER_ME_WITH_CARBONIZED_IRON = createAdvancement("cover_me_with_carbonized_iron", "Cover Me With Carbonized Carbon", "Wear a full set of Steel Armor with all the steel tools");
    public static final MutableComponent ENERGIZE_THE_FUEL = createAdvancement("energize_the_fuel", "Energize The Fuel", "Craft Copper Energy Generator");
    public static final MutableComponent BENCH_WORKING = createAdvancement("bench_working", "Benchworkin'", "Craft the Workbench");
    public static final MutableComponent FULLY_ENERGIZED = createAdvancement("fully_energized", "Fully Energized", "Craft and place down the Diamond Energy Generator");
    public static final MutableComponent RADIATION_PROTECTED = createAdvancement("radiation_protected", "Radiation Protected", "Obtain a full set of Uranium Armor.");
    public static final MutableComponent ISNT_THIS_STEEL_PICKAXE = createAdvancement("isnt_this_steel_pickaxe", "Isn't This Steel Pickaxe ?", "Craft Steel Pickaxe");
    public static final MutableComponent VERY_SERIOUS_DEDICATION = createAdvancement("very_serious_dedication", "A Very Serious Dedication", "You just broke the Netherite Hoe");
    public static final MutableComponent BURNING_ENERGY = createAdvancement("burning_energy", "Burning Energy", "Craft and use the Energy Smelter");
    public static final MutableComponent THIS_IS_FINE = createAdvancement("this_is_fine", "This is fine ! Too much energy from §o burnt fuel");
    public static final MutableComponent MACHINE_EXOSKELETON = createAdvancement("machine_exoskeleton", "Machine ExoSkeleton", "Craft a Copper Machine Frame.");
    public static final MutableComponent COMBINING_MATERIALS = createAdvancement("combining_materials", "Combining Materials", "Alloy Smelter allows you to combine materials together");
    public static final MutableComponent STORING_ENERGY = createAdvancement("storing_energy", "Craft and place down the Battery to store power !");
    private static final MutableComponent NO_TAG = create("button", "no_tag");
    private static final List<Block> ALL_BLOCKS;
    private static final List<Item> ALL_ITEMS;

    static {
        ALL_BLOCKS = ModBlocks.BLOCK_DEFERRED_REGISTER.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        //ALL_BLOCKS.addAll(NonDeferredBlocks.REGISTERED_BLOCKS);

        ALL_ITEMS = ModItems.ITEM_DEFERRED_REGISTER.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
    }

    public ModLanguage(DataGenerator gen) {
        super(gen, ToTheMoon.MOD_ID, "en_us");
    }

    public static MutableComponent create(String pre, String suf) {
        return TextUtils.translatable(pre, suf);
    }

    public static MutableComponent createAdvancement(String suf, String title, String description) {
        final String component = TextUtils.translateFormat("advancement", suf);
        return ADVANCEMENT_COMPONENT.add(component, title, description);
    }

    public static String convertToTranslatedText(@Nullable ResourceLocation location) {
        Preconditions.checkNotNull(location, "Location can not be null");
        String id = location.getPath();
        return convertToSpacedText(id);
    }


    private static String convertToSpacedText(String string) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(Character.toUpperCase(string.charAt(0)));

        for (int i = 1; i < string.length(); i++) {
            char c = string.charAt(i);

            if (c == '_') {
                buffer.append(' ');
            } else if (string.charAt(i - 1) == ' ' || string.charAt(i - 1) == '_') {
                buffer.append(Character.toUpperCase(string.charAt(i)));
            } else {
                buffer.append(c);
            }
        }

        return buffer.toString();
    }

    public static <T extends IForgeRegistry<?>> String getPureName(T object) {
        return convertToTranslatedText(object.getRegistryName());

    }

    private static MutableComponent createAdvancement(String key, String description) {
        return createAdvancement(key, convertToSpacedText(key), description);
    }

    @Override
    protected void addTranslations() {
        ALL_BLOCKS.forEach(this::addBlock);
        ALL_ITEMS.forEach(this::addItem);

        add(MESSAGE_NBF_FORM_ERROR, "Can not form Nether Brick Furnace");
        add(create("book", "title_crafting_smt"), "Crafting %s");

        // item - block - inventory
        add(TAG_TRANSLATOR, "A simple stonecutter-inspired block that convert items that has the same tag as items in this mod");

        //GUI - TOOLTIP
        add("gui.tothemoon.alloy_smelter", "Alloy Smelter");
        add("gui.tothemoon.metal_press", "Metal Press");
        add("tooltip.tothemoon.energy", "Energy %s %s / %s");
        add("tooltip.tothemoon.fluid_fuel_tank", "Fuel : %s / %s");
        add("tooltip.tothemoon.item_tank", "(%s): %s");
        add(TANK_ONLY_SUPPORTS_LAVA, "Can stores Lava only, more fluids will be added in future updates");
        add(NEXT, ">");
        add(PREV, "<");
        add(HOVER_TO_SHOW_TAG, "Show Tag");
        add(NO_TAG, "No Tag");
        add(NO_ITEM, "No Item");
        add(FILL_TANK, "Fill Tank");
        add(EMPTY_TANK, "Empty Tank");
        add(ANVIL_DESCRIPTION, "A heavy block used to repair tools, also a reliable way to smash the raw ores into dusts by using its weight");

        //JEI
        add(JEI_METAL_PRESS, "Metal Pressing");
        add(JEI_ALLOY_SMELTING, "Alloy Smelting");
        add(MACHINE_UPGRADE_LABELS, "Upgrades");
        add(NETHER_BRICK_FURNACE_CONTROLLER, "Nether Bricks Furnace");
        add(JEI_WORKBENCH_CRAFTING, "Workbench Crafting");
        add(JEI_HIGH_HEAT_SMELTING, "High Heat Smelting");
        add(JEI_METAL_PRESS_CONSUME_MOLD, "Consume Mold");
        add(ORE_PROCESSING, "Ore Processing");

        //sound
        //ModSoundsProvider.soundLanguages.forEach(lang -> lang.addTranslation(this));

        //ADVANCEMENT
        add(ROOT_DESCRIPTION, "Welcome To The Moon, this advancement will show you al the things that are in this mod");
        add(ROOT, "TTM Project");
        add(HIDDEN_ADVANCEMENT, "Hidden");
        ADVANCEMENT_COMPONENT.startTranslating(this);

        //PATCHOULI
        add(BOOK_NAME, "TTM Guide Book");
        add(BOOK_LANDING, "This guide book will show you all the features that are in this mod. $(br2) This mod is made possible by a single person who do 100% of the code, with the help of 2 texture makers");
        add(THE_BASICS, "The Basics");
        add(THE_BASICS_DESCRIPTION, "This category will show you all $(o)the basics$() as the first steps in the mod.");
        book("title.crafting_recipe", "Crafting Recipe");

        //PATCHOULI - THE BASICS
        add(MANUAL_CRUSHING, "Manual Crushing");
        add(MANUAL_CRUSHING_PAGE_1, "Vanilla Minecraft's anvil is used to smash the raw ores and coals in to dusts (this will work for all the mods such as Mekanism or Immersive Engineering, and of course, this mod) $(br2) This method of crushing will give you 15% of getting an extra dust");
        add(MANUAL_CRUSHING_CRAFTING_PAGE, "The Crafting recipe of Anvil is shown up here.");
        add(MANUAL_CRUSHING_CRAFTING_TITLE, "Crafting Anvil");

        add(MAKING_STEEL, "Making Steel");

        add(create("book", "feeling_the_heat_title"), "Feeling The Heat");
        add(create("book", "fth_page_1"), "Before you can make steel, you need to have some $(o)some$() pieces of $(l)heated coal dusts$() first. But before that, you need something so heat the $(l:tothemoon:manual_crushing)coal dust$().$(br2) So Nether Brick Furnace is your chose");
        add(create("book", "fth_craft_controller"), "First of all, you need to craft a Controller for this huge furnace. This list below shows the blocks that the furnace needs for construction $(li)x1 NetherBrick Furnace Controller $(li)x16 Nether Bricks $(li)x1 Blass Furnace $(li)x9 Smooth Blackstone");
        add(create("book", "fth_building_title"), "Building The Furnace");
        add(create("book", "fth_building_1"), "Once you have finished crafting all the required ingredients, it's time to start the construction of the Nether Bricks Furnace. $(t:Recipe Type: tothemoon:high_heat_smelting)(For mod/pack makers)$(). $(br2) This is a 3 x 3 x 3 multiblock. At the very bottom, place 9 blocks of nether bricks in a 3x3 square. $(br) Now decide which side the furnace is facing to and then place it down at the middle of the face of that side. Make sure it is facing directly towards the air. After that, place a Blast Furnace behind it, that should be the core/centre of the multiblock");
        add(create("book", "fth_building_2"), "after that, place the 7 remaining blocks with nether bricks. $(br) Finally, fill the last layer with Smooth Blackstone");
        add(create("book", "fth_using_multiblock"), "This special furnace is the key of the process, In order to produce Heated Coal Dust for steel, you need this furnace. However, to make this furnace function, you may need to have Blaze Powder as startup fuel and lava as a process maintenance, usually, the lava consumption is pretty low, at 3mB per a tick. After forming you can access the controller GUI to start making heated coal. The last thing you need is coal dust after smashing from normal coal by anvil");
        add(create("book", "fth_form_multiblock"), "In order to access the GUI and use it, you must right click on the controller for confirmation so other components like Fluid Acceptors can work as expected.");
        add(create("book", "fth_form_multiblock.title"), "Last Step");
        book("fth_multiblock_illustration", "I can not let you see the multiblock page right now be cause it might looks pretty ugly. SO in order for you to understand it easily, there are the illustration images that you can have a look at");
        add(TEXT_HOW_TO_USE, "How To Use");
        book("note", "Note");

        add(BENCH_WORKING_TITLE, "Benchworkin'");
        add(create("book", "bw_introduction"), "If you have every tried the Extended Crafting mod, so this must be very familiar to you. Workbench, however, has some slight differences. This is the key for the progression in TTM, Workbench is the ONLY way for you to craft the machine components as well as some essential stuff that the mod requires you to craft in Workbench. This block is a multi-part block just like the bed or door block. Workbench, on the other hand, will extends to the left and face towards you. The following image will show you exactly how it looks like. ");
        add(create("book", "bw_demonstration"), "In addition, the crafting grid of the workbench is a 5 x 5 grid with 2 additional slots for crafting");
        book("bw_crafting", "The following page describes how you can craft the workbench in Crafting Table. Please do note that the recipe would be changed in the future.");

        book("automate_the_fuel.title", "Automate The Fuel");
        book("crafting_acceptors.title", "Crafting Acceptors");
        book("atf.introduction", "$(o)Introduction :$() $(br)The Fluid Acceptors are essential to the automation of the fluid I/O for the furnace controller without having to open the screen. The following pages are the crafting recipes of it and how you can place it on the furnace. FYI, $(bold)not all$() acceptors are required as the image to make the furnace work.");
        book("atf.illustration.title", "Acceptors Placements");
        book("atf.illustration.note", "The type of acceptor must be corresponding to the layer of the multiblock, as shown on the image");

        //PATCHOULI - Basic Materials
        add(BASIC_MATERIAL_CATEGORY_DESCRIPTION, "A small but reliable category that shows you all the basic ores/metals which are important for your progress in this mod");
        add(BASIC_MATERIALS_CATEGORY, "Basic Materials");

        book("ore_info_title", "Ore Information");
        book("ore_info_1", "Ores are the most essential key for a process, therefore, you needs to find some materials by mining some ores. However, the question is that what and where are those ores can be found ? $(br2)This page will tell you all about this. $(o)Note: Not all of the information in this entry are visible, unless you enter the mod's exclusive dimension, those pageswill appears$()");
        bookOreInfo(OreVeins.URANIUM_OVERWORLD);

        //Utils
        add(CAP_UNKNOWN, "Capability unknown or not found");
        add(CAP_FOUND, "CAP FOUND !");

        add(create("gui", "blaze_fuel"), "Blaze Fuel: %s / %s");
    }

    private void add(MutableComponent component, String trans) {
        super.add(component.getString(), trans);
    }

    private void addBlock(Block block) {
        super.add(block, this.convertToTranslatedText(block));
    }

    private void addItem(Item item) {
        super.add(item, this.convertToTranslatedText(item));
    }

    private String convertToTranslatedText(ItemLike item) {
        return convertToTranslatedText(RegistryEntries.ITEM.getKey(item.asItem()));
    }

    private String book(String translateKey, String translation) {
        String key = "book.tothemoon." + translateKey;
        super.add(key, translation);
        return key;
    }

    @SuppressWarnings("SameParameterValue")
    private void bookOreInfo(OreVeins oreVein) {
        Block oreBlock = oreVein.getOreBlock();
        String oreName = RegistryEntries.BLOCK.getPath(oreBlock);
        String translation = "info_" + oreName;
        OreGenValues values = oreVein.getValues();
        ModUtils.log(this.book(translation,
                String.format("$(li)Ore : %s. $(li)Generate between : Y = %s to %s",
                        convertToTranslatedText(RegistryEntries.BLOCK.getKey(oreBlock)),
                        values.minWorldHeight(),
                        values.maxWorldHeight()
                )));
    }
}
