package com.khanhtypo.tothemoon.multiblock.blackstonefurnace.display;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicScreen;
import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import com.khanhtypo.tothemoon.utls.GuiRenderHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

@SuppressWarnings("deprecation")
public class BlackStoneFurnaceScreen extends BasicScreen<BlackStoneFurnaceMenu> {
    private static final NonNullList<Component> mutableComponentList = NonNullList.withSize(2, Component.empty());
    private static final Registry<Fluid> FLUID_REGISTRY = BuiltInRegistries.FLUID;

    public BlackStoneFurnaceScreen(BlackStoneFurnaceMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, 201, 177);
    }

    @Override
    protected void renderBgAddition(GuiGraphics renderer, ResourceLocation guiTexture) {
        super.drawHorizontalBar(renderer, guiTexture, 66, 42, 0, 177, 68, 16, 3, 4);

        GuiRenderHelper.renderFluidToScreen(renderer, super.getMinecraft(), super.menu.getData(0), super.menu.getData(1), super.menu.getData(2), leftPos + 189, topPos + 88, 5, 75);
    }

    @Override
    protected void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY) {
        super.renderTooltip(pGuiGraphics, pX, pY);
        mutableComponentList.clear();
        if (super.isHovering(189, 13, 5, 75, pX, pY)) {
            Fluid fluid = this.getFluid();
            IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluid);
            mutableComponentList.set(0, ModLanguageGenerator.STORING_FLUID.withParam(
                    ((MutableComponent) fluid.getFluidType().getDescription()).withStyle(Style.EMPTY.withColor(fluidTypeExtensions.getTintColor())))
            );
            mutableComponentList.set(1, ModLanguageGenerator.STORING_AMOUNT_FLUID.withParam(
                    Component.literal(String.valueOf(this.menu.getData(1))),
                    super.menu.getData(2)
            ));
            pGuiGraphics.renderComponentTooltip(super.font, mutableComponentList, pX, pY);
        }
        mutableComponentList.clear();
    }

    private Fluid getFluid() {
        return FLUID_REGISTRY.byIdOrThrow(this.menu.getData(0));
    }
}