package net.stedee.plushie_test.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.item.custom.plushies_item;

public class plushies {
    public static final DeferredRegister<Item> ITEMS = 
            DeferredRegister.create(ForgeRegistries.ITEMS, plushie_test.MOD_ID);

    public static final RegistryObject<plushies_item> PLUSH_AYM = ITEMS.register("plush_aym",
            () -> new plushies_item(true));

    public static final RegistryObject<plushies_item> NOVA_EARS = ITEMS.register("nova_ears",
            () -> new plushies_item(false));

    public static final RegistryObject<plushies_item> PLUSH_RAT = ITEMS.register("plush_rat",
            () -> new plushies_item(true));

    public static final RegistryObject<plushies_item> PLUSH_ACID = ITEMS.register("plush_acid",
            () -> new plushies_item(true));

    public static final RegistryObject<plushies_item> HYPNO_NOVA_EARS = ITEMS.register("hypno_nova_ears",
            () -> new plushies_item(false));

    public static final RegistryObject<plushies_item> PLUSH_RATACID = ITEMS.register("plush_ratacid",
            () -> new plushies_item(true));

    public static final RegistryObject<plushies_item> PLUSH_BRENZY = ITEMS.register("plush_brenzy",
            () -> new plushies_item(true));

    public static final RegistryObject<plushies_item> PLUSH_PYLA = ITEMS.register("plush_pyla",
            () -> new plushies_item(true));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}   
