package com.khanhtypo.tothemoon.serverdata.worldgen;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.registration.ModBlocks;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.util.TriConsumer;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@SuppressWarnings("SameParameterValue")
public final class OreDataGenerator {
    public static final TagMatchTest NORMAL_STONE = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    public static final TagMatchTest DEEPSLATE = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    private static final RegistrySetBuilder REGISTRY_SET_BUILDER = new RegistrySetBuilder();
    private static final String MOD_ID = ToTheMoon.MODID;
    private static HolderSet<PlacedFeature> features;

    public static void start(DataGenerator dataGenerator, boolean includeServer, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        add(Registries.CONFIGURED_FEATURE, (bootstrap, getter, key) ->
                FeatureUtils.register(bootstrap, key.create("uranium_ore_config"), Feature.ORE, new OreConfiguration(createOreTest(null, ModBlocks.DEEPSLATE_URANIUM_ORE), 9))
        );

        add(Registries.PLACED_FEATURE, (bootstrap, getter, key) -> {
            HolderGetter<ConfiguredFeature<?, ?>> configuredGetter = bootstrap.lookup(Registries.CONFIGURED_FEATURE);
            features = HolderSet.direct(bootstrap.register(key.create("uranium_ore_placed"), new PlacedFeature(configuredGetter.getOrThrow(FeatureUtils.createKey(ToTheMoon.MODID + ":uranium_ore_config")), createOrePlacement(10, VerticalAnchor.bottom(), VerticalAnchor.absolute(8)))));
        });

        add(ForgeRegistries.Keys.BIOME_MODIFIERS, (bootstrap, getter, key) -> {
            HolderSet<Biome> allBiomes = bootstrap.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD);
            bootstrap.register(key.create("overworld_ores"), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(allBiomes, features, GenerationStep.Decoration.UNDERGROUND_ORES));
        });
        dataGenerator.addProvider(includeServer, new DatapackBuiltinEntriesProvider(dataGenerator.getPackOutput(), lookupProvider, REGISTRY_SET_BUILDER, Set.of(MOD_ID)));
    }

    private static <T> void add(ResourceKey<Registry<T>> registry, TriConsumer<BootstapContext<T>, HolderGetter<T>, RegistryKeyGetter<T>> acceptor) {
        REGISTRY_SET_BUILDER.add(registry, pContext -> acceptor.accept(pContext, pContext.lookup(registry), new RegistryKeyGetter<>(registry)));
    }

    private static List<PlacementModifier> createOrePlacement(int chance, VerticalAnchor min, VerticalAnchor max) {
        return List.of(BiomeFilter.biome(), CountPlacement.of(chance), HeightRangePlacement.uniform(min, max));
    }

    private static List<OreConfiguration.TargetBlockState> createOreTest(@Nullable Supplier<Block> stoneOre, @Nullable Supplier<Block> deepslateOre) {
        Preconditions.checkState(stoneOre != null || deepslateOre != null);
        ImmutableList.Builder<OreConfiguration.TargetBlockState> builder = ImmutableList.builder();

        if (stoneOre != null) {
            builder.add(OreConfiguration.target(NORMAL_STONE, stoneOre.get().defaultBlockState()));
        }

        if (deepslateOre != null) {
            builder.add(OreConfiguration.target(DEEPSLATE, deepslateOre.get().defaultBlockState()));
        }

        return builder.build();
    }

    private record RegistryKeyGetter<T>(ResourceKey<Registry<T>> registry) {
        ResourceKey<T> create(String key) {
            return ResourceKey.create(this.registry, ModUtils.location(key));
        }
    }
}
