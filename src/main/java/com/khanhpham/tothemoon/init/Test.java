package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.utils.BlockRegister;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Test {
    public Test() {
    }

    static final BlockRegister blockRegister = new BlockRegister();

    public static final Block testBlock = blockRegister.register("test_block", new Block(BlockBehaviour.Properties.of(Material.LEAVES)));

    @SubscribeEvent
    public static void setBlockRegister(RegistryEvent.Register<Block> block) {
        blockRegister.blocks.forEach(b -> block.getRegistry().register(b));
    }
}
