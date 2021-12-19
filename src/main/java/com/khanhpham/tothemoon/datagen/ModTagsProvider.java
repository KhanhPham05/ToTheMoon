package com.khanhpham.tothemoon.datagen;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.registries.ModBlocks;
import com.khanhpham.tothemoon.registries.ModItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

import static com.khanhpham.tothemoon.datagen.ModTags.*;

public class ModTagsProvider {
    private final DataGenerator data;
    private final ExistingFileHelper fileHelper;
    private final String modid = ToTheMoon.ID;
    private final ModBlockTags blockTags;

    public ModTagsProvider(DataGenerator data, ExistingFileHelper fileHelper) {
        this.data = data;
        this.fileHelper = fileHelper;
        this.blockTags = new ModBlockTags(data, modid, fileHelper);
    }

    public IDataProvider addItemTags() {
        return new ModItemTags(data, blockTags, modid, fileHelper);
    }

    public IDataProvider addBlockTags() {
        return blockTags;
    }

    static final class ModItemTags extends ItemTagsProvider {
        public ModItemTags(DataGenerator data, BlockTagsProvider blockTag, String modid, @Nullable ExistingFileHelper fileHelper) {
            super(data, blockTag, modid, fileHelper);
        }

        @Override
        protected void addTags() {
            tag(ingots).addTag(ingotCopper).add(ModItems.COPPER_INGOT.get());
            tag(plate).add(ModItems.STEEL_SHEET.get(), ModItems.COPPER_SHEET.get(), ModItems.REDSTONE_STEEL_SHEET.get());
            tag(storageBlockItems).add(ModBlocks.COPPER_BLOCK.item(), ModBlocks.STEEL_BLOCK.item(), ModBlocks.REDSTONE_STEEL_ALLOY_BLOCK.item());
            tag(dust).add(ModItems.COPPER_DUST.get(), ModItems.STEEL_DUST.get(), ModItems.REDSTONE_STEEL_DUST.get());
            tag(oreBlockItems).add(ModBlocks.COPPER_ORE.item());
            tag(moonOreItem).add(ModBlocks.COPPER_MOON_ORE.item());
            tag(nuggets).add(ModItems.COPPER_NUGGET.get(), ModItems.STEEL_NUGGET.get(), ModItems.REDSTONE_STEEL_NUGGETS.get());

            //copper
            tag(ingotCopper).add(ModItems.COPPER_INGOT.get());
            tag(moonOreCopperItem).add(ModBlocks.COPPER_MOON_ORE.item());
            tag(oreCopperItem).add(ModBlocks.COPPER_ORE.item());
            tag(storageCopperItem).add(ModBlocks.COPPER_BLOCK.item());
            tag(dustCopper).add(ModItems.COPPER_DUST.get());
            tag(plateCopper).add(ModItems.COPPER_SHEET.get());
            tag(nuggetCopper).add(ModItems.COPPER_NUGGET.get());

            //steel
            tag(ingotSteel).add(ModItems.STEEL_INGOT.get());
            tag(dustSteel).add(ModItems.STEEL_DUST.get());
            tag(storageSteelItem).add(ModBlocks.STEEL_BLOCK.item());
            tag(plateSteel).add(ModItems.STEEL_SHEET.get());
            tag(nuggetSteel).add(ModItems.STEEL_NUGGET.get());

            //redstone steel alloy
            tag(dustRedstoneSteel).add(ModItems.REDSTONE_STEEL_DUST.get());
            tag(ingotRedstoneSteel).add(ModItems.REDSTONE_STEEL_INGOT.get());
            tag(storageRedstoneSteelItem).add(ModBlocks.REDSTONE_STEEL_ALLOY_BLOCK.item());
            tag(plateRedstoneSteel).add(ModItems.REDSTONE_STEEL_SHEET.get());
            tag(nuggetRedstoneSteel).add(ModItems.REDSTONE_STEEL_NUGGETS.get());
        }
    }

    static final class ModBlockTags extends BlockTagsProvider {
        public ModBlockTags(DataGenerator dataGenerator, String modid, @Nullable ExistingFileHelper existingFileHelper) {
            super(dataGenerator, modid, existingFileHelper);
        }

        @Override
        protected void addTags() {
            tag(oreBlocks).add(ModBlocks.COPPER_ORE.get());
            tag(moonOreBlocks).add(ModBlocks.COPPER_MOON_ORE.get());
            tag(storageBlocks).add(ModBlocks.COPPER_BLOCK.get(), ModBlocks.STEEL_BLOCK.get(), ModBlocks.REDSTONE_STEEL_ALLOY_BLOCK.get());

            tag(oreCopper).add(ModBlocks.COPPER_ORE.get());
            tag(moonOreCopper).add(ModBlocks.COPPER_MOON_ORE.get());

            tag(storageCopper).add(ModBlocks.COPPER_BLOCK.get());
            tag(storageSteel).add(ModBlocks.STEEL_BLOCK.get());
            tag(storageRedstoneSteel).add(ModBlocks.REDSTONE_STEEL_ALLOY_BLOCK.get());

            tag(energyReceive).add(ModBlocks.ENERGY_BANK.get());
            tag(energyTransferable).add(ModBlocks.ENERGY_BANK.get());
            tag(energyTransferable).add(ModBlocks.ENERGY_GENERATOR.get());
        }
    }
}
