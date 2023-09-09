package com.khanhtypo.tothemoon.common.item;

import com.google.common.base.Preconditions;
import com.khanhtypo.tothemoon.common.block.EnergyBlock;
import com.khanhtypo.tothemoon.common.capability.EnergyCapabilityProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnergyBlockItem extends BlockItem {
    private final EnergyCapabilityProvider energyCapabilityProvider;

    public EnergyBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
        Preconditions.checkState(pBlock instanceof EnergyBlock, "%s must extends EnergyBlock".formatted(pBlock.getClass().getSimpleName()));
        this.energyCapabilityProvider = new EnergyCapabilityProvider(((EnergyBlock) pBlock).getEnergyCapacity());
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return this.energyCapabilityProvider.of(stack.getTag());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        if (this.energyCapabilityProvider.hasEnergy()) {
            pTooltip.add(this.energyCapabilityProvider.toDisplay());
        }
    }
}
