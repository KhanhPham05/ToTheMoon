package com.khanhtypo.tothemoon.common.tank;

import com.khanhtypo.tothemoon.api.implemented.capability.fluid.NbtItemFluidStorageHandler;
import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import com.khanhtypo.tothemoon.utls.GuiRenderHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FluidTankItem extends BlockItem {
    private NbtItemFluidStorageHandler fluidHandlerItemStack;

    public FluidTankItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return this.fluidHandlerItemStack = new NbtItemFluidStorageHandler(stack, FluidTankBlockEntity.TANK_CAPACITY, (root, name, tank) -> root.getOrCompoundThrow("MachineData").put(name, tank));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        FluidStack fluidStack = fluidHandlerItemStack.getFluid();
        if (!fluidStack.isEmpty()) {
            pTooltip.add(ModLanguageGenerator.STORING_FLUID.withParam(fluidStack.getFluid().getFluidType().getDescription()));
            pTooltip.add(GuiRenderHelper.getStorageComponent(ModLanguageGenerator.STORING_AMOUNT_FLUID, fluidStack.getAmount(), fluidHandlerItemStack.getTankCapacity(0)));
        }
    }
}
