package net.stedee.plushie_test.inventory.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SeamstressItemHandler extends SlotItemHandler {

    private boolean isInput;

    public SeamstressItemHandler(IItemHandler itemHandler, int pSlot, int pX, int pY, boolean isInput) {
        super(itemHandler, pSlot, pX, pY);
        this.isInput = isInput;
    }

    @Override
    public boolean mayPlace(ItemStack pStack) {
        return this.isInput;
    }

}
