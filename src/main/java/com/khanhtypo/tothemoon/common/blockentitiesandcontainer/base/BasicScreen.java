package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import com.khanhtypo.tothemoon.client.DecorationButton;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BasicScreen<MENU extends BaseMenu> extends AbstractContainerScreen<MENU> {
    public static final int DEFAULT_WIDGET_HEIGHT = 22;
    public static final ResourceLocation RECIPE_BOOK_WIDGET = ModUtils.location("textures/gui/widgets.png");
    protected static final int TEXT_WHITE = 0xe0e0e0;
    protected static final int TEXT_BLACK = 0x404040;
    private final ResourceLocation guiTexture;

    public BasicScreen(MENU menu, Inventory inventory, Component component) {
        this(menu, inventory, component, 176, 166);
    }

    public BasicScreen(MENU menu, Inventory inventory, Component component, int imageWidth, int imageHeight) {
        super(menu, inventory, component);
        this.guiTexture = menu.getGuiPath();
        super.imageWidth = imageWidth;
        super.imageHeight = imageHeight;
    }

    @Override
    protected final void init() {
        super.init();
        super.inventoryLabelX = super.getMenu().getInvLabelX();
        super.inventoryLabelY = super.getMenu().getInvLabelY() - 12;
        if (this instanceof RecipeContainerMenu) {
            super.addRenderableOnly(new DecorationButton("jei", this.getButtonX(), this.getButtonY(), 22, 0));
            //TODO Patchouli Book
        }
        this.addExtraButtons();
    }

    protected void addExtraButtons() {
    }

    protected final int getButtonY() {
        return super.topPos + (super.renderables.size() * 22);
    }

    protected final int getButtonX() {
        return super.leftPos - 22;
    }

    @Override
    public void render(GuiGraphics p_283479_, int p_283661_, int p_281248_, float p_281886_) {
        this.renderBackground(p_283479_);
        super.render(p_283479_, p_283661_, p_281248_, p_281886_);
        this.renderTooltip(p_283479_, p_283661_, p_281248_);
    }

    @Override
    protected final void renderBg(GuiGraphics renderer, float pPartialTick, int pMouseX, int pMouseY) {
        renderer.blit(this.guiTexture, super.leftPos, super.topPos, 0, 0, super.imageWidth, super.imageHeight);
        this.renderBgAddition(renderer, this.guiTexture);
    }

    protected void renderBgAddition(GuiGraphics renderer, ResourceLocation guiTexture) {
    }
}
