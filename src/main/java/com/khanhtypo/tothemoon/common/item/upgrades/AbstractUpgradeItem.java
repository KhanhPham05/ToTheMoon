package com.khanhtypo.tothemoon.common.item.upgrades;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.AbstractMachineBlockEntity;
import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractUpgradeItem extends Item implements IUpgradeItem {
    private static final Component TOOLTIP = ModLanguageGenerator.UPGRADE_GENERATOR_ITEM.withParam(ModLanguageGenerator.POWER_GENERATOR_GENERAL);
    protected final int tier;
    private final UpgradeItemType upgradeType;

    public AbstractUpgradeItem(UpgradeItemType upgradeType, int tier) {
        super(new Properties().stacksTo(1));
        this.upgradeType = upgradeType;
        this.tier = tier;
    }

    @Override
    public UpgradeItemType getUpgradeType() {
        return upgradeType;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (this instanceof ItemPowerGeneratorUpgrade) {
            pTooltipComponents.add(TOOLTIP);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide) {
            if (pContext.getPlayer().isShiftKeyDown()) {
                BlockEntity blockEntity = pContext.getLevel().getBlockEntity(pContext.getClickedPos());
                if (blockEntity instanceof AbstractMachineBlockEntity powerBlockEntity) {
                    if (powerBlockEntity.canPutUpgradeIn(this)) {
                        powerBlockEntity.putUpgradeIn(this);
                        pContext.getPlayer().setItemInHand(pContext.getHand(), ItemStack.EMPTY);
                        return InteractionResult.CONSUME;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }
}
