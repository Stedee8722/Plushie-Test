package net.stedee.plushie_test.inventory.custom.Alchemical;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.inventory.custom.TableInventoryPersistent;

public class AlchemicalOutputSlot extends Slot {
    
    private final AlchemicalTableMenu container;
    protected TableInventoryPersistent tableInventoryPersistent;
    private Player player;
    private TableInventoryPersistent craftSlots;

    public AlchemicalOutputSlot(AlchemicalTableMenu container, TableInventoryPersistent tableInventoryPersistent, int slotIndex, int xPosition, int yPosition, Player player) {
        //remove player if not needed
        super(tableInventoryPersistent, slotIndex, xPosition, yPosition);
        this.container = container;
        this.tableInventoryPersistent = tableInventoryPersistent;
        this.player = player;
        this.craftSlots = tableInventoryPersistent;
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
        tableInventoryPersistent.setDoNotCallUpdates(true);
    
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
    
        tableInventoryPersistent.setDoNotCallUpdates(false);
        container.slotsChanged(tableInventoryPersistent);
    
        //return craftingResult;
      }

    @SuppressWarnings("null")
    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        plushie_test.LOGGER.debug("Trying to place: " + container.tileEntity.fromResult);
        return container.tileEntity.fromResult;
    }
}
