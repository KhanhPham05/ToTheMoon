package com.khanhpham.ttm.init;

import com.khanhpham.ttm.ToTheMoonMain;
import com.khanhpham.ttm.core.blockentities.energybank.EnergyBankEntity;
import com.khanhpham.ttm.core.blockentities.energygen.EnergyGeneratorBlockEntity;
import com.khanhpham.ttm.core.blockentities.energygen.impl.CopperGenEntity;
import com.khanhpham.ttm.core.containers.tier.generator.CopperGenMenu;
import com.khanhpham.ttm.core.containers.EnergyGenMenu;
import com.khanhpham.ttm.core.screen.EnergyGenScreen;
import com.khanhpham.ttm.core.screen.tier.CopperGenScreen;
import com.khanhpham.ttm.registration.BlockEntityRegister;
import com.khanhpham.ttm.registration.MenuTypeRegister;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ToTheMoonMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModMisc {
    public static final BlockEntityRegister BLOCK_ENTITIES = new BlockEntityRegister(ToTheMoonMain.MOD_ID);
    public static final MenuTypeRegister MENU_TYPES = new MenuTypeRegister(ToTheMoonMain.MOD_ID);

    public ModMisc() {
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.BlockEntitySupplier<T> entitySupplier, Block... blocks) {
        return BLOCK_ENTITIES.register(name, entitySupplier, blocks);
    }


    /**
     * @see MenuType
     */
    private static <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType.MenuSupplier<T> menuSupplier) {
        return MENU_TYPES.register(name, menuSupplier);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onBlockEntityRegistration(RegistryEvent.Register<BlockEntityType<?>> event) {
        ToTheMoonMain.LOG.info("Registering Block Entities");
        BLOCK_ENTITIES.init(event.getRegistry());
    }

    @SubscribeEvent
    public static void onMenuRegistration(RegistryEvent.Register<MenuType<?>> event) {
        ToTheMoonMain.LOG.info("Registering Menu Screens");
        MENU_TYPES.init(event.getRegistry());
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(ENERGY_GEN_MENU, EnergyGenScreen::new);
        MenuScreens.register(COPPER_GEN_MENU, CopperGenScreen::new);
    }

    public static final BlockEntityType<EnergyBankEntity> ENERGY_BANK_ENTITY = register("energy_bank_entity", EnergyBankEntity::new, ModBlocks.ENERGY_BANK);

    public static final BlockEntityType<EnergyGeneratorBlockEntity> ENERGY_GEN_ENTITY = register("energy_gen_entity", EnergyGeneratorBlockEntity::new, ModBlocks.ENERGY_GEN);
    public static final BlockEntityType<CopperGenEntity> COPPER_GEN_ENTITY = register("copper_energy_gen_entity", CopperGenEntity::new, ModBlocks.COPPER_ENERGY_GENERATOR);

    public static final MenuType<EnergyGenMenu> ENERGY_GEN_MENU = register("energy_gen_menu", EnergyGenMenu::new);
    public static final MenuType<CopperGenMenu> COPPER_GEN_MENU = register("copper_gen_menu", CopperGenMenu::new);
}
