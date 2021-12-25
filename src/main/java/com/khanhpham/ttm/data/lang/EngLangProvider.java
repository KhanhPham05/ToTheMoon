package com.khanhpham.ttm.data.lang;

import com.khanhpham.ttm.ToTheMoonMain;
import com.khanhpham.ttm.init.ModBlocks;
import com.khanhpham.ttm.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public final class EngLangProvider extends LanguageProvider {
    public EngLangProvider(DataGenerator gen) {
        super(gen, ToTheMoonMain.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModBlocks.ENERGY_BANK, "Energy Bank");
        add(ModBlocks.ENERGY_GEN, "Energy Generator");
        add(ModBlocks.MOON_COBBLESTONE, "Moon Cobblestone");
        add(ModBlocks.MOON_SURFACE_STONE, "Moon Surface Stone");
        add(ModBlocks.MOON_SURFACE_STONE_BRICKS, "Moon Surface Stone Bricks");
        add(ModItems.TTM_ICON, "TTM Icon");
    }
}
