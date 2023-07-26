package com.khanhtypo.tothemoon.data.c;

import com.google.common.base.Preconditions;
import com.khanhtypo.tothemoon.ModUtils;
import com.khanhtypo.tothemoon.common.block.Workbench;
import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import com.khanhtypo.tothemoon.registration.elements.BasicBlock;
import com.khanhtypo.tothemoon.registration.elements.BlockObject;
import com.khanhtypo.tothemoon.registration.elements.ChildBlock;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static com.khanhtypo.tothemoon.registration.ModBlocks.*;

@SuppressWarnings("SameParameterValue")
public class ModBlockStateAndModelGenerator extends BlockStateProvider {
    protected static final ExistingFileHelper.ResourceType TEXTURE = new ExistingFileHelper.ResourceType(PackType.CLIENT_RESOURCES, ".png", "textures");
    private final ItemModelProvider itemModelProvider;
    private final ExistingFileHelper fileHelper;
    private final Map<Direction, Integer> yRotationMap = Util.make(new HashMap<>(), map -> {
        map.put(Direction.SOUTH, 180);
        map.put(Direction.WEST, 270);
        map.put(Direction.EAST, 90);
        map.put(Direction.DOWN, 0);
        map.put(Direction.UP, 0);
        map.put(Direction.NORTH, 0);
    });

    private final Map<Direction, Integer> xRotationMap = Util.make(new HashMap<>(), map -> {
        map.put(Direction.DOWN, 180);
        map.put(Direction.EAST, 90);
        map.put(Direction.NORTH, 90);
        map.put(Direction.SOUTH, 90);
        map.put(Direction.UP, 0);
        map.put(Direction.WEST, 90);
    });

    public ModBlockStateAndModelGenerator(PackOutput output, ItemModelProvider itemModelProvider, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
        this.itemModelProvider = itemModelProvider;
        this.fileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(COBBLED_METEORITE);
        this.simpleBlock(COBBLED_MOON_ROCK);
        this.simpleBlock(DEEPSLATE_URANIUM_ORE);
        this.simpleBlock(ERODED_METEORITE);
        this.simpleBlock(GILDED_METEORITE_BRICKS);
        this.simpleBlock(GOLD_SHEET_BLOCK);
        this.simpleBlock(IRON_SHEET_BLOCK);
        this.simpleBlock(COPPER_SHEET_BLOCK);
        this.simpleBlock(METEORITE);
        this.simpleBlock(METEORITE_BRICKS);
        this.simpleBlock(METEORITE_TILES);
        this.simpleBlock(METEORITE_LAMP);
        this.simpleBlock(METEORITE_ZIRCONIUM_ORE);
        this.simpleBlock(MOON_DUST);
        this.simpleBlock(MOON_GOLD_ORE);
        this.simpleBlock(MOON_IRON_ORE);
        this.simpleBlock(MOON_QUARTZ_ORE);
        this.simpleBlock(MOON_ROCK);
        this.simpleBlock(MOON_ROCK_BRICKS);
        this.simpleBlock(MOON_URANIUM_ORE);
        this.simpleBlock(POLISHED_METEORITE);
        this.simpleBlock(POLISHED_MOON_ROCK);
        this.simpleBlock(PROCESSED_WOOD_PLANKS);
        this.simpleBlock(PURE_ZIRCONIUM_BLOCK);
        this.simpleBlock(PURIFIED_QUARTZ_BLOCK);
        this.simpleBlock(RAW_URANIUM_BLOCK);
        this.simpleBlock(RAW_ZIRCONIUM_BLOCK);
        this.simpleBlock(REDSTONE_METAL_BLOCK);
        this.simpleBlock(REDSTONE_STEEL_ALLOY_BLOCK);
        this.simpleBlock(REDSTONE_STEEL_ALLOY_SHEET_BLOCK);
        this.simpleBlock(REINFORCED_WOOD);
        this.simpleBlock(SMOOTH_BLACKSTONE);
        this.simpleBlock(SMOOTH_METEORITE);
        this.simpleBlock(SMOOTH_PURIFIED_QUARTZ_BLOCK);
        this.simpleBlock(STEEL_BLOCK);
        this.simpleBlock(STEEL_SHEET_BLOCK);
        this.simpleBlock(URANIUM_BLOCK);
        this.simpleBlock(ZIRCONIUM_ALLOY_BLOCK);
        this.simpleBlock(ZIRCONIUM_BLOCK);
        this.simpleBlock(MOON_REDSTONE_ORE);
        this.notSimple();
    }

    private void notSimple() {
        ChildBlock.generateModel(this::slab, this::stoneWall, this::stair);
        this.modelPreExisted(COPPER_MACHINE_FRAME, "copper_frame");
        this.modelPreExisted(REDSTONE_METAL_MACHINE_FRAME, "redstone_frame");
        this.modelPreExisted(STEEL_MACHINE_FRAME, "steel_frame");

        //WORKBENCH
        super.getVariantBuilder(Workbench.getInstance()).forAllStates(state -> {
            boolean isRightPart = state.getValue(Workbench.IS_RIGHT);
            Direction facing = state.getValue(Workbench.FACING);
            ConfiguredModel.Builder<?> configuredModel = ConfiguredModel.builder().modelFile(new ModelFile.ExistingModelFile(ModUtils.location("block/workbench_" + (isRightPart ? "right" : "left")), this.fileHelper));
            if (yRotationMap.containsKey(facing)) {
                configuredModel.rotationY(yRotationMap.get(facing));
            }
            return configuredModel.build();
        });
        this.itemModels().basicItem(WORKBENCH.asItem());

        this.barrelBlock(MOON_ROCK_BARREL);
    }

    private void modelPreExisted(ObjectSupplier<? extends Block> block, String modelName) {
        final ResourceLocation modelLocation = ModUtils.location("block/" + modelName);
        super.getVariantBuilder(block.get())
                .partialState()
                .addModels(this.configuredModel(new ModelFile.ExistingModelFile(modelLocation, this.fileHelper)));
        this.itemModels().withExistingParent(block.getId().getPath(), modelLocation);
    }

    private void simpleBlock(ObjectSupplier<? extends Block> block) {
        ensureTexturePresent(block.getId());
        ModelFile saveLocation = super.cubeAll(block.get());
        super.simpleBlockWithItem(block.get(), saveLocation);
    }

    private ResourceLocation ensureTexturePresent(ResourceLocation blockId) {
        ResourceLocation textureId = blockId.getPath().contains("block/") ? blockId : blockId.withPrefix("block/");
        Preconditions.checkState(this.fileHelper.exists(textureId, TEXTURE), "Couldn't find block texture file [%s] for [%s]".formatted(textureId, blockId));
        return textureId;
    }

    private void stair(BasicBlock blockSupplier) {
        Block block = blockSupplier.get();
        ResourceLocation textureFromBlock = ensureTexturePresent(blockSupplier.getParentObject().getId());
        if (block instanceof StairBlock stairBlock) {
            super.stairsBlock(stairBlock, textureFromBlock);
            this.itemModelProvider.withExistingParent(blockSupplier.getId().getPath(), blockSupplier.getId().withPrefix("block/"));
        }
    }

    private void barrelBlock(BlockObject<BarrelBlock> barrelSupplier) {
        BarrelBlock barrelBlock = barrelSupplier.get();
        String blockName = barrelSupplier.getId().getPath();

        ResourceLocation blockPath = barrelSupplier.getId().withPrefix("block/");
        ResourceLocation bottom = this.ensureTexturePresent(blockPath.withSuffix("_bottom"));
        ResourceLocation side = this.ensureTexturePresent(blockPath.withSuffix("_side"));
        ResourceLocation top = this.ensureTexturePresent(blockPath.withSuffix("_top"));
        ResourceLocation topOpen = this.ensureTexturePresent(top.withSuffix("_open"));

        BlockModelBuilder closeModel = super.models().cubeBottomTop(blockName, side, bottom, top);
        BlockModelBuilder openModel = super.models().cubeBottomTop(blockName + "_open", side, bottom, topOpen);

        super.getVariantBuilder(barrelBlock).forAllStates(state -> {
            Direction facing = state.getValue(BarrelBlock.FACING);
            boolean open = state.getValue(BarrelBlock.OPEN);

            return this.configuredModel(
                    open ? openModel.getLocation() : closeModel.getLocation(),
                    false,
                    builder -> builder.rotationX(this.xRotationMap.get(facing)).rotationY(this.yRotationMap.get(facing)));
        });

        this.itemModels().withExistingParent(blockName, closeModel.getLocation());
    }

    private void slab(BasicBlock blockProvider) {
        SlabBlock slabBlock = ((SlabBlock) blockProvider.get());
        ResourceLocation parentLocation = this.ensureTexturePresent(blockProvider.getParentObject().getId());
        super.slabBlock(slabBlock, parentLocation, parentLocation);
        this.itemModelProvider.withExistingParent(blockProvider.getId().getPath(), blockProvider.getId().withPrefix("block/"));
    }

    private void stoneWall(BasicBlock blockProvider) {
        WallBlock wallBlock = ((WallBlock) blockProvider.get());
        super.wallBlock(wallBlock, this.ensureTexturePresent(blockProvider.getParentObject().getId()));
        this.itemModelProvider.withExistingParent(blockProvider.getId().getPath(), blockProvider.getParentObject().getId().withPrefix("block/"));
    }

    private ConfiguredModel configuredModel(ModelFile modelFile) {
        return new ConfiguredModel(modelFile);
    }

    private ConfiguredModel[] configuredModel(ResourceLocation modelLocation, boolean required, Consumer<ConfiguredModel.Builder<?>> builderConsumer) {
        ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(required ? new ModelFile.ExistingModelFile(modelLocation, this.fileHelper) : new ModelFile.UncheckedModelFile(modelLocation));
        builderConsumer.accept(builder);
        return builder.build();
    }

    @Override
    public ItemModelProvider itemModels() {
        return this.itemModelProvider;
    }
}
