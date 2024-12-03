package net.stedee.plushie_test.inventory.custom.container;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.IntStream;

public class StationContainer implements Container, StackedContentsCompatible {
    private final Container container;
    private final AbstractContainerMenu menu;

    public StationContainer(Container container, AbstractContainerMenu menu) {
        this.container = container;
        this.menu = menu;
    }

    public List<ItemStack> getItems() {
        return IntStream.range(0, this.getContainerSize()).mapToObj(this::getItem).toList();
    }

    @Override
    public int getContainerSize() {
        return this.container.getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return this.container.isEmpty();
    }

    @Override
    public @NotNull ItemStack getItem(int index) {
        return this.container.getItem(index);
    }

    @Override
    public @NotNull ItemStack removeItem(int index, int count) {
        ItemStack itemstack = this.container.removeItem(index, count);
        if (!itemstack.isEmpty()) {
            this.menu.slotsChanged(this);
        }
        return itemstack;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int index) {
        return this.container.removeItemNoUpdate(index);
    }

    @Override
    public void setItem(int index, @NotNull ItemStack stack) {
        this.container.setItem(index, stack);
        this.menu.slotsChanged(this);
    }

    @Override
    public void setChanged() {
        this.container.setChanged();
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public void clearContent() {
        this.container.clearContent();
    }

    @Override
    public void fillStackedContents(@NotNull StackedContents helper) {
        for (int i = 0; i < this.getContainerSize(); i++) {
            helper.accountSimpleStack(this.getItem(i));
        }
    }
}
