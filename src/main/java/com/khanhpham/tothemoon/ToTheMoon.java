package com.khanhpham.tothemoon;

import com.khanhpham.tothemoon.datagen.EnglishProvider;
import com.khanhpham.tothemoon.datagen.ModClientProvider;
import com.khanhpham.tothemoon.datagen.ModRecipeProvider;
import com.khanhpham.tothemoon.datagen.ModTagsProvider;
import com.khanhpham.tothemoon.registries.ModBlocks;
import com.khanhpham.tothemoon.registries.ModFluids;
import com.khanhpham.tothemoon.registries.ModItems;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("tothemoon")
public class ToTheMoon {
    public static final String ID = "tothemoon";
    public static final Logger LOG = LogManager.getLogger();

    @MethodsReturnNonnullByDefault
    public static final ItemGroup TTM_TAB = new ItemGroup("ttm.tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.AIR);
        }
    };

    public ToTheMoon() {
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();


        ModBlocks.init(bus);
        ModFluids.init(bus);
        ModItems.init(bus);

    }


    @Mod.EventBusSubscriber(modid = ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModEvents {
        private ModEvents() {
        }

        @SubscribeEvent
        @SuppressWarnings("unused")
        public static void data(GatherDataEvent event) {
            DataGenerator data = event.getGenerator();
            ExistingFileHelper fileHelper = event.getExistingFileHelper();

            data.addProvider(new EnglishProvider(data));
            data.addProvider(new ModRecipeProvider(data));

            final ModClientProvider client = new ModClientProvider(data, fileHelper);
            data.addProvider(client.blockState());
            data.addProvider(client.itemModel());

            final ModTagsProvider tags = new ModTagsProvider(data, fileHelper);
            data.addProvider(tags.addBlockTags());
            data.addProvider(tags.addItemTags());
        }
    }
}
