package com.khanhpham.tothemoon.datagen.blocks;

import com.khanhpham.tothemoon.Names;
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
        super(gen, Names.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerModels() {
        ModBlocks.SOLID_BLOCKS.forEach(this::cubeAll);
        batteryBlockModel(ModBlocks.BATTERY.get());
        batteryBlockModel(ModBlocks.STEEL_BATTERY.get());
        batteryBlockModel(ModBlocks.REDSTONE_BATTERY.get());


        orientable("block/netherbrick_furnace_controller", modLoc("block/netherbrick_furnace_controller_side"), modLoc("block/netherbrick_furnace_controller_front"), modLoc("block/netherbrick_furnace_controller_top"));
        orientable("block/nether_brick_furnace_controller_on", modLoc("block/netherbrick_furnace_controller_side"), modLoc("block/netherbrick_furnace_controller_front_on"), modLoc("block/netherbrick_furnace_controller_top"));

    }


    private void batteryBlockModel(BatteryBlock batteryBlock) {
        String batteryName = ModUtils.getPath(batteryBlock);
        final String batteryModelFormat = "block/battery/" + batteryName + "%s";
        for (int i = 0; i <= 10; i++) {
            orientableWithBottom(batteryModelFormat.formatted("_level_" + i),
                    modLoc(batteryModelFormat.formatted("_side")),
                    modLoc(batteryModelFormat.formatted("/" + i)),
                    modLoc(batteryModelFormat.formatted("_bottom")),
                    modLoc(batteryModelFormat.formatted("_top"))
            );
        }
    }


    private void cubeAll(RegistryObject<? extends Block> supplier) {
        String id = ModUtils.getPath(supplier.get());
        super.cubeAll(id, modLoc("block/" + id));
    }
}
