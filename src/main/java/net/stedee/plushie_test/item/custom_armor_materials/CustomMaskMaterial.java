package net.stedee.plushie_test.item.custom_armor_materials;

import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.stedee.plushie_test.plushie_test;
import org.jetbrains.annotations.NotNull;

public enum CustomMaskMaterial implements ArmorMaterial {
    MASK_VAPOREON_MATS("mask_vaporeon_mats", 37, new int[]{ 3, 6, 8, 3 }, 15,
            SoundEvents.ARMOR_EQUIP_GENERIC, 3f, 0.1f, () -> Ingredient.of(Items.HEART_OF_THE_SEA)),

    MASK_ESPEON_MATS("mask_espeon_mats", 37, new int[]{ 3, 6, 8, 3 }, 15,
            SoundEvents.ARMOR_EQUIP_GENERIC, 3f, 0.1f, () -> Ingredient.of(Items.CLOCK)),

    MASK_FLAREON_MATS("mask_flareon_mats", 37, new int[]{ 3, 6, 8, 3 }, 15,
            SoundEvents.ARMOR_EQUIP_GENERIC, 3f, 0.1f, () -> Ingredient.of(Items.FIRE_CHARGE)),

    MASK_GLACEON_MATS("mask_glaceon_mats", 37, new int[]{ 3, 6, 8, 3 }, 15,
            SoundEvents.ARMOR_EQUIP_GENERIC, 3f, 0.1f, () -> Ingredient.of(Items.ICE)),

    MASK_EEVEE_MATS("mask_eevee_mats", 15, new int[]{ 2, 5, 6, 2 }, 9,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f, () -> Ingredient.of(Items.LEATHER));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = { 11, 16, 16, 13 };

    CustomMaskMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound,
                      float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @SuppressWarnings("null")
    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return BASE_DURABILITY[pType.ordinal()] * this.durabilityMultiplier;
    }

    @SuppressWarnings("null")
    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionAmounts[pType.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public @NotNull String getName() {
        return plushie_test.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

}
