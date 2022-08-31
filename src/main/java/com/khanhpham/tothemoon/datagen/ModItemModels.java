package com.khanhpham.tothemoon.datagen;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.core.blocks.MachineFrameBlock;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryBlock;
import com.khanhpham.tothemoon.core.items.GearItem;
import com.khanhpham.tothemoon.core.items.HandheldItem;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.helpers.RegistryEntries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.function.Supplier;

public class ModItemModels extends ItemModelProvider {
    public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Names.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        List<? extends Item> items = ModItems.ITEM_DEFERRED_REGISTER.getEntries().stream().map(Supplier::get).toList();

        for (Item item : items) {
            if (!(item instanceof GearItem)) {
                if (item instanceof HandheldItem || item instanceof DiggerItem) {
                    String path = RegistryEntries.getKeyFrom((item)).getPath();
                    withExistingParent(path, new ResourceLocation("item/handheld")).texture("layer0", "item/" + path);
                } else simpleItem(item);
            }
        }

        ModBlocks.BLOCK_DEFERRED_REGISTER.getEntries().stream().map(Supplier::get).filter(b -> !(b instanceof MachineFrameBlock || b instanceof BatteryBlock)).forEach(this::blockItem);
        //withExistingParent(ModUtils.registryToPath(ModBlocks.BATTERY.get()), modLoc("block/battery_level_0"));
        batteryItemModel(ModBlocks.BATTERY.get());
        batteryItemModel(ModBlocks.REDSTONE_BATTERY.get());
        batteryItemModel(ModBlocks.STEEL_BATTERY.get());
    }

    private void batteryItemModel(BatteryBlock batteryBlock) {
        String blockName = ModUtils.getPath(batteryBlock);
        super.withExistingParent(blockName, modLoc("block/battery/" + blockName + "_level_0"));
    }

    private void simpleItem(Item item) {
        String id = RegistryEntries.getKeyFrom(item).getPath();
        super.singleTexture(id, new ResourceLocation("item/generated"), "layer0", ModUtils.modLoc("item/" + id));
    }

    private <T extends Block> void blockItem(T block) {
        String id = RegistryEntries.getKeyFrom(block.asItem()).getPath();
        if (super.existingFileHelper.exists(modLoc("block/" + id), ModelProvider.MODEL)) {
            super.withExistingParent(id, modLoc("block/" + id));
        }
    }
}
