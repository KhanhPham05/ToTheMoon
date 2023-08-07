package com.khanhtypo.tothemoon.client;

import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.menus.AbstractMachineMenu;
import com.khanhtypo.tothemoon.network.ServerBoundMachineActivePacket;
import com.khanhtypo.tothemoon.registration.elements.MenuObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static com.khanhtypo.tothemoon.data.c.ModLanguageGenerator.*;

public class ActiveModeButton extends Button {
    private static final OnPress PRESS = b -> {
    };
    private static final ResourceLocation ON_OFF_TEXTURE = MenuObject.texturePath("buttons");
    private final Font font;
    private final AbstractMachineMenu menu;
    private final AbstractContainerScreen<?> screen;


    public ActiveModeButton(AbstractMachineMenu menu, AbstractContainerScreen<?> screen, int x, int y) {
        super(x, y, 22, 22, TOGGLE.getComponent(), PRESS, DEFAULT_NARRATION);
        this.menu = menu;
        this.screen = screen;
        this.font = Minecraft.getInstance().font;
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        boolean isActive = menu.isActive();
        pGuiGraphics.blit(ON_OFF_TEXTURE, super.getX(), super.getY(),
                isActive ? 0 : 22,
                super.isHovered() ? 44 : 22,
                22, 22);
        if (super.isHovered()) {
            pGuiGraphics.renderTooltip(this.font, TOGGLE.withParam(isActive ? OFF : ON), pMouseX, pMouseY);
        }
    }

    @Override
    public void onPress() {
        ServerBoundMachineActivePacket.send(this.screen.getMinecraft().getConnection().getConnection());
    }

    @Override
    public void playDownSound(SoundManager pHandler) {
        pHandler.play(SimpleSoundInstance.forUI(Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wooden_button.click_" + (menu.isActive() ? "on" : "off")))), 1));
    }
}
