package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.ModUtils;
import com.khanhtypo.tothemoon.registration.elements.MenuObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraftforge.registries.RegisterEvent;

import java.util.Map;

public class ModStats {
    public static final Map<MenuObject<?>, ResourceLocation> CONTAINER_INTERACTION_MAP = ModUtils.resourceSortedMap();

    public static void registerStats(RegisterEvent registerEvent) {
        registerEvent.register(Registries.CUSTOM_STAT,
                helper -> CONTAINER_INTERACTION_MAP.forEach((menuObject, resourceLocation) -> {
                    helper.register(resourceLocation, resourceLocation);
                    Stats.CUSTOM.get(resourceLocation);
                }));
    }

    public static ResourceLocation getCustomStat(MenuObject<?> menuObject) {
        return CONTAINER_INTERACTION_MAP.get(menuObject);
    }
}
