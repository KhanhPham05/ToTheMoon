package com.khanhpham.ttm.data.client;


import com.khanhpham.ttm.ToTheMoonMain;
import com.khanhpham.ttm.core.blocks.BaseEnergyGenBlock;
import com.khanhpham.ttm.core.blocks.variants.BaseBlock;
import com.khanhpham.ttm.core.blocks.variants.MineableSlabBlock;
import com.khanhpham.ttm.init.ModBlocks;
import com.khanhpham.ttm.utils.block.ModCapableBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.Objects;

public final class ModBlockStates extends BlockStateProvider {
    private final ExistingFileHelper fileHelper;

    public ModBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ToTheMoonMain.MOD_ID, exFileHelper);
        this.fileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.BLOCKS.getEntries().forEach(this::build);

    }

    private void build(Block block) {
        if (block instanceof BaseEnergyGenBlock<?>) {
            generator(block);
        } else if (block instanceof MineableSlabBlock) {
            slab((MineableSlabBlock) block);
        } else if (block instanceof BaseBlock) {
            simpleBlock(block);
        }
    }

    private void slab(MineableSlabBlock slabBlock) {

        VariantBlockStateBuilder.PartialBlockstate builder = getVariantBuilder(slabBlock).partialState();
        builder.with(SlabBlock.TYPE, SlabType.BOTTOM).addModels(getModel(modLoc("block/" + Objects.requireNonNull(slabBlock.getRegistryName()).getPath())));
        builder.with(SlabBlock.TYPE, SlabType.DOUBLE).addModels(getModel(modLoc(slabBlock.getParentBlockModelFile())));
        builder.with(SlabBlock.TYPE, SlabType.TOP).addModels(getModel(modLoc("block/" + Objects.requireNonNull(slabBlock.getRegistryName()).getPath() + "_top")));
    }

    private ConfiguredModel getModel(ResourceLocation rl) {
        return new ConfiguredModel(new ModelFile.ExistingModelFile(rl, fileHelper));
    }

    private ConfiguredModel getModel(ResourceLocation rl, int rotX, int rotY, boolean uvLock) {
        return new ConfiguredModel(new ModelFile.ExistingModelFile(rl, fileHelper), rotX, rotY, uvLock);
    };

    private void generator(Block block) {
        VariantBlockStateBuilder.PartialBlockstate builder = getVariantBuilder(block).partialState();
        Arrays.stream(Direction.values()).forEach(direction -> {
                    int rotY = 0;
                    if (direction == Direction.EAST || direction == Direction.SOUTH || direction == Direction.NORTH || direction == Direction.WEST) {

                        if (direction == Direction.EAST) {
                            rotY = 90;
                        } else if (direction == Direction.SOUTH) {
                            rotY = 180;
                        } else if (direction == Direction.WEST) {
                            rotY = 270;
                        }

                        builder.with(ModCapableBlock.FACING, direction).with(BaseEnergyGenBlock.LIT, Boolean.FALSE)
                                .addModels(getModel(modLoc("block/copper_energy_generator"), 0, rotY, false));
                        builder.with(ModCapableBlock.FACING, direction).with(BaseEnergyGenBlock.LIT, Boolean.TRUE)
                                .addModels(getModel(modLoc("block/copper_energy_generator_on"), 0, rotY, false));
                    }
                }
        );
    }
}