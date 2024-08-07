package net.stedee.plushie_test.inventory.custom;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ModdedItemHandler extends ItemStackHandler {

    public ModdedItemHandler(int size) {
        super(size);
    }

    public NonNullList<ItemStack> getContents() {
        return stacks;
    }

    public boolean isEmpty() {
        return getContents().stream().allMatch(ItemStack::isEmpty);
    }

}
