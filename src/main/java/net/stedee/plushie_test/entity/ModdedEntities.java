package net.stedee.plushie_test.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.plushie_test.entity.custom.BlasphemyProjectile;
import net.stedee.plushie_test.plushie_test;

public class ModdedEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, plushie_test.MOD_ID);

    public static final RegistryObject<EntityType<BlasphemyProjectile>> BLASPHEMY_PROJECTILE =
            ENTITY_TYPES.register("blasphemy_projectile", () -> EntityType.Builder.<BlasphemyProjectile>of(BlasphemyProjectile::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).build("blasphemy_projectile"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
