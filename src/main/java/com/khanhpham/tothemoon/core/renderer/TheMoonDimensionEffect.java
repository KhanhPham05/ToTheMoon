package com.khanhpham.tothemoon.core.renderer;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public class TheMoonDimensionEffect extends DimensionSpecialEffects {
    public TheMoonDimensionEffect() {
        super(Float.NaN, false, SkyType.NONE, true, false);
    }

    @Nonnull
    @Override
    public Vec3 getBrightnessDependentFogColor(@Nonnull Vec3 pFogColor, float pBrightness) {
        return pFogColor;
    }

    @Override
    public boolean isFoggyAt(int pX, int pY) {
        return pY >= 300;
    }
}
