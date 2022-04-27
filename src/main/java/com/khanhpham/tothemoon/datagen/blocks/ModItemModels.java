package com.khanhpham.tothemoon.datagen.blocks;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.core.blocks.machines.storageblock.MoonRockBarrel;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.core.blocks.MachineFrameBlock;
import com.khanhpham.tothemoon.core.items.GearItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class ModItemModels extends ItemModelProvider {
    public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Names.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModItems.ITEM_DEFERRED_REGISTER.getEntries().stream().map(Supplier::get).filter(i -> !(i instanceof GearItem)).forEach(this::simpleItem);
        ModBlocks.BLOCK_DEFERRED_REGISTER.getEntries().stream().map(Supplier::get).filter(b -> !(b instanceof MachineFrameBlock) && !(b instanceof MoonRockBarrel)).forEach(this::blockItem);
    }

    private void simpleItem(Item item) {
        String id = ModUtils.convertRlToPath(item);
        super.singleTexture(id, new ResourceLocation("item/generated"), "layer0", ModUtils.modLoc("item/" + id));
    }

    private <T extends Block> void blockItem(T block) {
        String id = ModUtils.convertRlToPath(block.asItem());
        super.withExistingParent(id, modLoc("block/" + id));
    }
}
