package net.stedee.plushie_test.inventory.custom;

import javax.annotation.Nonnull;

import java.util.stream.IntStream;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;

public class SeamstressInventoryPersistent extends TransientCraftingContainer {

    protected final SeamstressTableItemHandler inv;
    private boolean doNotCallUpdates;
    private AbstractContainerMenu menu;

    public SeamstressInventoryPersistent(AbstractContainerMenu pMenu, SeamstressTableItemHandler itemHandler, int pWidth, int pHeight) {
        super(pMenu, pWidth, pHeight);
        this.inv = itemHandler;
        this.menu = pMenu;
        doNotCallUpdates = false;
    }
    
    @Nonnull
    @Override
    public ItemStack getItem(int slot) {
        validate(slot);
        return inv.getStackInSlot(slot);
    }

    private void validate(int slot) {
        if (isValid(slot)) return;
        throw new IndexOutOfBoundsException("Someone attempted to poll an outofbounds stack at slot " +
        slot + " report to them, NOT Crafting Station");
    }

    public boolean isValid(int slot){
        return slot >= 0 && slot < getContainerSize();
    }

    @Nonnull
    @Override
    public ItemStack removeItem(int slot, int count) {
        validate(slot);
        ItemStack stack = inv.extractItem(slot,count,false);
        if (!stack.isEmpty())
        onCraftMatrixChanged();
        return stack;
    }

    @Override
    public void setItem(int slot,@Nonnull ItemStack stack) {
        validate(slot);
        inv.setStackInSlot(slot, stack);
        onCraftMatrixChanged();
    }

    @Nonnull
    @Override
    public ItemStack removeItemNoUpdate(int index) {
        validate(index);
        ItemStack s = getItem(index);
        if(s.isEmpty()) return ItemStack.EMPTY;
        onCraftMatrixChanged();
        setItem(index, ItemStack.EMPTY);
        return s;
    }

    public NonNullList<ItemStack> getStackList(){
        return inv.getContents();
    }

    @Override
    public boolean isEmpty() {
        return IntStream.range(0, inv.getSlots()).allMatch(i -> inv.getStackInSlot(i).isEmpty());
    }

    @Override
    public void clearContent(){
        //dont
    }

    public void setDoNotCallUpdates(boolean doNotCallUpdates) {
        this.doNotCallUpdates = doNotCallUpdates;
    }
    
    public void onCraftMatrixChanged() {
        if(!doNotCallUpdates) {
            this.menu.slotsChanged(this);
        }
    }
}
