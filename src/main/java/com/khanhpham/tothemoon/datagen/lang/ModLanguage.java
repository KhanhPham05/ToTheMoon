package com.khanhpham.tothemoon.datagen.lang;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.datagen.advancement.AdvancementComponent;
import com.khanhpham.tothemoon.datagen.sounds.ModSoundsProvider;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.init.nondeferred.NonDeferredItems;
import com.khanhpham.tothemoon.utils.text.TextUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModLanguage extends LanguageProvider {
    //ITEM / BLOCK /INVENTORY
    public static final TranslatableComponent TAG_TRANSLATOR = create("info", "tag_translator");

    //UTILS
    public static final TranslatableComponent CAP_UNKNOWN = create("command", "cap_unknown");
    public static final TranslatableComponent CAP_FOUND = create("command", "cap_found");

    //JEI
    public static final TranslatableComponent JEI_METAL_PRESS = create("jei", "metal_press_category");
    public static final TranslatableComponent JEI_ALLOY_SMELTING = create("jei", "alloy_smelter_category");
    @Deprecated
    public static final TranslatableComponent MANUAL_METAL_PRESSING_CATEGORY = create("jei", "manual_metal_pressing");
    public static final TranslatableComponent JEI_WORKBENCH_CRAFTING = create("jei", "workbench_crafting");


    //GUI
    public static final TranslatableComponent MACHINE_UPGRADE_LABELS = create("gui", "insert_upgrades");
    public static final TranslatableComponent NETHER_BRICK_FURNACE_CONTROLLER = create("gui", "nether_brick_furnace");
    public static final TranslatableComponent NEXT = create("container", "next");
    public static final TranslatableComponent PREV = create("container", "prev");
    public static final TranslatableComponent HOVER_TO_SHOW_TAG = create("button", "hover_show_tag");
    public static final TranslatableComponent NO_ITEM = create("button", "no_button");
    public static final TranslatableComponent JEI_HIGH_HEAT_SMELTING = create("jei", "high_heat_smelting");
    public static final TranslatableComponent MESSAGE_NBF_FORM_ERROR = create("message", "nbf_form_fail");
    private static final TranslatableComponent NO_TAG = create("button", "no_tag");
    public static final TranslatableComponent FILL_TANK = create("gui", "fill_tank");
    public static final TranslatableComponent EMPTY_TANK = create("gui", "empty_tank");


    //TOOLTIP
    public static final TranslatableComponent TANK_ONLY_SUPPORTS_LAVA = create("tooltip", "lava_support_only");
    public static final TranslatableComponent TANK_AMOUNT = create("tooltip", "amount");
    public static final TranslatableComponent ANVIL_DESCRIPTION = create("tooltip", "minecraft_anvil");


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
    public static final TranslatableComponent MAKING_STEEL_CRAFT_CONTROLLER = create("book", "making_steel.crafting_controller");
    public static final TranslatableComponent MAKING_STEEL_MULTIBLOCK = create("book", "making_steel.multiblock");
    public static final TranslatableComponent MAKING_STEEL_MULTIBLOCK_NOTICE = create("book", "making_steel.multiblock_notice");
    public static final TranslatableComponent BENCH_WORKING = create("book", "bench_working.title");
    public static final TranslatableComponent TEXT_HOW_TO_USE = create("book", "how_to_use");

    public static final TranslatableComponent BASIC_MATERIALS_CATEGORY = create("book", "category_basic_materials");
    public static final TranslatableComponent BASIC_MATERIAL_CATEGORY_DESCRIPTION = create("book", "category_basic_materials.description");

    //ADVANCEMENTS
    public static final TranslatableComponent ROOT = create("advancement", "root");
    public static final TranslatableComponent ROOT_DESCRIPTION = create("advancement", "root.description");
    private static final AdvancementComponent ADVANCEMENT_COMPONENT = new AdvancementComponent();
    public static final TranslatableComponent HEAVY_CRUSHING = createAdvancement("heavy_crushing", "Heavy Crushing", "Crush some ores and coals into dusts");
    public static final TranslatableComponent A_HEATED_TOPIC = createAdvancement("a_heated_topic", "A Heated Topic", "Craft a Nether Brick Furnace Controller");
    public static final TranslatableComponent HIGH_HEAT_SMELTING = createAdvancement("high_heat_smelting", "High Heat Smelting", "Construct A Nether Brick Furnace, see Guide Book for more information");
    public static final TranslatableComponent GETTING_A_TRUE_UPGRADE = createAdvancement("getting_a_true_upgrade", "Getting A True Upgrade", "Obtain Steel Ingot");
    public static final TranslatableComponent COVER_ME_WITH_CARBONIZED_IRON = createAdvancement("cover_me_with_carbonized_iron", "Cover Me With Carbonized Carbon", "Wear a full set of Steel Armor with all the steel tools");
    private static final List<Block> ALL_BLOCKS;
    private static final List<Item> ALL_ITEMS;

    static {
        ALL_BLOCKS = ModBlocks.BLOCK_DEFERRED_REGISTER.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        //ALL_BLOCKS.addAll(NonDeferredBlocks.REGISTERED_BLOCKS);

        ALL_ITEMS = ModItems.ITEM_DEFERRED_REGISTER.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        ALL_ITEMS.addAll(NonDeferredItems.REGISTERED_ITEMS);
    }

    public ModLanguage(DataGenerator gen) {
        super(gen, Names.MOD_ID, "en_us");
    }

    public static TranslatableComponent create(String pre, String suf) {
        return TextUtils.translateFormatText(pre, suf);
    }

    public static TranslatableComponent createAdvancement(String suf, String title, String description) {
        final String component = TextUtils.translateFormat("advancement", suf);
        return ADVANCEMENT_COMPONENT.add(component, title, description);
    }

    public static String convertToTranslatedText(ResourceLocation location) {
        StringBuilder buffer = new StringBuilder();
        String id = location.getPath();
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

    @Override
    protected void addTranslations() {
        ALL_BLOCKS.forEach(this::addBlock);
        ALL_ITEMS.forEach(this::addItem);

        add(MESSAGE_NBF_FORM_ERROR, "Can not form Nether Brick Furnace");

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
        add(THE_BASICS_DESCRIPTION, "This category will show you all $(o)the basics$() as the first steps in the mod.");

        //PATCHOULI - THE BASICS
        add(MANUAL_CRUSHING, "Manual Crushing");
        add(MANUAL_CRUSHING_PAGE_1, "Vanilla Minecraft's anvil is used to smash the raw ores and coals in to dusts (this will work for all the mods such as Mekanism or Immersive Engineering, and of course, this mod) $(br2) This method of crushing will give you 15% of getting an extra dust");
        add(MANUAL_CRUSHING_CRAFTING_PAGE, "The Crafting recipe of Anvil is shown up here.");
        add(MANUAL_CRUSHING_CRAFTING_TITLE, "Crafting Anvil");

        add(MAKING_STEEL, "Making Steel");
        add(MAKING_STEEL_PAGE_ONE, "Before you can make steel, you need to have some $(o)some$() pieces of $(l)heated coal dusts$() first. But before that, you need something so heat the $(l:tothemoon:manual_crushing)coal dust$().$(br2) So Nether Brick Furnace is your chose");
        add(MAKING_STEEL_CRAFT_CONTROLLER, "First of all, you need to craft a Controller for this huge furnace. This list below shows the blocks that the furnace needs for construction $(li)x1 NetherBrick Furnace Controller $(li)x15 Nether Bricks $(li)x1 Blass Furnace $(li)x9 Smooth Blackstone");
        add(MAKING_STEEL_MULTIBLOCK_NOTICE, "$(o)Notice:$()$(br) - This multiblock requires a $(l)Blast Furnace$() at the core / centre");
        add(MAKING_STEEL_MULTIBLOCK, "This special furnace is the key of the process, In order to produce Heated Coal Dust for steel, you need this furnace. However, to make this furnace function, you may need to have Blaze Powder af startup fuel and lava as a process maintenance, usually, the lava consumption is pretty low, at 3mB per a tick. After forming you can access the controller GUI to start making heated coal. The last thing you need is coal dust after smashing from normal coal by anvil");
        add(TEXT_HOW_TO_USE, "How To Use");


        //PATCHOULI - Basic Materials
        add(BASIC_MATERIAL_CATEGORY_DESCRIPTION, "A small but reliable category that shows you all the basic ores/metals which are important for your progress in this mod");
        add(BASIC_MATERIALS_CATEGORY, "Basic Materials");

        //Utils
        add(CAP_UNKNOWN, "Capability unknown or not found");
        add(CAP_FOUND, "CAP FOUND !");
    }

    private void add(TranslatableComponent component, String trans) {
        super.add(component.getKey(), trans);
    }

    private void addBlock(Block block) {
        super.add(block, this.convertToTranslatedText(block));
    }

    private void addItem(Item item) {
        super.add(item, this.convertToTranslatedText(item));
    }

    private String convertToTranslatedText(ItemLike item) {
        return convertToTranslatedText(Objects.requireNonNull(item.asItem().getRegistryName()));
    }
}
