package net.stedee.plushie_test.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeItem;
import net.stedee.plushie_test.advancement.ModdedAdvancements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.class)
public abstract class ItemMixin implements IForgeItem {
    @Unique
    ItemEntity plushie_test$itemEntity;

    @Override
    public Entity createEntity(Level level, Entity location, ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }

        this.plushie_test$itemEntity = new ItemEntity(level, location.getX(), location.getY(), location.getZ(), stack) {
            @Override
            protected void onBelowWorld() {
                this.getItem().onDestroyed(this, level.damageSources().fellOutOfWorld());
                super.onBelowWorld();
            }
        };

        if (location instanceof ItemEntity) {
            if (((ItemEntity) location).getOwner() != null) {
                this.plushie_test$itemEntity.setThrower(((ItemEntity) location).getOwner().getUUID());
            }
        }

        this.plushie_test$itemEntity.setDeltaMovement(location.getDeltaMovement());
        this.plushie_test$itemEntity.setPickUpDelay(40);
        return plushie_test$itemEntity;
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity, DamageSource damageSource) {
        if (itemEntity.getOwner() == null) {
            return;
        }
        ModdedAdvancements.PLAYER_DROP_ITEM.trigger(itemEntity.getOwner(), itemEntity, damageSource);
    }
}
