package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.core.blocks.machines.alloysmelter.AlloySmelterMenu;
import com.khanhpham.tothemoon.core.blocks.machines.battery.BatteryMenu;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.containers.EnergyGeneratorMenu;
import com.khanhpham.tothemoon.core.blocks.machines.energysmelter.EnergySmelterMenu;
import com.khanhpham.tothemoon.core.blocks.machines.metalpress.MetalPressMenu;
import com.khanhpham.tothemoon.core.blocks.machines.storageblock.MoonBarrelMenu;
import com.khanhpham.tothemoon.core.menus.BaseMenu;
import com.khanhpham.tothemoon.utils.registration.MenuTypeRegister;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModMenuTypes {

    public static final MenuTypeRegister MENU_TYPES = new MenuTypeRegister();

    public static final MenuType<MoonBarrelMenu> STORAGE_BLOCK;

    public static final MenuType<EnergyGeneratorMenu> ENERGY_GENERATOR_CONTAINER;

    public static final MenuType<AlloySmelterMenu> ALLOY_SMELTER;

    public static final MenuType<MetalPressMenu> METAL_PRESS;
    public static final MenuType<BatteryMenu> BATTERY;
    public static final MenuType<EnergySmelterMenu> ENERGY_SMELTER;

    static {
        STORAGE_BLOCK = register("moon_storage_container", MoonBarrelMenu::new);
        ENERGY_GENERATOR_CONTAINER = register("energy_generator_container", EnergyGeneratorMenu::new);
        ALLOY_SMELTER = register("alloy_smelter", AlloySmelterMenu::new);
        METAL_PRESS = register("metal_press_menu", MetalPressMenu::new);
        BATTERY = register("battery", BatteryMenu::new);
        ENERGY_SMELTER = register("energy_smelter", EnergySmelterMenu::new);
    }

    private ModMenuTypes() {
    }

    private static <T extends BaseMenu> MenuType<T> register(String name, MenuType.MenuSupplier<T> supplier) {
        return MENU_TYPES.register(name, supplier);
    }

    @SubscribeEvent
    public static void init(RegistryEvent.Register<MenuType<?>> event) {
        init(event.getRegistry());
    }

    public static void init(IForgeRegistry<MenuType<?>> reg) {
        MENU_TYPES.registerAll(reg);
    }
}
