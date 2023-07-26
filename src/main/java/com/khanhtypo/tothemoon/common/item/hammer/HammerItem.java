package com.khanhtypo.tothemoon.common.item.hammer;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public final class HammerItem extends Item {
    private final HammerLevel hammerLevel;

    public HammerItem(Properties properties, HammerLevel hammerLevel) {
        super(properties);
        this.hammerLevel = hammerLevel;
    }

    public HammerLevel getHammerLevel() {
        return hammerLevel;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        stack.hurt(1, level.getRandom(), null);
    }
}
