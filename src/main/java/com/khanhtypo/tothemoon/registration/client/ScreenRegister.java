package com.khanhtypo.tothemoon.registration.client;

import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BaseMenu;
import com.khanhtypo.tothemoon.common.machine.powergenerator.PowerGeneratorScreen;
import com.khanhtypo.tothemoon.common.tank.FluidTankScreen;
import com.khanhtypo.tothemoon.common.workbench.WorkbenchScreen;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.display.BlackStoneFurnaceScreen;
import com.khanhtypo.tothemoon.registration.elements.MenuObject;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.khanhtypo.tothemoon.registration.ModMenuTypes.*;

@Mod.EventBusSubscriber(modid = ToTheMoon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ScreenRegister {

    private ScreenRegister() throws IllegalAccessException {
        throw new IllegalAccessException("Utility Class");
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        registerScreen(POWER_GENERATOR, PowerGeneratorScreen::new);
        registerScreen(WORKBENCH, WorkbenchScreen::new);
        registerScreen(BLACK_STONE_FURNACE, BlackStoneFurnaceScreen::new);
        registerScreen(FLUID_TANK, FluidTankScreen::new);
    }

    private static <T extends BaseMenu, S extends AbstractContainerScreen<T>> void registerScreen(MenuObject<T> menuObject, MenuScreens.ScreenConstructor<T, S> constructor) {
        MenuScreens.register(menuObject.get(), constructor);
    }
}
