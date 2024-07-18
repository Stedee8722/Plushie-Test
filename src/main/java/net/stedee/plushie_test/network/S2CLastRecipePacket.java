package net.stedee.plushie_test.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.network.NetworkEvent;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.client.SeamstressTableScreen;
import net.stedee.plushie_test.recipe.custom.SeamstressRecipe;

public class S2CLastRecipePacket {
    public static final ResourceLocation NULL = new ResourceLocation("null", "null");

    ResourceLocation rec;

    public S2CLastRecipePacket() {
    }

    public S2CLastRecipePacket(SeamstressRecipe toSend) {
        rec = toSend == null ? NULL : toSend.getId();
    }

    public S2CLastRecipePacket(ResourceLocation toSend) {
        rec = toSend;
    }

    public S2CLastRecipePacket(FriendlyByteBuf buf) {
        rec = new ResourceLocation(buf.readUtf());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(rec.toString());
    }

    @SuppressWarnings({ "null" })
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft instance = Minecraft.getInstance();
            if (instance.screen instanceof SeamstressTableScreen) {
                Recipe<?> r = instance.level.getRecipeManager().byKey(rec).orElse(null);
                ((SeamstressTableScreen) instance.screen).getMenu().updateLastRecipeFromServer((SeamstressRecipe) r);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
