package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicMenu;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicScreen;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.workbench.WorkbenchMenu;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.workbench.WorkbenchScreen;
import com.khanhtypo.tothemoon.registration.elements.MenuObject;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModMenus {
    public static void staticInit() {
    }    public static final MenuObject<WorkbenchMenu> WORKBENCH = MenuObject.register("workbench_crafting", ModBlocks.WORKBENCH, WorkbenchMenu::new).translateMenu("Workbench Crafting");

    @SuppressWarnings("SameParameterValue")
    public static final class ScreenRegister {

        private ScreenRegister() {
        }

        public static void registerScreen(FMLClientSetupEvent a) {
            registerScreen(WORKBENCH, WorkbenchScreen::new);
        }

        private static <T extends BasicMenu, S extends BasicScreen<T>> void registerScreen(MenuObject<T> menuObject, MenuScreens.ScreenConstructor<T, S> screenConstructor) {
            MenuScreens.register(menuObject.get(), screenConstructor);
        }
    }


}
