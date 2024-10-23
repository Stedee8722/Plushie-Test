package net.stedee.plushie_test.inventory.custom;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class MultipleResultItemContainer implements Container, RecipeHolder {
    private final NonNullList<ItemStack> itemStacks = NonNullList.withSize(2, ItemStack.EMPTY);
    @Nullable
    private Recipe<?> recipeUsed;

    @Override
    public int getContainerSize() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.itemStacks) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int pSlot) {
        return this.itemStacks.get(pSlot);
    }

    @Override
    public @NotNull ItemStack removeItem(int pSlot, int pAmount) {
        return ContainerHelper.takeItem(this.itemStacks, pSlot);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int pSlot) {
        return ContainerHelper.takeItem(this.itemStacks, pSlot);
    }

    @Override
    public void setItem(int pSlot, @NotNull ItemStack pStack) {
        this.itemStacks.set(pSlot, pStack);
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return true;
    }

    @Override
    public void clearContent() {
        this.itemStacks.clear();
    }

    @Override
    public void setRecipeUsed(@org.jetbrains.annotations.Nullable Recipe<?> pRecipe) {
        this.recipeUsed = pRecipe;
    }

    @Override
    public @org.jetbrains.annotations.Nullable Recipe<?> getRecipeUsed() {
        return this.recipeUsed;
    }
}
