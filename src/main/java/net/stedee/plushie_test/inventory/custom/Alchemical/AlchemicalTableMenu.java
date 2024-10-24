package net.stedee.plushie_test.inventory.custom.Alchemical;

import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkDirection;
import net.stedee.plushie_test.block.ModdedBlocks;
import net.stedee.plushie_test.block.custom.AlchemicalTableBlockEntity;
import net.stedee.plushie_test.inventory.ModdedMenuTypes;
import net.stedee.plushie_test.inventory.custom.TableInventoryPersistent;
import net.stedee.plushie_test.item.custom.PlushiesItem;
import net.stedee.plushie_test.network.PacketHandler;
import net.stedee.plushie_test.network.S2CLastRecipePacket;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.recipe.ModdedRecipes;
import net.stedee.plushie_test.recipe.custom.SeamstressRecipe;

import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;

public class AlchemicalTableMenu extends AbstractContainerMenu {

    private final Container craftResult;
    private final ContainerLevelAccess access;
    private final Player player;
    public SeamstressRecipe lastRecipe;
    private final Level world;
    public AlchemicalTableBlockEntity tileEntity;
    public final TableInventoryPersistent craftMatrix;
    protected SeamstressRecipe lastLastRecipe;

    public AlchemicalTableMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    @SuppressWarnings("null")
    public AlchemicalTableMenu(int id, Inventory inv, BlockEntity entity) {
        super(ModdedMenuTypes.ALCHEMICAL_MENU_TYPE.get(), id);
        this.player = inv.player;
        this.world = player.level();
        if (entity instanceof AlchemicalTableBlockEntity be) {
            this.tileEntity = be;
        }
        else {
            throw new IllegalStateException("Incorrect block entity class (%s) passed into AlchemicalTableMenu".formatted(entity.getClass().getCanonicalName()));
        }

        this.craftMatrix = new TableInventoryPersistent(this, tileEntity.inventory, 1, 1);
        this.craftResult = tileEntity.craftResult;
        this.access = ContainerLevelAccess.create(Objects.requireNonNull(tileEntity.getLevel()), tileEntity.getBlockPos());

        layoutPlayerInventorySlots(player.getInventory(), 8, 94);

        this.addSlot(new Slot(craftMatrix, 0, 80, 19) {
            @Override
            public void setChanged() {
                slotsChanged(inv);
                super.setChanged();
            }

            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                if (!craftResult.isEmpty() && craftMatrix.isEmpty()) {
                    quickMoveStack(player, 37);
                    quickMoveStack(player, 38);
                }
                return stack.getItem() instanceof PlushiesItem;
            }
        });
        //this.addSlot(new Slot(craftMatrix, 1, 76, 31){
        //    @Override
        //    public void setChanged() {
        //        slotsChanged(inv);
        //        super.setChanged();
        //    }
        //    @Override
        //    public boolean mayPlace(@NotNull ItemStack stack) {
        //        return true;
        //    }
        //});
        this.addSlot(new AlchemicalOutputSlot(this, this.craftMatrix, this.craftResult, 0, 55, 59, player));
        this.addSlot(new AlchemicalOutputSlot(this, this.craftMatrix, this.craftResult, 1, 104, 59, player));
        slotsChanged(craftMatrix);
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 3;  // must be the number of slots you have!
    @SuppressWarnings("null")
    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex: " + pIndex + "\n check 1 " + (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) + "\ncheck 2 " + (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT));
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @SuppressWarnings("null")
    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(this.access, player, ModdedBlocks.ALCHEMICAL_TABLE.get());
    }

    private int addSlotRange(Container playerInventory, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new Slot(playerInventory, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(Container playerInventory, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(playerInventory, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(Container playerInventory, int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    @SuppressWarnings("null")
    @Override
    public void slotsChanged(@NotNull Container pContainer) {
        world.sendBlockUpdated(tileEntity.getBlockPos(), tileEntity.getBlockState(), tileEntity.getBlockState(), 2);
        tileEntity.setChanged();
        this.slotChangedCraftingGrid(world, player, craftMatrix, craftResult);
    }


    protected void slotChangedCraftingGrid(Level world, Player player, CraftingContainer inv, Container result) {
        ItemStack itemstack_1 = ItemStack.EMPTY;
        ItemStack itemstack_2 = ItemStack.EMPTY;

        // if the recipe is no longer valid, update it
        if (lastRecipe == null || !lastRecipe.getResultItem(RegistryAccess.EMPTY).is(inv.getItem(0).getItem())) {
            lastRecipe = (SeamstressRecipe) searchRecipe(inv.getItem(0), world.getRecipeManager());
            plushie_test.LOGGER.debug("Last recipe: " + lastRecipe);
        }

        // if we have a recipe, fetch its result
        if (lastRecipe != null) {
            itemstack_1 = lastRecipe.getInputItem(0);
            itemstack_2 = lastRecipe.getInputItem(1);
        }
        // set the slot on both sides, client is for display/so the client knows about the recipe
        result.setItem(0, itemstack_1);
        result.setItem(1, itemstack_2);

        // update recipe on server
        if (!world.isClientSide) {
            ServerPlayer entityplayermp = (ServerPlayer) player;

            // we need to sync to all players currently in the inventory
            List<ServerPlayer> relevantPlayers = getAllPlayersWithThisContainerOpen(this, entityplayermp.serverLevel());

            // sync result to all serverside inventories to prevent duplications/recipes being blocked
            // need to do this every time as otherwise taking items of the result causes desync
            syncResultToAllOpenWindows(itemstack_1, itemstack_2, relevantPlayers);

            // if the recipe changed, update clients last recipe
            // this also updates the client side display when the recipe is added
            if (lastLastRecipe != lastRecipe) {
                syncRecipeToAllOpenWindows(lastRecipe, relevantPlayers);
                lastLastRecipe = lastRecipe;
            }
        }
    }
    
    private void syncRecipeToAllOpenWindows(final SeamstressRecipe lastRecipe, List<ServerPlayer> players) {
        players.forEach(otherPlayer -> {
            // safe cast since hasSameContainerOpen does class checks
            ((AlchemicalTableMenu) otherPlayer.containerMenu).lastRecipe = lastRecipe;
            PacketHandler.INSTANCE.sendTo(new S2CLastRecipePacket(lastRecipe), otherPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        });
    }

    private void syncResultToAllOpenWindows(final ItemStack stack_1, final ItemStack stack_2, List<ServerPlayer> players) {
        this.craftMatrix.setDoNotCallUpdates(true);
        players.forEach(otherPlayer -> {
            otherPlayer.containerMenu.setItem(37, this.getStateId(), stack_1);
            otherPlayer.containerMenu.setItem(38, this.getStateId(), stack_2);
            //otherPlayer.connection.sendPacket(new SPacketSetSlot(otherPlayer.openContainer.windowId, SLOT_RESULT, stack));
        });
        this.craftMatrix.setDoNotCallUpdates(false);
    }

    public List<ServerPlayer> getAllPlayersWithThisContainerOpen(AlchemicalTableMenu container, ServerLevel server) {
        return server.players().stream()
                .filter(player -> hasSameContainerOpen(container, player))
                .collect(Collectors.toList());
    }

    private boolean hasSameContainerOpen(AlchemicalTableMenu container, ServerPlayer playerToCheck) {
        return playerToCheck instanceof ServerPlayer &&
                playerToCheck.containerMenu.getClass().isAssignableFrom(container.getClass()) &&
                this.sameGui((AlchemicalTableMenu) playerToCheck.containerMenu);
    }

    public boolean sameGui(AlchemicalTableMenu otherContainer) {
        return this.tileEntity == otherContainer.tileEntity;
    }

    @Override
    protected boolean moveItemStackTo(@Nonnull ItemStack stack, int startIndex, int endIndex, boolean useEndIndex) {
        boolean didSomething = mergeItemStackRefill(stack, startIndex, endIndex);
        if (!stack.isEmpty()) didSomething |= mergeItemStackMove(stack, startIndex, endIndex);
        return didSomething;
    }

    private boolean mergeItemStackMove(ItemStack stack, int startIndex, int endIndex) {
        if (stack.isEmpty()) return false;

        boolean didSomething = false;

        for (int k = startIndex; k < endIndex; k++) {
            Slot targetSlot = this.slots.get(k);
            ItemStack slotStack = targetSlot.getItem();

            if (slotStack.isEmpty() && targetSlot.mayPlace(stack) && this.canTakeItemForPickAll(stack, targetSlot)) // Forge: Make sure to respect isItemValid in the slot.
            {
                int limit = targetSlot.getMaxStackSize(stack);
                ItemStack stack2 = stack.copy();
                if (stack2.getCount() > limit) {
                    stack2.setCount(limit);
                    stack.shrink(limit);
                } else {
                    stack.setCount(0);
                }
                targetSlot.set(stack2);
                targetSlot.setChanged();
                didSomething = true;

                if (stack.isEmpty()) {
                    break;
                }
            }
        }
        return didSomething;
    }

    private boolean mergeItemStackRefill(ItemStack stack, int startIndex, int endIndex) {
        if (stack.isEmpty()) return false;

        boolean didSomething = false;

        Slot targetSlot;
        ItemStack slotStack;

        if (stack.isStackable()) {

            for (int k = startIndex; k < endIndex; k++) {
                if (stack.isEmpty()) break;
                targetSlot = this.slots.get(k);
                slotStack = targetSlot.getItem();

                if (!slotStack.isEmpty()
                        && ItemStack.isSameItemSameTags(stack, slotStack)
                        && this.canTakeItemForPickAll(stack, targetSlot)) {
                    int l = slotStack.getCount() + stack.getCount();
                    int limit = targetSlot.getMaxStackSize(stack);

                    if (l <= limit) {
                        stack.setCount(0);
                        slotStack.setCount(l);
                        targetSlot.setChanged();
                        didSomething = true;
                    } else if (slotStack.getCount() < limit) {
                        stack.shrink(limit - slotStack.getCount());
                        slotStack.setCount(limit);
                        targetSlot.setChanged();
                        didSomething = true;
                    }
                }
            }
        }
        return didSomething;
    }

    @SuppressWarnings("null")
    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != craftResult && super.canTakeItemForPickAll(stack, slot);
    }

    public void updateLastRecipeFromServer(SeamstressRecipe r) {
        lastRecipe = r;
        // if no recipe, set to empty to prevent ghost outputs when another player grabs the result
        this.craftResult.setItem(0, r != null ? r.getInputItem(0) : ItemStack.EMPTY);
        this.craftResult.setItem(1, r != null ? r.getInputItem(1) : ItemStack.EMPTY);
    }

    @SuppressWarnings("null")
    @Nullable
	public static Recipe<?> searchRecipe(ItemStack input, RecipeManager recipeManager) {
		Item inputItem = input.getItem();
		Optional<Recipe<?>> optionalRecipe = recipeManager.getRecipes().stream()
				.filter(recipe -> recipe.getType().equals(ModdedRecipes.SEAMSTRESS_RECIPE.get()))
				.filter(recipe -> recipe.getResultItem(null).getItem() == inputItem
						&& !recipe.getIngredients().isEmpty())
				.findAny();
		return optionalRecipe.orElse(null);
	}
}
