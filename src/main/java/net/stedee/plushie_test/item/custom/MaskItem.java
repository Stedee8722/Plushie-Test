package net.stedee.plushie_test.item.custom;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.stedee.plushie_test.item.custom_armor_materials.CustomMaskMaterial;

public class MaskItem extends ArmorItem {

    private final MobEffect effect;

    public MaskItem(Properties pProperties, CustomMaskMaterial mats, MobEffect effect) {
        super(mats, net.minecraft.world.item.ArmorItem.Type.HELMET, pProperties);
        this.effect = effect;
    }

    @SuppressWarnings("null")
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable(pStack.getDescriptionId() + ".tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @SuppressWarnings("removal")
    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (effect != null) {
            player.addEffect(new MobEffectInstance(effect, 2, 0, false, false, true));
        }
        super.onArmorTick(stack, level, player);
    }

}
