package net.stedee.plushie_test.network;

import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.stedee.plushie_test.plushie_test;
import net.minecraft.resources.ResourceLocation;

public class PacketHandler {

    public static SimpleChannel INSTANCE;

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(plushie_test.MOD_ID, channelName), () -> "1.0", s -> true, s -> true);
        INSTANCE.registerMessage(0, S2CLastRecipePacket.class,
                S2CLastRecipePacket::encode,
                S2CLastRecipePacket::new,
                S2CLastRecipePacket::handle);

        INSTANCE.registerMessage(4, C2SClearPacket.class,
                (c2SClearPacket, buffer) -> {},
                buffer -> new C2SClearPacket(),
                C2SClearPacket::handle);
    }
}
