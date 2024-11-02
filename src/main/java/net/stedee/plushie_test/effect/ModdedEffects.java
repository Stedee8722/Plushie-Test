package net.stedee.plushie_test.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.plushie_test.effect.custom.BloodlossEffect;
import net.stedee.plushie_test.plushie_test;

public class ModdedEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, plushie_test.MOD_ID);

    public static final RegistryObject<MobEffect> BLOODLOSS = MOB_EFFECTS.register("bloodloss",
            () -> new BloodlossEffect(MobEffectCategory.HARMFUL, 8323072));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
