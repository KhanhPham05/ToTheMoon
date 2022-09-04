package com.khanhpham.tothemoon.debug;

import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CapabilityInfoArgument implements ArgumentType<CapabilityInfoArgument.BuiltinCapabilities> {
    public static BuiltinCapabilities getCapInfo(CommandContext<CommandSourceStack> pContext, String pName) {
        return pContext.getArgument(pName, BuiltinCapabilities.class);
    }

    @Override
    public BuiltinCapabilities parse(StringReader reader) throws CommandSyntaxException {
        if (reader.canRead()) {
            String name = new ResourceLocation(reader.readString()).getPath();
            if (BuiltinCapabilities.contains(name)) {
                return BuiltinCapabilities.get(name);
            }
        }

        return BuiltinCapabilities.NONE;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        StringReader reader = new StringReader(builder.getInput());
        reader.setCursor(builder.getStart());
        return SharedSuggestionProvider.suggest(BuiltinCapabilities.SUGGESTION_LIST, builder, BuiltinCapabilities::getName, builtinCapabilities -> ModLanguage.CAP_FOUND);
    }

    public enum BuiltinCapabilities {
        NONE("none", null),
        FLUID_HANDLER_ITEM("fluid_handler_item", ForgeCapabilities.FLUID_HANDLER_ITEM),
        FLUID_HANDLER("fluid_handler", ForgeCapabilities.FLUID_HANDLER),
        ITEM_HANDLER("item_handler", ForgeCapabilities.ITEM_HANDLER),
        ENERGY_STORAGE("energy_storage", ForgeCapabilities.ENERGY);

        static final List<BuiltinCapabilities> SUGGESTION_LIST = List.of(FLUID_HANDLER, FLUID_HANDLER_ITEM, ENERGY_STORAGE, ITEM_HANDLER);
        private final String name;
        private final Capability<?> capability;

        BuiltinCapabilities(String name, Capability<?> capability) {
            this.name = name;
            this.capability = capability;
        }

        public static boolean contains(String name) {
            for (BuiltinCapabilities value : values()) {
                return name.equals(value.getName());
            }

            return false;
        }

        public static BuiltinCapabilities get(String capName) {
            for (BuiltinCapabilities value : values()) {
                if (value.name.equals(capName)) return value;
            }

            return NONE;
        }

        public String getName() {
            return name;
        }

        public Capability<?> getCapability() {
            return capability;
        }
    }
}
