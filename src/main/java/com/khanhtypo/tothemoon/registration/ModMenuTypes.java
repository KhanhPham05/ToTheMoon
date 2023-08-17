package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.common.machine.powergenerator.PowerGeneratorMenu;
import com.khanhtypo.tothemoon.common.machine.powergenerator.PowerGeneratorScreen;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.workbench.WorkbenchMenu;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.workbench.WorkbenchScreen;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.display.BlackStoneFurnaceMenu;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.display.BlackStoneFurnaceScreen;
import com.khanhtypo.tothemoon.registration.elements.MenuObject;

public class ModMenuTypes {
    public static void staticInit() {
    }

    public static final MenuObject<PowerGeneratorMenu> POWER_GENERATOR = new MenuObject<>("power_generator", PowerGeneratorMenu::new, PowerGeneratorScreen::new);
    public static final MenuObject<WorkbenchMenu> WORKBENCH = new MenuObject<>("workbench_crafting", WorkbenchMenu::new, WorkbenchScreen::new).translateMenu("Workbench Crafting");
    public static final MenuObject<BlackStoneFurnaceMenu> BLACK_STONE_FURNACE = new MenuObject<>("blackstone_furnace", BlackStoneFurnaceMenu::new, BlackStoneFurnaceScreen::new).translateMenu("Blackstone Furnace");
}
