package com.khanhpham.ttm.init;

import com.khanhpham.ttm.ToTheMoonMain;
import com.khanhpham.ttm.core.blockentities.energybank.EnergyBankEntity;
import com.khanhpham.ttm.core.blockentities.energygen.EnergyGeneratorEntity;
import com.khanhpham.ttm.testfeatures.RegistryHelper;
import com.khanhpham.ttm.testfeatures.TickableBlockEntity;
import com.khanhpham.ttm.utils.block.ModCapableBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
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

    public static final Block ENERGY_GEN = registerCapable(getProperties(ModBlockMaterials.METAL, 3.5f), "energy_generator", EnergyGeneratorEntity::new, ModMisc.ENERGY_GEN_ENTITY);
    public static final Block ENERGY_BANK = registerCapable(getProperties(ModBlockMaterials.METAL, 5, 6.0f), "energy_bank", EnergyBankEntity::new, ModMisc.ENERGY_BANK_ENTITY);

    public static final Block MOON_SURFACE_STONE = register(getProperties(ModBlockMaterials.STONE_SOUNDS_DEEPSLATE, 3.5f, 6.0f), "moon_surface_stone");
    public static final Block MOON_COBBLESTONE = register(getProperties(ModBlockMaterials.STONE_SOUNDS_DEEPSLATE, 2.0f, 6.0f), "moon_cobblestone");
    public static final Block MOON_SURFACE_STONE_BRICKS = register(getProperties(ModBlockMaterials.STONE_SOUNDS_DEEPSLATE_BRICKS, 3.5f, 6.0f), "moon_surface_stone_bricks");

    public ModBlocks() {
    }

    private static Block register(BlockBehaviour.Properties properties, String name) {
        return BLOCKS.register(name, new Block(properties));
    }

    private static BlockBehaviour.Properties getProperties(ModBlockMaterials mat, float strength) {
        return BlockBehaviour.Properties.of(mat.material).sound(mat.sound).strength(strength).requiresCorrectToolForDrops();
    }

    private static BlockBehaviour.Properties getProperties(ModBlockMaterials mat, float hardness, float blastResistance) {
        return BlockBehaviour.Properties.of(mat.material).sound(mat.sound).strength(hardness, blastResistance).requiresCorrectToolForDrops();
    }

    private static <TILE extends TickableBlockEntity, TYPE extends BlockEntityType<TILE>> Block registerCapable(BlockBehaviour.Properties properties, String name, BlockEntityType.BlockEntitySupplier<TILE> blockEntity, TYPE type) {
        return BLOCKS.register(name, new ModCapableBlock<>(properties, blockEntity));
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onRegisterEvent(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> reg = event.getRegistry();
        ToTheMoonMain.LOG.info("Event {} is fired", reg);
        BLOCKS.init(reg);
    }

    private enum ModBlockMaterials {
        STONE_SOUNDS_DEEPSLATE(Material.STONE, SoundType.DEEPSLATE),
        STONE_SOUNDS_DEEPSLATE_BRICKS(Material.STONE, SoundType.DEEPSLATE_BRICKS),
        METAL(Material.METAL, SoundType.METAL);

        private final Material material;
        private final SoundType sound;

        ModBlockMaterials(Material material, SoundType sound) {
            this.material = material;
            this.sound = sound;
        }
    }
}
