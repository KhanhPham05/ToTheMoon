package com.khanhpham.ttm.core.blocks.variants;

import com.khanhpham.ttm.ToTheMoonMain;
import com.khanhpham.ttm.core.ToolType;
import com.khanhpham.ttm.core.blocks.MineableBlock;
import com.khanhpham.ttm.core.blocks.MiningLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class MineableSlabBlock extends SlabBlock implements MineableBlock {
    private final ToolType tool;
    private final MiningLevel level;

    @Nonnull
    private final Block parentBlock;

    public MineableSlabBlock(Properties p_56359_, Block parentBlock, ToolType tool, MiningLevel level) {
        super(p_56359_);
        this.tool = tool;
        this.level = level;
        this.parentBlock = parentBlock;
    }

    @Nullable
    public ResourceLocation getParentBlockLocation() {
        return parentBlock.getRegistryName();
    }

    public String getParentBlockModelFile() {
        return "block/" + parentBlock.getRegistryName().getPath();
    }

    public String getParentBlockId() {
        return Objects.requireNonNull(getParentBlockLocation()).getPath();
    }

    @Override
    public ToolType getHarvestTool() {
        return tool;
    }

    @Override
    public MiningLevel getMiningLevel() {
        return level;
    }
}
