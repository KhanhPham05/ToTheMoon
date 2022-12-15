package com.khanhpham.tothemoon.core.items;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.items.tool.ModArmorMaterial;
import com.khanhpham.tothemoon.datagen.recipes.builders.MetalPressRecipeBuilder;
import com.khanhpham.tothemoon.datagen.recipes.builders.OreProcessingBuilder;
import com.khanhpham.tothemoon.datagen.recipes.elements.ShortenIngredient;
import com.khanhpham.tothemoon.datagen.recipes.provider.ModRecipeProvider;
import com.khanhpham.tothemoon.datagen.tags.ModItemTags;
import com.khanhpham.tothemoon.datagen.tags.ModTagProvider;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.helpers.RegistryEntries;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class CraftingMaterial {
    public static final List<CraftingMaterial> ALL_MATERIALS = new LinkedList<>();
    private final RegistryObject<Item> plate;
    private final TagKey<Item> plateTag;
    private final RegistryObject<Item> dust;
    private final TagKey<Item> dustTag;
    private final RegistryObject<GearItem> gear;
    private final TagKey<Item> gearTag;
    private final RegistryObject<HandheldItem> rod;
    private final TagKey<Item> rodTag;
    private final DeferredRegister<Item> deferredRegister;
    private final String materialName;
    @Nullable
    private RegistryObject<HandheldItem> wire;
    @Nullable
    private TagKey<Item> wireTag;
    @Nullable
    private Supplier<Item> ingot;
    @Nullable
    private TagKey<Item> ingotTag;
    @Nullable
    private Pair<RegistryObject<Block>, TagKey<Item>> sheetmetalPair;
    @Nullable
    private Pair<RegistryObject<Block>, TagKey<Item>> storageBlockPair;
    @Nullable
    private Pair<RegistryObject<Block>, TagKey<Item>> rawOreBlockPair;
    @Nullable
    private Pair<RegistryObject<Item>, TagKey<Item>> rawOreItemPair;
    private Pair<Supplier<Block>, TagKey<Item>> oreBlockPair;
    private Pair<Supplier<Block>, TagKey<Item>> deepslateOrePair;
    @Nullable
    private Map<EquipmentSlot, RegistryObject<ModArmorItem>> armorSetMap;
    private boolean isIngotVanilla = false;

    public CraftingMaterial(DeferredRegister<Item> deferredRegister, String materialName) {
        this.deferredRegister = deferredRegister;
        this.materialName = materialName;

        this.plate = deferredRegister.register(materialName + "_plate", () -> new Item(new Item.Properties().tab(ToTheMoon.TAB)));
        this.plateTag = createTag("plates");
        this.dust = deferredRegister.register(materialName + "_dust", () -> new Item(new Item.Properties().tab(ToTheMoon.TAB)));
        this.dustTag = createTag("dusts");
        this.gear = deferredRegister.register(materialName + "_gear", () -> new GearItem(new Item.Properties().tab(ToTheMoon.TAB)));
        this.gearTag = createTag("gears");
        this.rod = deferredRegister.register(materialName + "_rod", () -> new HandheldItem(new Item.Properties().tab(ToTheMoon.TAB)));
        this.rodTag = createTag("rods");

        ALL_MATERIALS.add(this);
    }

    public CraftingMaterial setRawMaterial(RegistryObject<Block> rawOreBlock) {
        Preconditions.checkState(this.rawOreBlockPair == null, "Raw material is already bounded");
        Preconditions.checkState(this.rawOreItemPair == null, "Raw ore is already bounded");
        this.rawOreBlockPair = Pair.of(rawOreBlock, ItemTags.create(new ResourceLocation("forge:storage_blocks/raw_" + this.materialName)));
        this.rawOreItemPair = Pair.of(this.deferredRegister.register("raw_" + this.materialName, () -> new Item(new Item.Properties().tab(ToTheMoon.TAB))), createTag("raw_materials"));
        return this;
    }

    public CraftingMaterial setIngot() {
        return this.setMainMaterial(deferredRegister.register(materialName + "_ingot", () -> new Item(new Item.Properties().tab(ToTheMoon.TAB))),
                createTag("ingots"));
    }

    public CraftingMaterial setWire() {
        Preconditions.checkState(this.wire == null || this.wireTag == null, "Wire is already bounded");
        this.wire = deferredRegister.register(materialName + "_wire", () -> new HandheldItem(new Item.Properties().tab(ToTheMoon.TAB)));
        this.wireTag = createTag("wires");
        return this;
    }

    private TagKey<Item> createTag(String tagName) {
        return ItemTags.create(new ResourceLocation("forge", tagName + "/" + this.materialName));
    }

    public CraftingMaterial setSheetmetal(RegistryObject<Block> sheetmetalBlock) {
        Preconditions.checkState(this.sheetmetalPair == null, "Sheet Metal is already bounded");
        this.sheetmetalPair = Pair.of(sheetmetalBlock, createTag("sheetmetals"));
        return this;
    }

    public CraftingMaterial storageBlock(RegistryObject<Block> storageBlock) {
        Preconditions.checkState(this.storageBlockPair == null, "Storage Block is already bounded");
        this.storageBlockPair = Pair.of(storageBlock, createTag("storage_blocks"));
        return this;
    }

    public Item getPlate() {
        return plate.get();
    }

    public @NotNull Item getDust() {
        return dust.get();
    }

    public @NotNull GearItem getGear() {
        return gear.get();
    }

    public @NotNull HandheldItem getRod() {
        return rod.get();
    }

    public HandheldItem getWire() {
        if (this.wire != null) {
            return wire.get();
        }
        throw new IllegalStateException("Wire is not represent for material " + this.getMaterialName());
    }

    public String getMaterialName() {
        return materialName;
    }

    public void generateRecipes(Consumer<FinishedRecipe> consumer) {
        if (this.getIngotTag() != null && this.ingot != null) {
            MetalPressRecipeBuilder.press(this.getIngotTag(), ModItemTags.PLATE_MOLD, this.getPlate(), 2).save(consumer, "tothemoon:metal_pressing/plate/" + this.plate.getId().getPath());
            MetalPressRecipeBuilder.press(this.getIngotTag(), ModItemTags.GEAR_MOLD, this.getGear(), 1).save(consumer, "tothemoon:metal_pressing/gear/" + this.gear.getId().getPath());
            MetalPressRecipeBuilder.press(this.getIngotTag(), ModItemTags.ROD_MOLD, this.getRod(), 2).save(consumer, "tothemoon:metal_pressing/rod/" + this.rod.getId().getPath() + "_from_ingot");
            MetalPressRecipeBuilder.press(this.getPlateTag(), ModItemTags.ROD_MOLD, this.getRod(), 1).save(consumer, "tothemoon:metal_pressing/rod/"+ this.rod.getId().getPath() + "_from_plate");

            ShapelessRecipeBuilder.shapeless(this.getRod(), 1).unlockedBy("tick", ModRecipeProvider.tick()).requires(ModItemTags.GENERAL_HAMMERS).requires(this.getPlateTag()).save(consumer, ModUtils.modLoc("shapeless_crafting/" + this.rod.getId().getPath()));
            ShapelessRecipeBuilder.shapeless(this.getPlate(), 1).unlockedBy("tick", ModRecipeProvider.tick()).requires(ModItemTags.GENERAL_HAMMERS).requires(this.getIngotTag()).save(consumer, ModUtils.modLoc("shapeless_crafting/" + this.plate.getId().getPath()));
            SimpleCookingRecipeBuilder.blasting(Ingredient.of(this.getDustTag()), this.getIngot(), 1.0f, 100).unlockedBy("tick", ModRecipeProvider.tick()).save(consumer, ModUtils.modLoc("smelting/" + RegistryEntries.ITEM.getPath(ingot.get())));

            if (this.wire != null) {
                ShapelessRecipeBuilder.shapeless(this.getWire(), 1)
                        .unlockedBy("tick", ModRecipeProvider.tick())
                        .requires(ModItemTags.GENERAL_HAMMERS)
                        .requires(this.getRodTag())
                        .save(consumer, ModUtils.modLoc("shapeless_crafting/" + this.wire.getId().getPath()));
            }

            ModUtils.checkNullAndDo(this.storageBlockPair, pair -> {
                RegistryObject<Block> storageBlock = pair.getFirst();
                ShapelessRecipeBuilder.shapeless(this.getIngot(), 9).unlockedBy("tick", ModRecipeProvider.tick()).requires(pair.getSecond()).save(consumer, ModUtils.modLoc("shapeless_crafting/" + RegistryEntries.ITEM.getPath(this.getIngot())));
            });
        }

        ModUtils.checkNullAndDo(this.sheetmetalPair, pair -> {
            RegistryObject<Block> blockRegistryObject = pair.getFirst();
            ShapelessRecipeBuilder.shapeless(this.getPlate(), 8).unlockedBy("tick", ModRecipeProvider.tick()).requires(pair.getSecond()).save(consumer, ModUtils.modLoc("shapeless_crafting/" + this.plate.getId().getPath() + "_from_" + blockRegistryObject.getId().getPath()));
            ShapedRecipeBuilder.shaped(blockRegistryObject.get()).pattern("AAA").pattern("A A").pattern("AAA").define('A', this.getPlate()).unlockedBy("tick", ModRecipeProvider.tick()).save(consumer, ModUtils.modLoc("crafting/" + blockRegistryObject.getId().getPath()));
        });

        if (this.rawOreBlockPair != null && this.rawOreItemPair != null) {
            RegistryObject<Block> rawOreBlock = this.rawOreBlockPair.getFirst();
            RegistryObject<Item> rawOreItem = this.rawOreItemPair.getFirst();

            ShapelessRecipeBuilder.shapeless(rawOreItem.get(), 9).unlockedBy("tick", ModRecipeProvider.tick()).requires(rawOreBlock.get()).save(consumer, ModUtils.modLoc("shapeless_crafting/" + rawOreItem.getId().getPath()));
            ShapedRecipeBuilder.shaped(rawOreBlock.get()).pattern("AAA").pattern("AAA").pattern("AAA").unlockedBy("tick", ModRecipeProvider.tick()).define('A', rawOreItem.get()).save(consumer, ModUtils.modLoc("crafting/" + rawOreBlock.getId().getPath()));
        }

        if (this.rawOreItemPair != null) {
            OreProcessingBuilder.process(this.getDust(), ShortenIngredient.create().add(rawOreItemPair.getSecond()));
        }
        if (!this.isIngotVanilla) {
            ModUtils.checkNullAndDo(this.oreBlockPair, pair -> {
                SimpleCookingRecipeBuilder.blasting(Ingredient.of(pair.getSecond()), this.getIngot(), 1.0f, 100).unlockedBy("tick", ModRecipeProvider.tick()).save(consumer, ModUtils.modLoc("blasting/" + RegistryEntries.ITEM.getPath(this.getIngot())));
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(pair.getSecond()), this.getIngot(), 1.0f, 200).unlockedBy("tick", ModRecipeProvider.tick()).save(consumer, "smelting/" + RegistryEntries.ITEM.getPath(this.getIngot()));
            });

            ModUtils.checkNullAndDo(this.deepslateOrePair, pair -> {
                SimpleCookingRecipeBuilder.blasting(Ingredient.of(pair.getSecond()), this.getIngot(), 1.0f, 100).unlockedBy("tick", ModRecipeProvider.tick()).save(consumer, ModUtils.modLoc("blasting/deepslate_" + RegistryEntries.ITEM.getPath(this.getIngot())));
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(pair.getSecond()), this.getIngot(), 1.0f, 200).unlockedBy("tick", ModRecipeProvider.tick()).save(consumer, "smelting/deepslate_" + RegistryEntries.ITEM.getPath(this.getIngot()));
            });
        }


        ModUtils.checkNullAndDo(this.armorSetMap, map -> map.forEach(
                (equipmentSlot, armorItemRegistryObject) -> {
                    ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(armorItemRegistryObject.get()).unlockedBy("tick", ModRecipeProvider.tick()).define('A', armorItemRegistryObject.get().getCraftItem());
                    switch (equipmentSlot) {
                        case FEET -> builder.pattern("A A").pattern("A A");
                        case HEAD -> builder.pattern("AAA").pattern("A A");
                        case LEGS -> builder.pattern("AAA").pattern("A A").pattern("A A");
                        default -> builder.pattern("A A").pattern("AAA").pattern("AAA");
                    }
                    builder.save(consumer, ModUtils.modLoc("crafting/" + armorItemRegistryObject.getId().getPath()));
                }
        ));

        if (this.oreBlockPair != null || this.deepslateOrePair != null) {
            OreProcessingBuilder.process(this.getDust(), 2, ShortenIngredient.create().add(createTag("ores"))).doubleChance(50);

        }
    }

    public void addTags(ModTagProvider.ModItemTagsProvider tagProvider) {
        if (this.wireTag != null && this.wire != null) {
            add(tagProvider, ModItemTags.WIRES, this.wireTag, this.wire);
        }

        ModUtils.checkNullAndDo(this.sheetmetalPair, pair -> add(tagProvider, ModItemTags.GENERAL_SHEETMETALS, pair.getSecond(), pair.getFirst()));
        ModUtils.checkNullAndDo(this.storageBlockPair, pair -> add(tagProvider, ModItemTags.GENERAL_STORAGE_BLOCKS, pair.getSecond(), pair.getFirst()));
        ModUtils.checkNullAndDo(this.rawOreBlockPair, pair -> add(tagProvider, Tags.Items.STORAGE_BLOCKS, pair.getSecond(), pair.getFirst()));
        ModUtils.checkNullAndDo(this.rawOreItemPair, pair -> add(tagProvider, Tags.Items.RAW_MATERIALS, pair.getSecond(), pair.getFirst()));

        if (!this.isIngotVanilla) {
            if (this.ingotTag != null && this.ingot != null) {
                add(tagProvider, Tags.Items.INGOTS, this.ingotTag, this.ingot);
            }
            ModUtils.checkNullAndDo(this.oreBlockPair, pair -> add(tagProvider, Tags.Items.ORES, pair.getSecond(), pair.getFirst()));
            ModUtils.checkNullAndDo(this.deepslateOrePair, pair -> add(tagProvider, Tags.Items.ORES, pair.getSecond(), pair.getFirst()));
        }
        add(tagProvider, ModItemTags.GENERAL_PLATES, this.plateTag, this.plate);
        add(tagProvider, Tags.Items.DUSTS, this.dustTag, this.dust);
        add(tagProvider, Tags.Items.RODS, this.plateTag, this.plate);
        add(tagProvider, ModItemTags.GENERAL_PLATES, this.plateTag, this.plate);
        tagProvider.add(getDustTag(), this.dust);
        tagProvider.add(getRodTag(), this.rod);
        tagProvider.add(getGearTag(), this.gear);
    }

    private void add(ModTagProvider.ModItemTagsProvider tagProvider, TagKey<Item> rootTag, @Nullable TagKey<Item> parentTag, @Nullable Supplier<? extends ItemLike> item) {
        if (parentTag != null && item != null) {
            tagProvider.tag(rootTag).addTag(parentTag);
            tagProvider.tag(parentTag).add(item.get().asItem());
        }
    }

    public Item getIngot() {
        Preconditions.checkNotNull(ingot, "Ingot is not represent for material " + this.materialName);
        return ingot.get();
    }

    public CraftingMaterial setVanillaIngot(Item vanillaItem) {
        this.isIngotVanilla = true;
        return this.setMainMaterial(() -> vanillaItem, createTag("ingots"));
    }

    @Nullable
    public TagKey<Item> getIngotTag() {
        return ingotTag;
    }

    public TagKey<Item> getPlateTag() {
        return plateTag;
    }

    public TagKey<Item> getDustTag() {
        return dustTag;
    }

    public TagKey<Item> getGearTag() {
        return gearTag;
    }

    public TagKey<Item> getRodTag() {
        return rodTag;
    }

    public TagKey<Item> getWireTag() {
        Preconditions.checkNotNull(this.wireTag);
        return wireTag;
    }

    public CraftingMaterial setArmor(ModArmorMaterial armorMaterial) {
        Preconditions.checkState(this.ingot != null, "Material ingot must present before setting an armor set");
        return setArmor(armorMaterial, this.ingot);
    }

    public CraftingMaterial setArmor(ModArmorMaterial armorMaterial, @Nullable Supplier<Item> craftedFrom) {
        if (craftedFrom != null) {
            final Map<EquipmentSlot, String> map = ImmutableMap.of(EquipmentSlot.HEAD, "helmet", EquipmentSlot.CHEST, "chestplate", EquipmentSlot.LEGS, "leggings", EquipmentSlot.FEET, "boots");

            this.armorSetMap = new HashMap<>();
            List<EquipmentSlot> armorSlots = ModUtils.collectCondition(List.of(EquipmentSlot.values()), (slot) -> slot.getType() == EquipmentSlot.Type.ARMOR);
            for (EquipmentSlot armorSlot : armorSlots) {
                this.armorSetMap.put(armorSlot, this.deferredRegister.register(this.materialName + "_" + map.get(armorSlot), () -> new ModArmorItem(craftedFrom.get(), armorMaterial, armorSlot, ModItems.defaultProperties())));
            }
        }
        return this;
    }

    private CraftingMaterial setMainMaterial(Supplier<Item> item, TagKey<Item> itemTagKey) {
        Preconditions.checkState(this.ingot == null || this.ingotTag == null, "Main material is already bounded");
        this.ingot = item;
        this.ingotTag = itemTagKey;
        return this;
    }

    //Mainly used for vanilla stuff
    public CraftingMaterial setGem(Item itemSupplier) {
        this.isIngotVanilla = true;
        return this.setMainMaterial(() -> itemSupplier, createTag("gems"));
    }

    public ModArmorItem getArmorItem(EquipmentSlot slot) {
        Preconditions.checkState(this.armorSetMap != null, "No armor set is defined for material " + this.materialName.toUpperCase(Locale.ROOT));
        return this.armorSetMap.get(slot).get();
    }

    public Stream<RegistryObject<ModArmorItem>> getArmorItems() {
        Preconditions.checkState(this.armorSetMap != null, "No armor set is defined for material " + this.materialName.toUpperCase(Locale.ROOT));
        return this.armorSetMap.values().stream();
    }

    public CraftingMaterial setOre(Supplier<Block> oreBlock) {
        Preconditions.checkState(this.ingot != null, "Ingot is not represent");
        Preconditions.checkState(this.oreBlockPair == null, "Ore block has already been set");
        this.oreBlockPair = Pair.of(oreBlock, createTag("ores"));
        return this;
    }

    public CraftingMaterial setDeepslateOre(Supplier<Block> deepslateOre) {
        Preconditions.checkState(this.ingot != null, "Ingot is not represent");
        Preconditions.checkState(this.deepslateOrePair == null, "Deepslate ore block has already been set");
        this.deepslateOrePair = Pair.of(deepslateOre, createTag("ores"));
        return this;
    }

    public CraftingMaterial setOres(Supplier<Block> oreBlock, Supplier<Block> deepslateOre) {
        return this.setOre(oreBlock).setDeepslateOre(deepslateOre);
    }

    public CraftingMaterial setOres(Block vanillaOreItem, Block vanillaDeepslateOreItem) {
        return this.setOres(() -> vanillaOreItem, () -> vanillaDeepslateOreItem);
    }

}