package com.khanhtypo.tothemoon.network;

import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class NetworkUtils {
    public static final String PROTOCOL_VERSION = "1.0";
    private NetworkUtils() {}

    public static SimpleChannel createChannel() {
        SimpleChannel channel =  NetworkRegistry.newSimpleChannel(
                ModUtils.location("ttm_channel"),
                () -> PROTOCOL_VERSION,
                s -> s.equals(PROTOCOL_VERSION),
                s -> s.equals(PROTOCOL_VERSION)
        );

        channel.registerMessage(0, MachineActiveToggleMessage.class, MachineActiveToggleMessage::write, MachineActiveToggleMessage::read, MachineActiveToggleMessage::handle);
        //channel.registerMessage(1, WorkbenchToolBrokenMessage.class, WorkbenchToolBrokenMessage::write, WorkbenchToolBrokenMessage::read, WorkbenchToolBrokenMessage::handle);
        return channel;
    }
}
