package com.khanhtypo.tothemoon.network;

import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.menus.AbstractMachineMenu;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerBoundMachineActivePacket {

    public ServerBoundMachineActivePacket() {}

    public void write(FriendlyByteBuf ignored) {}

    public static ServerBoundMachineActivePacket read(FriendlyByteBuf ignored) {return new ServerBoundMachineActivePacket();}

    public static void send(Connection connection) {
        ToTheMoon.CHANNEL.sendTo(
                new ServerBoundMachineActivePacket(),
                connection,
                NetworkDirection.PLAY_TO_SERVER
        );
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (context.getSender().containerMenu instanceof AbstractMachineMenu machineMenu) {
            machineMenu.toggleActive();
        }
    }
}
