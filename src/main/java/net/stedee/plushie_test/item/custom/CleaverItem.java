package net.stedee.plushie_test.item.custom;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.stedee.plushie_test.effect.ModdedEffects;
import net.stedee.plushie_test.effect.custom.BloodlossEffect;
import net.stedee.plushie_test.enchantment.ModdedEnchantments;
import org.jetbrains.annotations.NotNull;

public class CleaverItem extends AxeItem {
    protected MobEffect[] effects;
    protected boolean isUnbreakable;
    
    public CleaverItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties, boolean isUnbreakable, MobEffect... effects) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.effects = effects;
        this.isUnbreakable = isUnbreakable;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (isUnbreakable && pStack.getTag() != null && !pStack.getTag().contains("Unbreakable")) {
            pStack.addTagElement("Unbreakable", IntTag.valueOf(1));
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @SuppressWarnings("null")
    @Override
    public boolean hurtEnemy(ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
            pStack.hurtAndBreak(1, pAttacker, (pOnBroken) -> pOnBroken.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            if (effects != null) {
                Arrays.stream(effects).forEach((effect) -> {
                    if (!(effect instanceof BloodlossEffect)) {
                        pTarget.addEffect(new MobEffectInstance(effect, 100, 0));
                    } else {
                        MobEffectInstance mobEffect = pTarget.getEffect(ModdedEffects.BLOODLOSS.get());
                        if (mobEffect != null) {
                            pTarget.addEffect(new MobEffectInstance(effect, 1200, Math.min(mobEffect.getAmplifier() + 1, EnchantmentHelper.getEnchantmentLevel(ModdedEnchantments.SERRATED.get(), pAttacker))));
                        } else {
                            pTarget.addEffect(new MobEffectInstance(effect, 1200, 0));
                        }
                    }
                });
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
