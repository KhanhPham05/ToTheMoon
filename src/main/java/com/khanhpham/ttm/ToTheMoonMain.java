package com.khanhpham.ttm;

import com.khanhpham.ttm.init.ModItems;
import com.khanhpham.ttm.utils.RegistryTypes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod(ToTheMoonMain.MOD_ID)
@SuppressWarnings("unused")
public final class ToTheMoonMain {
    public static final String MOD_ID = "tothemoon";
    public static final Logger LOG = LogManager.getLogger("ToTheMoonMain");

    public static final CreativeModeTab TTM_TAB = new CreativeModeTab("ttm_tab") {
        @Override
        @Nonnull
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.TTM_ICON);
        }
    };

    public ToTheMoonMain() {
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        RegistryTypes.init(bus);
    }
}