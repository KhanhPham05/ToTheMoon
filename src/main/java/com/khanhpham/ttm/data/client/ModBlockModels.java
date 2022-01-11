package com.khanhpham.ttm.data.client;

import com.khanhpham.ttm.ToTheMoonMain;
import com.khanhpham.ttm.core.blocks.BaseEnergyGenBlock;
import com.khanhpham.ttm.core.blocks.variants.BaseBlock;
import com.khanhpham.ttm.core.blocks.variants.MineableSlabBlock;
import com.khanhpham.ttm.init.ModBlocks;
import com.khanhpham.ttm.testfeatures.EnergyGenBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @see net.minecraft.data.models.BlockModelGenerators
 */
public final class ModBlockModels extends BlockModelProvider {
    public ModBlockModels(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ToTheMoonMain.MOD_ID, exFileHelper);
    }

    private String getBlockId(Block block) {
        return Objects.requireNonNull(block.getRegistryName()).getPath();
    }

    @Override
    protected void registerModels() {
        generate();
    }

    private void simpleBlock(Block block) {
        cubeAll(getBlockId(block), modLoc("block/" + getBlockId(block)));
    }

    private void generate() {
        Set<Block> set = ModBlocks.BLOCKS.getEntries();

        set.stream().filter(block -> block instanceof MineableSlabBlock)
                .forEach(block -> {
                    String path = Objects.requireNonNull(block.getRegistryName()).getPath();
                    slabTop(path + "_top", modLoc("block/" + ((MineableSlabBlock) block).getParentBlockId()), modLoc("block/" + ((MineableSlabBlock) block).getParentBlockId()), modLoc("block/" + ((MineableSlabBlock) block).getParentBlockId()));
                    ToTheMoonMain.LOG.info("Generating [ {} ] with type [ {} ]", path + "_top", BLOCK_FOLDER + "/slab_top");

                    slab(path, modLoc("block/" + ((MineableSlabBlock) block).getParentBlockId()), modLoc("block/" + ((MineableSlabBlock) block).getParentBlockId()), modLoc("block/" + ((MineableSlabBlock) block).getParentBlockId()));
                    ToTheMoonMain.LOG.info("Generating [ {} ] with type [ {} ]", path, BLOCK_FOLDER + "/slab");});
        set.stream().filter(block -> block instanceof BaseBlock).forEach(this::simpleBlock);
        set.stream().filter(block -> block instanceof BaseEnergyGenBlock<?>).forEach(this::generatorBlock);
    }

    private void generatorBlock(Block block) {
        if (block instanceof EnergyGenBlock) return;
        String name = Objects.requireNonNull(block.getRegistryName()).getPath();

        generatorBlock(name, "block/" + name + "_side", "block/" + name + "_front", "block/" + name + "_bottom", "block/" + name + "_top");
    }

    public void generatorBlock(String name, String side, String frontOFF, String bottom, String top) {

        var sideRl = modLoc(side);
        var frontOffRl = modLoc(frontOFF);
        var frontOnRl = modLoc(frontOFF + "_on");
        var bottomRl = modLoc(bottom);
        var topRl = modLoc(top);

        super.orientableWithBottom(name, sideRl, frontOffRl, bottomRl, topRl);
        super.orientableWithBottom(name + "_on", sideRl, frontOnRl, bottomRl, topRl);
    }
}
