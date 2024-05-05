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

    public static final RegistryObject<plushies_item> plush_aym = ITEMS.register("plush_aym",
            () -> new plushies_item(true));

    public static final RegistryObject<plushies_item> nova_ears = ITEMS.register("nova_ears",
            () -> new plushies_item(false));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}   
