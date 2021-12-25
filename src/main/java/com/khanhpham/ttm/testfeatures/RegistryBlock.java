package com.khanhpham.ttm.testfeatures;

import com.khanhpham.ttm.ToTheMoonMain;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.IForgeRegistry;

public class RegistryBlock extends RegistryHelper<Block> {
    public RegistryBlock (String modid) {
        super(modid);
    }

    @Deprecated(forRemoval = true)
    public void initBlockItem(IForgeRegistry<Item> reg) {
        super.registrySet.forEach(block -> {
                reg.register(new BlockItem(block, new Item.Properties().tab(ToTheMoonMain.TTM_TAB)));
        });
    }
}
