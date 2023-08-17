package com.khanhtypo.tothemoon.multiblock.blackstonefurnace;

import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import com.khanhtypo.tothemoon.registration.ModBlocks;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EmptyAcceptorPartEntity extends BasicBlackStoneFurnacePartEntity {
    private static final BlockEntityType<EmptyAcceptorPartEntity> BLOCK_ENTITY = ModBlockEntities.EMPTY_ACCESSOR_PART.get();

    public EmptyAcceptorPartEntity(BlockPos position, BlockState blockState) {
        this(BLOCK_ENTITY, position, blockState);
    }

    public EmptyAcceptorPartEntity(BlockEntityType<?> type, BlockPos position, BlockState blockState) {
        super(type, position, blockState);
    }

    @Override
    public boolean isGoodForPosition(PartPosition partPosition, IMultiblockValidator iMultiblockValidator) {
        if (ModBlocks.BLACK_STONE_FURNACE_CONTROLLER.isSame(this.getBlockState())) {
            if (!partPosition.isFrame()) {
                iMultiblockValidator.setLastError(super.getWorldPosition(), ModLanguageGenerator.BLACKSTONE_ACCEPTOR_NOT_FRAME);
                return false;
            }
            return true;
        } else if (ModBlocks.NETHER_BRICKS_EMPTY_ACCEPTOR.isSame(this.getBlockState())) {
            if (partPosition.isFace() && !partPosition.isFrame()) {
                return true;
            }

            iMultiblockValidator.setLastError(super.getWorldPosition(), ModLanguageGenerator.NETHER_ACCEPTOR_NOT_FRAME);
            return false;
        }

        return false;
    }
}
