package net.stedee.plushie_test.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.plushie_test.enchantment.custom.BiggerFireballEnchantment;
import net.stedee.plushie_test.enchantment.custom.FireballAllAroundEnchantment;
import net.stedee.plushie_test.plushie_test;

public class ModdedEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, plushie_test.MOD_ID);

    public static RegistryObject<Enchantment> BIGGER_FIREBALL =
            ENCHANTMENTS.register("bigger_fireball",
                    () -> new BiggerFireballEnchantment(Enchantment.Rarity.UNCOMMON,
                            EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));

    public static RegistryObject<Enchantment> FIREBALL_ALL_AROUND =
            ENCHANTMENTS.register("fireball_all_around",
                    () -> new FireballAllAroundEnchantment(Enchantment.Rarity.UNCOMMON,
                            EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
