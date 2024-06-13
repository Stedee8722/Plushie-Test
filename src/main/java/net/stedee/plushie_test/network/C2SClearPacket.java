package net.stedee.plushie_test.network;

import java.util.function.Supplier;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;
import net.stedee.plushie_test.inventory.custom.SeamstressTableMenu;

public class C2SClearPacket {
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        Player player = ctx.get().getSender();

        if (player == null) return;

        ctx.get().enqueueWork(() -> {
        AbstractContainerMenu container = player.containerMenu;
        if (container instanceof SeamstressTableMenu seamstressTableMenu) {
            for (int i = 1; i < 10;i++)
            seamstressTableMenu.quickMoveStack(player,i);
        }
        });
        ctx.get().setPacketHandled(true);
    }
}
