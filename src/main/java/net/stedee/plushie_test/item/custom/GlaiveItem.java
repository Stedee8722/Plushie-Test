package net.stedee.plushie_test.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.stedee.plushie_test.entity.custom.ThrownGlaive;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GlaiveItem extends TridentItem {
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public GlaiveItem(Properties pProperties) {
        super(pProperties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> $$1 = ImmutableMultimap.builder();
        $$1.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 14.0F, AttributeModifier.Operation.ADDITION));
        $$1.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -2.9F, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = $$1.build();
    }

    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player $$4) {
            int $$5 = this.getUseDuration(pStack) - pTimeLeft;
            if ($$5 >= 10) {
                int $$6 = EnchantmentHelper.getRiptide(pStack);
                if ($$6 <= 0 || $$4.isInWaterOrRain()) {
                    if (!pLevel.isClientSide) {
                        pStack.hurtAndBreak(1, $$4, (p_43388_) -> p_43388_.broadcastBreakEvent(pEntityLiving.getUsedItemHand()));
                        if ($$6 == 0) {
                            ThrownGlaive $$7 = new ThrownGlaive(pLevel, $$4, pStack);
                            $$7.shootFromRotation($$4, $$4.getXRot(), $$4.getYRot(), 0.0F, 2.5F + (float)$$6 * 0.5F, 1.0F);
                            if ($$4.getAbilities().instabuild) {
                                $$7.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                            }

                            pLevel.addFreshEntity($$7);
                            pLevel.playSound(null, $$7, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                            if (!$$4.getAbilities().instabuild) {
                                $$4.getInventory().removeItem(pStack);
                            }
                        }
                    }

                    $$4.awardStat(Stats.ITEM_USED.get(this));
                    if ($$6 > 0) {
                        float $$8 = $$4.getYRot();
                        float $$9 = $$4.getXRot();
                        float $$10 = -Mth.sin($$8 * ((float)Math.PI / 180F)) * Mth.cos($$9 * ((float)Math.PI / 180F));
                        float $$11 = -Mth.sin($$9 * ((float)Math.PI / 180F));
                        float $$12 = Mth.cos($$8 * ((float)Math.PI / 180F)) * Mth.cos($$9 * ((float)Math.PI / 180F));
                        float $$13 = Mth.sqrt($$10 * $$10 + $$11 * $$11 + $$12 * $$12);
                        float $$14 = 3.0F * ((1.0F + (float)$$6) / 4.0F);
                        $$10 *= $$14 / $$13;
                        $$11 *= $$14 / $$13;
                        $$12 *= $$14 / $$13;
                        $$4.push($$10, $$11, $$12);
                        $$4.startAutoSpinAttack(20);
                        if ($$4.onGround()) {
                            $$4.move(MoverType.SELF, new Vec3(0.0F, 1.1999999F, 0.0F));
                        }

                        SoundEvent $$16;
                        if ($$6 >= 3) {
                            $$16 = SoundEvents.TRIDENT_RIPTIDE_3;
                        } else if ($$6 == 2) {
                            $$16 = SoundEvents.TRIDENT_RIPTIDE_2;
                        } else {
                            $$16 = SoundEvents.TRIDENT_RIPTIDE_1;
                        }

                        pLevel.playSound(null, $$4, $$16, SoundSource.PLAYERS, 1.0F, 1.0F);
                    }
                }
            }
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.CHANNELING) {
            return false;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable(pStack.getDescriptionId() + ".tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pStack.getTag() != null && !pStack.getTag().contains("Unbreakable")) {
            pStack.addTagElement("Unbreakable", IntTag.valueOf(1));
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }
}
