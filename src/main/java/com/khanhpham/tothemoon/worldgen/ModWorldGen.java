package com.khanhpham.tothemoon.worldgen;

import com.khanhpham.tothemoon.ModUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

/**
 * @see net.minecraft.world.level.biome.Biomes
 * @see net.minecraft.world.level.Level
 * @see net.minecraft.sounds.SoundEvents
 */

public class ModWorldGen {
    public static final ResourceKey<Level> MOON_WORLD = ResourceKey.create(Registry.DIMENSION_REGISTRY, ModUtils.modLoc("the_moon_surface"));

    //TODO : Add water freezing effect when place , see BucketItem::emptyContent
    public static final ResourceKey<Biome> MOON_BIOME = ResourceKey.create(Registry.BIOME_REGISTRY, ModUtils.modLoc("the_moon"));
}
