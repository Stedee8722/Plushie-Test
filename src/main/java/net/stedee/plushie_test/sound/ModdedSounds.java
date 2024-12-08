package net.stedee.plushie_test.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.plushie_test.plushie_test;

public class ModdedSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = 
        DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, plushie_test.MOD_ID);

    public static final RegistryObject<SoundEvent> PLUSHIE_USE = registerSoundEvents("plushie_use");

    public static final RegistryObject<SoundEvent> START_A_CULT = registerSoundEvents("start_a_cult");
    public static final RegistryObject<SoundEvent> SACRIFICE = registerSoundEvents("sacrifice");
    public static final RegistryObject<SoundEvent> GLUTTONY_OF_CANNIBALS = registerSoundEvents("gluttony_of_cannibals");
    public static final RegistryObject<SoundEvent> FIRST_SON_BAAL = registerSoundEvents("first_son_baal");
    public static final RegistryObject<SoundEvent> SECOND_SON_AYM = registerSoundEvents("second_son_aym");
    public static final RegistryObject<SoundEvent> THE_ONE_WHO_WAITS = registerSoundEvents("the_one_who_waits");
    public static final RegistryObject<SoundEvent> LESHY = registerSoundEvents("leshy");
    public static final RegistryObject<SoundEvent> HEKET = registerSoundEvents("heket");
    public static final RegistryObject<SoundEvent> KALLAMAR = registerSoundEvents("kallamar");
    public static final RegistryObject<SoundEvent> SHAMURA = registerSoundEvents("shamura");
    public static final RegistryObject<SoundEvent> AMDUSIAS = registerSoundEvents("amdusias");
    public static final RegistryObject<SoundEvent> ELIGOS = registerSoundEvents("eligos");
    public static final RegistryObject<SoundEvent> SALEOS = registerSoundEvents("saleos");
    public static final RegistryObject<SoundEvent> VEPHAR = registerSoundEvents("vephar");
    public static final RegistryObject<SoundEvent> DARKWOOD = registerSoundEvents("darkwood");
    public static final RegistryObject<SoundEvent> ANURA = registerSoundEvents("anura");
    public static final RegistryObject<SoundEvent> ANCHORDEEP = registerSoundEvents("anchordeep");
    public static final RegistryObject<SoundEvent> SILK_CRADLE = registerSoundEvents("silk_cradle");
    public static final RegistryObject<SoundEvent> SAVIOUR = registerSoundEvents("saviour");
    public static final RegistryObject<SoundEvent> CHEMACH = registerSoundEvents("chemach");
    public static final RegistryObject<SoundEvent> PLIMBO = registerSoundEvents("plimbo");
    public static final RegistryObject<SoundEvent> WRITINGS_OF_THE_FANATIC = registerSoundEvents("writings_of_the_fanatic");
    public static final RegistryObject<SoundEvent> IN_THE_END = registerSoundEvents("bruh_wtf_is_this_did_you_even_tested_the_file");
    public static final RegistryObject<SoundEvent> CHAMPION_BATTLE_MUSIC = registerSoundEvents("champion_battle_music");
    public static final RegistryObject<SoundEvent> ENTER_THE_GUNGEON = registerSoundEvents("enter_the_gungeon");
    public static final RegistryObject<SoundEvent> INFINITE_AMETHYST = registerSoundEvents("infinite_amethyst");

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(plushie_test.MOD_ID, name)));
    }
}
