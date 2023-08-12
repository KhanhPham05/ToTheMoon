package com.khanhtypo.tothemoon.client;

import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicScreen;
import com.khanhtypo.tothemoon.common.machine.AbstractMachineMenu;
import com.khanhtypo.tothemoon.common.machine.MachineRedstoneMode;
import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import com.khanhtypo.tothemoon.network.RedstoneModeTogglePacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

public class RedstoneModeToggleButton extends AbstractMachineButton {
    public RedstoneModeToggleButton(AbstractMachineMenu menu, BasicScreen<?> screen, int dataGetterIndex) {
        this(menu, screen, screen.getButtonX(), screen.getButtonY(), dataGetterIndex);
    }

    public RedstoneModeToggleButton(AbstractMachineMenu menu, BasicScreen<?> screen, int x, int y, int dataGetterIndex) {
        super(x, y, menu, screen, dataGetterIndex);
    }

    @Override
    public Component getMessage() {
        return ModLanguageGenerator.REDSTONE_MODE.withParam(MachineRedstoneMode.valueFromIndex(super.menu.getData(super.dataGetterIndex)).getTooltipTitle());
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        MachineRedstoneMode redstoneMode = MachineRedstoneMode.valueFromIndex(super.menu.getData(super.dataGetterIndex));
        int u = 44 + (redstoneMode.getIndex() * 22);
        if (isHovered()) {
            pGuiGraphics.blit(TEXTURE, super.getX(), super.getY(), u, 44, 22, 22);
            pGuiGraphics.renderComponentTooltip(super.font, redstoneMode.getTooltipComponents(), pMouseX, pMouseY);
        } else {
            pGuiGraphics.blit(TEXTURE, super.getX(), super.getY(), u, 22, 22, 22);
        }
    }

    @Override
    public void onClick(double pMouseX, double pMouseY) {
        int currentModeIndex = super.menu.getData(super.dataGetterIndex);
        ToTheMoon.CHANNEL.sendToServer(new RedstoneModeTogglePacket(MachineRedstoneMode.valueFromIndex(currentModeIndex < 2 ? currentModeIndex + 1 : 0)));
    }

    @Override
    public void playDownSound(SoundManager pHandler) {
        pHandler.play(SimpleSoundInstance.forUI(SoundEvents.LEVER_CLICK, 1, 1));
    }
}
