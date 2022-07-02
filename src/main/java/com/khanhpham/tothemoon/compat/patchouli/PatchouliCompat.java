package com.khanhpham.tothemoon.compat.patchouli;

import net.minecraftforge.fml.ModList;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.api.stub.StubMultiblock;

public class PatchouliCompat {
    public static boolean isPatchouliLoaded = false;
    public static final PatchouliAPI.IPatchouliAPI API = PatchouliAPI.get();

    private PatchouliCompat() {}

    private static void registerMultiblock() {
    }

    public static void loadPatchouli() {
        isPatchouliLoaded = ModList.get().isLoaded("patchouli");
        if (isPatchouliLoaded) {
            registerMultiblock();
        }
    }
}
