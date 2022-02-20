package com.khanhpham.tothemoon.data;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.energygenerator.tileentities.CopperEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.core.energygenerator.tileentities.IronEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.core.storageblock.MoonBarrelTileEntity;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.ModLang;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator gen) {
        super(gen, Names.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ModItems.ITEMS.forEachItem(this::add);
        add(((TranslatableComponent)ToTheMoon.TAB.getDisplayName()).getKey(), "To The Moon");
        add(ModLang.MOON_ROCK_BARREL, "Moon Rock Barrel");
        add(MoonBarrelTileEntity.MENU_DISPLAY_NAME, "Moon Rock Barrel");
        add(CopperEnergyGeneratorBlockEntity.LABEL, "Copper Energy Generator");
        add("gui.tothemoon.energy_per_capacity", "Energy: %s / %s");
        add(IronEnergyGeneratorBlockEntity.LABEL, "Iron Energy Generator");
    }

    private void add(TranslatableComponent component, String trans) {
        super.add(component.getKey(), trans);
    }

    private void add(Component component, String trans) {
        this.add((TranslatableComponent) component, trans);
    }

    private void add(ItemLike item) {
        String itemName = item.asItem().getRegistryName().getPath();
        char[] cs = new char[itemName.length()];
        for (int i = 0; i < itemName.length(); i++) {
            cs[i] = itemName.charAt(i);
        }

        cs[0] = Character.toUpperCase(cs[0]);

        for (int i = 1; i < cs.length; i++) {
            if (cs[i] == '_') {
                cs[i] = ' ';
            }

            if (cs[i - 1] == ' ') {
                char c = cs[i];
                cs[i] = Character.toUpperCase(c);
            }
        }

        String finalName = String.copyValueOf(cs);
        super.add(item.asItem(), finalName);
    }
}
