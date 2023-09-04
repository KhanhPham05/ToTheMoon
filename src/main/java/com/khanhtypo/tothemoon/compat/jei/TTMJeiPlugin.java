package com.khanhtypo.tothemoon.compat.jei;

import com.khanhtypo.tothemoon.utls.ModUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
@JeiPlugin
public class TTMJeiPlugin implements IModPlugin {
    public static final ResourceLocation PLUGIN_ID = ModUtils.location("ttm_plugin");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        ModJeiCategories.forEachTab(t -> t.register(registration));
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        ModJeiCategories.forEachTab(t -> t.registerTransferHandler(registration));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        ModJeiCategories.forEachTab(t -> t.registerCatalyst(registration));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ModJeiCategories.forEachTab(t -> t.gatherRecipes(registration));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        ModJeiCategories.forEachTab(t -> t.registerGuiHandler(registration));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }
}