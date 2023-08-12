package com.khanhtypo.tothemoon.network;

import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.common.machine.AbstractMachineMenu;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MachineActiveTogglePacket {

    public MachineActiveTogglePacket() {}

    public void write(FriendlyByteBuf ignored) {}

    public static MachineActiveTogglePacket read(FriendlyByteBuf ignored) {return new MachineActiveTogglePacket();}

    public static void send() {
        ToTheMoon.CHANNEL.sendToServer(new MachineActiveTogglePacket());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (context.getSender().containerMenu instanceof AbstractMachineMenu machineMenu) {
            machineMenu.toggleActive();
        }
        context.setPacketHandled(true);
    }
}
