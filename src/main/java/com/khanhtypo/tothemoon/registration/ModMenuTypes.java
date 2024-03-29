package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.common.machine.electricalsmelter.ElectricalSmelterMenu;
import com.khanhtypo.tothemoon.common.tank.FluidTankMenu;
import com.khanhtypo.tothemoon.common.workbench.WorkbenchMenu;
import com.khanhtypo.tothemoon.common.machine.powergenerator.PowerGeneratorMenu;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.display.BlackStoneFurnaceMenu;
import com.khanhtypo.tothemoon.registration.elements.MenuObject;

public class ModMenuTypes {
    public static void staticInit() {
    }

    public static final MenuObject<PowerGeneratorMenu> POWER_GENERATOR = new MenuObject<>("power_generator", PowerGeneratorMenu::new);
    public static final MenuObject<WorkbenchMenu> WORKBENCH = new MenuObject<>("workbench_crafting", WorkbenchMenu::new).translateMenu("Workbench Crafting");
    public static final MenuObject<BlackStoneFurnaceMenu> BLACK_STONE_FURNACE = new MenuObject<>("blackstone_furnace", BlackStoneFurnaceMenu::new).translateMenu("Blackstone Furnace");
    public static final MenuObject<FluidTankMenu> FLUID_TANK = new MenuObject<>("fluid_tank", FluidTankMenu::new);
    public static final MenuObject<ElectricalSmelterMenu> ELECTRICAL_SMELTER = new MenuObject<>("electrical_smelter", ElectricalSmelterMenu::new);

}
