package com.khanhpham.tothemoon.advancements;

import net.minecraft.advancements.CriteriaTriggers;

public class ModdedTriggers {
    public static final AnvilCrushingTrigger ANVIL_CRUSHING = CriteriaTriggers.register(new AnvilCrushingTrigger());
    public static final MultiblockFormedTrigger MULTIBLOCK_FORMED = CriteriaTriggers.register(new MultiblockFormedTrigger());

    public static void init() {}
}
