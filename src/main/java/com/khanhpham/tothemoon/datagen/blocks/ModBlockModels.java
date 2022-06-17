package com.khanhpham.tothemoon.datagen.blocks;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class ModBlockModels extends BlockModelProvider {
    public ModBlockModels(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Names.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerModels() {
        ModBlocks.SOLID_BLOCKS.forEach(this::cubeAll);
        batteryBlockModel();
    }

    private void batteryBlockModel() {
        for (int i = 0; i <= 10; i++) {
            orientableWithBottom("block/battery_level_" + i,
                    modLoc("block/battery/battery_side"),
                    modLoc("block/battery/" + "none" + '/' + i),
                    modLoc("block/battery/battery_bottom"),
                    modLoc("block/battery/battery_top")
            );
        }
    }


    private void cubeAll(Supplier<? extends Block> supplier) {
        String id = ModUtils.getPath(supplier.get());
        super.cubeAll(id, modLoc("block/" + id));
    }
}
