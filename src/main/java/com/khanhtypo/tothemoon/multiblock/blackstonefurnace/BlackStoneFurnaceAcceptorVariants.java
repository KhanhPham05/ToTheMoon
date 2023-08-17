package com.khanhtypo.tothemoon.multiblock.blackstonefurnace;

import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import it.zerono.mods.zerocore.lib.multiblock.variant.IMultiblockVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

public enum BlackStoneFurnaceAcceptorVariants implements IMultiblockVariant {
    NETHER_BRICK(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)),
    BLACKSTONE(BlockBehaviour.Properties.copy(Blocks.BLACKSTONE));

    private final BlockBehaviour.Properties blockProperties;

    BlackStoneFurnaceAcceptorVariants(BlockBehaviour.Properties blockProperties) {
        this.blockProperties = blockProperties;
    }

    public static BlackStoneFurnaceAcceptorVariants getDefinitionForBlock(Level level, BlockPos blockPos) {
        Block block = level.getBlockState(blockPos).getBlock();
        if (block instanceof BaseBlackStoneFurnacePartBlock partBlock) {
            return partBlock.getMultiblockVariant().map(v -> ((BlackStoneFurnaceAcceptorVariants) v)).orElseThrow(() -> new IllegalStateException(BlackStoneFurnaceAcceptorVariants.class.getSimpleName() + " variant does not present in block " + ForgeRegistries.BLOCKS.getKey(block)));
        }
        throw new IllegalStateException("block " + ForgeRegistries.BLOCKS.getKey(block) + " is not a type of " + BaseBlackStoneFurnacePartBlock.class.getSimpleName());
    }

    @Override
    public int getId() {
        return this.ordinal();
    }

    @Override
    public String getName() {
        return this.toString().toLowerCase(Locale.ROOT);
    }

    @Override
    public String getTranslationKey() {
        String path = "blackstone_furnace_variant_" + this.getName();
        return ModLanguageGenerator.createTranslatableKey("multiblock", path, ModLanguageGenerator.transform(path));
    }

    public boolean isBlackstone() {
        return this == BLACKSTONE;
    }

    @Override
    public BlockBehaviour.Properties getBlockProperties() {
        return blockProperties;
    }

    @Override
    public BlockBehaviour.Properties getDefaultBlockProperties() {
        return this.getBlockProperties();
    }

}
