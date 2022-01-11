package com.khanhpham.ttm.init;

import com.khanhpham.ttm.ToTheMoonMain;
import com.khanhpham.ttm.core.ToolType;
import com.khanhpham.ttm.core.blockentities.energybank.EnergyBankEntity;
import com.khanhpham.ttm.core.blockentities.energygen.EnergyGeneratorBlockEntity;
import com.khanhpham.ttm.core.blockentities.energygen.impl.CopperGenEntity;
import com.khanhpham.ttm.core.blocks.variants.BaseBlock;
import com.khanhpham.ttm.core.blocks.EnergyBankBlock;
import com.khanhpham.ttm.core.blocks.MiningLevel;
import com.khanhpham.ttm.core.blocks.impl.gen.CopperGenBlock;
import com.khanhpham.ttm.core.blocks.variants.MineableSlabBlock;
import com.khanhpham.ttm.core.blocks.variants.MineableStairBlock;
import com.khanhpham.ttm.registration.RegistryHelper;
import com.khanhpham.ttm.testfeatures.EnergyGenBlock;
import com.khanhpham.ttm.utils.block.ModCapableBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * NOTE: All the blocks harvesting level/tool type in 1.17 and above are replaced with tag so {@link BlockBehaviour} no longer contains
 * harvestLevel() and HarvestTool() methods.
 *
 * @see net.minecraft.world.level.block.Blocks#COBBLESTONE
 */
@Mod.EventBusSubscriber(modid = ToTheMoonMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModBlocks {
    public static final RegistryHelper<Block> BLOCKS = new RegistryHelper<>(ToTheMoonMain.MOD_ID);

    public static final Block ENERGY_GEN = registerCapable("energy_generator", new EnergyGenBlock(getProperties(Material.METAL, 3.5f), ToolType.PICKAXE, MiningLevel.STONE_PICKAXE, EnergyGeneratorBlockEntity::new));

    public static final Block ENERGY_BANK = registerCapable("energy_bank", new EnergyBankBlock(getProperties(Material.METAL, 5.0f, 6.0f), ToolType.PICKAXE, MiningLevel.STONE_PICKAXE, EnergyBankEntity::new));

    public static final Block COPPER_ENERGY_GENERATOR = registerCapable("copper_energy_generator", new CopperGenBlock(getProperties(Material.METAL, 3.5f), ToolType.PICKAXE, MiningLevel.STONE_PICKAXE, CopperGenEntity::new));

    public static final Block MOON_SURFACE_STONE = register("moon_surface_stone");
    public static final Block MOON_COBBLESTONE = register("moon_cobblestone");
    public static final Block MOON_STONE_BRICKS = register("moon_surface_stone_bricks", ToolType.PICKAXE, MiningLevel.WOODEN_PICKAXE, getProperties(Material.STONE_SOUNDS_DEEPSLATE_BRICKS, 3.5f, 4.0f));
    public static final Block MOON_STONE = register("moon_stone");
    public static final Block MOON_SMOOTH_STONE= register("moon_smooth_stone");

    public static final Block MOON_STONE_SLAB = slab("moon_stone_slab", MOON_STONE, Material.STONE_SOUNDS_DEEPSLATE);
    public static final Block MOON_COBBLESTONE_SLAB = slab("moon_cobblestone_slab", MOON_COBBLESTONE, Material.STONE_SOUNDS_DEEPSLATE);
    public static final Block MOON_STONE_BRICK_SLAB = slab("moon_stone_brick_slab", MOON_STONE_BRICKS, Material.STONE_SOUNDS_DEEPSLATE_BRICKS);
    public static final Block MOON_SMOOTH_STONE_SLAB = slab("moon_smooth_stone_slab", MOON_SMOOTH_STONE, Material.STONE_SOUNDS_DEEPSLATE);

    public static final Block MOON_STONE_STAIRS = stair("moon_stone_stairs", MOON_SURFACE_STONE, Material.STONE_SOUNDS_DEEPSLATE);
    public static final Block MOON_COBBLESTONE_STAIRS = stair("moon_cobblestone_stairs", MOON_COBBLESTONE, Material.STONE_SOUNDS_DEEPSLATE);
    public static final Block MOON_STONE_BRICK_STAIRS = stair("moon_stone_brick_stairs", MOON_STONE_BRICKS, Material.STONE_SOUNDS_DEEPSLATE_BRICKS);
    public static final Block MOON_SMOOTH_STONE_STAIRS = stair("moon_smooth_stone_stairs", MOON_SMOOTH_STONE, Material.STONE_SOUNDS_DEEPSLATE);


    //public static final Block IRON_ENERGY_GENERATOR = register("iron_energy_generator", ToolType.PICKAXE, MiningLevel.STONE_PICKAXE, getProperties(Material.METAL, 4.0f));

    public ModBlocks() {
    }

    private static Block stair(String name, Block parentBlock, Material material) {
        return BLOCKS.register(name, new MineableStairBlock(parentBlock, getProperties(material, 3.5f, 4.0f), ToolType.PICKAXE, MiningLevel.STONE_PICKAXE));
    }

    private static Block slab(String name,Block doubleSlab, Material materials) {
        return BLOCKS.register(name, new MineableSlabBlock(getProperties(materials, 3.5f, 4.0f), doubleSlab, ToolType.PICKAXE, MiningLevel.WOODEN_PICKAXE));
    }

    private static Block register(String name) {
        return register(name, ToolType.PICKAXE, MiningLevel.WOODEN_PICKAXE, getProperties(Material.STONE_SOUNDS_DEEPSLATE, 3.5f, 4.0f));
    }

    private static Block register(String name, ToolType toolType, MiningLevel miningLevel, BlockBehaviour.Properties properties) {
        return BLOCKS.register(name, new BaseBlock(properties, toolType, miningLevel));
    }

    private static BlockBehaviour.Properties getProperties(Material mat, float strength) {
        return BlockBehaviour.Properties.of(mat.material).sound(mat.sound).strength(strength).requiresCorrectToolForDrops();
    }

    private static BlockBehaviour.Properties getProperties(Material mat, float hardness, float blastResistance) {
        return BlockBehaviour.Properties.of(mat.material).sound(mat.sound).strength(hardness, blastResistance).requiresCorrectToolForDrops();
    }

    private static <T extends ModCapableBlock<?>> Block registerCapable(String name, T blockEntity) {

        return BLOCKS.register(name, blockEntity);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onRegisterEvent(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> reg = event.getRegistry();
        ToTheMoonMain.LOG.info("Event {} is fired", reg);
        BLOCKS.init(reg);
    }

    private enum Material {
        STONE_SOUNDS_DEEPSLATE(net.minecraft.world.level.material.Material.STONE, SoundType.DEEPSLATE),
        STONE_SOUNDS_DEEPSLATE_BRICKS(net.minecraft.world.level.material.Material.STONE, SoundType.DEEPSLATE_BRICKS),
        METAL(net.minecraft.world.level.material.Material.METAL, SoundType.METAL);

        private final net.minecraft.world.level.material.Material material;
        private final SoundType sound;

        Material(net.minecraft.world.level.material.Material material, SoundType sound) {
            this.material = material;
            this.sound = sound;
        }
    }
}
