package com.khanhpham.tothemoon.registries;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.api.registration.RegisterBlock;
import com.khanhpham.tothemoon.common.energybank.EnergyBank;
import com.khanhpham.tothemoon.common.machineblock.EnergyGenerator;
import com.khanhpham.tothemoon.util.Identity;
import com.khanhpham.tothemoon.util.RegistryTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    /**
     * Harvest levels:
     * 0 - Empty Hands
     * 1 - Woods Tools
     * 2 - Stone Tools
     * 3 - Iron Tools
     * 4 - Diamond Tools
     */

    public static final RegisterBlock<Block> MOON_SURFACE_ROCK;

    public static final RegisterBlock<OreBlock> COPPER_MOON_ORE;
    public static final RegisterBlock<OreBlock> COPPER_ORE;


    public static final RegisterBlock<Block> STEEL_MACHINE_CASING;
    public static final RegisterBlock<Block> BASIC_MACHINE_CASING;
    public static final RegisterBlock<Block> REDSTONE_POWERED_MACHINE_CASING;

    public static final RegisterBlock<Block> COPPER_BLOCK;
    public static final RegisterBlock<Block> REDSTONE_STEEL_ALLOY_BLOCK;
    public static final RegisterBlock<Block> STEEL_BLOCK;

    public static final RegisterBlock<EnergyGenerator.BlockInstance> ENERGY_GENERATOR;
    public static final RegisterBlock<EnergyBank.BlockInstance> ENERGY_BANK;

    static {
        MOON_SURFACE_ROCK = register("moon_surface_rock", properties(Material.STONE, 1, 3.5f, 6));

        COPPER_MOON_ORE = register("copper_moon_ore", () -> new OreBlock(properties(Material.STONE, 1, 3.5f, 6.5f)));
        COPPER_ORE = register("copper_ore", () -> new OreBlock(properties(Material.STONE, 1, 3, 6)));
        COPPER_BLOCK = register("copper_block", properties(Material.METAL, 1, 3.5f, 6));

        STEEL_MACHINE_CASING = register("steel_machine_casing", properties(Material.METAL, 2, 3.5f, 5f));
        BASIC_MACHINE_CASING = register("basic_machine_casing", properties(Material.METAL, 2, 3f, 5f));
        REDSTONE_POWERED_MACHINE_CASING = register("redstone_powered_machine_casing", properties(Material.METAL, 2, 3.5f, 5f));

        REDSTONE_STEEL_ALLOY_BLOCK = register("redstone_steel_alloy_block", properties(Material.METAL, 2, 3.5f, 6.5f));
        STEEL_BLOCK = register("steel_block", properties(Material.METAL, 2, 3.5f, 6.5f));
        //BRONZE_BLOCK = register("bronze_block", properties(Material.METAL, 2, 3.5f, 6.5f));

        ENERGY_BANK = register("energy_bank", EnergyBank.BlockInstance::new);
        ENERGY_GENERATOR = register("energy_generator", EnergyGenerator.BlockInstance::new);

    }

    public static void init(IEventBus bus) {
        RegistryTypes.BLOCKS.register(bus);
    }

    private static RegisterBlock<Block> register(String name, AbstractBlock.Properties properties) {
        return register(name, () -> new Block(properties));
    }

    private static <T extends Block> RegisterBlock<T> register(String name, Supplier<T> blockSupplier) {
        Identity.ensureValid(name);
        RegisterBlock<T> blockReg = new RegisterBlock<>(RegistryTypes.BLOCKS.register(name, blockSupplier));
        RegistryTypes.ITEMS.register(name, () -> new BlockItem(blockReg.get(), new Item.Properties().tab(ToTheMoon.TTM_TAB)));
        return blockReg;
    }

    private static AbstractBlock.Properties properties(Material material) {
        return AbstractBlock.Properties.of(material);
    }

    private static AbstractBlock.Properties properties(Material material, int harvestLevel, float hardness, float resistance) {
        return properties(material).strength(hardness, resistance).harvestTool(ToolType.PICKAXE).harvestLevel(harvestLevel).requiresCorrectToolForDrops();
    }
}
