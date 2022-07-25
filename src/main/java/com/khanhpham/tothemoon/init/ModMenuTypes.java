package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryMenu;
import com.khanhpham.tothemoon.core.blocks.machines.alloysmelter.AlloySmelterMenu;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.containers.EnergyGeneratorMenu;
import com.khanhpham.tothemoon.core.blocks.machines.energysmelter.EnergySmelterMenu;
import com.khanhpham.tothemoon.core.blocks.machines.metalpress.MetalPressMenu;
import com.khanhpham.tothemoon.core.blocks.machines.storageblock.MoonBarrelMenu;
import com.khanhpham.tothemoon.core.blocks.processblocks.tagtranslator.TagTranslatorMenu;
import com.khanhpham.tothemoon.core.blocks.tanks.FluidTankMenu;
import com.khanhpham.tothemoon.core.multiblock.block.brickfurnace.NetherBrickFurnaceControllerMenu;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.registration.MenuTypeRegister;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.tags.ITagManager;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModMenuTypes {

    public static final MenuTypeRegister MENU_TYPES = new MenuTypeRegister();

    public static final MenuType<MoonBarrelMenu> STORAGE_BLOCK;

    public static final MenuType<EnergyGeneratorMenu> ENERGY_GENERATOR_CONTAINER;

    public static final MenuType<AlloySmelterMenu> ALLOY_SMELTER;

    public static final MenuType<MetalPressMenu> METAL_PRESS;
    public static final MenuType<BatteryMenu> BATTERY;
    public static final MenuType<EnergySmelterMenu> ENERGY_SMELTER;
    public static final MenuType<NetherBrickFurnaceControllerMenu> NETHER_BRICK_FURNACE;
    public static final MenuType<FluidTankMenu> FLUID_TANK;
    public static final MenuType<TagTranslatorMenu> TAG_TRANSLATOR;

    static {
        STORAGE_BLOCK = register("moon_storage_container", MoonBarrelMenu::new);
        ENERGY_GENERATOR_CONTAINER = register("energy_generator_container", EnergyGeneratorMenu::new);
        ALLOY_SMELTER = register("alloy_smelter", AlloySmelterMenu::new);
        METAL_PRESS = register("metal_press_menu", MetalPressMenu::new);
        BATTERY = register("battery", BatteryMenu::new);
        ENERGY_SMELTER = register("energy_smelter", EnergySmelterMenu::new);
        NETHER_BRICK_FURNACE = register("nether_brick_furnace", NetherBrickFurnaceControllerMenu::new);
        FLUID_TANK = MENU_TYPES.register("fluid_tank_menu", FluidTankMenu::new);
        TAG_TRANSLATOR = register("tag_translator_menu", TagTranslatorMenu::new);
    }

    private ModMenuTypes() {
    }

    private static <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType.MenuSupplier<T> supplier) {
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
