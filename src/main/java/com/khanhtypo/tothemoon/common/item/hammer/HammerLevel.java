package com.khanhtypo.tothemoon.common.item.hammer;

import com.khanhtypo.tothemoon.registration.elements.ItemObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.*;

public enum HammerLevel {
    WOODEN(1),
    STEEL(2),
    DIAMOND(3),
    NETHERITE(4);
    static final int baseDurability = 32;
    final int hammerDurability;
    private final int hammerLevel;
    @Nullable
    private ItemObject<HammerItem> hammerItemSupplier;

    HammerLevel(int hammerLevel) {
        this.hammerDurability = hammerLevel * baseDurability;
        this.hammerLevel = hammerLevel;
        HammerGetter.HAMMER_LEVEL_MAP.put(this.hammerLevel, this);
    }

    public static HammerLevel byLevel(int hammerLevel) {
        return Objects.requireNonNull(HammerGetter.HAMMER_LEVEL_MAP.get(hammerLevel), "No level satisfies.");
    }

    public static HammerLevel readFromServer(FriendlyByteBuf reader) {
        return HammerGetter.HAMMER_LEVEL_MAP.get(reader.readInt());
    }

    public static Ingredient getHammersIngredient(HammerLevel minLevel) {
        return Ingredient.of(Arrays.stream(HammerLevel.values()).filter(level -> level.isBetterThanOrSameAs(minLevel)).sorted(Comparator.comparingInt(HammerLevel::getHammerLevel)).map(HammerLevel::getOrRegisterItem).map(ItemObject::get).toArray(HammerItem[]::new));
    }

    public void writeToServer(FriendlyByteBuf writer) {
        writer.writeInt(this.hammerLevel);
    }

    public ItemObject<HammerItem> getOrRegisterItem() {
        if (this.hammerItemSupplier == null) {
            final Item.Properties properties = new Item.Properties().defaultDurability(this.hammerDurability);
            if (this == NETHERITE) properties.fireResistant();
            this.hammerItemSupplier = ItemObject.of(this.toString().toLowerCase(Locale.ROOT) + "_hammer", () -> new HammerItem(properties, this));
        }

        return hammerItemSupplier;
    }

    public boolean testHammer(ItemStack toTest) {
        return (toTest.getItem() instanceof HammerItem hammerItem) && hammerItem.getHammerLevel().isBetterThanOrSameAs(this);
    }

    public int getHammerLevel() {
        return hammerLevel;
    }

    private boolean isBetterThanOrSameAs(HammerLevel levelToTest) {
        return this.hammerLevel >= levelToTest.hammerLevel;
    }

    private static final class HammerGetter {
        private static final Map<Integer, HammerLevel> HAMMER_LEVEL_MAP = new TreeMap<>();

    }
}
