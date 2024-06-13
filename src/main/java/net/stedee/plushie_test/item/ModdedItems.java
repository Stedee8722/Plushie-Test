package net.stedee.plushie_test.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.item.custom.CleaverItem;
import net.stedee.plushie_test.item.custom.PlushiesItem;

public class ModdedItems {
    public static final DeferredRegister<Item> ITEMS = 
            DeferredRegister.create(ForgeRegistries.ITEMS, plushie_test.MOD_ID);

    public static final RegistryObject<PlushiesItem> PLUSH_AYM = ITEMS.register("plush_aym",
            () -> new PlushiesItem(true));

    public static final RegistryObject<PlushiesItem> NOVA_EARS = ITEMS.register("nova_ears",
            () -> new PlushiesItem(false));

    public static final RegistryObject<PlushiesItem> PLUSH_RAT = ITEMS.register("plush_rat",
            () -> new PlushiesItem(true));

    public static final RegistryObject<PlushiesItem> PLUSH_ACID = ITEMS.register("plush_acid",
            () -> new PlushiesItem(true));

    public static final RegistryObject<PlushiesItem> HYPNO_NOVA_EARS = ITEMS.register("hypno_nova_ears",
            () -> new PlushiesItem(false));

    public static final RegistryObject<PlushiesItem> PLUSH_RATACID = ITEMS.register("plush_ratacid",
            () -> new PlushiesItem(true));

    public static final RegistryObject<PlushiesItem> PLUSH_BRENZY = ITEMS.register("plush_brenzy",
            () -> new PlushiesItem(true));

    public static final RegistryObject<PlushiesItem> PLUSH_PYLA = ITEMS.register("plush_pyla",
            () -> new PlushiesItem(true));

    public static final RegistryObject<PlushiesItem> PLUSH_TEALET = ITEMS.register("plush_tealet",
            () -> new PlushiesItem(true));

    public static final RegistryObject<CleaverItem> CLEAVER = ITEMS.register("cleaver",
            () -> new CleaverItem(Tiers.NETHERITE, 5.0F, -2.4F, (new Item.Properties()).durability(1016).rarity(Rarity.EPIC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}   
