package com.khanhpham.tothemoon.compat.top;

import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;

public class TopCompat implements ITheOneProbe {
    @Override
    public void registerProvider(IProbeInfoProvider iProbeInfoProvider) {

    }

    @Override
    public void registerEntityProvider(IProbeInfoEntityProvider iProbeInfoEntityProvider) {

    }

    @Override
    public void registerElementFactory(IElementFactory iElementFactory) {

    }

    @Override
    public IElementFactory getElementFactory(ResourceLocation resourceLocation) {
        return null;
    }

    @Override
    public IOverlayRenderer getOverlayRenderer() {
        return null;
    }

    @Override
    public IProbeConfig createProbeConfig() {
        return null;
    }

    @Override
    public void registerProbeConfigProvider(IProbeConfigProvider iProbeConfigProvider) {

    }

    @Override
    public void registerBlockDisplayOverride(IBlockDisplayOverride iBlockDisplayOverride) {

    }

    @Override
    public void registerEntityDisplayOverride(IEntityDisplayOverride iEntityDisplayOverride) {

    }

    @Override
    public IStyleManager getStyleManager() {
        return null;
    }
}
