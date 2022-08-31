package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryMenu;
import com.khanhpham.tothemoon.core.blocks.machines.alloysmelter.AlloySmelterMenu;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.containers.EnergyGeneratorMenu;
import com.khanhpham.tothemoon.core.blocks.machines.energysmelter.EnergySmelterMenu;
import com.khanhpham.tothemoon.core.blocks.machines.metalpress.MetalPressMenu;
import com.khanhpham.tothemoon.core.blocks.machines.oreprocessor.OreProcessorMenu;
import com.khanhpham.tothemoon.core.blocks.machines.storageblock.MoonBarrelMenu;
import com.khanhpham.tothemoon.core.blocks.processblocks.tagtranslator.TagTranslatorMenu;
import com.khanhpham.tothemoon.core.blocks.tanks.FluidTankMenu;
import com.khanhpham.tothemoon.core.blocks.workbench.WorkbenchMenu;
import com.khanhpham.tothemoon.core.multiblock.block.brickfurnace.NetherBrickFurnaceControllerMenu;
import com.khanhpham.tothemoon.utils.registration.MenuTypeRegister;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

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
    public static final MenuType<WorkbenchMenu> WORKBENCH_CRAFTING;
    public static final MenuType<OreProcessorMenu> ENERGY_PROCESSOR;

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
        WORKBENCH_CRAFTING = register("workbench", WorkbenchMenu::new);
        ENERGY_PROCESSOR = register("energy_processor", OreProcessorMenu::new);
    }

    private ModMenuTypes() {
    }

    private static <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType.MenuSupplier<T> supplier) {
        return MENU_TYPES.register(name, supplier);
    }

    @SubscribeEvent
    public static void init(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.MENU_TYPES, MENU_TYPES::registerAll);
    }
}
