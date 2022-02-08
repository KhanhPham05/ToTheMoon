package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.core.energygenerator.containers.EnergyGeneratorContainer;
import com.khanhpham.tothemoon.core.storageblock.MoonBarrelContainer;
import com.khanhpham.tothemoon.utils.ContainerTypeRegister;
import com.khanhpham.tothemoon.utils.containers.BaseContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModContainerTypes {
    private ModContainerTypes() {
    }

    public static final ContainerTypeRegister CONTAINERS = new ContainerTypeRegister();

    public static final MenuType<MoonBarrelContainer> STORAGE_BLOCK;
    public static final MenuType<EnergyGeneratorContainer> ENERGY_GENERATOR_CONTAINER;

    static {
        STORAGE_BLOCK = register("moon_storage_container", MoonBarrelContainer::new);
        ENERGY_GENERATOR_CONTAINER = register("energy_generator_container", EnergyGeneratorContainer::new);
    }

    private static <T extends BaseContainer> MenuType<T> register(String name, MenuType.MenuSupplier<T> supplier) {
        return CONTAINERS.register(name, supplier);
    }

    @SubscribeEvent
    public final void registerContainerTypes(RegistryEvent.Register<MenuType<?>> event) {
        CONTAINERS.registerAll(event.getRegistry());
    }

    public static void run() {}

    public static ContainerTypeRegister init() {
        run();
        return CONTAINERS;
    }
}
