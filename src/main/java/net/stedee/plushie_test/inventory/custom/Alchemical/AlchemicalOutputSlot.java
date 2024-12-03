package net.stedee.plushie_test.inventory.custom.alchemical;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.stedee.plushie_test.inventory.custom.container.StationContainer;
import net.stedee.plushie_test.recipe.ModdedRecipes;
import org.jetbrains.annotations.NotNull;

public class AlchemicalOutputSlot extends Slot {
    private final Player player;
    private final StationContainer craftSlots;
    public final AbstractContainerMenu container;
    private int removeCount;
    private final Container resultContainer;

    public AlchemicalOutputSlot(AbstractContainerMenu container, StationContainer stationContainer, Container resultContainer, int slotIndex, int xPosition, int yPosition, Player player) {
        //remove player if not needed
        super(resultContainer, slotIndex, xPosition, yPosition);
        this.container = container;
        this.resultContainer = resultContainer;
        this.player = player;
        this.craftSlots = stationContainer;
    }

    @Override
    protected void checkTakeAchievements(@NotNull ItemStack itemStack) {
        if (this.removeCount > 0) {
            itemStack.onCraftedBy(this.player.level(), this.player, this.removeCount);
        }
        if (resultContainer instanceof RecipeHolder recipe) {

            recipe.awardUsedRecipes(this.player, this.craftSlots.getItems());
        }

        this.removeCount = 0;
    }

    @SuppressWarnings("null")
    @Override
    public void onTake(@NotNull Player thePlayer, @NotNull ItemStack craftingResult) {
        this.checkTakeAchievements(craftingResult);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(thePlayer);
        /* CHANGE BEGINS HERE */
        NonNullList<ItemStack> nonnulllist = thePlayer.level().getRecipeManager().getRemainingItemsFor(ModdedRecipes.SEAMSTRESS_RECIPE.get(), this.craftSlots, thePlayer.level());
        /* END OF CHANGE */
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);

        // note: craftMatrixPersistent and this.craftSlots are the same object!

        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack stackInSlot = this.craftSlots.getItem(i);
            ItemStack stack1 = nonnulllist.get(i);

            if (!stackInSlot.isEmpty()) {
                this.craftSlots.removeItem(i, 1);
                stackInSlot = this.craftSlots.getItem(i);
            }

            if (!stack1.isEmpty()) {
                if (stackInSlot.isEmpty()) {
                    this.craftSlots.setItem(i, stack1);
                } else if (ItemStack.isSameItemSameTags(stackInSlot,stack1)) {
                    stack1.grow(stackInSlot.getCount());
                    this.craftSlots.setItem(i, stack1);
                } else if (!this.player.getInventory().add(stack1)) {
                    this.player.drop(stack1, false);
                }
            }
        }

        container.slotsChanged(craftSlots);

        //return craftingResult;
    }

    @SuppressWarnings("null")
    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public @NotNull ItemStack remove(int $$0) {
        if (this.hasItem()) {
            this.removeCount += Math.min($$0, this.getItem().getCount());
        }

        return super.remove($$0);
    }

    @Override
    protected void onQuickCraft(@NotNull ItemStack $$0, int $$1) {
        this.removeCount += $$1;
        this.checkTakeAchievements($$0);
    }

    @Override
    protected void onSwapCraft(int $$0) {
        this.removeCount += $$0;
    }
}
