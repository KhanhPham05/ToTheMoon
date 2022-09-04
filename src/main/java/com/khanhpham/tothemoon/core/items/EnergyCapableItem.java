package com.khanhpham.tothemoon.core.items;

import com.khanhpham.tothemoon.config.TTMConfigs;
import com.khanhpham.tothemoon.utils.capabilities.ItemEnergyCapabilityProvider;
import com.khanhpham.tothemoon.utils.capabilities.ItemStackEnergy;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.text.TextUtils;
import net.minecraft.ChatFormatting;
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

public abstract class EnergyCapableItem extends BlockItem {

    public EnergyCapableItem(Block block, Properties pProperties) {
        super(block, pProperties.stacksTo(1));
    }

    protected abstract int getMaxEnergyStored();

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemEnergyCapabilityProvider(new ItemStackEnergy(stack, getMaxEnergyStored()));
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        int energyStored = ItemStackEnergy.getEnergy(pStack);
        pTooltip.add(ModUtils.translate("tooltip.tothemoon.energy",
                TTMConfigs.CLIENT_CONFIGS.showsItemEnergyPercentageOnToolTip.get() ? TextUtils.showPercentage(energyStored, getMaxEnergyStored()) : "",
                TextUtils.translateEnergy(energyStored),
                TextUtils.translateEnergy(getMaxEnergyStored())).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }
}
