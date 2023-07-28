package com.khanhtypo.tothemoon.common.block;

import com.khanhtypo.tothemoon.registration.elements.MenuObject;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;


@SuppressWarnings("deprecation")
public class ContainerBlock extends Block {
    private final MenuObject<?> menuObject;

    public ContainerBlock(Properties p_49795_, MenuObject<?> menuObject) {
        super(p_49795_);
        this.menuObject = menuObject;
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level level, BlockPos clickedPos, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if (!level.isClientSide()) {
            this.menuObject.openScreen(p_60506_, level, clickedPos);
            return InteractionResult.CONSUME;
        }

        return InteractionResult.SUCCESS;
    }
}
