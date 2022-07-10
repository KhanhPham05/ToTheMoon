package com.khanhpham.tothemoon.worldgen;

import com.khanhpham.tothemoon.init.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.function.Supplier;

public enum OreVeins {
    URANIUM_OVERWORLD(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_URANIUM_ORE, new OreGenValues(10, 3, 0, -64));

    private final Holder<ConfiguredFeature<OreConfiguration, ?>> oreConfig;
    private final Holder<PlacedFeature> oreFeature;

    OreVeins(RuleTest oreSpawnRule, Supplier<OreBlock> deepslateUraniumOre, OreGenValues values) {
        this.oreConfig = FeatureUtils.register("tothemoon:" + this.name().toLowerCase() + "_config", Feature.ORE, new OreConfiguration(oreSpawnRule, deepslateUraniumOre.get().defaultBlockState(), values.countInVein()));
        this.oreFeature = PlacementUtils.register("tothemoon:" + this.name().toLowerCase() + "_feature", this.oreConfig, CountPlacement.of(values.veinsInChunk()), HeightRangePlacement.uniform(VerticalAnchor.absolute(values.maxWorldHeight()), VerticalAnchor.absolute(values.minWorldHeight())));
    }

    public Holder<ConfiguredFeature<OreConfiguration, ?>> getOreConfig() {
        return oreConfig;
    }

    public Holder<PlacedFeature> getOreFeature() {
        return oreFeature;
    }
}
