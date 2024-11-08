package net.stedee.plushie_test.entity.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.stedee.plushie_test.entity.BlasphemyProjectileEntity;
import net.stedee.plushie_test.entity.ModdedEntities;
import org.jetbrains.annotations.NotNull;

public class BlasphemyProjectile extends BlasphemyProjectileEntity {
    float pDamage;
    public BlasphemyProjectile(EntityType<? extends BlasphemyProjectile> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public BlasphemyProjectile(Level pLevel, LivingEntity livingEntity, double pOffsetX, double pOffsetY, double pOffsetZ, float pDamage) {
        super(ModdedEntities.BLASPHEMY_PROJECTILE.get(), livingEntity, pOffsetX, pOffsetY, pOffsetZ, pLevel);
        this.pDamage = pDamage;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        if (!this.level().isClientSide) {
            Entity pTarget = pResult.getEntity();
            Entity pAttacker = this.getOwner();
            pTarget.hurt(this.level().damageSources().indirectMagic(this, pAttacker), this.pDamage);
            if (pAttacker instanceof LivingEntity) {
                this.doEnchantDamageEffects((LivingEntity) pAttacker, pTarget);
            }
        }
        super.onHitEntity(pResult);
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }
}
