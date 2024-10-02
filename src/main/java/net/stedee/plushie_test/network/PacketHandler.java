package net.stedee.plushie_test.network;

import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.stedee.plushie_test.plushie_test;
import net.minecraft.resources.ResourceLocation;

public class PacketHandler {

    public static SimpleChannel INSTANCE;

    private static final String PROTOCOL_VERSION = "1.0";

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(plushie_test.MOD_ID, channelName), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
        
        INSTANCE.registerMessage(0, S2CLastRecipePacket.class,
                S2CLastRecipePacket::encode,
                S2CLastRecipePacket::new,
                S2CLastRecipePacket::handle);
    }
}
