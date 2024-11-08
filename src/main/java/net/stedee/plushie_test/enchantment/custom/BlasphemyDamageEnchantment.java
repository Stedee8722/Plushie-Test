package net.stedee.plushie_test.enchantment.custom;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.stedee.plushie_test.item.custom.BlasphemyItem;
import org.jetbrains.annotations.NotNull;

public class BlasphemyDamageEnchantment extends Enchantment {
    public BlasphemyDamageEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof BlasphemyItem;
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment pOther) {
        return super.checkCompatibility(pOther);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack pStack) {
        return pStack.getItem() instanceof BlasphemyItem;
    }
}
