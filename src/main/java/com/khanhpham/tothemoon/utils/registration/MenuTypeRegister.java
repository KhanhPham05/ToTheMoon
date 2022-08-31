package com.khanhpham.tothemoon.utils.registration;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.RegisterEvent;

import java.util.HashMap;
import java.util.Map;

public class MenuTypeRegister {
    private final Map<ResourceLocation, MenuType<?>> containerTypes = new HashMap<>();

    public MenuTypeRegister() {
    }

    public <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType.MenuSupplier<T> supplier) {
        MenuType<T> containerType = new MenuType<>(supplier);
        this.containerTypes.put(ModUtils.modLoc(name), containerType);
        return containerType;
    }

    public void registerAll(RegisterEvent.RegisterHelper<MenuType<?>> helper) {
        this.containerTypes.forEach(helper::register);
    }
}
