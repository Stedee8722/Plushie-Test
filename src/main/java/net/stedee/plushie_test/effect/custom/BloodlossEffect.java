package net.stedee.plushie_test.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class BloodlossEffect extends MobEffect {

    public BloodlossEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
        float maxHealth = (float) (pLivingEntity.getAttributeBaseValue(Attributes.MAX_HEALTH)-((pLivingEntity.getAttributeBaseValue(Attributes.MAX_HEALTH)*15/100)*(pAmplifier + 1)));
        if (!pLivingEntity.level().isClientSide()) {
            if (maxHealth < 2) {maxHealth = 2;}
            if (pLivingEntity.getHealth() > maxHealth) {
                //float health = pLivingEntity.getHealth();
                //int dur = Objects.requireNonNull(pLivingEntity.getEffect(ModdedEffects.BLOODLOSS.get())).getDuration();
                float amount = 0.05F + (float) pAmplifier / 8;
                pLivingEntity.hurt(pLivingEntity.damageSources().magic(), amount);
            } else {
                AttributeModifier modifier = new AttributeModifier(UUID.fromString("7d8cb6e3-213f-42c9-b905-706eeebeabb0"), "bloodloss_effect",  maxHealth - pLivingEntity.getAttributeBaseValue(Attributes.MAX_HEALTH), AttributeModifier.Operation.ADDITION);
                if (!pLivingEntity.getAttribute(Attributes.MAX_HEALTH).hasModifier(modifier)) {
                    Objects.requireNonNull(pLivingEntity.getAttribute(Attributes.MAX_HEALTH)).addPermanentModifier(modifier);
                }
            }
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
        Objects.requireNonNull(pLivingEntity.getAttribute(Attributes.MAX_HEALTH)).removePermanentModifier(UUID.fromString("7d8cb6e3-213f-42c9-b905-706eeebeabb0"));
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        int j = 25 >> pAmplifier;
        if (j > 0) {
            return pDuration % j == 0;
        } else {
            return true;
        }
    }
}
