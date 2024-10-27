package net.stedee.plushie_test.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class MoonStaffItem extends SwordItem {


    public MoonStaffItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    //@SuppressWarnings("null")
    //@Override
    //public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
    //        pStack.hurtAndBreak(1, pAttacker, (pOnBroken) -> {
    //           pOnBroken.broadcastBreakEvent(EquipmentSlot.MAINHAND);
    //        });
    //        pTarget.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, 1));
    //        return true;
    //}

    @SuppressWarnings("null")
    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return super.getUseAnimation(pStack);
    }

    @SuppressWarnings("null")
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable(pStack.getDescriptionId() + ".tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @SuppressWarnings("null")
    @Override
    public boolean isValidRepairItem(@NotNull ItemStack pToRepair, @NotNull ItemStack pRepair) {
        return Ingredient.of(Items.IRON_INGOT).test(pRepair);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack pItem = pPlayer.getItemInHand(pUsedHand);
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1F, 1F);

        pPlayer.getCooldowns().addCooldown(this, 200);
        if (!pLevel.isClientSide) {
            Vec3 look = pPlayer.getViewVector(1F);
            LargeFireball fireball = new LargeFireball(pLevel, pPlayer, 0, 0, 0, 1);
            fireball.setPos(pPlayer.getX() + look.x * 0.5, pPlayer.getY() + 1.25, pPlayer.getZ() + look.z * 0.5);
            Vec3 vec3 = pPlayer.getLookAngle();
            fireball.setDeltaMovement(vec3);
            fireball.xPower = vec3.x * 0.1D;
            fireball.yPower = vec3.y * 0.1D;
            fireball.zPower = vec3.z * 0.1D;
            pLevel.addFreshEntity(fireball);
            pItem.hurtAndBreak(3, pPlayer, (pOnBroken) -> pOnBroken.broadcastBreakEvent(pUsedHand));
        }
        return InteractionResultHolder.sidedSuccess(pItem, pLevel.isClientSide());
    }
}
