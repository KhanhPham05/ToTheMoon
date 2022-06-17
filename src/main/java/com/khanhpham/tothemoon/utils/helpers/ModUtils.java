package com.khanhpham.tothemoon.utils.helpers;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.blocks.machines.battery.BatteryConnectionMode;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;

public class ModUtils {
    public static final IntegerProperty ENERGY_LEVEL = IntegerProperty.create("level", 0, 10);
    public static final EnumProperty<BatteryConnectionMode> BATTERY_CONNECTION_MODE = EnumProperty.create("connect", BatteryConnectionMode.class);
    public static final Random RANDOM = new Random();

    public static ResourceLocation modLoc(String loc) {
        return new ResourceLocation(Names.MOD_ID, loc);
    }

    public static ResourceLocation append(ResourceLocation pre, String suf) {
        String path = pre.getPath() + suf;
        return new ResourceLocation(pre.getNamespace(), path);
    }

    public static TranslatableComponent translate(String key, Object... param) {
        return new TranslatableComponent(key, param);
    }

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(ResourceLocation location) {
        return new RecipeType<>() {
            @Override
            public String toString() {
                return location.getPath();
            }
        };
    }

    public static <T extends IForgeRegistryEntry<T>> String convertRlToPath(@Nonnull T entry) {
        return Objects.requireNonNull(entry.getRegistryName()).getPath();
    }

    public static void log(String message, Object... arguments) {
        ToTheMoon.LOG.info(message, arguments);
    }

    @SuppressWarnings("deprecation")
    public static String getNameFromItem(Item item) {
        return Registry.ITEM.getKey(item).toString();
    }

    public static <T extends IForgeRegistryEntry<T>> String getPath(T object) {
        return Objects.requireNonNull(object.getRegistryName()).getPath();
    }

    public static int roll(int whenHit, int chance) {
        int attempt = RANDOM.nextInt(100);
        ModUtils.log("Rolling for double : {}", attempt );
        return attempt < chance ? whenHit : 0;
    }
}
