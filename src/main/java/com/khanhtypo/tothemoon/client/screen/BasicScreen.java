package com.khanhtypo.tothemoon.client.screen;

import com.google.common.base.Preconditions;
import com.khanhtypo.tothemoon.client.ModRequiredButton;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BaseMenu;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.ScreenHasRecipe;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

@SuppressWarnings({"SameParameterValue", "unused"})
public class BasicScreen<MENU extends BaseMenu> extends AbstractContainerScreen<MENU> {
    public static final ResourceLocation RECIPE_BOOK_WIDGET = ModUtils.location("textures/gui/widgets.png");
    protected static final int TEXT_WHITE = 0xe0e0e0;
    protected static final int TEXT_BLACK = 0x404040;
    private final ResourceLocation guiTexture;
    private final int containerSize;

    public BasicScreen(MENU menu, Inventory inventory, Component title) {
        this(menu, inventory, title, 176, 166);
    }

    public BasicScreen(MENU menu, Inventory inventory, Component title, int imageWidth, int imageHeight) {
        super(menu, inventory, title);
        this.guiTexture = menu.getGuiPath();
        super.imageWidth = imageWidth;
        super.imageHeight = imageHeight;
        this.containerSize = menu.slots.size();
        Preconditions.checkState(menu.inventorySlotIndex >= 0, "Container %s did not include player inventory".formatted(menu.getMenuId()));
    }

    @Override
    protected final void init() {
        super.init();
        super.inventoryLabelX = super.getMenu().getInvLabelX();
        super.inventoryLabelY = super.getMenu().getInvLabelY() - 12;
        if (this instanceof ScreenHasRecipe) {
            super.addRenderableOnly(new ModRequiredButton("jei", this.getButtonX(), this.getButtonY(), 22, 0));
            //TODO Patchouli Book
        }
        this.addExtraButtons();
    }

    protected void addExtraButtons() {
    }

    public final int getButtonY() {
        return super.topPos + (super.renderables.size() * 22);
    }

    public final int getButtonX() {
        return super.leftPos - 22;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected final void renderBg(GuiGraphics renderer, float pPartialTick, int pMouseX, int pMouseY) {
        renderer.blit(this.guiTexture, super.leftPos, super.topPos, 0, 0, super.imageWidth, super.imageHeight);
        this.renderBgAddition(renderer, this.guiTexture);
    }

    protected void renderBgAddition(GuiGraphics renderer, ResourceLocation guiTexture) {
    }

    protected void drawVerticalBar(GuiGraphics renderer, ResourceLocation guiTexture, int maxCorerX, int maxCornerY, int xOffset, int barWidth, int barHeight, int dataIndex, int dataIndexMax) {
        int height = this.getDataHeight(dataIndex, dataIndexMax, barHeight);
        if (height > 0)
            renderer.blit(guiTexture, super.leftPos + maxCorerX, super.topPos + maxCornerY + barHeight - height, xOffset, barHeight - height, barWidth, height);
    }

    protected int getDataHeight(int dataCurrent, int dataMax, int barHeight) {
        final int current = super.menu.getData(dataCurrent);
        final int max = super.menu.getData(dataMax);
        return current != 0 && max != 0 ? (current * barHeight / max) : 0;
    }

    protected void drawHorizontalBar(GuiGraphics guiGraphics, ResourceLocation guiTexture, int minCornerX, int minCornerY, int startXOffset, int startYOffset, int barWidth, int barHeight, int dataSlotCurrent, int dataSlotMax) {
        final int current = super.menu.getData(dataSlotCurrent);
        if (current != 0) {
            final int max = super.menu.getData(dataSlotMax);
            final int renderWidth = max != 0 ? (current * barWidth / max) : 0;
            if (renderWidth > 0)
                guiGraphics.blit(guiTexture, super.leftPos + minCornerX, super.topPos + minCornerY, startXOffset, startYOffset, renderWidth, barHeight);
        }
    }

    protected void drawDebugText(GuiGraphics renderer) {
        for (int i = 0; i < this.menu.getDataCount(); i++) {
            renderer.drawString(font, "%s : %s".formatted(i, menu.getData(i)), 0, super.topPos + i * 8, TEXT_WHITE, true);
        }
    }

    protected void renderDebugSlotNumber(GuiGraphics renderer) {
        for (int i = 0; i < this.containerSize; i++) {
            Slot slot = super.menu.slots.get(i);
            if (slot.isActive()) {
                renderer.drawString(font, String.valueOf(i), leftPos + slot.x, topPos + slot.y, TEXT_BLACK, true);
            }
        }
    }
}
