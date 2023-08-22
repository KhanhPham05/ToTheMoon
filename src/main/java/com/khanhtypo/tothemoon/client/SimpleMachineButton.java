package com.khanhtypo.tothemoon.client;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BaseMenu;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicScreen;
import com.khanhtypo.tothemoon.common.machine.AbstractMachineMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class SimpleMachineButton extends AbstractMachineButton {
    private final int u;
    private final int v;
    private final Component tooltip;
    private final Consumer<SimpleMachineButton> performClick;

    public SimpleMachineButton(int x, int y, int u, int v, BaseMenu menu, BasicScreen<?> screen, Component tooltip, Consumer<SimpleMachineButton> performClick) {
        super(x, y, menu, screen, -1);
        this.u = u;
        this.v = v;
        this.tooltip = tooltip;
        this.performClick = performClick;
    }

    @Override
    public Component getMessage() {
        return this.tooltip;
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int renderV = this.v;
        if (super.isHovered()) {
            renderV += 22;
            pGuiGraphics.renderTooltip(font, this.tooltip, pMouseX, pMouseY);
        }

        pGuiGraphics.blit(TEXTURE, super.getX(), super.getY(), u, renderV, 22, 22);
    }

    @Override
    public void onClick(double pMouseX, double pMouseY, int button) {
        this.performClick.accept(this);
    }
}
