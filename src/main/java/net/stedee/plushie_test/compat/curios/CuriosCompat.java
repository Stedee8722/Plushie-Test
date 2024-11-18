package net.stedee.plushie_test.compat.curios;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.stedee.plushie_test.client.renderer.armor.CuriosCrownRenderer;
import net.stedee.plushie_test.client.renderer.armor.CuriosMaskRenderer;
import net.stedee.plushie_test.init.ICompat;
import net.stedee.plushie_test.item.ModdedItems;
import net.stedee.plushie_test.item.custom.MaskItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CuriosCompat implements ICompat {
    @SuppressWarnings("removal")
    public CuriosCompat() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::sendImc);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SuppressWarnings({"UnstableApiUsage", "removal"})
    private void sendImc(InterModEnqueueEvent evt) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<ItemStack> evt) {
        ItemStack stack = evt.getObject();
        Item item = stack.getItem();
        if ((item instanceof MaskItem)) {
            evt.addCapability(CuriosCapability.ID_ITEM, CuriosApi.createCurioProvider(new ICurio() {
                @Override
                public ItemStack getStack() {
                    return stack;
                }

                @Override
                public void curioTick(top.theillusivec4.curios.api.SlotContext slotContext) {
                    if (!(slotContext.entity() instanceof Player player)) {
                        return;
                    }
                    if (!(player.hasEffect(((MaskItem)item).effect) && !((MaskItem)item).applyConstantly)) {
                        player.addEffect(new MobEffectInstance(((MaskItem)item).effect, ((MaskItem)item).dur, ((MaskItem)item).amp, false, false, true));
                    }
                }
            }));
        }
    }

    @Override
    public void setup() {
        CuriosRendererRegistry.register(ModdedItems.MASK_EEVEE.get(), CuriosMaskRenderer::new);
        CuriosRendererRegistry.register(ModdedItems.MASK_ESPEON.get(), CuriosMaskRenderer::new);
        CuriosRendererRegistry.register(ModdedItems.MASK_FLAREON.get(), CuriosMaskRenderer::new);
        CuriosRendererRegistry.register(ModdedItems.MASK_GLACEON.get(), CuriosMaskRenderer::new);
        CuriosRendererRegistry.register(ModdedItems.MASK_JOLTEON.get(), CuriosMaskRenderer::new);
        CuriosRendererRegistry.register(ModdedItems.MASK_LEAFEON.get(), CuriosMaskRenderer::new);
        CuriosRendererRegistry.register(ModdedItems.MASK_SYLVEON.get(), CuriosMaskRenderer::new);
        CuriosRendererRegistry.register(ModdedItems.MASK_UMBREON.get(), CuriosMaskRenderer::new);
        CuriosRendererRegistry.register(ModdedItems.MASK_VAPOREON.get(), CuriosMaskRenderer::new);

        CuriosRendererRegistry.register(ModdedItems.CROWN_GREEN.get(), CuriosCrownRenderer::new);
    }
}
