package com.khanhpham.tothemoon.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

public class ModUtils {

    @SuppressWarnings("deprecation")
    public static int getBurnTime(ItemStack stack) {
        if (!stack.isEmpty())
            return ForgeHooks.getBurnTime(stack);
        return -1;
    }
}
