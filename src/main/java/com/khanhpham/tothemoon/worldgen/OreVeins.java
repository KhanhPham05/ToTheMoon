package com.khanhpham.tothemoon.worldgen;

import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.registries.RegisterEvent;

import java.util.function.Supplier;

public enum OreVeins {
    URANIUM_OVERWORLD(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_URANIUM_ORE, new OreGenValues(10, 10, 8, -64));

    private final Holder<PlacedFeature> oreFeature;
    private final Block ore;
    private final OreGenValues values;
    private final ResourceLocation featureId;

    OreVeins(RuleTest oreSpawnRule, Supplier<? extends Block> deepslateUraniumOre, OreGenValues values) {
        ore = deepslateUraniumOre.get();
        this.values = values;
        this.featureId = ModUtils.modLoc(this.name().toLowerCase() + "_feature");

        Holder<ConfiguredFeature<OreConfiguration, ?>> oreConfig = FeatureUtils.register("tothemoon:" + this.name().toLowerCase() + "_config", Feature.ORE, new OreConfiguration(oreSpawnRule, deepslateUraniumOre.get().defaultBlockState(), values.countInVein()));
        this.oreFeature = PlacementUtils.register(this.featureId.toString(), oreConfig, CountPlacement.of(values.veinsInChunk()), InSquarePlacement.spread(), HeightRangePlacement.triangle(VerticalAnchor.absolute(values.minWorldHeight()), VerticalAnchor.absolute(values.maxWorldHeight())), BiomeFilter.biome());
    }

    public static void registerAll(RegisterEvent.RegisterHelper<PlacedFeature> ignored) {
        //for (OreVeins oreVein : values()) {
        //    registerHelper.register(oreVein.getFeatureId(), oreVein.getOreFeature().get());
        //}
    }

    public Holder<PlacedFeature> getOreFeature() {
        return oreFeature;
    }

    public Block getOreBlock() {
        return ore;
    }

    public OreGenValues getValues() {
        return values;
    }

    public ResourceLocation getFeatureId() {
        return featureId;
    }
}
