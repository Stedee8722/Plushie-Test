package net.stedee.plushie_test.enchantment.custom;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.stedee.plushie_test.item.ModdedItems;
import org.jetbrains.annotations.NotNull;

public class BiggerFireballEnchantment extends Enchantment {
    public BiggerFireballEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.is(ModdedItems.MOON_STAFF.get());
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment pOther) {
        return !(pOther instanceof FireballAllAroundEnchantment);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack pStack) {
        return pStack.is(ModdedItems.MOON_STAFF.get());
    }
}