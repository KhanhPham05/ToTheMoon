package com.khanhpham.tothemoon.utils.text;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.datagen.loottable.LootUtils;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public class TextUtils {

    private static final String TRANSLATION_FORMAT = "%s.%s.%s";
    private static final String FLUID_FORMAT = "%,d mB";
    private static final String ENERGY_FORMAT = "%,d FE";
    private static final String FLUID_TANK_ITEM_FORMAT = "%,dmB / %,dmB";

    @Deprecated
    public static String energyToString(int energy) {
        String str = "";
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

    public static TranslatableComponent translateFormatText(String pre, String suf, Object... params) {
        return new TranslatableComponent(String.format(TRANSLATION_FORMAT, pre, Names.MOD_ID, suf), params);
    }

    public static Component fluidFuel(int fluid, int capacity) {
        String fluidString = String.format(FLUID_FORMAT, fluid);
        String capacityString = String.format(FLUID_FORMAT, capacity);
        return translateFormatText("tooltip", "fluid_fuel_tank", fluidString, capacityString);
    }

    public static String translateFormat(String pre, String suf) {
        return String.format(TRANSLATION_FORMAT, pre, Names.MOD_ID, suf);
    }

    public static Component translateItemFluidTank(ItemStack pStack, int capacity) {
        CompoundTag dataTag = LootUtils.getDataTag(pStack);
        if (dataTag.contains(LootUtils.LOOT_DATA_FLUID_AMOUNT, LootUtils.TAG_TYPE_INT) && dataTag.contains(LootUtils.LOOT_DATA_FLUID, LootUtils.TAG_TYPE_STRING)) {
            return TextUtils.translateFluidTank(getRegistry(Registry.FLUID, new ResourceLocation(dataTag.getString(LootUtils.LOOT_DATA_FLUID))), dataTag.getInt(LootUtils.LOOT_DATA_FLUID_AMOUNT), capacity);
            //return TextUtils.translateFormatText("tooltip", "item_tank", ModLanguage.convertToTranslatedText(new ResourceLocation(dataTag.getString(LootUtils.LOOT_DATA_FLUID))), String.format(FLUID_TANK_ITEM_FORMAT, dataTag.getInt(LootUtils.LOOT_DATA_FLUID_AMOUNT), capacity));
        }

        return TextUtils.translateFormatText("tooltip", "item_tank", "Empty", "0mB");
    }

    public static <T> T getRegistry(Registry<T> registry, ResourceLocation location) {
        if (registry.containsKey(location)) return registry.get(location);
        throw new IllegalStateException("No registry represent for [" + location + "]");
    }

    public static Component translateFluidTank(Fluid fluid, int amount, int capacity) {
        String param2 = String.format(FLUID_TANK_ITEM_FORMAT, amount, capacity);
        String param1 = ModLanguage.convertToTranslatedText(fluid.getRegistryName());
        return translateFormatText("tooltip", "item_tank", param1, param2);
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
