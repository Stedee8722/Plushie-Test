package net.stedee.plushie_test.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.stedee.plushie_test.entity.BlasphemyProjectileEntity;
import net.stedee.plushie_test.entity.ModdedEntities;

public class BlasphemyBrenzyProjectile extends BlasphemyProjectileEntity {
    public BlasphemyBrenzyProjectile(EntityType<? extends BlasphemyBrenzyProjectile> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public BlasphemyBrenzyProjectile(Level pLevel, LivingEntity livingEntity, double pOffsetX, double pOffsetY, double pOffsetZ, float pDamage) {
        super(ModdedEntities.BLASPHEMY_BRENZY_PROJECTILE.get(), livingEntity, pOffsetX, pOffsetY, pOffsetZ, pLevel, pDamage);
    }
}
