package com.khanhpham.tothemoon.datagen.modelandstate;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryBlock;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.BaseEnergyGeneratorBlock;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.helpers.RegistryEntries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
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
        orientable("block/netherbrick_furnace_controller", modLoc("block/netherbrick_furnace_controller_side"), modLoc("block/netherbrick_furnace_controller_front"), modLoc("block/netherbrick_furnace_controller_top"));
        orientable("block/nether_brick_furnace_controller_on", modLoc("block/netherbrick_furnace_controller_side"), modLoc("block/netherbrick_furnace_controller_front_on"), modLoc("block/netherbrick_furnace_controller_top"));

        for (Block block : ModBlockStateProvider.facingLitBlocks) {
            if (block instanceof BatteryBlock<?> batteryBlock) {
                this.batteryBlockModel(batteryBlock);
            } else if (block instanceof BaseEnergyGeneratorBlock<?> generatorBlock) {
                this.energyGenerator(generatorBlock);
            } else {
                String blockPath = RegistryEntries.BLOCK.getPath(block);
                for (Boolean lit : BlockStateProperties.LIT.getPossibleValues()) {
                    this.orientable(blockPath, lit);
                }
            }
        }

    }

    public void orientable(String blockPath, boolean lit) {
        String textureName = "block/" + blockPath;
        String saveName =  textureName + (lit ? "_on" : "");
        orientable(saveName, textureName + "_side", textureName + (lit ? "_on" : ""), textureName + "_top");
    }

    public void orientable(String saveName, String side, String front, String top) {
        super.orientable(saveName, ModUtils.modLoc(side), ModUtils.modLoc(front), ModUtils.modLoc(top));
    }

    private void energyGenerator(BaseEnergyGeneratorBlock<?> block) {
        String path = RegistryEntries.BLOCK.getPath(block);
        for (Boolean lit : BlockStateProperties.LIT.getPossibleValues()) {
            super.orientable("block/" + path + (lit ? "_on" :""), modLoc("block/energy_generator_side"), modLoc("block/" + path + (lit ? "_on" :"")), modLoc("block/energy_generator_top"));

        }
    }

    private void batteryBlockModel(BatteryBlock<?> batteryBlock) {
        String batteryName = ModUtils.getPath(batteryBlock);
        final String batteryModelFormat = "block/battery/" + batteryName + "%s";
        for (int i = 0; i <= 10; i++) {
            this.batteryBase(
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
