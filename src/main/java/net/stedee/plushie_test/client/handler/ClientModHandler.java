package net.stedee.plushie_test.client.handler;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.stedee.plushie_test.client.renderer.entity.BlasphemyEntityModel;
import net.stedee.plushie_test.client.renderer.entity.BlasphemyEntityRenderer;
import net.stedee.plushie_test.entity.ModdedEntities;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.block.ModdedBlockEntities;
import net.stedee.plushie_test.client.renderer.block.SeamstressTableBlockEntityRenderer;
import net.stedee.plushie_test.client.renderer.block.AlchemicalTableBlockEntityRenderer;
import net.stedee.plushie_test.client.screen.AlchemicalTableScreen;
import net.stedee.plushie_test.client.screen.SeamstressTableScreen;
import net.stedee.plushie_test.inventory.ModdedMenuTypes;

@Mod.EventBusSubscriber(modid = plushie_test.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModdedMenuTypes.SEAMSTRESS_MENU_TYPE.get(), SeamstressTableScreen::new);
            MenuScreens.register(ModdedMenuTypes.ALCHEMICAL_MENU_TYPE.get(), AlchemicalTableScreen::new);
            BlockEntityRenderers.register(ModdedBlockEntities.SEAMSTRESS_TABLE_BLOCK_ENTITY.get(), SeamstressTableBlockEntityRenderer::new);
            BlockEntityRenderers.register(ModdedBlockEntities.ALCHEMICAL_TABLE_BLOCK_ENTITY.get(), AlchemicalTableBlockEntityRenderer::new);
            EntityRenderers.register(ModdedEntities.BLASPHEMY_PROJECTILE.get(), BlasphemyEntityRenderer::new);
        });
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BlasphemyEntityModel.LAYER_LOCATION, BlasphemyEntityModel::createBodyLayer);
    }
}
