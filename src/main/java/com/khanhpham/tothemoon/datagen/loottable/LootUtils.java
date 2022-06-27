package com.khanhpham.tothemoon.datagen.loottable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.BiConsumer;

public class LootUtils {
    public static final String LOOT_DATA_ENERGY;
    public static final String LOOT_DATA_FLUID;
    public static final String LOOT_DATA_FLUID_AMOUNT;

    public static final int TAG_TYPE_INT = 3;
    public static final int TAG_TYPE_STRING = 4;
    public static final int TAG_TYPE_COMPOUND = 10;

    private static final String TTM_DATA = "ttmData";

    static {
        LOOT_DATA_ENERGY = makeDataName("energy");
        LOOT_DATA_FLUID = makeDataName("fluid");
        LOOT_DATA_FLUID_AMOUNT = makeDataName("fluidAmount");
    }

    private LootUtils() {
        throw new IllegalStateException("Utilities Class");
    }

    private static String makeDataName(String saveName) {
        return "ttmData." + saveName;
    }

    public static CompoundTag getBlockEntityTag(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();

        return nbt.contains(TTM_DATA, TAG_TYPE_COMPOUND) ? nbt.getCompound(TTM_DATA) : nbt;
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> void loadItemTagToBlockEntity(ItemStack stack, Level level, BlockPos pos, BlockEntityType<T> beType, BiConsumer<CompoundTag, T> actionWithTag) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        CompoundTag nbt = getBlockEntityTag(stack);
        if (blockEntity != null && blockEntity.getType().equals(beType) && !nbt.isEmpty())
            actionWithTag.accept(nbt, (T) blockEntity);
    }
}
