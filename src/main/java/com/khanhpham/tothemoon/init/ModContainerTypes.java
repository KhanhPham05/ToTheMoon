package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.core.energygenerator.containers.EnergyGeneratorContainer;
import com.khanhpham.tothemoon.core.storageblock.MoonBarrelContainer;
import com.khanhpham.tothemoon.utils.ContainerTypeRegister;
import com.khanhpham.tothemoon.utils.containers.BaseContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContainerTypes {
   // public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Names.MOD_ID);
    public static final ContainerTypeRegister CONTAINERS = new ContainerTypeRegister();

    public static final MenuType<MoonBarrelContainer> STORAGE_BLOCK;

    public static final MenuType<EnergyGeneratorContainer> ENERGY_GENERATOR_CONTAINER;

    static {
        STORAGE_BLOCK = register("moon_storage_container", MoonBarrelContainer::new);
        ENERGY_GENERATOR_CONTAINER = register("energy_generator_container", EnergyGeneratorContainer::new);
    }

    private ModContainerTypes() {
    }

    private static <T extends BaseContainer> MenuType<T> register(String name, MenuType.MenuSupplier<T> supplier) {
        return CONTAINERS.register(name, supplier);
    }

    @SubscribeEvent
    public static void init(RegistryEvent.Register<MenuType<?>> event) {
        init(event.getRegistry());
    }

    public static void init(IForgeRegistry<MenuType<?>> reg) {
        CONTAINERS.registerAll(reg);
    }
}
