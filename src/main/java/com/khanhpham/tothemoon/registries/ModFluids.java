package com.khanhpham.tothemoon.registries;

import com.khanhpham.tothemoon.api.registration.FluidRegistryObject;
import com.khanhpham.tothemoon.util.RegistryTypes;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.awt.*;
import java.util.function.Supplier;

public class ModFluids {
    public static final ResourceLocation FLOWING_RL = new ResourceLocation("block/lava_flow");
    public static final ResourceLocation OVERLAY_RL = new ResourceLocation("block/lava_overlay");
    public static final ResourceLocation STILL_RL = new ResourceLocation("block/lava_still");


    public static final FluidRegistryObject<FlowingFluid> MOLTEN_REDSTONE_STEEL_ALLOY = new FluidRegistryObject<>(RegistryTypes.FLUIDS.register("molten_redstone_steel_alloy", () -> new ForgeFlowingFluid.Source(GasProperties.MOLTEN_RS_PROPERTIES)));
    public static final FluidRegistryObject<FlowingFluid> MOLTEN_REDSTONE_STEEL_ALLOY_FLOWING = new FluidRegistryObject<>(RegistryTypes.FLUIDS.register("molten_redstone_steel_alloy_flowing", () -> new ForgeFlowingFluid.Flowing(GasProperties.MOLTEN_RS_PROPERTIES)));

    public static final FluidRegistryObject<FlowingFluid> MOLTEN_REDSTONE_ALLOY = register("molten_redstone_alloy", () -> new ForgeFlowingFluid.Source(GasProperties.MOLTEN_R_PROPERTIES));
    public static final FluidRegistryObject<FlowingFluid> MOLTEN_REDSTONE_ALLOY_FLOWING = register("molten_redstone_alloy_flowing", () -> new ForgeFlowingFluid.Flowing(GasProperties.MOLTEN_R_PROPERTIES));

    public static FluidRegistryObject<FlowingFluid> register(String name, Supplier<ForgeFlowingFluid> fluidSupplier) {
        return new FluidRegistryObject<>(RegistryTypes.FLUIDS.register(name, fluidSupplier));
    }

    public static void init(IEventBus bus) {
        RegistryTypes.FLUIDS.register(bus);
    }

    /***
     * @see net.minecraftforge.common.ForgeHooks
     */
//https://github.com/Cy4Shot/MACHINA/blob/main/src/main/java/com/machina/init/FluidInit.java

    private static class GasProperties {
        public static final Color redstoneSteelColor = new Color(156,44,44);
        public static final Color redstoneAlloyColor = new Color(255, 68, 0);

        private static final ForgeFlowingFluid.Properties MOLTEN_RS_PROPERTIES = getProcessGas(MOLTEN_REDSTONE_STEEL_ALLOY, MOLTEN_REDSTONE_STEEL_ALLOY_FLOWING, redstoneSteelColor, 3000, 1300, 15, ModItems.REDSTONE_STEEL_ALLOY_BUCKET.item());
        private static final ForgeFlowingFluid.Properties MOLTEN_R_PROPERTIES = getProcessGas(MOLTEN_REDSTONE_ALLOY, MOLTEN_REDSTONE_ALLOY_FLOWING, redstoneAlloyColor, 1000, 1300, 15, ModItems.REDSTONE_ALLOY_BUCKET.item());

        private static ForgeFlowingFluid.Properties getProcessGas(Supplier<FlowingFluid> still, Supplier<FlowingFluid> flowing, Color color, float density, float temperature, int luminosity, Item bucket) {
            return new ForgeFlowingFluid.Properties(still, flowing,
                    FluidAttributes.builder(STILL_RL, FLOWING_RL).color(color.getRGB())
                            .density(Math.round(density)).viscosity(Math.round(density))
                            .temperature(Math.round(temperature)).luminosity(luminosity)
                            .overlay(OVERLAY_RL).gaseous()).bucket(() -> bucket);


        }
    }
}