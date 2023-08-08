package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.common.block.machine.powergenerator.PowerGeneratorMenu;
import com.khanhtypo.tothemoon.common.block.machine.powergenerator.PowerGeneratorScreen;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.workbench.WorkbenchMenu;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.workbench.WorkbenchScreen;
import com.khanhtypo.tothemoon.registration.elements.MenuObject;

public class ModMenus {
    public static void staticInit() {
    }

    public static final MenuObject<PowerGeneratorMenu> POWER_GENERATOR = new MenuObject<>("power_generator", PowerGeneratorMenu::new, PowerGeneratorScreen::new);
    public static final MenuObject<WorkbenchMenu> WORKBENCH = MenuObject.register("workbench_crafting", ModBlocks.WORKBENCH, WorkbenchMenu::new, WorkbenchScreen::new).translateMenu("Workbench Crafting");
}
