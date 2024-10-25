package net.stedee.plushie_test.inventory.custom.Alchemical;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.stedee.plushie_test.inventory.custom.TableInventoryPersistent;
import org.jetbrains.annotations.NotNull;

public class AlchemicalOutputSlot extends Slot {

    private final Player player;
    private final TableInventoryPersistent craftSlots;
    private final AbstractContainerMenu container;
    private final NonNullList<ItemStack> nonnulllist = NonNullList.withSize(1, ItemStack.EMPTY);
    private final Container resultContainer;
    private int removeCount;

    public AlchemicalOutputSlot(AbstractContainerMenu container, TableInventoryPersistent tableInventoryPersistent, Container resultContainer, int slotIndex, int xPosition, int yPosition, Player player) {
        //remove player if not needed
        super(resultContainer, slotIndex, xPosition, yPosition);
        this.container = container;
        this.resultContainer = resultContainer;
        this.player = player;
        this.craftSlots = tableInventoryPersistent;
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

        /* END OF CHANGE */
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);
    
        // note: craftMatrixPersistent and this.craftSlots are the same object!
        craftSlots.setDoNotCallUpdates(true);
    
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
    
        craftSlots.setDoNotCallUpdates(false);
        container.quickMoveStack(player, 37);
        container.quickMoveStack(player, 38);
        container.slotsChanged(craftSlots);

        //return craftingResult;
      }

    @SuppressWarnings("null")
    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return false;
    }
}
