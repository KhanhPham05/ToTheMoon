package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import com.khanhtypo.tothemoon.ModUtils;
import com.khanhtypo.tothemoon.client.DecorationButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BasicScreen<MENU extends BasicMenu> extends AbstractContainerScreen<MENU> {
    public static final ResourceLocation RECIPE_BOOK_WIDGET = ModUtils.location("textures/gui/widgets.png");
    protected static final int TEXT_WHITE = 0xe0e0e0;
    protected static final int TEXT_BLACK = 0x404040;
    private final ResourceLocation guiTexture;
    private DecorationButton[] buttons;

    public BasicScreen(MENU p_97741_, Inventory p_97742_, Component p_97743_) {
        this(p_97741_, p_97742_, p_97743_, 176, 166);
    }

    public BasicScreen(MENU p_97741_, Inventory p_97742_, Component p_97743_, int imageWidth, int imageHeight) {
        super(p_97741_, p_97742_, p_97743_);
        this.guiTexture = p_97741_.getGuiPath();
        super.imageWidth = imageWidth;
        super.imageHeight = imageHeight;
    }

    @Override
    protected void init() {
        super.init();
        super.inventoryLabelX = super.getMenu().getInvLabelX();
        super.inventoryLabelY = super.getMenu().getInvLabelY() - 12;
        this.buttons = new DecorationButton[]{
                super.addRenderableOnly(new DecorationButton("jei", super.leftPos - 22, super.topPos, 22, 0)),
                //TODO Patchouli Book
        };
    }


    @Override
    public void render(GuiGraphics p_283479_, int p_283661_, int p_281248_, float p_281886_) {
        this.renderBackground(p_283479_);
        this.setHoverToButtons(p_283661_, p_281248_);
        super.render(p_283479_, p_283661_, p_281248_, p_281886_);
        this.renderTooltip(p_283479_, p_283661_, p_281248_);
    }

    @Override
    protected void renderBg(GuiGraphics renderer, float p_97788_, int p_97789_, int p_97790_) {
        renderer.blit(this.guiTexture, super.leftPos, super.topPos, 0, 0, super.imageWidth, super.imageHeight);
        this.renderBgAddition(renderer);
    }

    private void setHoverToButtons(int mouseX, int mouseY) {
        for (DecorationButton button : this.buttons) {
            button.setHovered(super.isHovering(button.x - super.leftPos, button.y - super.topPos, button.width, button.height, mouseX, mouseY));
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics p_283594_, int mouseX, int mouseY) {
        super.renderTooltip(p_283594_, mouseX, mouseY);
        for (DecorationButton button : this.buttons) {
            if (button.isHovered) {
                button.renderToolTip(p_283594_, super.font, mouseX, mouseY);
            }
        }
    }

    protected void renderBgAddition(GuiGraphics renderer) {
    }
}
