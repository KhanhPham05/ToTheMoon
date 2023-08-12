package com.khanhtypo.tothemoon.network;

import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class NetworkUtils {
    public static final String PROTOCOL_VERSION = "1.0";

    private NetworkUtils() {
    }

    public static SimpleChannel createChannel() {
        SimpleChannel channel = NetworkRegistry.newSimpleChannel(
                ModUtils.location("ttm_channel"),
                () -> PROTOCOL_VERSION,
                s -> s.equals(PROTOCOL_VERSION),
                s -> s.equals(PROTOCOL_VERSION)
        );

        channel.registerMessage(0, MachineActiveTogglePacket.class, MachineActiveTogglePacket::write, MachineActiveTogglePacket::read, MachineActiveTogglePacket::handle);
        channel.registerMessage(1, RedstoneModeTogglePacket.class, RedstoneModeTogglePacket::write, RedstoneModeTogglePacket::read, RedstoneModeTogglePacket::handle);
        return channel;
    }
}
