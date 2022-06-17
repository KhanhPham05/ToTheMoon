package com.khanhpham.tothemoon.core.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class ModArmorItem extends ArmorItem {
    private final Item craftItem;

    public ModArmorItem(Item craftItem, ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
        this.craftItem = craftItem;
    }

    public Item getCraftItem() {
        return craftItem;
    }
}
