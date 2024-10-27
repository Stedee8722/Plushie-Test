package net.stedee.plushie_test.item.custom;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.stedee.plushie_test.sound.ModdedSounds;
import org.jetbrains.annotations.NotNull;

public class PlushiesItem extends Item implements Equipable {

    protected final boolean canSqueak;
    protected boolean squeakUsed = false;

    public PlushiesItem(Properties properties, Boolean canSqueak) {
        super(properties);
        this.canSqueak = canSqueak;
    }

    @SuppressWarnings("null")
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable(pStack.getDescriptionId() + ".tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @SuppressWarnings("null")
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack pItem = pPlayer.getItemInHand(pUsedHand);
        if (this.canSqueak) {
            if (!this.squeakUsed && !pLevel.isClientSide) {
                pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModdedSounds.PLUSHIE_USE.get(), SoundSource.NEUTRAL, 1f, 1f);
                this.squeakUsed = true;
                pPlayer.startUsingItem(pUsedHand);
                return InteractionResultHolder.consume(pItem);
            }
            return InteractionResultHolder.consume(pItem);
        }
        else {
            return this.swapWithEquipmentSlot(this, pLevel, pPlayer, pUsedHand);
        }
    }

    @SuppressWarnings("null")
    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 100000;
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @SuppressWarnings("null")
    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, int pTimeCharged) {
        this.squeakUsed = false;
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    @Override
    public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
        this.squeakUsed = false;
        super.onStopUsing(stack, entity, count);
    }
}
