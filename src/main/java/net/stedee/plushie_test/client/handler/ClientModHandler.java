package net.stedee.plushie_test.client.handler;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.client.SeamstressTableScreen;
import net.stedee.plushie_test.inventory.ModdedMenuTypes;

@Mod.EventBusSubscriber(modid = plushie_test.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModdedMenuTypes.SEAMSTRESS_MENU_TYPE.get(), SeamstressTableScreen::new);
        });
    }
}
