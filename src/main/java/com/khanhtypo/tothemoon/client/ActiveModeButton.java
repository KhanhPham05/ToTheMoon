package com.khanhtypo.tothemoon.client;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicScreen;
import com.khanhtypo.tothemoon.common.machine.AbstractMachineMenu;
import com.khanhtypo.tothemoon.network.MachineActiveTogglePacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static com.khanhtypo.tothemoon.data.c.ModLanguageGenerator.*;

public class ActiveModeButton extends AbstractMachineButton {

    public ActiveModeButton(AbstractMachineMenu menu, BasicScreen<? extends AbstractMachineMenu> screen) {
        super(screen.getButtonX(), screen.getButtonY(), menu, screen, 0);
    }

    @Override
    public Component getMessage() {
        return getToggleText();
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        boolean isActive = menu.isActive();
        pGuiGraphics.blit(TEXTURE, super.getX(), super.getY(),
                isActive ? 0 : 22,
                super.isHovered() ? 44 : 22,
                22, 22);
        if (super.isHovered()) {
            pGuiGraphics.renderTooltip(this.font, getToggleText(), pMouseX, pMouseY);
        }
    }

    private Component getToggleText() {
        boolean isActive = menu.isActive();
        return TOGGLE.withParam(isActive ? OFF : ON);
    }

    @Override
    public void onClick(double pMouseX, double pMouseY) {
        MachineActiveTogglePacket.send();
    }

    @Override
    public void playDownSound(SoundManager pHandler) {
        pHandler.play(SimpleSoundInstance.forUI(Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wooden_button.click_" + (menu.isActive() ? "on" : "off")))), 1));
    }
}
