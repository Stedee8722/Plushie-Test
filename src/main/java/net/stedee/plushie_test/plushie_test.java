package net.stedee.plushie_test;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.stedee.plushie_test.block.ModdedBlockEntities;
import net.stedee.plushie_test.block.ModdedBlocks;
import net.stedee.plushie_test.inventory.ModdedMenuTypes;
import net.stedee.plushie_test.item.ModdedItems;
import net.stedee.plushie_test.network.PacketHandler;
import net.stedee.plushie_test.recipe.ModdedRecipes;
import net.stedee.plushie_test.sound.ModdedSounds;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(plushie_test.MOD_ID)
public class plushie_test {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "plushie_test";
    // Directly reference a slf4j logger
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    public plushie_test() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModdedItems.register(modEventBus);
        ModdedBlocks.register(modEventBus);

        ModdedBlockEntities.register(modEventBus);
        ModdedMenuTypes.register(modEventBus);

        ModdedSounds.register(modEventBus);

        ModdedRecipes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);

        MinecraftForge.EVENT_BUS.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        PacketHandler.registerMessages(MOD_ID);
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModdedItems.PLUSH_AYM);
            event.accept(ModdedItems.PLUSH_RAT);
            event.accept(ModdedItems.PLUSH_ACID);
            event.accept(ModdedItems.PLUSH_RATACID);
            event.accept(ModdedItems.PLUSH_BRENZY);
            event.accept(ModdedItems.PLUSH_PYLA);
            event.accept(ModdedItems.PLUSH_TEALET);
            event.accept(ModdedItems.PLUSH_NETH);
            event.accept(ModdedItems.PLUSH_BRENNETH);
            event.accept(ModdedItems.NOVA_EARS);
            event.accept(ModdedItems.HYPNO_NOVA_EARS);
            event.accept(ModdedBlocks.SEAMSTRESS_TABLE.get());
        }
        else if(event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModdedItems.CLEAVER);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        //LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            //LOGGER.info("HELLO FROM CLIENT SETUP");
            //LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
