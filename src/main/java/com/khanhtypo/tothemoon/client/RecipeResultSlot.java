package com.khanhtypo.tothemoon.client;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.AlwaysSavedResultContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.BiConsumer;

public class RecipeResultSlot extends FilterSlot {
    private final Container craftingContainer;
    private final BiConsumer<Player, ItemStack> onResultSlotTaken;
    private int removeCount;
    RecipeResultSlot(AlwaysSavedResultContainer p_40223_, Container craftingContainer, int p_40224_, int p_40225_, int p_40226_, BiConsumer<Player, ItemStack> onResultSlotTaken) {
        super(p_40223_, p_40224_, p_40225_, p_40226_, SlotUtils.NO, SlotUtils.YES);
        this.craftingContainer = craftingContainer;
        this.onResultSlotTaken = onResultSlotTaken;
    }

    @Override
    protected void onQuickCraft(ItemStack p_40232_, int p_40233_) {
        this.removeCount += p_40233_;
        this.checkTakeAchievements(p_40232_);
    }

    @Override
    protected void onSwapCraft(int p_40237_) {
        this.removeCount += p_40237_;
    }

    @Override
    public ItemStack remove(int p_40227_) {
        if (super.hasItem()) {
            this.removeCount += Math.min(p_40227_, super.getItem().getCount());
        }

        return super.remove(p_40227_);
    }

    @Override
    protected void checkTakeAchievements(ItemStack p_40239_) {
        if (this.removeCount > 0) {
            Player player = this.getContainer().getMenu().player();
            p_40239_.onCraftedBy(this.getContainer().getMenu().getLevel(), player, this.removeCount);
            ForgeEventFactory.firePlayerCraftingEvent(player, p_40239_, this.craftingContainer);
        }

        this.removeCount = 0;
    }

    @Override
    public void onTake(Player p_150645_, ItemStack p_150646_) {
        this.checkTakeAchievements(p_150646_);
        this.onResultSlotTaken.accept(p_150645_, p_150646_);
    }

    @Override
    protected AlwaysSavedResultContainer getContainer() {
        return (AlwaysSavedResultContainer) super.getContainer();
    }
}
