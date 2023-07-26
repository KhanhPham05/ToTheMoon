package com.khanhtypo.tothemoon.common.item;

import com.khanhtypo.tothemoon.registration.elements.ItemObject;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public class BasicArmorItem extends ItemObject<ArmorItem> {
    public BasicArmorItem(ModArmorMaterials material, ArmorItem.Type armorType) {
        this(material.getName() + "_" + armorType.getName(), material, armorType);
    }

    public BasicArmorItem(String name, ModArmorMaterials material, ArmorItem.Type armorType) {
        super(name, () -> new ArmorItem(material, armorType, new Item.Properties()));
        material.put(armorType, this);
    }

    public Ingredient repairIngredient() {
        return this.get().getMaterial().getRepairIngredient();
    }

    public ItemPredicate getCheckPredicate() {
        return ((ModArmorMaterials) this.get().getMaterial()).getItemPredicate();
    }
}
