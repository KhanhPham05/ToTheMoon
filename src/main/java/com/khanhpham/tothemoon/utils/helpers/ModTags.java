package com.khanhpham.tothemoon.utils.helpers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<Item> GENERAL_PRESS_MOLDS = mod("metal_press_molds");
    public static final TagKey<Item> PLATE_MOLD = mod("metal_press_molds/plate");
    public static final TagKey<Item> ROD_MOLD = mod("metal_press_molds/rod");
    public static final TagKey<Item> GEAR_MOLD = mod("metal_press_molds/gear");

    public static final TagKey<Item> INGOTS_STEEL = forge("ingots/steel");
    public static final TagKey<Item> INGOTS_URANIUM = forge("ingots/uranium");
    public static final TagKey<Item> INGOTS_REDSTONE_METAL = forge("ingots/redstone_metal");
    public static final TagKey<Item> INGOTS_REDSTONE_STEEL_ALLOY = forge("ingots/redstone_steel_alloy");
    public static final TagKey<Item> DUSTS_COAL = forge("dusts/coal");

    public static final TagKey<Item> GENERAL_PLATES = forge("plates");
    public static final TagKey<Item> PLATES_STEEL = forge("plates/steel");
    public static final TagKey<Item> PLATES_URANIUM = forge("plates/uranium");
    public static final TagKey<Item> PLATES_REDSTONE_METAL = forge("plates/redstone_metal");
    public static final TagKey<Item> PLATES_REDSTONE_STEEL_ALLOY = forge("plates/redstone_steel_alloy");

    public static final TagKey<Item> PLATES_IRON = forge("plates/iron");
    public static final TagKey<Item> PLATES_COPPER = forge("plates/copper");
    public static final TagKey<Item> PLATES_GOLD = forge("plates/gold");

    public static final TagKey<Item> DUSTS_GOLD = forge("dust/gold");
    public static final TagKey<Item> DUSTS_REDSTONE_STEEL_ALLOY = forge("dusts/redstone_steel_metal");
    public static final TagKey<Item> GENERAL_TTM_HAMMERS = mod("hammers");

    private static TagKey<Item> mod(final String name) {
        return ItemTags.create(ModUtils.modLoc(name));
    }

    private static TagKey<Item> forge(final String name) {
        return ItemTags.create(new ResourceLocation("forge", name));
    }

}
