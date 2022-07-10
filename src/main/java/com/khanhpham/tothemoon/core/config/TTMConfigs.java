package com.khanhpham.tothemoon.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class TTMConfigs {
    public static final ClientConfigs CLIENT;
    private static final ForgeConfigSpec clientSpec;

    static {
        var specPair = new ForgeConfigSpec.Builder().configure(ClientConfigs::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static void registerConfigs(IEventBus modBus, ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.CLIENT, clientSpec);
        modBus.register(TTMConfigs.class);
    }

    public static final class ClientConfigs {

        public final ForgeConfigSpec.BooleanValue showsItemEnergyPercentageOnToolTip;
        public final ForgeConfigSpec.BooleanValue showsFluidPercentageOnToolTip;

        public ClientConfigs(ForgeConfigSpec.Builder builder) {
            builder.push("client");

            showsItemEnergyPercentageOnToolTip = builder.define("showsItemEnergyPercentageOnToolTip", false);
            showsFluidPercentageOnToolTip = builder.define("showsFluidPercentageOnToolTip", false);

            builder.pop();
        }
    }
}
