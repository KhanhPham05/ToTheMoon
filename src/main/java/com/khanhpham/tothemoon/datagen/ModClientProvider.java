package com.khanhpham.tothemoon.datagen;


import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.registries.ModBlocks;
import com.khanhpham.tothemoon.registries.ModItems;
import com.khanhpham.tothemoon.util.Identity;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @see net.minecraft.client
 */
public class ModClientProvider {
    private final DataGenerator data;
    private final ExistingFileHelper fileHelper;
    private final String modid = ToTheMoon.ID;

    public ModClientProvider(DataGenerator data, ExistingFileHelper fileHelper) {
        this.data = data;
        this.fileHelper = fileHelper;
    }

    public IDataProvider itemModel() {
        return new ModItemModel(data, modid, fileHelper);
    }

    @Deprecated
    public IDataProvider blockModel() {
        return new ModBlockModel(data, modid, fileHelper);
    }


    public IDataProvider blockState() {
        return new ModBlockState(data, modid, fileHelper);
    }


    static final class ModItemModel extends ItemModelProvider {

        public ModItemModel(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
            super(generator, modid, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

            item(itemGenerated, ModItems.COPPER_INGOT);
            item(itemGenerated, ModItems.REDSTONE_STEEL_INGOT);

            blockItem(ModBlocks.REDSTONE_STEEL_ALLOY_BLOCK);
            blockItem(ModBlocks.COPPER_BLOCK);
            blockItem(ModBlocks.ENERGY_BANK);
        }

        <BLOCK extends Block> void blockItem(Supplier<BLOCK> blockSupplier) {
            ResourceLocation location = Objects.requireNonNull(blockSupplier.get().getRegistryName());
            String blockId = location.getPath();
            withExistingParent(blockId, modLoc("block/" + blockId));
        }

        private void item(ModelFile modelfile, Supplier<Item> itemSupplier) {
            ResourceLocation location = Objects.requireNonNull(itemSupplier.get().getRegistryName());
            this.item(modelfile, location.getPath());
        }

        private void item(ModelFile itemGenerated, String name) {
            getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
        }

        @Nonnull
        public String getName() {
            return "TTM Item Models";
        }
    }

    static final class ModBlockState extends BlockStateProvider {
        public ModBlockState(DataGenerator gen, String modid, ExistingFileHelper fileHelper) {
            super(gen, modid, fileHelper);

        }

        @Override
        protected void registerStatesAndModels() {
            simpleBlock(ModBlocks.REDSTONE_STEEL_ALLOY_BLOCK.get());
            simpleBlock(ModBlocks.COPPER_BLOCK.get());
            horizontalBlock(ModBlocks.ENERGY_BANK.get(), Identity.mod("block/energy_bank_sides"), Identity.mod("block/energy_bank_sides"), Identity.mod("block/energy_bank_top"));
        }

        @Nonnull
        public String getName() {
            return "TTM blockstate";
        }
    }

    @Deprecated
    static final class ModBlockModel extends BlockModelProvider {
        public ModBlockModel(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
            super(generator, modid, existingFileHelper);
            throw new RuntimeException("I told you ! " + this + " should not be called since it is deprecated !");
        }

        @Override
        protected void registerModels() {
            build(ModBlocks.REDSTONE_STEEL_ALLOY_BLOCK);
            build(ModBlocks.COPPER_BLOCK);
        }

        void build(Supplier<Block> block) {
            ResourceLocation location = Objects.requireNonNull(block.get().getRegistryName());
            String blockId = location.getPath();
            withExistingParent(blockId, modLoc("block/" + blockId));
        }

        @Nonnull
        public String getName() {
            return "TTM block Models";
        }
    }
}
