package com.khanhtypo.tothemoon.network;

import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.menus.AbstractMachineMenu;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MachineActiveToggleMessage {

    public MachineActiveToggleMessage() {}

    public void write(FriendlyByteBuf ignored) {}

    public static MachineActiveToggleMessage read(FriendlyByteBuf ignored) {return new MachineActiveToggleMessage();}

    public static void send(Connection connection) {
        ToTheMoon.CHANNEL.sendTo(
                new MachineActiveToggleMessage(),
                connection,
                NetworkDirection.PLAY_TO_SERVER
        );
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (context.getSender().containerMenu instanceof AbstractMachineMenu machineMenu) {
            machineMenu.toggleActive();
        }
        context.setPacketHandled(true);
    }
}
