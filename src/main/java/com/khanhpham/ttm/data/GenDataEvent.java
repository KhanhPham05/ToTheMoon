package com.khanhpham.ttm.data;

import com.khanhpham.ttm.ToTheMoonMain;
import com.khanhpham.ttm.data.client.BlockModelStates;
import com.khanhpham.ttm.data.client.ModItemModels;
import com.khanhpham.ttm.data.lang.EngLangProvider;
import com.khanhpham.ttm.data.loot.ModLootTables;
import com.khanhpham.ttm.data.recipes.ModRecipes;
import com.khanhpham.ttm.data.tags.ModTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ToTheMoonMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class GenDataEvent {
    public GenDataEvent() {
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onData(GatherDataEvent event) {
        DataGenerator data = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        ModTagsProvider tagsProvider = new ModTagsProvider(data, fileHelper);

        data.addProvider(new BlockModelStates(data, fileHelper));
        data.addProvider(new ModItemModels(data, fileHelper));
        data.addProvider(new EngLangProvider(data));
        data.addProvider(new ModLootTables(data));
        data.addProvider(new ModRecipes(data));
        tagsProvider.init();
    }
}
