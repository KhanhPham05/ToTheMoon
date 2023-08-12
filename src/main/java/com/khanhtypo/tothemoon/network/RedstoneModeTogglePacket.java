package com.khanhtypo.tothemoon.network;

import com.khanhtypo.tothemoon.common.machine.AbstractMachineMenu;
import com.khanhtypo.tothemoon.common.machine.MachineRedstoneMode;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class RedstoneModeTogglePacket {
    private final MachineRedstoneMode redstoneMode;

    public RedstoneModeTogglePacket(MachineRedstoneMode redstoneMode) {
        this.redstoneMode = redstoneMode;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(redstoneMode.getIndex());
    }

    public static RedstoneModeTogglePacket read(FriendlyByteBuf buf) {
        return new RedstoneModeTogglePacket(MachineRedstoneMode.valueFromIndex(buf.readInt()));
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        if (contextSupplier.get().getSender().containerMenu instanceof AbstractMachineMenu machineMenu) {
            machineMenu.toggleRedstone(this.redstoneMode);
        }
        contextSupplier.get().setPacketHandled(true);
    }
}
