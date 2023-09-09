package com.khanhtypo.tothemoon.common.machine.powergenerator;

import com.khanhtypo.tothemoon.common.item.EnergyBlockItem;
import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PowerGeneratorItem extends EnergyBlockItem {
    public static final Component INSTALLED_UPGRADES = ModLanguageGenerator.createTranslatable("tooltip", "installed_upgrades", "Installed Upgrades :").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC);

    public PowerGeneratorItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        CompoundTag itemTag = pStack.getTag();
        if (itemTag != null) {
            if (itemTag.contains("MachineData", CompoundTag.TAG_COMPOUND)) {
                CompoundTag machineData = itemTag.getCompound("MachineData");
                if (machineData.contains("Upgrades", CompoundTag.TAG_COMPOUND)) {
                    CompoundTag upgrades = machineData.getCompound("Upgrades");
                    if (!upgrades.isEmpty()) {
                        pTooltip.add(INSTALLED_UPGRADES);
                        for (int i = 0; i < 3; i++) {
                            if (upgrades.contains("Slot" + i, CompoundTag.TAG_STRING)) {
                                ModRegistries.getOptional(Registries.ITEM, new ResourceLocation(upgrades.getString("Slot" + i))).ifPresent(item -> pTooltip.add(((MutableComponent) item.getDescription()).withStyle(ChatFormatting.ITALIC)));
                            }
                        }
                    }
                }
            }
        }
    }
}
