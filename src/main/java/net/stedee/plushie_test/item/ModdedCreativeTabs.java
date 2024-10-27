package net.stedee.plushie_test.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.plushie_test.plushie_test;

public class ModdedCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = 
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, plushie_test.MOD_ID);

    public static final RegistryObject<CreativeModeTab> PLUSHIES_TAB = CREATIVE_TABS.register("plushies",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModdedItems.PLUSH_AYM.get()))
            .title(Component.translatable("creativetab.plushie_test.plushies.text"))
            .displayItems((pParameter, pOutput) -> {
                pOutput.accept(ModdedItems.PLUSH_AYM.get());
                pOutput.accept(ModdedItems.PLUSH_RAT.get());
                pOutput.accept(ModdedItems.PLUSH_HYPNO_NOVA.get());
                pOutput.accept(ModdedItems.PLUSH_TEALET.get());
                pOutput.accept(ModdedItems.PLUSH_NETH.get());
                pOutput.accept(ModdedItems.PLUSH_ACID.get());
                pOutput.accept(ModdedItems.PLUSH_PYLA.get());
                pOutput.accept(ModdedItems.PLUSH_BRENZY.get());
                pOutput.accept(ModdedItems.PLUSH_RATACID.get());
                pOutput.accept(ModdedItems.PLUSH_BRENNETH.get());
                pOutput.accept(ModdedItems.PLUSH_BRELA.get());
                pOutput.accept(ModdedItems.NOVA_EARS.get());
                pOutput.accept(ModdedItems.HYPNO_NOVA_EARS.get());
                pOutput.accept(ModdedItems.LOAF_MONO.get());
                pOutput.accept(ModdedItems.LOAF_BREN.get());
                pOutput.accept(ModdedItems.LOAF_PYLA.get());
                pOutput.accept(ModdedItems.LOAF_DUOTAZER.get());
                pOutput.accept(ModdedItems.LOAF_TRIODERG.get());
            })
            .build());

    public static final RegistryObject<CreativeModeTab> MASKS_TAB = CREATIVE_TABS.register("masks",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModdedItems.MASK_EEVEE.get()))
            .title(Component.translatable("creativetab.plushie_test.masks.text"))
            .displayItems((pParameter, pOutput) -> {
                pOutput.accept(ModdedItems.MASK_EEVEE.get());
                pOutput.accept(ModdedItems.MASK_VAPOREON.get());
                pOutput.accept(ModdedItems.MASK_ESPEON.get());
                pOutput.accept(ModdedItems.MASK_FLAREON.get());
                pOutput.accept(ModdedItems.MASK_GLACEON.get());
                pOutput.accept(ModdedItems.MASK_LEAFEON.get());
                pOutput.accept(ModdedItems.MASK_UMBREON.get());
                pOutput.accept(ModdedItems.MASK_JOLTEON.get());
                pOutput.accept(ModdedItems.MASK_SYLVEON.get());
            })
            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }
}
