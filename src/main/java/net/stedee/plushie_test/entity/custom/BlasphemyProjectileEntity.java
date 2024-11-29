package net.stedee.plushie_test.entity.custom;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.stedee.plushie_test.damage_type.ModdedDamageTypes;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class BlasphemyProjectileEntity extends Projectile {
    public double xPower;
    public double yPower;
    public double zPower;
    private int age = 0;
    float pDamage;

    private static final EntityDataAccessor<Vector3f> DIRECTION =
            SynchedEntityData.defineId(BlasphemyProjectileEntity.class, EntityDataSerializers.VECTOR3);

    protected BlasphemyProjectileEntity(EntityType<? extends BlasphemyProjectileEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BlasphemyProjectileEntity(EntityType<? extends BlasphemyProjectileEntity> pEntityType, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel) {
        this(pEntityType, pLevel);
        this.moveTo(pX, pY, pZ, this.getYRot(), this.getXRot());
        this.reapplyPosition();
        double d0 = Math.sqrt(pOffsetX * pOffsetX + pOffsetY * pOffsetY + pOffsetZ * pOffsetZ);
        if (d0 != 0.0D) {
            this.xPower = pOffsetX / d0 * 0.1D;
            this.yPower = pOffsetY / d0 * 0.1D;
            this.zPower = pOffsetZ / d0 * 0.1D;
        }
    }

    public BlasphemyProjectileEntity(EntityType<? extends BlasphemyProjectileEntity> pEntityType, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel, float pDamage) {
        this(pEntityType, pShooter.getX(), pShooter.getY(), pShooter.getZ(), pOffsetX, pOffsetY, pOffsetZ, pLevel);
        this.setOwner(pShooter);
        this.setRot(pShooter.getYRot(), pShooter.getXRot());
        this.pDamage = pDamage;
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult pResult) {
        super.onHitBlock(pResult);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DIRECTION, Vec3.ZERO.toVector3f());
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        double d0 = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 *= 64.0D;
        return pDistance < d0 * d0;
    }

    @SuppressWarnings({"SuspiciousNameCombination", "deprecation"})
    @Override
    public void tick() {
        Entity entity = this.getOwner();
        if (this.level().isClientSide || (entity == null || !entity.isRemoved()) && this.level().hasChunkAt(this.blockPosition())) {
            super.tick();

            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;


            Vec3 deltaMovement = this.getDeltaMovement();
            if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
                double horizontalDistance = deltaMovement.horizontalDistance();
                this.setYRot((float)(Mth.atan2(deltaMovement.x, deltaMovement.z) * 57.2957763671875));
                this.setXRot((float)(Mth.atan2(deltaMovement.y, horizontalDistance) * 57.2957763671875));
                this.yRotO = this.getYRot();
                this.xRotO = this.getXRot();
            }

            float f = this.getInertia();
            if (this.isInWater()) {
                for(int i = 0; i < 4; ++i) {
                    this.level().addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25D, d1 - vec3.y * 0.25D, d2 - vec3.z * 0.25D, vec3.x, vec3.y, vec3.z);
                }

                f = 0.8F;
            }

            this.setDeltaMovement(vec3.add(this.xPower, this.yPower, this.zPower).scale(f));
            this.level().addParticle(this.getTrailParticle(), d0, d1 + 0.25D, d2, 0.0D, 0.0D, 0.0D);
            this.setPos(d0, d1, d2);

            if (this.age != -32768) {
                ++this.age;
            }

            if (!this.level().isClientSide && this.age >= 2400) {
                this.discard();
            }
        } else {
            this.discard();
        }
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity pEntity) {
        return super.canHitEntity(pEntity) && !pEntity.noPhysics;
    }

    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.CRIT;
    }

    protected float getInertia() {
        return 1F;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.put("power", this.newDoubleList(this.xPower, this.yPower, this.zPower));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("power", 9)) {
            ListTag listtag = pCompound.getList("power", 6);
            if (listtag.size() == 3) {
                this.xPower = listtag.getDouble(0);
                this.yPower = listtag.getDouble(1);
                this.zPower = listtag.getDouble(2);
            }
        }
    }

    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        Entity entity = this.getOwner();
        int i = entity == null ? 0 : entity.getId();
        return new ClientboundAddEntityPacket(this.getId(), this.getUUID(), this.getX(), this.getY(), this.getZ(), this.getXRot(), this.getYRot(), this.getType(), i, new Vec3(this.xPower, this.yPower, this.zPower), 0.0D);
    }

    @Override
    public void recreateFromPacket(@NotNull ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        double d0 = pPacket.getXa();
        double d1 = pPacket.getYa();
        double d2 = pPacket.getZa();
        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
        if (d3 != 0.0D) {
            this.xPower = d0 / d3 * 0.1D;
            this.yPower = d1 / d3 * 0.1D;
            this.zPower = d2 / d3 * 0.1D;
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        if (!this.level().isClientSide) {
            Entity pTarget = pResult.getEntity();
            Entity pAttacker = this.getOwner();
            pTarget.hurt(ModdedDamageTypes.BlasphemyProjectile(pAttacker != null ? pAttacker.level().registryAccess() : RegistryAccess.EMPTY, pAttacker, pTarget), this.pDamage);
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
