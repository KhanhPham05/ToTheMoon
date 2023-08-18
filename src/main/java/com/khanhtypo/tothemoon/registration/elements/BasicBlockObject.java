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

    public BasicBlockObject(String name, Function<BlockBehaviour.Properties, ? extends Block> factory, ObjectSupplier<? extends Block> property) {
        super(name, () -> factory.apply(BlockBehaviour.Properties.copy(property.get())));
    }

    public BasicBlockObject(String name, Block vanillaBlock) {
        this(name, vanillaBlock, vanillaBlock.defaultMapColor());
    }

    public BasicBlockObject(String name, Block vanillaBlock, MapColor mapColor) {
        this(name, BlockBehaviour.Properties.copy(vanillaBlock).mapColor(mapColor));
    }

    public static ChildBlockObject stairs(BlockObject<?> parent) {
        return new ChildBlockObject(parent.getId().getPath() + "_stairs", properties -> new StairBlock(parent.defaultBlockState(), properties), parent);
    }

    public static ChildBlockObject slab(BlockObject<?> parent) {
        return new ChildBlockObject(parent.getId().getPath() + "_slab", SlabBlock::new, parent);
    }

    public static ChildBlockObject wall(BlockObject<?> parent) {
        return new ChildBlockObject(parent.getId().getPath() + "_wall", WallBlock::new, parent);
    }

    public BlockObject<?> getParentObject() {
        return this;
    }
}
