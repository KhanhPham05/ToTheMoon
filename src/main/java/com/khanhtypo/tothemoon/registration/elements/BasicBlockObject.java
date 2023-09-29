package com.khanhtypo.tothemoon.registration.elements;

import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

public class BasicBlockObject extends BlockObject<Block> {
    public BasicBlockObject(String name, ObjectSupplier<? extends Block> property) {
        super(name, () -> new Block(BlockBehaviour.Properties.copy(property.get())));
    }

    public BasicBlockObject(String name, BlockBehaviour.Properties properties) {
        super(name, () -> new Block(properties));
    }

    public BasicBlockObject(String name, ObjectSupplier<? extends Block> property, Function<BlockBehaviour.Properties, ? extends Block> factory) {
        super(name, () -> factory.apply(BlockBehaviour.Properties.copy(property.get())));
    }

    public BasicBlockObject(String name, Block vanillaBlock) {
        this(name, vanillaBlock, vanillaBlock.defaultMapColor());
    }

    public BasicBlockObject(String name, BlockBehaviour vanillaBlock, MapColor mapColor) {
        this(name, BlockBehaviour.Properties.copy(vanillaBlock).mapColor(mapColor));
    }

    public static ChildBlockObject stairs(BlockObject<?> parent) {
        return new ChildBlockObject(parent.getId().getPath() + "_stairs", parent, properties -> new StairBlock(parent.defaultBlockState(), properties));
    }

    public static ChildBlockObject slab(BlockObject<?> parent) {
        return new ChildBlockObject(parent.getId().getPath() + "_slab", parent, SlabBlock::new);
    }

    public static ChildBlockObject wall(BlockObject<?> parent) {
        return new ChildBlockObject(parent.getId().getPath() + "_wall", parent, WallBlock::new);
    }

    public BlockObject<?> getParentObject() {
        return this;
    }
}
