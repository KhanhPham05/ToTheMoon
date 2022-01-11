package com.khanhpham.ttm.registration;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public final class MenuTypeRegister extends RegistryHelper<MenuType<?>> {
    public MenuTypeRegister(String modid) {
        super(modid);
    }

    @Deprecated
    @Override
    public <I extends MenuType<?>> MenuType<?> register(String name, I instance) {
        throw new IllegalArgumentException("Use another version os register() instead");
    }

    public <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType.MenuSupplier<T> menuSupplier) {
        MenuType<T> menu = new MenuType<>(menuSupplier);
        menu.setRegistryName(modid, name);

        if (!registrySet.add(menu)) {
            throw new IllegalStateException(menu + " has already registered.");
        }
        return menu;
    };
}
