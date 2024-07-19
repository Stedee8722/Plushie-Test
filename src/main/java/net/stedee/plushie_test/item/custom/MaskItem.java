package net.stedee.plushie_test.item.custom;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class MaskItem extends Item implements Equipable {

    public MaskItem(Properties pProperties) {
        super(pProperties);
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
        return Ingredient.of(new ItemLike[]{Items.SALMON}).test(pRepair) || Ingredient.of(new ItemLike[]{Items.ENDER_EYE}).test(pRepair);
    }

    @SuppressWarnings("removal")
    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 2, 0, false, false, true));
        super.onArmorTick(stack, level, player);
    }

    @SuppressWarnings("null")
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return this.swapWithEquipmentSlot(this, pLevel, pPlayer, pUsedHand);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_TURTLE;
    }
}
