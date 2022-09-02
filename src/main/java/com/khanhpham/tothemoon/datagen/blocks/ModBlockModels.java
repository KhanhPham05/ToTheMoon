package com.khanhpham.tothemoon.datagen.blocks;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryBlock;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockModels extends BlockModelProvider {
    public ModBlockModels(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ToTheMoon.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerModels() {
        ModBlocks.SOLID_BLOCKS.forEach(block -> this.cubeAllWithRenderType(block, "cutout"));
        batteryBlockModel(ModBlocks.BATTERY.get());
        batteryBlockModel(ModBlocks.STEEL_BATTERY.get());
        batteryBlockModel(ModBlocks.REDSTONE_BATTERY.get());
        this.batteryBase("fluid_tank", "block/fluid_tank_side", "block/fluid_tank_top", "block/fluid_tank_bottom", "block/fluid_tank_side");

        orientable("block/netherbrick_furnace_controller", modLoc("block/netherbrick_furnace_controller_side"), modLoc("block/netherbrick_furnace_controller_front"), modLoc("block/netherbrick_furnace_controller_top"));
        orientable("block/nether_brick_furnace_controller_on", modLoc("block/netherbrick_furnace_controller_side"), modLoc("block/netherbrick_furnace_controller_front_on"), modLoc("block/netherbrick_furnace_controller_top"));
    }

    private void batteryBlockModel(BatteryBlock batteryBlock) {
        String batteryName = ModUtils.getPath(batteryBlock);
        final String batteryModelFormat = "block/battery/" + batteryName + "%s";
        for (int i = 0; i <= 10; i++) {
            this.batteryBase(
                    //saveName
                    batteryModelFormat.formatted("_level_" + i),

                    batteryModelFormat.formatted("_side"),
                    batteryModelFormat.formatted("_top"),
                    batteryModelFormat.formatted("_bottom"),
                    batteryModelFormat.formatted("/" + i)
            );
        }
    }

    private void batteryBase(String saveName, String side, String top, String bottom, String front) {
        this.withExistingParent(saveName, super.modLoc("block/templates/battery_base"))
                .texture("side", modLoc(side))
                .texture("top", modLoc(top))
                .texture("bottom", modLoc(bottom))
                .texture("battery_front", modLoc(front));
    }

    private void cubeAllWithRenderType(RegistryObject<? extends Block> supplier, String renderType) {
        String id = ModUtils.getPath(supplier.get());
        super.cubeAll(id, modLoc("block/" + id)).renderType(renderType);
    }
}
