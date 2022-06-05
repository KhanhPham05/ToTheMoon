package com.khanhpham.tothemoon.core.items;

import com.khanhpham.tothemoon.ToTheMoon;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.Item;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Deprecated
public abstract class UpgradeItem extends Item {
    public final int tier;

    public UpgradeItem(int tier) {
        super(new Properties().stacksTo(1).tab(ToTheMoon.TAB));
        this.tier = tier;
    }

    public static final class EnergyUpgrade extends UpgradeItem {

        public EnergyUpgrade(int level) {
            super(level);
        }

    }


    public static final class SpeedUpgrade extends UpgradeItem {

        public SpeedUpgrade(int level) {
            super(level);
        }

    }
}
