package com.khanhpham.tothemoon.core.items.tool;

import com.khanhpham.tothemoon.Names;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

@MethodsReturnNonnullByDefault
public enum ModArmorMaterial implements ArmorMaterial {
    STEEL("steel", 21, new int[]{2, 6, 7, 2}, 10, Ingredient.EMPTY),
    URANIUM("uranium", 25, new int[]{2, 6, 6, 2}, 12, Ingredient.EMPTY),
    REDSTONE_STEEL("redstone_steel", 30, new int[]{3, 7, 6, 3}, 15, Ingredient.EMPTY);

    private static final int[] DURABILITY_PER_SLOT = new int[]{14, 16, 17, 12};
    private final int durabilityMultiplier;
    private final String name;
    private final int[] slotProtections;
    private final int enchantability;
    private final Ingredient repairIngredient;

    ModArmorMaterial(String name, int durabilityMultiplier, int[] slotProtections, int enchantability, Ingredient repairIngredient) {
        this.durabilityMultiplier = durabilityMultiplier;
        this.name = name;
        this.slotProtections = slotProtections;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlot pSlot) {
        return DURABILITY_PER_SLOT[pSlot.getIndex()] * durabilityMultiplier;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot pSlot) {
        return slotProtections[pSlot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_IRON;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient;
    }

    @Override
    public String getName() {
        return Names.MOD_ID + ':' + name;
    }

    @Override
    public float getToughness() {
        return 0.15f;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.0f;
    }
}
