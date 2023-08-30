package com.khanhtypo.tothemoon.client;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BaseMenu;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class ItemStackMachineButton extends AbstractMachineButton {
    private final ItemStack onDisplay;
    private final Component message;
    private final Consumer<AbstractMachineButton> onPress;

    public ItemStackMachineButton(int x, int y, BaseMenu menu, BasicScreen<?> screen, int dataGetterIndex, ItemLike onDisplay, Component message, Consumer<AbstractMachineButton> onPress) {
        super(x, y, menu, screen, dataGetterIndex);
        this.onDisplay = new ItemStack(onDisplay);
        this.message = message;
        this.onPress = onPress;
    }

    @Override
    public Component getMessage() {
        return this.message;
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.blit(TEXTURE, super.getX(), super.getY(), 234, 212 + (super.isHovered ? 22 : 0), 22, 22);
        pGuiGraphics.renderFakeItem(this.onDisplay, super.getX() + 3, super.getY() + 3);
        if (isHovered()) {
            pGuiGraphics.renderTooltip(super.font, this.getMessage(), pMouseX, pMouseY);
        }
    }

    @Override
    public void onClick(double pMouseX, double pMouseY, int button) {
        this.onPress.accept(this);
    }
}
