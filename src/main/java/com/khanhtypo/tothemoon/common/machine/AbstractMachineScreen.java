package com.khanhtypo.tothemoon.common.machine;

import com.khanhtypo.tothemoon.client.ActiveModeButton;
import com.khanhtypo.tothemoon.client.RedstoneModeToggleButton;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicScreen;
import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractMachineScreen<T extends AbstractMachineMenu> extends BasicScreen<T> {
    public AbstractMachineScreen(T menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    public AbstractMachineScreen(T menu, Inventory inventory, Component component, int imageWidth, int imageHeight) {
        super(menu, inventory, component, imageWidth, imageHeight);
    }


    @Override
    protected void addExtraButtons() {
        super.addRenderableWidget(new ActiveModeButton(super.getMenu(), this));
        super.addRenderableWidget(new RedstoneModeToggleButton(super.getMenu(), this, 7));
    }

    public void tryDrawEnergyStorageTooltip(GuiGraphics guiGraphics, int x, int y, int boxWidth, int boxHeight, int energyDataSlot, int energyCapDataSlot, int mouseX, int mouseY) {
        if (isHovering(x, y, boxWidth, boxHeight, mouseX, mouseY)) {
            int energy = super.menu.getData(energyDataSlot);
            int cap = super.menu.getData(energyCapDataSlot);
            float f = Math.max(0.0F, 1.0f - ((cap - (float) energy) / cap));
            int color = Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
            guiGraphics.renderTooltip(font, ModLanguageGenerator.ENERGY_TOOLTIP.withParam(Component.literal(String.valueOf(energy)).withStyle(Style.EMPTY.withColor(color)), cap), mouseX, mouseY);
        }
    }
}
