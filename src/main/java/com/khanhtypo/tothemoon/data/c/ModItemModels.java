package com.khanhtypo.tothemoon.data.c;

import com.google.common.base.Preconditions;
import com.khanhtypo.tothemoon.utls.ModUtils;
import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.khanhtypo.tothemoon.registration.ModItems.*;

public class ModItemModels extends ItemModelProvider {
    private final ModelFile gearTemplate = new ModelFile.ExistingModelFile(ModUtils.location("item/templates/gear_template"), existingFileHelper);
    private final ModelFile generatedTemplate = new ModelFile.ExistingModelFile(new ResourceLocation("item/generated"), existingFileHelper);
    private final ModelFile handheldTemplate = new ModelFile.ExistingModelFile(new ResourceLocation("item/handheld"), existingFileHelper);
    public ModItemModels(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.basicItem(CPU_CHIP);
        this.basicItem(AMETHYST_DUST);
        this.gearModel(AMETHYST_GEAR);
        this.basicItem(AMETHYST_PLATE);
        this.basicItem(AMETHYST_ROD);
        this.basicItem(COPPER_DUST);
        this.gearModel(COPPER_GEAR);
        this.basicItem(COPPER_NUGGET);
        this.basicItem(COPPER_PLATE);
        this.basicItem(COPPER_ROD);
        this.basicItem(COPPER_WIRE);
        this.basicItem(BLANK_PRESS_MOLD);
        this.basicItem(CIRCUIT_BOARD);
        this.basicItem(COAL_DUST);
        this.basicItem(DIAMOND_DUST);
        this.gearModel(DIAMOND_GEAR);
        this.handheldModel(DIAMOND_HAMMER);
        this.basicItem(DIAMOND_PLATE);
        this.basicItem(DIAMOND_ROD);
        this.basicItem(EMERALD_DUST);
        this.gearModel(EMERALD_GEAR);
        this.basicItem(EMERALD_PLATE);
        this.basicItem(EMERALD_ROD);
        this.basicItem(GEAR_MOLD);
        this.basicItem(GOLD_DUST);
        this.gearModel(GOLD_GEAR);
        this.basicItem(GOLD_PLATE);
        this.basicItem(GOLD_ROD);
        this.basicItem(GOLD_WIRE);
        this.basicItem(GUIDE_BOOK);
        this.basicItem(HEATED_COAL_DUST);
        this.basicItem(IRON_DUST);
        this.gearModel(IRON_GEAR);
        this.basicItem(IRON_PLATE);
        this.basicItem(IRON_ROD);
        this.basicItem(IRON_WIRE);
        this.basicItem(LAPIS_DUST);
        this.gearModel(LAPIS_GEAR);
        this.basicItem(LAPIS_PLATE);
        this.basicItem(LAPIS_ROD);
        this.basicItem(NETHERITE_DUST);
        this.gearModel(NETHERITE_GEAR);
        this.handheldModel(NETHERITE_HAMMER);
        this.basicItem(NETHERITE_PLATE);
        this.basicItem(NETHERITE_ROD);
        this.basicItem(PURIFIED_QUARTZ);
        this.basicItem(PLATE_MOLD);
        this.basicItem(PROCESSOR_CHIP);
        this.basicItem(QUARTZ_DUST);
        this.gearModel(QUARTZ_GEAR);
        this.basicItem(QUARTZ_PLATE);
        this.basicItem(QUARTZ_ROD);
        this.basicItem(RAW_URANIUM_CHUNK);
        this.basicItem(ROD_MOLD);
        this.handheldModel(WOODEN_HAMMER);

        this.basicItem(REDSTONE_METAL_DUST);
        this.gearModel(REDSTONE_METAL_GEAR);
        this.basicItem(REDSTONE_METAL_INGOT);
        this.basicItem(REDSTONE_METAL_PLATE);
        this.basicItem(REDSTONE_METAL_ROD);
        this.basicItem(REDSTONE_METAL_WIRE);

        this.basicItem(REDSTONE_STEEL_BOOTS);
        this.basicItem(REDSTONE_STEEL_CHESTPLATE);
        this.basicItem(REDSTONE_STEEL_HELMET);
        this.basicItem(REDSTONE_STEEL_LEGGINGS);
        this.basicItem(REDSTONE_STEEL_DUST);
        this.gearModel(REDSTONE_STEEL_GEAR);
        this.basicItem(REDSTONE_STEEL_INGOT);
        this.basicItem(REDSTONE_STEEL_PLATE);
        this.basicItem(REDSTONE_STEEL_ROD);
        this.basicItem(REDSTONE_STEEL_WIRE);

        this.basicItem(STEEL_INGOT);
        this.basicItem(STEEL_DUST);
        this.gearModel(STEEL_GEAR);
        this.basicItem(STEEL_PLATE);
        this.basicItem(STEEL_ROD);
        this.basicItem(STEEL_WIRE);
        this.handheldModel(STEEL_HAMMER);
        this.handheldModel(STEEL_SWORD);
        this.handheldModel(STEEL_SHOVEL);
        this.handheldModel(STEEL_PICKAXE);
        this.handheldModel(STEEL_AXE);
        this.handheldModel(STEEL_HOE);
        this.basicItem(STEEL_BOOTS);
        this.basicItem(STEEL_LEGGINGS);
        this.basicItem(STEEL_CHESTPLATE);
        this.basicItem(STEEL_HELMET);

        this.basicItem(URANIUM_ARMOR_PLATE);
        this.basicItem(URANIUM_INGOT);
        this.handheldModel(URANIUM_AXE);
        this.handheldModel(URANIUM_HOE);
        this.handheldModel(URANIUM_PICKAXE);
        this.handheldModel(URANIUM_SHOVEL);
        this.handheldModel(URANIUM_SWORD);
        this.basicItem(URANIUM_BOOTS);
        this.basicItem(URANIUM_CHESTPLATE);
        this.basicItem(URANIUM_HELMET);
        this.basicItem(URANIUM_LEGGINGS);
        this.basicItem(URANIUM_DUST);
        this.gearModel(URANIUM_GEAR);
        this.basicItem(URANIUM_PLATE);
        this.basicItem(URANIUM_ROD);
        this.basicItem(URANIUM_WIRE);

        this.basicItem(ZIRCONIUM_PURE);
        this.basicItem(RAW_ZIRCONIUM);
        this.basicItem(ZIRCONIUM_ALLOY);
        this.basicItem(ZIRCONIUM_INGOT);
    }

    private void basicItem(ObjectSupplier<? extends Item> itemObjectSupplier) {
        this.basicItem(itemObjectSupplier.getId());
    }

    @Override
    public ItemModelBuilder basicItem(ResourceLocation item) {
        ResourceLocation textureId = new ResourceLocation(item.getNamespace(), "item/" + item.getPath());
        checkTextureExist(textureId, item);
        return getBuilder(item.toString())
                .parent(generatedTemplate)
                .texture("layer0", textureId);
    }

    private void gearModel(ObjectSupplier<Item> itemSupplier) {
        ResourceLocation modelId = itemSupplier.getId();
        ResourceLocation textureId = itemSupplier.getId().withPrefix("item/");
        checkTextureExist(textureId, modelId);
        super.getBuilder(modelId.toString()).parent(gearTemplate).texture("gear", textureId);
    }

    private void handheldModel(ObjectSupplier<?> supplier) {
        ResourceLocation saveId = supplier.getId();
        ResourceLocation textureId = saveId.withPrefix("item/");
        checkTextureExist(textureId, saveId);
        super.getBuilder(saveId.toString()).parent(handheldTemplate).texture("layer0", textureId);
    }

    private void checkTextureExist(ResourceLocation textureId, ResourceLocation itemSaveId) {
        final ResourceLocation checkerPath = textureId.withPrefix("textures/").withSuffix(".png");
        Preconditions.checkState(existingFileHelper.exists(checkerPath, PackType.CLIENT_RESOURCES), "Texture Path [%s] for item [%s] is not exist in assets files".formatted(checkerPath, itemSaveId));
    }

    @Override
    protected void clear() {}
}
