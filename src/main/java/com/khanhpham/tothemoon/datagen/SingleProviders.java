package com.khanhpham.tothemoon.datagen;

import com.google.gson.JsonElement;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.helpers.RegistryEntries;
import com.khanhpham.tothemoon.worldgen.OreVeins;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SingleProviders {
    private SingleProviders() {
    }

    public static void biomeModifiers(boolean includeServer, DataGenerator data, ExistingFileHelper fileHelper) {
        final RegistryAccess registryAccess = RegistryAccess.builtinCopy();
        final RegistryOps<JsonElement> jsonOps = RegistryOps.create(JsonOps.INSTANCE, registryAccess);

        final List<Holder<Biome>> overworldBiomeHolders = MultiNoiseBiomeSource.Preset.OVERWORLD.possibleBiomes().map(ForgeRegistries.BIOMES::getHolder).filter(Optional::isPresent).map(Optional::get).toList();
        final HolderSet<Biome> overworldBiomesHolderSet = HolderSet.direct(overworldBiomeHolders);


        final Map<ResourceLocation, BiomeModifier> modifierMap = Map.ofEntries(
                entry(ModUtils.modLoc("ore_generation"), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(overworldBiomesHolderSet, HolderSet.direct(OreVeins.URANIUM_OVERWORLD.getOreFeature()), GenerationStep.Decoration.UNDERGROUND_ORES))
        );

        JsonCodecProvider<BiomeModifier> provider = JsonCodecProvider.forDatapackRegistry(data, fileHelper, ToTheMoon.MOD_ID, jsonOps, ForgeRegistries.Keys.BIOME_MODIFIERS, modifierMap);

        data.addProvider(includeServer, provider);
    }

    private static Map.Entry<ResourceLocation, PlacedFeature> getFeatureMap(OreVeins feature) {
        return entry(feature.getFeatureId(), feature.getOreFeature().get());
    }

    private static <A, B> Map.Entry<A, B> entry(A key, B value) {
        return Map.entry(key, value);
    }
}
