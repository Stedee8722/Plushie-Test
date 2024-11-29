package net.stedee.plushie_test.damage_type;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.stedee.plushie_test.plushie_test;

public class ModdedDamageTypes {
    public static final ResourceKey<DamageType> BLASPHEMY_PROJECTILE =
            ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(plushie_test.MOD_ID, "blasphemy_projectile"));

    public static final ResourceKey<DamageType> GLAIVE_PROJECTILE =
            ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(plushie_test.MOD_ID, "glaive_projectile"));

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static DamageSource BlasphemyProjectile(RegistryAccess registryAccess, Entity pAttacker, Entity pTarget) {
        return new DamageSource(registryAccess.registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(BLASPHEMY_PROJECTILE), pTarget, pAttacker, null);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static DamageSource GlaiveProjectile(RegistryAccess registryAccess, Entity pTarget) {
        return new DamageSource(registryAccess.registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(GLAIVE_PROJECTILE), pTarget, null, null);
    }
}
