package com.khanhtypo.tothemoon.common.item;

import com.google.common.base.Preconditions;
import com.khanhtypo.tothemoon.data.ModItemTags;
import com.khanhtypo.tothemoon.registration.ModItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ModArmorMaterials implements ArmorMaterial {
    public static final ModArmorMaterials URANIUM = new ModArmorMaterials(
            "uranium", 25,
            new int[]{2, 6, 6, 2},
            12,
            1.7f, 0.1f, SoundEvents.ARMOR_EQUIP_NETHERITE,
            null,
            () -> ModItems.URANIUM_ARMOR_PLATE);

    public static final ModArmorMaterials STEEL = new ModArmorMaterials(
            "steel", 21,
            new int[]{2, 6, 7, 2},
            10,
            1.7f, 0.15f, SoundEvents.ARMOR_EQUIP_NETHERITE,
            () -> ModItemTags.INGOT_STEEL,
            null);
    public static final ModArmorMaterials REDSTONE_STEEL = new ModArmorMaterials(
            "redstone_steel_alloy", 27,
            new int[]{3, 7, 6, 3},
            15,
            1.5f, 0.1f, SoundEvents.ARMOR_EQUIP_NETHERITE,
            () -> ModItemTags.INGOT_REDSTONE_STEEL,
            null);
    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266653_) -> {
        p_266653_.put(ArmorItem.Type.BOOTS, 13);
        p_266653_.put(ArmorItem.Type.LEGGINGS, 15);
        p_266653_.put(ArmorItem.Type.CHESTPLATE, 16);
        p_266653_.put(ArmorItem.Type.HELMET, 11);
    });
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> defensiveMap;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final Supplier<Ingredient> repairIngredient;
    private final String name;
    private final float toughness;
    private final float knockbackResistance;
    private final Map<ArmorItem.Type, BasicArmorItem> armorItemMap;
    private final @Nullable Supplier<TagKey<Item>> repairTag;
    private final @Nullable Supplier<ItemLike> repairItem;

    private ModArmorMaterials(String name, int multiplier, int[] defensive, int enchantability, float toughness, float knockbackResistance, SoundEvent equipSound, @Nullable Supplier<TagKey<Item>> repairTag, @Nullable Supplier<ItemLike> repairItem) {
        this.repairTag = repairTag;
        this.repairItem = repairItem;
        Preconditions.checkState(defensive.length == 4);
        Preconditions.checkState(!(repairTag == null && repairItem == null), "Repair Tag and Repair item can not be null at the same time");
        this.durabilityMultiplier = multiplier;
        this.defensiveMap = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.HELMET, defensive[0]);
            map.put(ArmorItem.Type.CHESTPLATE, defensive[1]);
            map.put(ArmorItem.Type.LEGGINGS, defensive[2]);
            map.put(ArmorItem.Type.BOOTS, defensive[3]);
        });
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.repairIngredient = repairTag != null ? () -> Ingredient.of(repairTag.get()) : () -> Ingredient.of(repairItem.get());
        this.name = name;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.armorItemMap = new HashMap<>();
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type armorType) {
        return this.durabilityMultiplier * HEALTH_FUNCTION_FOR_TYPE.get(armorType);
    }

    @Override
    public int getDefenseForType(ArmorItem.Type p_267168_) {
        return this.defensiveMap.get(p_267168_);
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    public void forEachArmorPiece(BiConsumer<ArmorItem.Type, BasicArmorItem> consumer) {
        this.armorItemMap.forEach(consumer);
    }

    public void put(ArmorItem.Type type, BasicArmorItem armorItem) {
        this.armorItemMap.put(type, armorItem);
    }

    public ItemPredicate getItemPredicate() {
        ItemPredicate.Builder builder= ItemPredicate.Builder.item();

        if (this.repairItem != null) {
            builder.of(this.repairItem.get());
        }

        if (this.repairTag != null) {
            builder.of(this.repairTag.get());
        }

        return builder.build();
    }
}
