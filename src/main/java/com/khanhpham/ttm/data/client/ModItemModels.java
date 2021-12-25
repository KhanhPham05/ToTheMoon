package com.khanhpham.ttm.data.client;

import com.khanhpham.ttm.ToTheMoonMain;
import com.khanhpham.ttm.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public final class ModItemModels extends ItemModelProvider {
    private final ModelFile modelFile = getExistingFile(mcLoc("item/generated"));

    public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ToTheMoonMain.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        blockItem(ModBlocks.MOON_SURFACE_STONE_BRICKS);
        blockItem(ModBlocks.MOON_SURFACE_STONE);

    }

    @NotNull
    @Override
    public String getName() {
        return "TTM Item Models";
    }

    private void blockItem(Block block) {
        String name = Objects.requireNonNull(block.getRegistryName()).getPath();
        getBuilder(name).parent(getExistingFile(modLoc("block/" + name)));
    }

    private void build(Item item) {
        String name = Objects.requireNonNull(item.getRegistryName()).getPath();
        getBuilder(name).parent(modelFile).texture("layer0", "item/" + name);
    }
}
