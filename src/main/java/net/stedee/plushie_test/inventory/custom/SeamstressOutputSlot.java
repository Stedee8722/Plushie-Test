package net.stedee.plushie_test.inventory.custom;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;

public class SeamstressOutputSlot extends ResultSlot {
    
    private final SeamstressTableMenu container;
    protected SeamstressInventoryPersistent seamstressInventoryPersistent;
    private Player player;
    private SeamstressInventoryPersistent craftSlots;

    public SeamstressOutputSlot(SeamstressTableMenu container, SeamstressInventoryPersistent seamstressInventoryPersistent, Container resultInventory, int slotIndex, int xPosition, int yPosition, Player player) {
        super(player, seamstressInventoryPersistent, resultInventory, slotIndex, xPosition, yPosition);
        this.container = container;
        this.seamstressInventoryPersistent = seamstressInventoryPersistent;
        this.player = player;
        this.craftSlots = seamstressInventoryPersistent;
    }

    @SuppressWarnings("null")
    @Override
    public void onTake(Player thePlayer, ItemStack craftingResult) {
        //this.checkTakeAchievements(craftingResult);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(thePlayer);
        /* CHANGE BEGINS HERE */
        NonNullList<ItemStack> nonnulllist = container.getRemainingItems();
        /* END OF CHANGE */
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);
    
        // note: craftMatrixPersistent and this.craftSlots are the same object!
        seamstressInventoryPersistent.setDoNotCallUpdates(true);
    
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
    
        seamstressInventoryPersistent.setDoNotCallUpdates(false);
        container.slotsChanged(seamstressInventoryPersistent);
    
        //return craftingResult;
      }

    @SuppressWarnings("null")
    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return true;
    }
}
