package net.stedee.plushie_test.item.custom;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.stedee.plushie_test.sound.plushies_sounds;

public class plushies_item extends Item implements Equipable {

    protected final boolean canSqueak;

    public plushies_item(Boolean canSqueak) {
        super(new Item.Properties()
            .stacksTo(1)
            .rarity(Rarity.EPIC)
        );
        this.canSqueak = canSqueak;
    }

    @SuppressWarnings("null")
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable(pStack.getDescriptionId() + ".tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @SuppressWarnings("null")
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack pItem = pPlayer.getItemInHand(pUsedHand);
        if (this.canSqueak) {
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), plushies_sounds.PLUSHIE_USE.get(), SoundSource.NEUTRAL, 1f, 1f);
            return InteractionResultHolder.consume(pItem);
        }
        else {
            return this.swapWithEquipmentSlot(this, pLevel, pPlayer, pUsedHand);
        }
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
