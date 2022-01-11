package com.khanhpham.ttm.data.lang;

import com.khanhpham.ttm.TTMLang;
import com.khanhpham.ttm.ToTheMoonMain;
import com.khanhpham.ttm.init.ModBlocks;
import com.khanhpham.ttm.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.data.LanguageProvider;

public final class EngLangProvider extends LanguageProvider {
    public static final TranslatableComponent COPPER_GEN_LABEL = translate("gui", "copper_gen");

    public EngLangProvider(DataGenerator gen) {
        super(gen, ToTheMoonMain.MOD_ID, "en_us");
    }

    public static TranslatableComponent translate(String suf, String pre) {
        return new TranslatableComponent(suf + '.' + ToTheMoonMain.MOD_ID + '.' + pre);
    }

    @Override
    protected void addTranslations() {
        add(ModBlocks.ENERGY_BANK, "Energy Bank");
        add(ModBlocks.ENERGY_GEN, "Energy Generator");
        add(ModBlocks.MOON_COBBLESTONE, "Moon Cobblestone");
        add(ModBlocks.MOON_SURFACE_STONE, "Moon Surface Stone");
        add(ModBlocks.MOON_STONE_BRICKS, "Moon Surface Stone Bricks");
        add(ModBlocks.COPPER_ENERGY_GENERATOR, "Copper Energy Generator");
        add(ModItems.TTM_ICON, "TTM Icon");
        addMisc();
        addAll();
    }

    private void addAll() {
        TTMLang.map.forEach(this::add);
    }

    private void addMisc() {
        add(COPPER_GEN_LABEL, "Copper Energy Generator");
    }

    private void add(TranslatableComponent component, String value) {
        super.add(component.getKey(), value);
    }
}
