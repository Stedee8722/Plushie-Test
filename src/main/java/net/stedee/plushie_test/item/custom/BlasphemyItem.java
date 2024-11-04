package net.stedee.plushie_test.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.Vec3;
import net.stedee.plushie_test.entity.custom.BlasphemyProjectile;

public class BlasphemyItem extends SwordItem {
    public BlasphemyItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (Minecraft.getInstance().crosshairPickEntity instanceof LivingEntity) {
            return super.onEntitySwing(stack, entity);
        }
        if (!entity.level().isClientSide()) {
            Vec3 look = entity.getViewVector(1F);
            Entity blasphemy = new BlasphemyProjectile(entity.level(), entity, look.x * 0.1, look.y * 0.1, look.z * 0.1);
            blasphemy.moveTo(blasphemy.getX() + (look.x * 0.7), blasphemy.getY() + 1, blasphemy.getZ() + (look.z * 0.7));
            entity.level().addFreshEntity(blasphemy);
        }
        return super.onEntitySwing(stack, entity);
    }


}
