package net.stedee.plushie_test.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
                    inventory.setItem(i, newItem);
                }
            }
        }
    }
}
