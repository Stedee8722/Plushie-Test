package net.stedee.plushie_test.mixin;

import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.stedee.plushie_test.item.ModdedItems;
import net.stedee.plushie_test.item.custom.MaskItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin {

    @Redirect(method = "createResult()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 0))
    public boolean moddedCreateResult(ItemStack itemStack1, Item item2) {
        return itemStack1.is(item2) || ((itemStack1.getItem() instanceof MaskItem) && (new ItemStack(item2).is(ModdedItems.MASK_EEVEE.get())));
    }
}
