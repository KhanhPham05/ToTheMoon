package com.khanhpham.tothemoon.registries;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.common.energybank.EnergyBank;
import com.khanhpham.tothemoon.common.machineblock.EnergyGenerator;
import com.khanhpham.tothemoon.util.Identity;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Supplier;

/**
 * @see ContainerType
 */
@Mod.EventBusSubscriber(modid = ToTheMoon.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSpecials {
    public static TileEntityType<EnergyGenerator.Tile> energyGeneratorTileEntity;
    public static ContainerType<EnergyGenerator.Storage> energyGeneratorContainer;

    public static TileEntityType<EnergyBank.Tile> energyBankTileEntity;
    public static ContainerType<EnergyBank.Storage> energyBankContainer;

    private static RegistryEvent.Register<ContainerType<?>> containerEvent;
    private static RegistryEvent.Register<TileEntityType<?>> teEvent;

    private ModSpecials() {
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void initContainer(RegistryEvent.Register<ContainerType<?>> typeRegister) {
        ToTheMoon.LOG.debug("Container Type Registry Event has fired");
        containerEvent = typeRegister;
        energyGeneratorContainer = register("energy_generator_container", EnergyGenerator.Storage::new);
        energyBankContainer = register("energy_bank_container", EnergyBank.Storage::new);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void initTileEntity(RegistryEvent.Register<TileEntityType<?>> event) {
        ToTheMoon.LOG.debug("TE Registry Event has fired");
        teEvent = event;
        energyGeneratorTileEntity = register("energy_generator_tile", EnergyGenerator.Tile::new, ModBlocks.ENERGY_GENERATOR.get());
        energyBankTileEntity = register("energy_bank_tile", EnergyBank.Tile::new, ModBlocks.ENERGY_BANK.get());
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void clientEvent(FMLClientSetupEvent event) {
        ScreenManager.register(energyGeneratorContainer, EnergyGenerator.StorageScreen::new);
        ScreenManager.register(energyBankContainer, EnergyBank.StorageScreen::new);
    }

    private static <TILE extends TileEntity> TileEntityType<TILE> register(String name, Supplier<TILE> supplier, Block... blocks) {
        TileEntityType<TILE> type = TileEntityType.Builder.of(supplier, blocks).build(null);
        teEvent.getRegistry().register(type.setRegistryName(Identity.mod(name)));
        return type;
    }

    private static <C extends Container> ContainerType<C> register(String name, ContainerType.IFactory<C> containerFactory) {
        ContainerType<C> type = new ContainerType<>(containerFactory);
        return register(name, type);
    }

    private static <C extends Container> ContainerType<C> register(String name, ContainerType<C> containerType) {
        containerType.setRegistryName(Identity.mod(name));
        containerEvent.getRegistry().register(containerType);
        return containerType;
    }
}
