package com.khanhpham.tothemoon.datagen.loottable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.function.BiConsumer;

/**
 * @see net.minecraftforge.fluids.capability.ItemFluidContainer
 */
public class LootUtils {
    public static final String LOOT_DATA_ENERGY;
    public static final String LOOT_DATA_FLUID;
    public static final String LOOT_DATA_FLUID_AMOUNT;
    public static final int TAG_TYPE_INT = Tag.TAG_INT;
    public static final int TAG_TYPE_STRING = Tag.TAG_STRING;
    public static final int TAG_TYPE_COMPOUND = Tag.TAG_COMPOUND;
    protected static final String SAVE_DATA_ENERGY = "ttmData.energy";
    protected static final String SAVE_DATA_FLUID_NAME = "ttmData.fluid";
    protected static final String SAVE_DATA_FLUID_AMOUNT = "ttmData.fluidAmount";
    private static final String TTM_DATA = "ttmData";

    static {
        LOOT_DATA_ENERGY = "energy";
        LOOT_DATA_FLUID = "fluid";
        LOOT_DATA_FLUID_AMOUNT = "fluidAmount";
    }

    private LootUtils() {
        throw new IllegalStateException("Utilities Class");
    }

    public static CompoundTag getDataTag(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();

        return nbt.contains(TTM_DATA, TAG_TYPE_COMPOUND) ? nbt.getCompound(TTM_DATA) : nbt;
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> void loadItemTagToBlockEntity(ItemStack stack, Level level, BlockPos pos, BlockEntityType<T> beType, BiConsumer<CompoundTag, T> actionWithTag) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        CompoundTag nbt = getDataTag(stack);
        if (blockEntity != null && blockEntity.getType().equals(beType) && !nbt.isEmpty())
            actionWithTag.accept(nbt, (T) blockEntity);
    }

    public static void updateFluidTag(CompoundTag tag, int newAmount) {
        if (tag.contains(LOOT_DATA_FLUID, TAG_TYPE_STRING)) {
            tag.putInt(LOOT_DATA_FLUID_AMOUNT, newAmount);
        }
    }

    @SuppressWarnings("deprecation")
    public static FluidTank getTankFromTag(ItemStack itemStack, int capacity) {
        CompoundTag dataTag = getDataTag(itemStack);
        FluidTank tank = new FluidTank(capacity);

        if (dataTag.contains(LOOT_DATA_FLUID_AMOUNT, TAG_TYPE_INT) && dataTag.contains(LOOT_DATA_FLUID, TAG_TYPE_STRING)) {
            tank.setFluid(new FluidStack(Registry.FLUID.get(ResourceLocation.tryParse(dataTag.getString(LOOT_DATA_FLUID))), dataTag.getInt(LOOT_DATA_FLUID_AMOUNT)));
        }

        return tank;
    }
}
