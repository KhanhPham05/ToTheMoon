package com.khanhtypo.tothemoon.common.tank;

import com.khanhtypo.tothemoon.client.screen.BasicScreen;
import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.utls.GuiRenderHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.Optional;

public class FluidTankScreen extends BasicScreen<FluidTankMenu> {
    private final NonNullList<Component> tooltipComponents = NonNullList.withSize(2, Component.empty());

    public FluidTankScreen(FluidTankMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 176, 186);
    }

    @Override
    protected void renderBgAddition(GuiGraphics renderer, ResourceLocation guiTexture) {
        if (menu.hasFluid()) {
            GuiRenderHelper.renderFluidToScreen(renderer, getMinecraft(), menu.getData(2), menu.getData(0), menu.getData(1), leftPos + 78, topPos + 90, 64, 69);
        }

        if (menu.getData(3) > 0) {
            super.drawHorizontalBar(renderer, guiTexture, 58, 37, 194, 0, 16, 10, 3, 4);
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY) {
        super.renderTooltip(pGuiGraphics, pX, pY);
        if (super.isHovering(78, 21, 64, 69, pX, pY)) {
            final Fluid fluid = ModRegistries.getFromIdOrThrow(Registries.FLUID, menu.getData(2));
            this.tooltipComponents.set(0,
                    fluid.isSame(Fluids.EMPTY) ? ModLanguageGenerator.EMPTY_TANK : ModLanguageGenerator.STORING_FLUID.withParam(fluid.getFluidType().getDescription())
            );
            this.tooltipComponents.set(1, GuiRenderHelper.getStorageComponent(ModLanguageGenerator.STORING_AMOUNT_FLUID, menu.getData(0), menu.getData(1)));
            pGuiGraphics.renderTooltip(font, this.tooltipComponents, Optional.empty(), pX, pY);
        }
    }
}
