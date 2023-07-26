package com.khanhtypo.tothemoon.common.tag;

import com.khanhtypo.tothemoon.ToTheMoon;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;

public class ItemTagFamilies {
    public static final TagFamily<Item> INGOTS = TagFamily.forItem(Tags.Items.INGOTS);
    public static final TagFamily<Item> PLATES = TagFamily.forItem("forge", "plates");
    public static final TagFamily<Item> RODS = TagFamily.forItem(Tags.Items.RODS);
    public static final TagFamily<Item> GEARS = TagFamily.forItem("forge", "gears");
    public static final TagFamily<Item> NUGGETS = TagFamily.forItem(Tags.Items.NUGGETS);
    public static final TagFamily<Item> WIRES = TagFamily.forItem("forge", "wires");
    public static final TagFamily<Item> HAMMERS = TagFamily.forItem(ToTheMoon.MODID, "hammers");
    public static final TagFamily<Item> RAW_ORES = TagFamily.forItem(Tags.Items.RAW_MATERIALS);
    public static final TagFamily<Item> DUSTS = TagFamily.forItem(Tags.Items.DUSTS);
    public static final TagFamily<Item> MOLDS = TagFamily.forItem(ToTheMoon.MODID, "metal_press_molds");
    public static final TagFamily<Item> GEMS = TagFamily.forItem(Tags.Items.GEMS);
    public static final TagFamily<Item> SWORDS = TagFamily.forItem(ItemTags.SWORDS);
    public static final TagFamily<Item> AXES = TagFamily.forItem(ItemTags.AXES);
    public static final TagFamily<Item> HOES = TagFamily.forItem(ItemTags.HOES);
    public static final TagFamily<Item> PICKAXES = TagFamily.forItem(ItemTags.PICKAXES);
    public static final TagFamily<Item> SHOVELS = TagFamily.forItem(ItemTags.SHOVELS);
    public static final TagFamily<Item> HELMETS = TagFamily.forItem(Tags.Items.ARMORS_HELMETS);
    public static final TagFamily<Item> CHESTPLATES = TagFamily.forItem(Tags.Items.ARMORS_CHESTPLATES);
    public static final TagFamily<Item> LEGGINGS = TagFamily.forItem(Tags.Items.ARMORS_LEGGINGS);
    public static final TagFamily<Item> BOOTS = TagFamily.forItem(Tags.Items.ARMORS_BOOTS);
}
