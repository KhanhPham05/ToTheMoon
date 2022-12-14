package com.khanhpham.tothemoon.core.processes.single;

import com.khanhpham.tothemoon.core.abstracts.BaseMenuScreen;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.utils.ModRequiredFunctionalButton;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.helpers.RegistryEntries;
import com.khanhpham.tothemoon.utils.text.TextUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class SingleProcessMenuScreen extends BaseMenuScreen<SingleProcessMenu> {
    public static final ResourceLocation GUI_LOCATION = ModUtils.getGuiId("process/single");

    public SingleProcessMenuScreen(SingleProcessMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, GUI_LOCATION);
        super.setImageSize(198, 177);
    }

    @Override
    protected void init() {
        super.init();
        super.addRenderableWidget(new ModRequiredFunctionalButton(this, 0, 155, 0, 0, ModLanguage.TEXT_HOW_TO_USE, "patchouli", () -> {
        }) {
            @Override
            public void playDownSound(@NotNull SoundManager pHandler) {
                pHandler.play(SimpleSoundInstance.forUI(RegistryEntries.SOUND.getFromKey(new ResourceLocation("patchouli", "book_open")), 1f));
            }
        });
        super.addRenderableOnly(new ModRequiredFunctionalButton(this, 0, 133, 22, 0, Component.translatable("key.jei.showRecipe"), "jei"){
            @Override
            public boolean shouldShowTooltip() {
                return false;
            }
        });
    }

    @Override
    protected void renderExtra(PoseStack pPoseStack) {
        super.blit(pPoseStack, leftPos, topPos + 4, 198, 9 + (22 * this.menu.getEnergyStatus()), 22, 22);
        super.blit(pPoseStack, leftPos + 86, topPos + 36, 198, 0, this.menu.getProcessBar(), 9);
    }

    @Override
    protected void renderTooltip(PoseStack pPoseStack, int pX, int pY) {
        super.renderTooltip(pPoseStack, pX, pY);
        if (super.isHovering(0, 4, 22, 22, pX, pY)) {
            super.renderTooltip(pPoseStack, Component.translatable(ModLanguage.ENERGY_STRING, TextUtils.showColoredPercentage(this.menu.getStoredEnergy(), this.menu.getEnergyCapacity()), TextUtils.translateEnergy(this.menu.getStoredEnergy()), TextUtils.translateEnergy(this.menu.getEnergyCapacity())), leftPos + 22, topPos + 4);
        }
    }
}
