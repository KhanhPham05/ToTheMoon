package com.khanhtypo.tothemoon.client.screen;

import com.khanhtypo.tothemoon.client.ActiveModeButton;
import com.khanhtypo.tothemoon.client.ItemStackMachineButton;
import com.khanhtypo.tothemoon.client.MutableSlot;
import com.khanhtypo.tothemoon.client.RedstoneModeToggleButton;
import com.khanhtypo.tothemoon.common.item.upgrades.UpgradeItemType;
import com.khanhtypo.tothemoon.common.machine.AbstractMachineMenu;
import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import com.khanhtypo.tothemoon.utls.GuiRenderHelper;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractMachineScreen<T extends AbstractMachineMenu> extends BasicScreen<T> {
    public static final ResourceLocation UPGRADE_BOX_BG = ModUtils.location("textures/gui/upgrade_panel.png");
    private static final int upgradeBoxHeight = 32;
    private boolean isUpgradeBoxVisible;

    public AbstractMachineScreen(T menu, Inventory inventory, Component component, int imageWidth, int imageHeight) {
        super(menu, inventory, component, imageWidth, imageHeight);
        this.isUpgradeBoxVisible = false;
    }

    protected abstract int getEnergy();

    protected abstract int getEnergyCapacity();

    @Override
    protected void addExtraButtons() {
        super.addRenderableWidget(new ActiveModeButton(super.getMenu(), this));
        super.addRenderableWidget(new RedstoneModeToggleButton(super.getMenu(), this, 7));
        super.addRenderableWidget(
                new ItemStackMachineButton(
                        super.getButtonX(),
                        super.topPos + super.imageHeight - 22,
                        this.getMenu(),
                        this,
                        -1,
                        UpgradeItemType.SPEED_UPGRADE.getWithLevel(1),
                        ModLanguageGenerator.TOGGLE_UPGRADE_BOX,
                        button -> {
                            isUpgradeBoxVisible = !isUpgradeBoxVisible;
                            final int upgradeIndex = super.getMenu().getUpgradeSlotIndex();
                            for (int i = upgradeIndex; i < upgradeIndex + 3; i++) {
                                if (super.getMenu().slots.get(i) instanceof MutableSlot slot) {
                                    slot.setActive(isUpgradeBoxVisible);
                                }
                            }
                        }));
    }

    @Override
    protected void renderBgAddition(GuiGraphics renderer, ResourceLocation guiTexture) {
        if (this.isUpgradeBoxVisible) {
            renderer.blit(UPGRADE_BOX_BG, super.leftPos - 68 - 22, super.topPos + super.imageHeight - upgradeBoxHeight, 0, 0, 68, upgradeBoxHeight);
        }
    }

    @Override
    public void removed() {
        this.isUpgradeBoxVisible = false;
        final int upgradeIndex = super.getMenu().getUpgradeSlotIndex();
        for (int i = upgradeIndex; i < upgradeIndex + 3; i++) {
            if (super.getMenu().slots.get(i) instanceof MutableSlot slot) {
                slot.setActive(false);
            }
        }
        super.removed();
    }

    public void tryDrawEnergyStorageTooltip(GuiGraphics guiGraphics, int x, int y, int boxWidth, int boxHeight, int mouseX, int mouseY) {
        if (isHovering(x, y, boxWidth, boxHeight, mouseX, mouseY)) {
            GuiRenderHelper.renderStorageTooltip(guiGraphics, font, ModLanguageGenerator.ENERGY_TOOLTIP, this.getEnergy(), this.getEnergyCapacity(), mouseX, mouseY);
        }
    }
}
