package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.common.machine.powergenerator.*;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.ControllerPartEntity;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.EmptyAcceptorPartEntity;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.SmoothBlackStonePartEntity;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.ioparts.entity.BlackStoneFurnaceItemAcceptorPartEntity;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;

public class ModBlockEntities {
    static void staticInit() {
    }

    public static final BlockEntityObject<PowerGeneratorBlockEntity> COPPER_POWER_GENERATOR = BlockEntityObject.register("copper_power_generator", CopperPowerGeneratorBlockEntity::new, ModBlocks.COPPER_POWER_GENERATOR);
    public static final BlockEntityObject<PowerGeneratorBlockEntity> IRON_POWER_GENERATOR = BlockEntityObject.register("iron_power_generator", IronPowerGeneratorBlockEntity::new, ModBlocks.IRON_POWER_GENERATOR);
    public static final BlockEntityObject<PowerGeneratorBlockEntity> GOLD_POWER_GENERATOR = BlockEntityObject.register("gold_power_generator", GoldPowerGeneratorBlockEntity::new, ModBlocks.GOLD_POWER_GENERATOR);
    public static final BlockEntityObject<PowerGeneratorBlockEntity> DIAMOND_POWER_GENERATOR = BlockEntityObject.register("diamond_power_generator", DiamondPowerGeneratorBlockEntity::new, ModBlocks.DIAMOND_POWER_GENERATOR);
    public static final BlockEntityObject<PowerGeneratorBlockEntity> NETHERITE_POWER_GENERATOR = BlockEntityObject.register("netherite_power_generator", NetheritePowerGeneratorBlockEntity::new, ModBlocks.NETHERITE_POWER_GENERATOR);
    public static final BlockEntityObject<ControllerPartEntity> BLACK_STONE_FURNACE_CONTROLLER_PART = BlockEntityObject.register("blackstone_furnace_controller_part", ControllerPartEntity::new, ModBlocks.BLACK_STONE_FURNACE_CONTROLLER);
    public static final BlockEntityObject<SmoothBlackStonePartEntity> SMOOTH_BLACK_STONE_PART = BlockEntityObject.register("smooth_black_stone", SmoothBlackStonePartEntity::new, ModBlocks.SMOOTH_BLACKSTONE);
    public static final BlockEntityObject<EmptyAcceptorPartEntity> EMPTY_ACCESSOR_PART = BlockEntityObject.register("empty_accessor_part", EmptyAcceptorPartEntity::new, ModBlocks.BLACKSTONE_EMPTY_ACCEPTOR, ModBlocks.NETHER_BRICKS_EMPTY_ACCEPTOR);
    public static final BlockEntityObject<BlackStoneFurnaceItemAcceptorPartEntity> BLACK_STONE_FURNACE_ITEM_ACCEPTOR = BlockEntityObject.register("blackstone_item_acceptor", BlackStoneFurnaceItemAcceptorPartEntity::new, ModBlocks.BLACKSTONE_ITEM_ACCEPTOR, ModBlocks.NETHER_BRICKS_ITEM_ACCEPTOR);

}