package com.khanhpham.tothemoon.config;

import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.stream.Stream;

public class TTMConfigs {
    public static final ClientConfigs CLIENT_CONFIGS;
    public static final CommonConfigs COMMON_CONFIGS;
    private static final ForgeConfigSpec clientSpec;
    private static final ForgeConfigSpec commonSpecs;

    static {
        var specPair = new ForgeConfigSpec.Builder().configure(ClientConfigs::new);
        clientSpec = specPair.getRight();
        CLIENT_CONFIGS = specPair.getLeft();

        var commonSpec = new ForgeConfigSpec.Builder().configure(CommonConfigs::new);
        commonSpecs = commonSpec.getRight();
        COMMON_CONFIGS = commonSpec.getLeft();
    }

    public static void registerConfigs(IEventBus modBus, ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.CLIENT, clientSpec);
        context.registerConfig(ModConfig.Type.COMMON, commonSpecs);
        modBus.register(TTMConfigs.class);
    }

    public static final class ClientConfigs {

        public final ForgeConfigSpec.BooleanValue showsItemEnergyPercentageOnToolTip;
        public final ForgeConfigSpec.BooleanValue showsFluidPercentageOnToolTip;

        private ClientConfigs(ForgeConfigSpec.Builder builder) {
            builder.push("client");

            showsItemEnergyPercentageOnToolTip = builder.define("showsItemEnergyPercentageOnToolTip", false);
            showsFluidPercentageOnToolTip = builder.define("showsFluidPercentageOnToolTip", false);

            builder.pop();
        }
    }

    public static final class CommonConfigs {
        private final ForgeConfigSpec.ConfigValue<List<? extends String>> whitelistedTags;

        private CommonConfigs(ForgeConfigSpec.Builder builder) {

            builder.push("general");

            whitelistedTags = builder.comment("Tags that are whitelisted to be used in Tag Translator, PLEASE DO NOT EXPLOIT THIS")
                    .defineList("tagTranslator.tagsWhitelist", Lists.newArrayList("minecraft:coal_ores", "minecraft:soul_fire_base_blocks", "forge:ingots/steel", "forge:ingots/uranium", "forge:rods/treated_wood", "forge:dusts/iron", "forge:dusts/gold"),
                            (string) -> true
                    );

            builder.pop();
        }

        public Stream<TagKey<Item>> allWhitelistedTags() {
            return whitelistedTags.get().stream().filter(ResourceLocation::isValidResourceLocation).map(ResourceLocation::new).map(ItemTags::create).filter(ForgeRegistries.ITEMS.tags()::isKnownTagName);
        }
    }
}
