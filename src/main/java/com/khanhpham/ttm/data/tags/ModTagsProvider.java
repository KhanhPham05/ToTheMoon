package com.khanhpham.ttm.data.tags;

import com.khanhpham.ttm.ToTheMoonMain;
import com.khanhpham.ttm.core.tag.ModTags;
import com.khanhpham.ttm.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record ModTagsProvider(DataGenerator data, ExistingFileHelper fileHelper) {

    public void init() {
        BlockTagsProvider blockTags = new Block(data, fileHelper);
        data.addProvider(blockTags);
        data.addProvider(new Item(data, blockTags, fileHelper));
    }


    private static final class Block extends BlockTagsProvider {
        public Block(DataGenerator pGenerator, ExistingFileHelper fileHelper) {
            super(pGenerator, ToTheMoonMain.MOD_ID, fileHelper);
        }

        @NotNull
        @Override
        public String getName() {
            return "Mod Tags - Blocks";
        }

        @Override
        protected void addTags() {
            tag(ModTags.REQ_PICKAXE).add(ModBlocks.ENERGY_BANK, ModBlocks.ENERGY_GEN);
            tag(ModTags.REG_TIER_STONE).add(ModBlocks.ENERGY_BANK, ModBlocks.ENERGY_GEN);
        }
    }

    private static final class Item extends ItemTagsProvider {
        public Item(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(pGenerator, pBlockTagsProvider, ToTheMoonMain.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags() {
        }

        @NotNull
        @Override
        public String getName() {
            return "Mod Tags - Items";
        }

    }
}
