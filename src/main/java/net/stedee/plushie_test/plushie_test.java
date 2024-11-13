package net.stedee.plushie_test;

import com.mojang.logging.LogUtils;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.stedee.plushie_test.advancement.ModdedAdvancements;
import net.stedee.plushie_test.block.ModdedBlockEntities;
import net.stedee.plushie_test.block.ModdedBlocks;
import net.stedee.plushie_test.config.ClientConfig;
import net.stedee.plushie_test.effect.ModdedEffects;
import net.stedee.plushie_test.enchantment.ModdedEnchantments;
import net.stedee.plushie_test.entity.ModdedEntities;
import net.stedee.plushie_test.inventory.ModdedMenuTypes;
import net.stedee.plushie_test.item.ModdedCreativeTabs;
import net.stedee.plushie_test.item.ModdedItems;
import net.stedee.plushie_test.network.PacketHandler;
import net.stedee.plushie_test.painting.ModdedPaintings;
import net.stedee.plushie_test.recipe.ModdedRecipes;
import net.stedee.plushie_test.sound.ModdedSounds;
import software.bernie.geckolib.GeckoLib;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@SuppressWarnings("removal")
@Mod(plushie_test.MOD_ID)
public class plushie_test {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "plushie_test";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    
    public static ClientConfig CONFIG = new ClientConfig();

    public plushie_test() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModdedCreativeTabs.register(modEventBus);

        ModdedItems.register(modEventBus);
        ModdedBlocks.register(modEventBus);
        ModdedEntities.register(modEventBus);

        ModdedBlockEntities.register(modEventBus);
        ModdedMenuTypes.register(modEventBus);

        ModdedSounds.register(modEventBus);

        ModdedRecipes.register(modEventBus);

        ModdedPaintings.register(modEventBus);

        ModdedEnchantments.register(modEventBus);
        ModdedEffects.register(modEventBus);

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);
        
        initConfig();

        //ModLoadingContext.get().registerConfig(Type.CLIENT, PlushieTestClientConfig.SPEC, MOD_ID + "-client.toml");
        ModLoadingContext.get().registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                    (minecraft, screen) -> AutoConfig.getConfigScreen(ClientConfig.class, screen).get()
                )
        );

        MinecraftForge.EVENT_BUS.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        PacketHandler.registerMessages(MOD_ID);
        ModdedAdvancements.init();
    }

    private static void initConfig() {
        AutoConfig.register(ClientConfig.class, Toml4jConfigSerializer::new);
        // Intuitive way to load a config
        CONFIG = AutoConfig.getConfigHolder(ClientConfig.class).getConfig();
    }
    

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModdedBlocks.SEAMSTRESS_TABLE);
            event.accept(ModdedBlocks.ALCHEMICAL_TABLE);
        }
        else if(event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModdedItems.CLEAVER);
            event.accept(ModdedItems.WORN_CLEAVER);
            event.accept(ModdedItems.GODLY_CLEAVER);
            event.accept(ModdedItems.MOON_STAFF);
            event.accept(ModdedItems.BLASPHEMY);
            event.accept(ModdedItems.BLASPHEMY_BRENZY);
        }
        else if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModdedItems.DISC_START_A_CULT);
            event.accept(ModdedItems.DISC_SACRIFICE);
            event.accept(ModdedItems.DISC_SAVIOUR);
            event.accept(ModdedItems.DISC_GLUTTONY_OF_CANNIBALS);
            event.accept(ModdedItems.DISC_WRITINGS_OF_THE_FANATIC);
            event.accept(ModdedItems.DISC_LESHY);
            event.accept(ModdedItems.DISC_HEKET);
            event.accept(ModdedItems.DISC_KALLAMAR);
            event.accept(ModdedItems.DISC_SHAMURA);
            event.accept(ModdedItems.DISC_FIRST_SON_BAAL);
            event.accept(ModdedItems.DISC_SECOND_SON_AYM);
            event.accept(ModdedItems.DISC_THE_ONE_WHO_WAITS);
            event.accept(ModdedItems.DISC_AMDUSIAS);
            event.accept(ModdedItems.DISC_ELIGOS);
            event.accept(ModdedItems.DISC_SALEOS);
            event.accept(ModdedItems.DISC_VEPHAR);
            event.accept(ModdedItems.DISC_CHEMACH);
            event.accept(ModdedItems.DISC_PLIMBO);
            event.accept(ModdedItems.DISC_DARKWOOD);
            event.accept(ModdedItems.DISC_ANURA);
            event.accept(ModdedItems.DISC_ANCHORDEEP);
            event.accept(ModdedItems.DISC_SILK_CRADLE);
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
