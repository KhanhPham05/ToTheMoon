package com.khanhpham.tothemoon.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<Item> GENERAL_PRESS_MOLDS = create("metal_press_molds");
    public static final TagKey<Item> PLATES_MOLD = create("metal_press_molds/plate");
    public static final TagKey<Item> ROD_MOLD = create("metal_press_molds/rod");
    public static final TagKey<Item> GEAR_MOLD = create("metal_press_molds/gear");

    public static final TagKey<Item> INGOTS_STEEL = forge("ingots/steel");
    public static final TagKey<Item> INGOTS_URANIUM = forge("ingots/uranium");
    public static final TagKey<Item> INGOTS_REDSTONE_METAL = forge("ingots/redstone_metal");
    public static final TagKey<Item> INGOTS_REDSTONE_STEEL_ALLOY = forge("ingots/redstone_steel_alloy");
    public static final TagKey<Item> DUSTS_COAL = forge("dusts/coal");

    private static TagKey<Item> create(final String name) {
        return ItemTags.create(ModUtils.modLoc(name));
    }

    private static TagKey<Item> forge(final String name) {
        return ItemTags.create(new ResourceLocation("forge", name));
    }

}
