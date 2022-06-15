package com.khanhpham.tothemoon.core.items.tool;

import com.khanhpham.tothemoon.datagen.tags.ModItemTags;
import com.khanhpham.tothemoon.datagen.tags.ModToolTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

import javax.annotation.Nonnull;

public class ModToolTiers {
    public static final Tier STEEL = new Tier("steel", new ForgeTier(2, 325, 6.2f, 2.3f, 15, ModToolTags.NEEDS_STEEL_TOOLS, () -> Ingredient.of(ModItemTags.INGOTS_STEEL)));
    public static final Tier URANIUM = new Tier("uranium", new ForgeTier(3, 535, 6.8f, 3.0f, 15, ModToolTags.NEEDS_URANIUM_TOOLS, () -> Ingredient.of(ModItemTags.INGOTS_URANIUM)));


    public ModToolTiers() {
        ModToolTags.initTags();
    }

    public enum ToolType {
        PICKAXE(1.0f, -2.8f),
        HOE(-2.0f, -0.8f),
        AXE(6.5f, -2.8f),
        SHOVEL(1.5f, -2.8f),
        SWORD(3.0f, -2.0f);

        private final float attackDamageModifier;
        private final float attackSpeedModifier;

        ToolType(float attackDamageModifier, float attackSpeedModifier) {
            this.attackDamageModifier = attackDamageModifier;
            this.attackSpeedModifier = attackSpeedModifier;
        }

        public TieredItem toItem(ForgeTier tier, Item.Properties generalProperties) {
            return switch (this) {
                case PICKAXE -> new PickaxeItem(tier, (int) attackDamageModifier, attackSpeedModifier, generalProperties);
                case HOE -> new HoeItem(tier, (int) attackDamageModifier, attackSpeedModifier, generalProperties);
                case AXE -> new AxeItem(tier, attackDamageModifier, attackSpeedModifier, generalProperties);
                case SHOVEL -> new ShovelItem(tier, attackDamageModifier, attackSpeedModifier, generalProperties);
                case SWORD -> new SwordItem(tier, (int) attackDamageModifier, attackSpeedModifier, generalProperties);
            };
        }
    }

    public record Tier(String name, ForgeTier tier) implements net.minecraft.world.item.Tier {

        @Override
        public int getUses() {
            return tier.getUses();
        }

        @Override
        public float getSpeed() {
            return tier.getSpeed();
        }

        @Override
        public float getAttackDamageBonus() {
            return tier.getAttackDamageBonus();
        }

        @Override
        public int getLevel() {
            return tier.getLevel();
        }

        @Override
        public int getEnchantmentValue() {
            return tier.getEnchantmentValue();
        }

        @Nonnull
        @Override
        public Ingredient getRepairIngredient() {
            return tier.getRepairIngredient();
        }

    }
}
