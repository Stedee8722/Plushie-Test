package net.stedee.plushie_test.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.stedee.plushie_test.mixin.ITridentAccessor;
import net.stedee.plushie_test.entity.custom.ThrownGlaive;
import net.stedee.plushie_test.item.ModdedItems;

import static net.stedee.plushie_test.plushie_test.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GlaiveReplaceTridentEvent {
    @SubscribeEvent
    public static void StruckByLightningEvent(EntityStruckByLightningEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            Inventory inventory = player.getInventory();
            for (int i = 0; i < 41; ++i) {
                ItemStack item = inventory.getItem(i);
                if (item.is(Items.TRIDENT)) {
                    ItemStack newItem = new ItemStack(ModdedItems.ELECTROSTORM_GLAIVE.get(), 1);
                    assert item.getTag() != null;
                    newItem.setTag(item.getTag().copy());
                    ListTag enchantmentTags = newItem.getEnchantmentTags();
                    for (int j = 0; j < enchantmentTags.size(); j++) {
                        CompoundTag enchantmentTag = enchantmentTags.getCompound(j);
                        if (enchantmentTag.getString("id").contains("channeling")) {
                            enchantmentTags.remove(j);
                            break;
                        }
                    }
                    inventory.setItem(i, newItem);
                }
            }
        }
        if (event.getEntity() instanceof ThrownTrident trident) {
            ItemStack newItem = new ItemStack(ModdedItems.ELECTROSTORM_GLAIVE.get(), 1);
            CompoundTag tag = ((ITridentAccessor) trident).getTridentItem().getTag();
            assert tag != null;
            newItem.setTag(tag.copy());
            ListTag enchantmentTags = newItem.getEnchantmentTags();
            for (int i = 0; i < enchantmentTags.size(); i++) {
                CompoundTag enchantmentTag = enchantmentTags.getCompound(i);
                if (enchantmentTag.getString("id").contains("channeling")) {
                    return;
                }
            }
            ThrownGlaive entity = new ThrownGlaive(trident.level(), (LivingEntity) trident.getOwner(), newItem);
            entity.copyPosition(trident);
            trident.level().addFreshEntity(entity);
            trident.discard();
        }
    }
}
