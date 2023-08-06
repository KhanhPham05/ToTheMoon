package com.khanhtypo.tothemoon.common.item;

import com.google.common.collect.ImmutableMap;
import com.khanhtypo.tothemoon.utls.ModUtils;
import com.khanhtypo.tothemoon.data.ModBlockTags;
import com.khanhtypo.tothemoon.data.ModItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;
import java.util.Map;

public class ModToolTiers {
    public static final ForgeTier URANIUM = new ForgeTier(3, 535, 6.8f, 3.0f, 15, ModBlockTags.NEEDS_URANIUM_TOOLS, () -> Ingredient.of(ModItemTags.INGOT_URANIUM));
    public static final ForgeTier STEEL = new ForgeTier(2, 512, 7.0f, 2.5f, 12, ModBlockTags.NEEDS_STEEL_TOOLS, () -> Ingredient.of(ModItemTags.INGOT_STEEL));

    public static final Map<Tier, TagKey<Item>> TAG_MAPPING = ImmutableMap.of(URANIUM, ModItemTags.INGOT_URANIUM, STEEL, ModItemTags.INGOT_STEEL);

    static {
        TierSortingRegistry.registerTier(URANIUM, ModUtils.location("uranium_tools"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
        TierSortingRegistry.registerTier(STEEL, ModUtils.location("steel_tools"), List.of(Tiers.IRON, URANIUM), List.of(Tiers.DIAMOND));
    }
}
