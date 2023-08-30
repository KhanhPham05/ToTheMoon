package com.khanhtypo.tothemoon.common.item.upgrades;

import com.google.common.base.Preconditions;
import com.khanhtypo.tothemoon.common.StringRepresentableImpl;
import com.khanhtypo.tothemoon.data.c.ModItemModels;
import com.khanhtypo.tothemoon.registration.elements.ItemObject;

import java.util.function.BiFunction;

@SuppressWarnings("unchecked")
public enum UpgradeItemType implements StringRepresentableImpl {
    SPEED_UPGRADE(3, SpeedUpgradeItem::new),
    ENERGY_CAPACITY_UPGRADE(3, EnergyCapacityUpgrade::new);
    private final int maxTiers;
    private final BiFunction<UpgradeItemType, Integer, AbstractUpgradeItem> builder;
    private final ItemObject<AbstractUpgradeItem>[] items;

    UpgradeItemType(int maxTiers, BiFunction<UpgradeItemType, Integer, AbstractUpgradeItem> builder) {
        this.maxTiers = maxTiers;
        this.builder = builder;
        this.items = new ItemObject[maxTiers];
    }

    public static void register() {
        for (UpgradeItemType upgradeType : values()) {
            for (int i = 1; i <= upgradeType.maxTiers; i++) {
                final int tier = i;
                final var itemObject = ItemObject.register(upgradeType.getSerializedName() + "_tier_" + tier, () -> upgradeType.builder.apply(upgradeType, tier));
                upgradeType.items[i - 1] = itemObject;
                ModItemModels.MODEL_ORDERS.add(modelGenerator -> modelGenerator.basicItem(itemObject));
            }
        }
    }

    public static boolean isSameTypeWith(IUpgradeItem item1, IUpgradeItem item2) {
        return item1.getUpgradeType() == item2.getUpgradeType();
    }

    public ItemObject<AbstractUpgradeItem> getWithLevel(int level) {
        Preconditions.checkState(level <= this.maxTiers && level > 0);
        return this.items[level - 1];
    }
}
