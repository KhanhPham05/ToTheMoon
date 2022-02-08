package com.khanhpham.tothemoon.data;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.blocks.Mineable;
import com.khanhpham.tothemoon.utils.mining.MiningTool;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModTagsProvider {

    private final BlockProvider blockTags;

    public ModTagsProvider(DataGenerator data, ExistingFileHelper fileHelper) {
        this.blockTags = new BlockProvider(data, fileHelper);
    }

    public BlockProvider getBlockTags() {
        return blockTags;
    }

    private static final class BlockProvider extends BlockTagsProvider {
        public BlockProvider(DataGenerator p_126511_, ExistingFileHelper existingFileHelper) {
            super(p_126511_, Names.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            ModBlocks.BLOCKS.getRegisteredBlocks().stream().filter(block -> block instanceof Mineable).forEach(block -> {
                MiningTool tool = ((Mineable) block).getTool();
                super.tag(tool.getToolTag()).add(block);
                super.tag(tool.getGetLevelTag()).add(block);
            });
        }
    }
}
