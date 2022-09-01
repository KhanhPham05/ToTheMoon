package com.khanhpham.tothemoon.utils.text;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.datagen.loottable.LootUtils;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

@SuppressWarnings("deprecation")
public class TextUtils {

    private static final String TRANSLATION_FORMAT = "%s.%s.%s";
    private static final String FLUID_FORMAT = "%,d mB";
    private static final String ENERGY_FORMAT = "%,d FE";
    private static final String FLUID_TANK_ITEM_FORMAT = "%,dmB / %,dmB";

    @Deprecated
    public static String energyToString(int energy) {
        String str;
        String fullEnergyString = Integer.toString(energy);
        int energyStringLength = fullEnergyString.length();

        //terra-FE = more than 15 characters
        if (energyStringLength > 15) {
            str = cutPos(fullEnergyString, 15, "TFE");
        } else

            //giga FE = more than 12 character
            if (energyStringLength > 12) {
                str = cutPos(fullEnergyString, 12, "GFE");
            } else
                //Mega FE
                if (energyStringLength > 9) {
                    str = cutPos(fullEnergyString, 9, "MFE");
                } else if (energyStringLength > 6) {
                    str = cutPos(fullEnergyString, 6, "kFE");
                } else str = energy + "FE";

        if (str.isEmpty()) {
            str = "EMPTY";
        }

        return str;
    }

    public static String translateEnergy(int energy) {
        return ENERGY_FORMAT.formatted(energy);
    }

    public static MutableComponent translateEnergyStorage(int stored, int capacity) {
        return translatable("tooltip", "energy", translateEnergy(stored), translateEnergy(capacity));
    }

    public static MutableComponent translatable(String pre, String suf, Object... params) {
        return Component.translatable(String.format(TRANSLATION_FORMAT, pre, ToTheMoon.MOD_ID, suf), params);
    }

    public static Component fluidFuel(int fluid, int capacity) {
        String fluidString = String.format(FLUID_FORMAT, fluid);
        String capacityString = String.format(FLUID_FORMAT, capacity);
        return translatable("tooltip", "fluid_fuel_tank", fluidString, capacityString);
    }

    public static String translateFormat(String pre, String suf) {
        return String.format(TRANSLATION_FORMAT, pre, ToTheMoon.MOD_ID, suf);
    }

    public static MutableComponent translatable(String key) {
        return Component.translatable(key);
    }

    public static Component translateItemFluidTank(ItemStack pStack, int capacity) {
        CompoundTag dataTag = LootUtils.getDataTag(pStack);
        if (dataTag.contains(LootUtils.LOOT_DATA_FLUID_AMOUNT, LootUtils.TAG_TYPE_INT) && dataTag.contains(LootUtils.LOOT_DATA_FLUID, LootUtils.TAG_TYPE_STRING)) {
            return TextUtils.translateFluidTank(ModUtils.getRegistry(Registry.FLUID, new ResourceLocation(dataTag.getString(LootUtils.LOOT_DATA_FLUID))), dataTag.getInt(LootUtils.LOOT_DATA_FLUID_AMOUNT), capacity);
        }

        return TextUtils.translatable("tooltip", "item_tank", "Empty", "0mB");
    }

    public static MutableComponent translateFluidTank(Fluid fluid, int amount, int capacity) {
        String param2 = String.format(FLUID_TANK_ITEM_FORMAT, amount, capacity);
        String param1 = fluid.getFluidType().getDescription().getString();
        return translatable("tooltip", "item_tank", param1, param2);
    }

    private static String cutPos(String string, int posToCut, String extension) {
        int dotPos;
        dotPos = string.length() - posToCut;
        String str = copy(string, dotPos);
        str = str.concat('.' + copy(string, dotPos, dotPos + 2) + extension);
        return str;
    }

    private static String copy(String string, int startIndex, int endIndex) {
        String copied = "";
        for (int i = startIndex; i < endIndex; i++) {
            copied = copied.concat(String.valueOf(string.charAt(i)));
        }
        return copied;
    }

    private static String copy(String string, int end) {
        return copy(string, 0, end);
    }

    public static String showPercentage(int value, int maxValue) {
        return "(" + value * 100 / maxValue + "%)";
    }
}
