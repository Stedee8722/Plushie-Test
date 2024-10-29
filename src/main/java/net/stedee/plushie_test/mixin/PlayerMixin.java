package net.stedee.plushie_test.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.stedee.plushie_test.item.ModdedItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Shadow public abstract Inventory getInventory();

    @Redirect(method = "isInvulnerableTo(Lnet/minecraft/world/damagesource/DamageSource;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z", ordinal = 3))
    public boolean moddedIsVulnerableto(GameRules instance, GameRules.Key<GameRules.BooleanValue> pKey) {
        PlayerMixin player = (PlayerMixin) (Object) this;
        return instance.getBoolean(pKey) && !player.getInventory().getArmor(3).is(ModdedItems.MASK_GLACEON.get());
    }
}
