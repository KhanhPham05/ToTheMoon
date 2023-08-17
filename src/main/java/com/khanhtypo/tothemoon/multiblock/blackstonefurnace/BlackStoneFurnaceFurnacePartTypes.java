package com.khanhtypo.tothemoon.multiblock.blackstonefurnace;

import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import it.zerono.mods.zerocore.lib.block.multiblock.IMultiblockPartType2;
import it.zerono.mods.zerocore.lib.block.multiblock.MultiblockPartTypeProperties;
import it.zerono.mods.zerocore.lib.multiblock.variant.IMultiblockVariant;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public enum BlackStoneFurnaceFurnacePartTypes implements IMultiblockPartType2<MultiblockBlackStoneFurnace, BlackStoneFurnaceFurnacePartTypes> {
    FRAME(
            new MultiblockPartTypeProperties<>(
                    () -> ModBlockEntities.SMOOTH_BLACK_STONE_PART::get,
                    BaseBlackStoneFurnacePartBlock::new,
                    ModLanguageGenerator.createKey("block", "smooth_blackstone"),
                    p -> p,
                    p -> p.setBlockProperties(BlockBehaviour.Properties.copy(Blocks.BLACKSTONE))
            )
    ),

    ACCEPTOR_EMPTY(
            new MultiblockPartTypeProperties<>(
                    () -> ModBlockEntities.EMPTY_ACCESSOR_PART::get,
                    BaseBlackStoneFurnacePartBlock::new,
                    ModLanguageGenerator.createKey("block", "empty_acceptors"),
                    p -> p,
                    p -> p
            )
    ),

    ITEM_ACCEPTOR(
            new MultiblockPartTypeProperties<>(
                    () -> ModBlockEntities.BLACK_STONE_FURNACE_ITEM_ACCEPTOR::get,
                    BaseBlackStoneFurnacePartBlock::new,
                    ModLanguageGenerator.createKey("block", "item_acceptor"),
                    p -> p,
                    p -> p
            )
    ),

    CONTROLLER(
            new MultiblockPartTypeProperties<>(
                    () -> ModBlockEntities.BLACK_STONE_FURNACE_CONTROLLER_PART::get,
                    BlackstoneFurnaceControllerBlock::new,
                    ModLanguageGenerator.createKey("block", "nether_brick_furnace_controller"),
                    p -> p,
                    partProperties -> partProperties.setBlockProperties(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS).lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0))
            ));

    private final MultiblockPartTypeProperties<MultiblockBlackStoneFurnace, BlackStoneFurnaceFurnacePartTypes> properties;

    BlackStoneFurnaceFurnacePartTypes(MultiblockPartTypeProperties<MultiblockBlackStoneFurnace, BlackStoneFurnaceFurnacePartTypes> properties) {
        this.properties = properties;
    }

    @Override
    public BaseBlackStoneFurnacePartBlock createBlock(IMultiblockVariant variant) {
        return (BaseBlackStoneFurnacePartBlock) IMultiblockPartType2.super.createBlock(variant);
    }

    @Override
    public BaseBlackStoneFurnacePartBlock createBlock() {
        return (BaseBlackStoneFurnacePartBlock) IMultiblockPartType2.super.createBlock();
    }

    @Override
    public MultiblockPartTypeProperties<MultiblockBlackStoneFurnace, BlackStoneFurnaceFurnacePartTypes> getPartTypeProperties() {
        return this.properties;
    }

    @Override
    public String getSerializedName() {
        return this.toString();
    }
}
