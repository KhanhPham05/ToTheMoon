package com.khanhtypo.tothemoon.network;

import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public final class NetworkUtils {
    public static final String PROTOCOL_VERSION = "1.0";
    public static final SimpleChannel CHANNEL_INSTANCE = createChannel();

    private NetworkUtils() {
    }

    private static SimpleChannel createChannel() {
        SimpleChannel channel = NetworkRegistry.newSimpleChannel(
                ModUtils.location("ttm_channel"),
                () -> PROTOCOL_VERSION,
                s -> s.equals(PROTOCOL_VERSION),
                s -> s.equals(PROTOCOL_VERSION)
        );

        channel.registerMessage(0, MachineActiveTogglePacket.class, MachineActiveTogglePacket::write, MachineActiveTogglePacket::read, MachineActiveTogglePacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        channel.registerMessage(1, RedstoneModeTogglePacket.class, RedstoneModeTogglePacket::write, RedstoneModeTogglePacket::read, RedstoneModeTogglePacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        return channel;
    }
}
