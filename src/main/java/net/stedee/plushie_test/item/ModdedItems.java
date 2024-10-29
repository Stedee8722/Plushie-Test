package net.stedee.plushie_test.item;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.plushie_test.item.custom.MoonStaffItem;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.item.custom.CleaverItem;
import net.stedee.plushie_test.item.custom.MaskItem;
import net.stedee.plushie_test.item.custom.PlushiesItem;
import net.stedee.plushie_test.item.custom_armor_materials.CustomMaskMaterial;

public class ModdedItems {
    public static final DeferredRegister<Item> ITEMS = 
            DeferredRegister.create(ForgeRegistries.ITEMS, plushie_test.MOD_ID);

    public static final RegistryObject<PlushiesItem> PLUSH_AYM = ITEMS.register("plush_aym",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,true));

    public static final RegistryObject<PlushiesItem> NOVA_EARS = ITEMS.register("nova_ears",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,false));

    public static final RegistryObject<PlushiesItem> AYM_EARS = ITEMS.register("aym_ears",
            () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,false));

    public static final RegistryObject<PlushiesItem> PLUSH_RAT = ITEMS.register("plush_rat",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,true));

    public static final RegistryObject<PlushiesItem> PLUSH_ACID = ITEMS.register("plush_acid",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,true));

    public static final RegistryObject<PlushiesItem> HYPNO_NOVA_EARS = ITEMS.register("hypno_nova_ears",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,false));

    public static final RegistryObject<PlushiesItem> PLUSH_HYPNO_NOVA = ITEMS.register("plush_hypno_nova",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,true));

    public static final RegistryObject<PlushiesItem> PLUSH_RATACID = ITEMS.register("plush_ratacid",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,true));

    public static final RegistryObject<PlushiesItem> PLUSH_BRENZY = ITEMS.register("plush_brenzy",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,true));

    public static final RegistryObject<PlushiesItem> PLUSH_PYLA = ITEMS.register("plush_pyla",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,true));

    public static final RegistryObject<PlushiesItem> PLUSH_TEALET = ITEMS.register("plush_tealet",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .fireResistant()
                    ,true));

    public static final RegistryObject<CleaverItem> CLEAVER = ITEMS.register("cleaver",
            () -> new CleaverItem(Tiers.IRON, 6.5F, -2.4F, null, (new Item.Properties()).defaultDurability(508).rarity(Rarity.UNCOMMON), false));

    public static final RegistryObject<CleaverItem> WORN_CLEAVER = ITEMS.register("worn_cleaver",
            () -> new CleaverItem(Tiers.DIAMOND, 6.0F, -2.4F, null, (new Item.Properties()).defaultDurability(762).rarity(Rarity.RARE), true));

    public static final RegistryObject<CleaverItem> GODLY_CLEAVER = ITEMS.register("godly_cleaver",
            () -> new CleaverItem(Tiers.NETHERITE, 6.0F, -2.4F, MobEffects.HUNGER, (new Item.Properties()).defaultDurability(1016).rarity(Rarity.EPIC), true));

    public static final RegistryObject<MoonStaffItem> MOON_STAFF = ITEMS.register("moon_staff",
            () -> new MoonStaffItem(Tiers.NETHERITE, 5, -2.4F, (new Item.Properties()).defaultDurability(753).rarity(Rarity.EPIC)));

    public static final RegistryObject<PlushiesItem> PLUSH_NETH = ITEMS.register("plush_neth",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,true));

    public static final RegistryObject<PlushiesItem> PLUSH_BRENNETH = ITEMS.register("plush_brenneth",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,true));

    public static final RegistryObject<PlushiesItem> PLUSH_BRELA = ITEMS.register("plush_brela",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,true));

    public static final RegistryObject<PlushiesItem> LOAF_BREN = ITEMS.register("loaf_bren",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,true));

    public static final RegistryObject<PlushiesItem> LOAF_MONO = ITEMS.register("loaf_mono",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,true));

    public static final RegistryObject<PlushiesItem> LOAF_PYLA = ITEMS.register("loaf_pyla",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,true));

    public static final RegistryObject<PlushiesItem> LOAF_DUOTAZER = ITEMS.register("loaf_duotazer",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,true));

    public static final RegistryObject<PlushiesItem> LOAF_TRIODERG = ITEMS.register("loaf_trioderg",
                () -> new PlushiesItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    ,true));

    public static final RegistryObject<MaskItem> MASK_VAPOREON = ITEMS.register("mask_vaporeon",
                () -> new MaskItem(new Item.Properties()
                    .stacksTo(1)
                    .defaultDurability(275)
                    .rarity(Rarity.EPIC), CustomMaskMaterial.MASK_VAPOREON_MATS, MobEffects.WATER_BREATHING, 40, 0, true));

    public static final RegistryObject<MaskItem> MASK_EEVEE = ITEMS.register("mask_eevee",
                () -> new MaskItem(new Item.Properties()
                    .stacksTo(1)
                    .defaultDurability(275)
                    .rarity(Rarity.RARE), CustomMaskMaterial.MASK_EEVEE_MATS, null, 0, 0, true));

    public static final RegistryObject<MaskItem> MASK_ESPEON = ITEMS.register("mask_espeon",
                () -> new MaskItem(new Item.Properties()
                    .stacksTo(1)
                    .defaultDurability(275)
                    .rarity(Rarity.EPIC), CustomMaskMaterial.MASK_ESPEON_MATS, MobEffects.SLOW_FALLING,100,0, true));

    public static final RegistryObject<MaskItem> MASK_FLAREON = ITEMS.register("mask_flareon",
                () -> new MaskItem(new Item.Properties()
                    .fireResistant()
                    .stacksTo(1)
                    .defaultDurability(275)
                    .rarity(Rarity.EPIC), CustomMaskMaterial.MASK_FLAREON_MATS, MobEffects.FIRE_RESISTANCE,200, 0, true));

    public static final RegistryObject<MaskItem> MASK_GLACEON = ITEMS.register("mask_glaceon",
                () -> new MaskItem(new Item.Properties()
                    .stacksTo(1)
                    .defaultDurability(275)
                    .rarity(Rarity.EPIC), CustomMaskMaterial.MASK_GLACEON_MATS, MobEffects.JUMP,40, 1, true));

    public static final RegistryObject<MaskItem> MASK_LEAFEON = ITEMS.register("mask_leafeon",
                () -> new MaskItem(new Item.Properties()
                    .stacksTo(1)
                    .defaultDurability(275)
                    .rarity(Rarity.EPIC), CustomMaskMaterial.MASK_LEAFEON_MATS, MobEffects.DIG_SPEED,40, 0, true));

    public static final RegistryObject<MaskItem> MASK_JOLTEON = ITEMS.register("mask_jolteon",
                () -> new MaskItem(new Item.Properties()
                    .stacksTo(1)
                    .defaultDurability(275)
                    .rarity(Rarity.EPIC), CustomMaskMaterial.MASK_JOLTEON_MATS, MobEffects.MOVEMENT_SPEED,40, 1, true));

    public static final RegistryObject<MaskItem> MASK_SYLVEON = ITEMS.register("mask_sylveon",
                () -> new MaskItem(new Item.Properties()
                    .stacksTo(1)
                    .defaultDurability(275)
                    .rarity(Rarity.EPIC), CustomMaskMaterial.MASK_SYLVEON_MATS, MobEffects.REGENERATION,40, 1, false));

    public static final RegistryObject<MaskItem> MASK_UMBREON = ITEMS.register("mask_umbreon",
                () -> new MaskItem(new Item.Properties()
                    .stacksTo(1)
                    .defaultDurability(275)
                    .rarity(Rarity.EPIC), CustomMaskMaterial.MASK_UMBREON_MATS, MobEffects.NIGHT_VISION,220, 0, true));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}   
