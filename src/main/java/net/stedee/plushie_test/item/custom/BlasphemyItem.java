package net.stedee.plushie_test.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.stedee.plushie_test.enchantment.ModdedEnchantments;
import net.stedee.plushie_test.entity.custom.BlasphemyProjectile;
import net.stedee.plushie_test.item.ModdedItems;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class BlasphemyItem extends SwordItem {
    float eDamage = 3;
    public BlasphemyItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties, float eDamage) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.eDamage = eDamage;
    }

    public BlasphemyItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if ((Minecraft.getInstance().hitResult != null ? Minecraft.getInstance().hitResult.getType() : null) != HitResult.Type.MISS) {
            return super.onEntitySwing(stack, entity);
        }
        if (!entity.level().isClientSide() && stack.is(ModdedItems.BLASPHEMY.get()) && (entity.getHealth() == entity.getMaxHealth() || ((Player)entity).isCreative())) {
            float pDamage = eDamage * (EnchantmentHelper.getEnchantmentLevel(ModdedEnchantments.BLASPHEMY_DAMAGE.get(), entity) + 1);

            Vec3 look = entity.getViewVector(1F);
            Entity blasphemy = makeProjectile(new BlasphemyProjectile(entity.level(), entity, 0, 0, 0, pDamage * ((Player)entity).getAttackStrengthScale(0)), entity, look);

            entity.level().addFreshEntity(blasphemy);
            stack.hurtAndBreak(1, entity, (pOnBroken) -> pOnBroken.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return false;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public Entity makeProjectile(Entity blasphemy, LivingEntity entity, Vec3 look) {
        blasphemy.moveTo(blasphemy.getX() + (look.x * 0.7), blasphemy.getY() + 1, blasphemy.getZ() + (look.z * 0.7));

        float pX = entity.getXRot();
        float pY = entity.getYRot();
        float pZ = 0F;

        float f = -Mth.sin(pY * ((float)Math.PI / 180F)) * Mth.cos(pX * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((pX + pZ) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(pY * ((float)Math.PI / 180F)) * Mth.cos(pX * ((float)Math.PI / 180F));

        Vec3 vec3 = (new Vec3(f, f1, f2)).normalize();
        blasphemy.setDeltaMovement(vec3);
        double d0 = vec3.horizontalDistance();

        blasphemy.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        blasphemy.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
        return blasphemy;
    }

    @SuppressWarnings("null")
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable(pStack.getDescriptionId() + ".tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack pToRepair, @NotNull ItemStack pRepair) {
        return Ingredient.of(Items.IRON_INGOT).test(pRepair);
    }
}
