package net.stedee.plushie_test.item.custom;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CleaverItem extends AxeItem {
    protected MobEffect effects;
    
    public CleaverItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, MobEffect effects, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.effects = effects;
    }

    @SuppressWarnings("null")
    @Override
    public boolean hurtEnemy(ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
            pStack.hurtAndBreak(1, pAttacker, (pOnBroken) -> pOnBroken.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            if (effects != null) {
                pTarget.addEffect(new MobEffectInstance(effects, 100, 1));
            }
            return true;
    }

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
        return Ingredient.of(Items.ROTTEN_FLESH).test(pRepair) || Ingredient.of(Items.IRON_INGOT).test(pRepair);
    }
}
