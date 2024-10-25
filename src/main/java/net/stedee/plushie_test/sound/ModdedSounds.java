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

    public static final RegistryObject<SoundEvent> PLUSHIE_USE = registerSoundEvents();

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

    private static RegistryObject<SoundEvent> registerSoundEvents() {
        return SOUND_EVENTS.register("plushie_use", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(plushie_test.MOD_ID, "plushie_use")));
    }
}
