package net.stedee.plushie_test.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.stedee.plushie_test.entity.BlasphemyProjectileEntity;
import net.stedee.plushie_test.entity.ModdedEntities;

public class BlasphemyProjectile extends BlasphemyProjectileEntity {
    public BlasphemyProjectile(EntityType<? extends BlasphemyProjectile> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public BlasphemyProjectile(Level pLevel, LivingEntity livingEntity, double pOffsetX, double pOffsetY, double pOffsetZ, float pDamage) {
        super(ModdedEntities.BLASPHEMY_PROJECTILE.get(), livingEntity, pOffsetX, pOffsetY, pOffsetZ, pLevel, pDamage);
    }
}
