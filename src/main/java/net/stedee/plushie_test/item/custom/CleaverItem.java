package net.stedee.plushie_test.item.custom;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class CleaverItem extends AxeItem {

    
    public CleaverItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @SuppressWarnings("null")
    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
            pStack.hurtAndBreak(1, pAttacker, (pOnBroken) -> {
               pOnBroken.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
            pTarget.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, 1));
            return true;
    }

    @SuppressWarnings("null")
    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return super.getUseAnimation(pStack);
    }

    @SuppressWarnings("null")
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable(pStack.getDescriptionId() + ".tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @SuppressWarnings("null")
    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return Ingredient.of(new ItemLike[]{Items.ROTTEN_FLESH}).test(pRepair) || Ingredient.of(new ItemLike[]{Items.IRON_INGOT}).test(pRepair);
    }
}
