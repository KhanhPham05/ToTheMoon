package com.khanhtypo.tothemoon.data;

import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.data.c.ModBlockStateAndModelGenerator;
import com.khanhtypo.tothemoon.data.c.ModItemModels;
import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import com.khanhtypo.tothemoon.data.common.ModBlockLoots;
import com.khanhtypo.tothemoon.data.common.ModRecipeGenerator;
import com.khanhtypo.tothemoon.data.common.ModTagGenerators;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

public class DataStarter {
    public static void gatherData(GatherDataEvent event) {
        final DataGenerator generator = event.getGenerator();
        final PackOutput packOutput = generator.getPackOutput();
        final var lookupProvider = event.getLookupProvider();
        final ExistingFileHelper fileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ModLanguageGenerator(packOutput, ToTheMoon.MODID, "en_us"));
        final ItemModelProvider itemModelProvider = new ModItemModels(packOutput, ToTheMoon.MODID, fileHelper);
        generator.addProvider(event.includeClient(), new ModBlockStateAndModelGenerator(packOutput, itemModelProvider, ToTheMoon.MODID, fileHelper));
        generator.addProvider(event.includeClient(), itemModelProvider);

        ModTagGenerators.addProviders(event.includeServer(), generator, packOutput, lookupProvider, fileHelper);
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Set.of(), List.of(new LootTableProvider.SubProviderEntry(ModBlockLoots::new, LootContextParamSets.BLOCK))));
        generator.addProvider(event.includeServer(), new ModRecipeGenerator(packOutput));
    }
}
