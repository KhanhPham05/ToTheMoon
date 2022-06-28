package com.khanhpham.tothemoon.core.items;

import com.khanhpham.tothemoon.core.config.TTMConfigs;
import com.khanhpham.tothemoon.utils.capabilities.EnergyCapabilityProvider;
import com.khanhpham.tothemoon.utils.energy.ItemStackEnergy;
import com.khanhpham.tothemoon.utils.text.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class EnergyCapableItem extends BlockItem {
    //public final TranslatableComponent ENERGY_CAPACITY = new TranslatableComponent("tooltip.tothemoon.energy", getMaxEnergyStored());

    public EnergyCapableItem(Block block, Properties pProperties) {
        super(block, pProperties.stacksTo(1));
    }

    protected abstract int getMaxEnergyStored();

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new EnergyCapabilityProvider(new ItemStackEnergy(stack, getMaxEnergyStored()));
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        LazyOptional<IEnergyStorage> energyStorage = pStack.getCapability(CapabilityEnergy.ENERGY);
        return energyStorage.map(energy -> {
            float maxEnergy = energy.getMaxEnergyStored();
            float f = Math.max(0.0f, (maxEnergy - (float) energy.getEnergyStored()));
            return Mth.hsvToRgb(f / 3.0f, 1, 1);
        }).orElse(super.getBarColor(pStack));
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        LazyOptional<IEnergyStorage> energyStorage = pStack.getCapability(CapabilityEnergy.ENERGY);
        return energyStorage.map(energy -> Math.round(13 - energy.getEnergyStored()) * 13 / energy.getMaxEnergyStored()).orElse(super.getBarWidth(pStack));
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        int energyStored = ItemStackEnergy.getNbt(pStack).getInt("energy");
        pTooltip.add(new TranslatableComponent("tooltip.tothemoon.energy", TTMConfigs.CLIENT.showsItemEnergyPercentageOnToolTip.get() ? TextUtils.showPercentage(energyStored, getMaxEnergyStored()) : "", TextUtils.energyToString(energyStored), TextUtils.energyToString(getMaxEnergyStored())).withStyle(ChatFormatting.GREEN));
    }
}
