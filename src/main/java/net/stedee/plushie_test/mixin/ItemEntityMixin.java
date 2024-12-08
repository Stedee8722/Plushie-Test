package net.stedee.plushie_test.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void onBelowWorld() {
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        itemEntity.getItem().onDestroyed(itemEntity, itemEntity.level().damageSources().fellOutOfWorld());
        super.onBelowWorld();
    }
}
