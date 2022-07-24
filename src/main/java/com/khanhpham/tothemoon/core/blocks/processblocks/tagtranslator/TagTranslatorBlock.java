package com.khanhpham.tothemoon.core.blocks.processblocks.tagtranslator;

import com.khanhpham.tothemoon.core.blocks.HasCustomBlockItem;
import com.khanhpham.tothemoon.core.items.DetailedBlockItem;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class TagTranslatorBlock extends Block implements HasCustomBlockItem {
    public TagTranslatorBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.5F).noOcclusion());
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            pPlayer.openMenu(new SimpleMenuProvider(
                    (id, inv, player) -> new TagTranslatorMenu(id, inv, ContainerLevelAccess.create(pLevel, pPos)),
                    this.getName()
            ));

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }


    @Override
    public BlockItem getRawItem() {
        return new DetailedBlockItem(this, 1, ModLanguage.TAG_TRANSLATOR);
    }
}
