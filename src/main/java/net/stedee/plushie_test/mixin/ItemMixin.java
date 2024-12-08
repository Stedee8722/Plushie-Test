package net.stedee.plushie_test.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.extensions.IForgeItem;
import net.stedee.plushie_test.advancement.ModdedAdvancements;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public abstract class ItemMixin implements IForgeItem {
    @SuppressWarnings("deprecation")
    @Override
    public void onDestroyed(ItemEntity pItemEntity, DamageSource damageSource) {
        Item item = (Item) (Object) this;
        item.onDestroyed(pItemEntity);
        ModdedAdvancements.PLAYER_DROP_ITEM.trigger(pItemEntity.getOwner(), pItemEntity, damageSource);
    }
}
