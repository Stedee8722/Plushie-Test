package net.stedee.plushie_test.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.ItemStack;
import net.stedee.plushie_test.access.IThunderBolt;
import net.stedee.plushie_test.item.ModdedItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;

@Mixin(LightningBolt.class)
public abstract class LightningBoltMixin implements IThunderBolt {
    @Unique
    ItemStack plushieTest$causeItem;

    @Override
    public void plushieTest$setCauseItem(@Nullable ItemStack pCause) {
        this.plushieTest$causeItem = pCause;
    }

    @Unique
    public ItemStack plushieTest$getCauseItem() {
        return this.plushieTest$causeItem;
    }

    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;thunderHit(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LightningBolt;)V", ordinal = 0))
    public void moddedTick(Entity pEntity, ServerLevel pLevel, LightningBolt pLightning) {
        LightningBolt lightningBolt = (LightningBolt) (Object) this;
        ServerPlayer player = lightningBolt.getCause();
        if ((pEntity instanceof ServerPlayer) && pEntity == player && plushieTest$getCauseItem() != null && plushieTest$getCauseItem().is(ModdedItems.ELECTROSTORM_GLAIVE.get())) {
            return;
        }
        pEntity.thunderHit(pLevel, pLightning);    }
}