package com.khanhpham.tothemoon.utils.registration;

import com.khanhpham.tothemoon.Names;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

public class MenuTypeRegister {
    private final Set<MenuType<?>> containerTypes = new HashSet<>();

    public MenuTypeRegister() {
    }

    public <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType.MenuSupplier<T> supplier) {
        MenuType<T> containerType = new MenuType<>(supplier);
        this.containerTypes.add(containerType.setRegistryName(new ResourceLocation(Names.MOD_ID, name)));
        return containerType;
    }

    public void registerAll(IForgeRegistry<MenuType<?>> registry) {
        this.containerTypes.forEach(registry::register);
    }
}
