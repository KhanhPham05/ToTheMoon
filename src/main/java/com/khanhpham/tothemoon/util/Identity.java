package com.khanhpham.tothemoon.util;

import com.khanhpham.tothemoon.ToTheMoon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;

import javax.annotation.Nonnull;

public class Identity {

    public static ResourceLocation of(String modid, String path) {
        ensureValid(path);
        return new ResourceLocation(modid, path);
    }

    public static ResourceLocation mod(String path) {
        return of(ToTheMoon.ID, path);
    }

    public static ResourceLocation vanillaTexture(String path) {
        ensureValid(path);
        if (path.contains(".png"))
            return of(path);
        else return of(path + ".png");
    }

    public static ResourceLocation of(String path) {
        ensureValid(path);
        return new ResourceLocation(path);
    }

    public static void ensureValid(@Nonnull String string) {
        if (string.isEmpty() || string.contains(" ")) {
            throw new ResourceLocationException(string + " contains space or left blank");
        }

        for (int i = 0; i < string.length(); i++) {
            if (Character.isUpperCase(string.charAt(i)))
                throw new ResourceLocationException(string + " contains capital letters");
        }
    }
}
