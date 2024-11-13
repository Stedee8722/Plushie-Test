package net.stedee.plushie_test.item;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.plushie_test.effect.ModdedEffects;
import net.stedee.plushie_test.item.custom.*;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.item.custom_armor_materials.CustomMaskMaterial;
import net.stedee.plushie_test.sound.ModdedSounds;

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
            () -> new CleaverItem(Tiers.IRON, 3F, -3F, (new Item.Properties()).defaultDurability(508).rarity(Rarity.UNCOMMON), false));

    public static final RegistryObject<CleaverItem> WORN_CLEAVER = ITEMS.register("worn_cleaver",
            () -> new CleaverItem(Tiers.DIAMOND, 5F, -2.4F, (new Item.Properties()).defaultDurability(762).rarity(Rarity.RARE), true, MobEffects.HUNGER));

    public static final RegistryObject<CleaverItem> GODLY_CLEAVER = ITEMS.register("godly_cleaver",
            () -> new CleaverItem(Tiers.NETHERITE, 5F, -2.4F, (new Item.Properties()).defaultDurability(1016).rarity(Rarity.EPIC), true, MobEffects.HUNGER, ModdedEffects.BLOODLOSS.get()));

    public static final RegistryObject<MoonStaffItem> MOON_STAFF = ITEMS.register("moon_staff",
            () -> new MoonStaffItem(Tiers.NETHERITE, 5, -2.4F, (new Item.Properties()).defaultDurability(753).rarity(Rarity.EPIC)));

    public static final RegistryObject<BlasphemyItem> BLASPHEMY = ITEMS.register("blasphemy",
            () -> new BlasphemyItem(Tiers.DIAMOND, 4, -2.4F, (new Item.Properties().defaultDurability(762).rarity(Rarity.RARE))));

    public static final RegistryObject<BlasphemyBrenzyItem> BLASPHEMY_BRENZY = ITEMS.register("blasphemy_brenzy",
            () -> new BlasphemyBrenzyItem(Tiers.NETHERITE, 5, -3F, (new Item.Properties().defaultDurability(762).rarity(Rarity.EPIC))));

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

    public static final RegistryObject<Item> DISC_START_A_CULT = ITEMS.register("disc_start_a_cult",
            () -> new RecordItem(6, ModdedSounds.START_A_CULT, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 6000));

    public static final RegistryObject<Item> DISC_SACRIFICE = ITEMS.register("disc_sacrifice",
            () -> new RecordItem(6, ModdedSounds.SACRIFICE, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 5075));

    public static final RegistryObject<Item> DISC_FIRST_SON_BAAL = ITEMS.register("disc_first_son_baal",
            () -> new RecordItem(12, ModdedSounds.FIRST_SON_BAAL, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .fireResistant(), 1960));

    public static final RegistryObject<Item> DISC_SECOND_SON_AYM = ITEMS.register("disc_second_son_aym",
            () -> new RecordItem(13, ModdedSounds.SECOND_SON_AYM, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .fireResistant(), 1041));

    public static final RegistryObject<Item> DISC_LESHY = ITEMS.register("disc_leshy",
            () -> new RecordItem(0, ModdedSounds.LESHY, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 2271));

    public static final RegistryObject<Item> DISC_HEKET = ITEMS.register("disc_heket",
            () -> new RecordItem(1, ModdedSounds.HEKET, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 1810));

    public static final RegistryObject<Item> DISC_KALLAMAR = ITEMS.register("disc_kallamar",
            () -> new RecordItem(2, ModdedSounds.KALLAMAR, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 2580));

    public static final RegistryObject<Item> DISC_SHAMURA = ITEMS.register("disc_shamura",
            () -> new RecordItem(3, ModdedSounds.SHAMURA, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 3110));

    public static final RegistryObject<Item> DISC_THE_ONE_WHO_WAITS = ITEMS.register("disc_the_one_who_waits",
            () -> new RecordItem(4, ModdedSounds.THE_ONE_WHO_WAITS, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 2138));

    public static final RegistryObject<Item> DISC_AMDUSIAS = ITEMS.register("disc_amdusias",
            () -> new RecordItem(0, ModdedSounds.AMDUSIAS, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 1540));

    public static final RegistryObject<Item> DISC_ELIGOS = ITEMS.register("disc_eligos",
            () -> new RecordItem(1, ModdedSounds.ELIGOS, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 2160));

    public static final RegistryObject<Item> DISC_SALEOS = ITEMS.register("disc_saleos",
            () -> new RecordItem(2, ModdedSounds.SALEOS, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 1600));

    public static final RegistryObject<Item> DISC_VEPHAR = ITEMS.register("disc_vephar",
            () -> new RecordItem(3, ModdedSounds.VEPHAR, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 2260));

    public static final RegistryObject<Item> DISC_GLUTTONY_OF_CANNIBALS = ITEMS.register("disc_gluttony_of_cannibals",
            () -> new RecordItem(6, ModdedSounds.GLUTTONY_OF_CANNIBALS, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 724));

    public static final RegistryObject<Item> DISC_SAVIOUR = ITEMS.register("disc_saviour",
            () -> new RecordItem(6, ModdedSounds.SAVIOUR, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 1534));

    public static final RegistryObject<Item> DISC_CHEMACH = ITEMS.register("disc_chemach",
            () -> new RecordItem(5, ModdedSounds.CHEMACH, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 1942));

    public static final RegistryObject<Item> DISC_PLIMBO = ITEMS.register("disc_plimbo",
            () -> new RecordItem(7, ModdedSounds.PLIMBO, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 1430));

    public static final RegistryObject<Item> DISC_WRITINGS_OF_THE_FANATIC = ITEMS.register("disc_writings_of_the_fanatic",
            () -> new RecordItem(6, ModdedSounds.WRITINGS_OF_THE_FANATIC, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 1272));

    public static final RegistryObject<Item> DISC_DARKWOOD = ITEMS.register("disc_darkwood",
            () -> new RecordItem(8, ModdedSounds.DARKWOOD, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 1680));

    public static final RegistryObject<Item> DISC_ANURA = ITEMS.register("disc_anura",
            () -> new RecordItem(9, ModdedSounds.ANURA, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 2700));

    public static final RegistryObject<Item> DISC_ANCHORDEEP = ITEMS.register("disc_anchordeep",
            () -> new RecordItem(10, ModdedSounds.ANCHORDEEP, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 4160));

    public static final RegistryObject<Item> DISC_SILK_CRADLE = ITEMS.register("disc_silk_cradle",
            () -> new RecordItem(11, ModdedSounds.SILK_CRADLE, new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC), 4980));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}   
