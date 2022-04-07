package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.machines.alloysmelter.AlloySmelterBlockEntity;
import com.khanhpham.tothemoon.core.machines.energygenerator.tileentities.CopperEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.core.machines.energygenerator.tileentities.DiamondEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.core.machines.energygenerator.tileentities.GoldEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.core.machines.energygenerator.tileentities.IronEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.core.machines.metalpress.MetalPressBlockEntity;
import com.khanhpham.tothemoon.core.machines.storageblock.MoonBarrelTileEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

//@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Names.MOD_ID);

    public static final RegistryObject<BlockEntityType<MoonBarrelTileEntity>> MOON_STORAGE;

    public static final RegistryObject<BlockEntityType<CopperEnergyGeneratorBlockEntity>> COPPER_ENERGY_GENERATOR_TE;
    public static final RegistryObject<BlockEntityType<IronEnergyGeneratorBlockEntity>> IRON_ENERGY_GENERATOR_TE;
    public static final RegistryObject<BlockEntityType<GoldEnergyGeneratorBlockEntity>> GOLD_ENERGY_GENERATOR_TE;
    public static final RegistryObject<BlockEntityType<DiamondEnergyGeneratorBlockEntity>> DIAMOND_ENERGY_GENERATOR_TE;

    public static final RegistryObject<BlockEntityType<AlloySmelterBlockEntity>> ALLOY_SMELTER;

    public static final RegistryObject<BlockEntityType<MetalPressBlockEntity>> METAL_PRESS;

    static {
        ToTheMoon.LOG.info("Registering BEs");
        MOON_STORAGE = BE_DEFERRED_REGISTER.register("moon_storage", () -> BlockEntityType.Builder.of(MoonBarrelTileEntity::new, ModBlocks.MOON_ROCK_BARREL.get()).build(null));
        COPPER_ENERGY_GENERATOR_TE = register("copper_gen", () -> BlockEntityType.Builder.of(CopperEnergyGeneratorBlockEntity::new, ModBlocks.COPPER_ENERGY_GENERATOR.get()).build(null));
        IRON_ENERGY_GENERATOR_TE = register("iron_gen", () -> BlockEntityType.Builder.of(IronEnergyGeneratorBlockEntity::new, ModBlocks.IRON_ENERGY_GENERATOR.get()).build(null));
        GOLD_ENERGY_GENERATOR_TE = register("gold_gen", () -> BlockEntityType.Builder.of(GoldEnergyGeneratorBlockEntity::new, ModBlocks.GOLD_ENERGY_GENERATOR.get()).build(null));
        DIAMOND_ENERGY_GENERATOR_TE = register("diamond_gen", () -> BlockEntityType.Builder.of(DiamondEnergyGeneratorBlockEntity::new, ModBlocks.DIAMOND_ENERGY_GENERATOR.get()).build(null));
        ALLOY_SMELTER = register("alloy_smelter", () -> BlockEntityType.Builder.of(AlloySmelterBlockEntity::new, ModBlocks.ALLOY_SMELTER.get()).build(null));
        METAL_PRESS = register("metal_press", () -> BlockEntityType.Builder.of(MetalPressBlockEntity::new, ModBlocks.METAL_PRESS.get()).build(null));
    }

    private ModBlockEntityTypes() {
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> supplier) {
        return BE_DEFERRED_REGISTER.register(name, supplier);
    }

    public static void init() {
    }




    /*
    @SubscribeEvent
    public static void init(RegistryEvent.Register<BlockEntityType<?>> event) throws IllegalAccessException {
        IForgeRegistry<BlockEntityType<?>> registry = event.getRegistry();
        Class<ModBlockEntityTypes> clazz = ModBlockEntityTypes.class;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType().equals(BlockEntityType.class)) {
                BlockEntityType<?> oldBe = (BlockEntityType<?>) field.get(BlockEntityType.class);
                BlockEntityType<?> neeBe = oldBe.setRegistryName(ModUtils.modLoc(field.getName().toLowerCase(Locale.ROOT)));
                ModUtils.info("Registering BE [{}] with hasCode [{}]", neeBe.getRegistryName(), neeBe.hashCode());
                registry.register(neeBe);
                field.set(oldBe, neeBe);
            }
        }
    }*/
}
