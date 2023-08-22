package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.common.battery.BatteryBlockEntity;
import com.khanhtypo.tothemoon.common.machine.powergenerator.*;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.ControllerPartEntity;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.EmptyAcceptorPartEntity;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.SmoothBlackStonePartEntity;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.ioparts.entity.BlackStoneFurnaceItemAcceptorPartEntity;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;

public class ModBlockEntities {
    static void staticInit() {
    }

    public static final BlockEntityObject<PowerGeneratorBlockEntity> POWER_GENERATOR = BlockEntityObject.register("power_generator", PowerGeneratorBlockEntity.createSupplier(), ModBlocks.COPPER_POWER_GENERATOR, ModBlocks.IRON_POWER_GENERATOR, ModBlocks.GOLD_POWER_GENERATOR, ModBlocks.DIAMOND_POWER_GENERATOR, ModBlocks.NETHERITE_POWER_GENERATOR);
    public static final BlockEntityObject<ControllerPartEntity> BLACK_STONE_FURNACE_CONTROLLER_PART = BlockEntityObject.register("blackstone_furnace_controller_part", ControllerPartEntity::new, ModBlocks.BLACK_STONE_FURNACE_CONTROLLER);
    public static final BlockEntityObject<SmoothBlackStonePartEntity> SMOOTH_BLACK_STONE_PART = BlockEntityObject.register("smooth_black_stone", SmoothBlackStonePartEntity::new, ModBlocks.SMOOTH_BLACKSTONE);
    public static final BlockEntityObject<EmptyAcceptorPartEntity> EMPTY_ACCESSOR_PART = BlockEntityObject.register("empty_accessor_part", EmptyAcceptorPartEntity::new, ModBlocks.BLACKSTONE_EMPTY_ACCEPTOR, ModBlocks.NETHER_BRICKS_EMPTY_ACCEPTOR);
    public static final BlockEntityObject<BlackStoneFurnaceItemAcceptorPartEntity> BLACK_STONE_FURNACE_ITEM_ACCEPTOR = BlockEntityObject.register("blackstone_item_acceptor", BlackStoneFurnaceItemAcceptorPartEntity::new, ModBlocks.BLACKSTONE_ITEM_ACCEPTOR, ModBlocks.NETHER_BRICKS_ITEM_ACCEPTOR);
    public static final BlockEntityObject<BatteryBlockEntity> BATTERY = BlockEntityObject.register("battery", BatteryBlockEntity.createSupplier(), ModBlocks.STANDARD_BATTERY, ModBlocks.REDSTONE_BATTERY, ModBlocks.STEEL_BATTERY);
}