package net.stedee.plushie_test.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
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
}
