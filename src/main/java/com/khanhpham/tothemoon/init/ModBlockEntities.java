package com.khanhpham.tothemoon.init;

import com.google.common.collect.ImmutableSet;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.blockentities.others.AlloySmelterBlockEntity;
import com.khanhpham.tothemoon.core.blockentities.others.MetalPressBlockEntity;
import com.khanhpham.tothemoon.core.blocks.battery.AbstractBatteryBlockEntity;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryBlockEntity;
import com.khanhpham.tothemoon.core.blocks.battery.creative.CreativeBatteryBlockEntity;
import com.khanhpham.tothemoon.core.blocks.battery.tiered.RedstoneBatteryBlockEntity;
import com.khanhpham.tothemoon.core.blocks.battery.tiered.SteelBatteryBlockEntity;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.tileentities.*;
import com.khanhpham.tothemoon.core.blocks.machines.energysmelter.EnergySmelterBlockEntity;
import com.khanhpham.tothemoon.core.blocks.machines.oreprocessor.OreProcessorBlockEntity;
import com.khanhpham.tothemoon.core.blocks.tanks.FluidTankBlockEntity;
import com.khanhpham.tothemoon.core.multiblock.block.brickfurnace.NetherBrickFurnaceControllerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;
import java.util.function.Supplier;

@SuppressWarnings("ConstantConditions")
public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BE_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ToTheMoon.MOD_ID);

    public static final RegistryObject<BlockEntityType<CopperEnergyGeneratorBlockEntity>> COPPER_ENERGY_GENERATOR_TE;
    public static final RegistryObject<BlockEntityType<IronEnergyGeneratorBlockEntity>> IRON_ENERGY_GENERATOR_TE;
    public static final RegistryObject<BlockEntityType<GoldEnergyGeneratorBlockEntity>> GOLD_ENERGY_GENERATOR_TE;
    public static final RegistryObject<BlockEntityType<DiamondEnergyGeneratorBlockEntity>> DIAMOND_ENERGY_GENERATOR_TE;

    public static final RegistryObject<BlockEntityType<AlloySmelterBlockEntity>> ALLOY_SMELTER;

    public static final RegistryObject<BlockEntityType<MetalPressBlockEntity>> METAL_PRESS;

    public static final RegistryObject<BlockEntityType<BatteryBlockEntity>> BATTERY;
    public static final RegistryObject<BlockEntityType<EnergySmelterBlockEntity>> ENERGY_SMELTER;
    public static final RegistryObject<BlockEntityType<CreativeBatteryBlockEntity>> CREATIVE_BATTERY;

    public static final RegistryObject<BlockEntityType<NetherBrickFurnaceControllerBlockEntity>> BRICK_FURNACE;
    public static final RegistryObject<BlockEntityType<OreProcessorBlockEntity>> ENERGY_PROCESSOR;
    public static final RegistryObject<BlockEntityType<RedstoneBatteryBlockEntity>> REDSTONE_BATTERY;
    public static final RegistryObject<BlockEntityType<SteelBatteryBlockEntity>> STEEL_BATTERY;
    //public static final RegistryObject<BlockEntityType<TagTranslatorBlockEntity>> TAG_TRANSLATOR;
    public static final RegistryObject<BlockEntityType<FluidTankBlockEntity>> FLUID_TANK;
    public static final RegistryObject<BlockEntityType<NetheriteEnergyGeneratorBlockEntity>> NETHERITE_GENERATOR;

    static {
        ToTheMoon.LOG.info("Registering BEs");
        COPPER_ENERGY_GENERATOR_TE = register("copper_gen", () -> BlockEntityType.Builder.of(CopperEnergyGeneratorBlockEntity::new, ModBlocks.COPPER_ENERGY_GENERATOR.get()).build(null));
        IRON_ENERGY_GENERATOR_TE = register("iron_gen", () -> BlockEntityType.Builder.of(IronEnergyGeneratorBlockEntity::new, ModBlocks.IRON_ENERGY_GENERATOR.get()).build(null));
        GOLD_ENERGY_GENERATOR_TE = register("gold_gen", () -> BlockEntityType.Builder.of(GoldEnergyGeneratorBlockEntity::new, ModBlocks.GOLD_ENERGY_GENERATOR.get()).build(null));
        DIAMOND_ENERGY_GENERATOR_TE = register("diamond_gen", () -> BlockEntityType.Builder.of(DiamondEnergyGeneratorBlockEntity::new, ModBlocks.DIAMOND_ENERGY_GENERATOR.get()).build(null));
        ALLOY_SMELTER = register("alloy_smelter", () -> BlockEntityType.Builder.of(AlloySmelterBlockEntity::new, ModBlocks.ALLOY_SMELTER.get()).build(null));
        METAL_PRESS = register("metal_press", () -> BlockEntityType.Builder.of(MetalPressBlockEntity::new, ModBlocks.METAL_PRESS.get()).build(null));
        //METAL_PRESSING_PLATE = register("metal_pressing_plate", () -> BlockEntityType.Builder.of(MetalPressingPlateBlockEntity::new, ModBlocks.METAL_PRESSING_PLATE.get()).build(null));
        BATTERY = register("battery", () -> new BlockEntityType<>(BatteryBlockEntity::new, Collections.singleton(ModBlocks.BATTERY.get()), null));
        CREATIVE_BATTERY = register("creative_battery", () -> BlockEntityType.Builder.of(CreativeBatteryBlockEntity::new, ModBlocks.CREATIVE_BATTERY.get()).build(null));
        ENERGY_SMELTER = register("energy_smelter", () -> new BlockEntityType<>(EnergySmelterBlockEntity::new, ImmutableSet.of(ModBlocks.ENERGY_SMELTER.get()), null));
        BRICK_FURNACE = register("brick_furnace", () -> BlockEntityType.Builder.of(NetherBrickFurnaceControllerBlockEntity::new, ModBlocks.NETHER_BRICK_FURNACE_CONTROLLER.get()).build(null));
        ENERGY_PROCESSOR = register("energy_processor", () -> BlockEntityType.Builder.of(OreProcessorBlockEntity::new, ModBlocks.ORE_PROCESSOR.get()).build(null));
        //TAG_TRANSLATOR = register("tag_translators", () -> BlockEntityType.Builder.of(TagTranslatorBlockEntity::new, ModBlocks.TAG_TRANSLATOR.get()).build(null));
        FLUID_TANK = register("fluid_tanks", () -> BlockEntityType.Builder.of(FluidTankBlockEntity::new, ModBlocks.FLUID_TANK.get()).build(null));
        REDSTONE_BATTERY = register("redstone_battery", () -> new BlockEntityType<>(RedstoneBatteryBlockEntity::new, Collections.singleton(ModBlocks.REDSTONE_BATTERY.get()), null));
        STEEL_BATTERY = register("steel_battery", () -> new BlockEntityType<>(SteelBatteryBlockEntity::new, Collections.singleton(ModBlocks.STEEL_BATTERY.get()), null));
        NETHERITE_GENERATOR = register("netherite_generator", () -> BlockEntityType.Builder.of(NetheriteEnergyGeneratorBlockEntity::new, ModBlocks.NETHERITE_ENERGY_GENERATOR.get()).build(null));
    }

    private ModBlockEntities() {
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> supplier) {
        return BE_DEFERRED_REGISTER.register(name, supplier);
    }

    public static void init() {
    }
}
