package com.khanhpham.tothemoon.debug;

import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class GetCapInfoCommand {

    static final SimpleCommandExceptionType CAP_NOT_FOUND = new SimpleCommandExceptionType(ModLanguage.CAP_UNKNOWN);

    private GetCapInfoCommand() {

    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        var command = Commands.literal("getcapinfo").requires(commandSourceStack -> commandSourceStack.hasPermission(0));
        dispatcher.register(command
                .then(Commands.argument("cap", new CapabilityInfoArgument()).executes(context -> getCap(context.getSource(), CapabilityInfoArgument.getCapInfo(context, "cap"))))
        );
    }

    private static int getCap(CommandSourceStack source, CapabilityInfoArgument.BuiltinCapabilities cap) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        ItemStack mainHandItem = player.getMainHandItem();

        ModUtils.log("Checking Cap");
        if (mainHandItem.getCapability(cap.getCapability()).isPresent()) {
            source.sendSuccess(ModLanguage.CAP_FOUND, true);
        }
        ModUtils.log("Check DONE");

        return 1;
    }
}
