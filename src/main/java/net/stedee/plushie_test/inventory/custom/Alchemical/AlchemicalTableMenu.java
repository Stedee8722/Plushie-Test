package net.stedee.plushie_test.inventory.custom.Alchemical;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.stedee.plushie_test.block.custom.AlchemicalTableBlockEntity;
import net.stedee.plushie_test.inventory.ModdedMenuTypes;
import net.stedee.plushie_test.inventory.custom.MultipleResultItemContainer;
import net.stedee.plushie_test.inventory.custom.container.StationContainer;
import net.stedee.plushie_test.item.custom.PlushiesItem;
import net.stedee.plushie_test.recipe.ModdedRecipes;
import net.stedee.plushie_test.recipe.custom.SeamstressRecipe;

import javax.annotation.Nonnull;

import java.util.Optional;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

public class AlchemicalTableMenu extends AbstractContainerMenu implements ContainerListener {
    private final StationContainer craftSlots;
    private final MultipleResultItemContainer resultSlots;
    private final ContainerLevelAccess access;
    private final Player player;
    private final Consumer<NonNullList<ItemStack>> containerData;
    public AlchemicalTableBlockEntity tileEntity;

    public AlchemicalTableMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(i, inventory, inventory.player.level().getBlockEntity(friendlyByteBuf.readBlockPos()));
    }

    public AlchemicalTableMenu(int id, Inventory inventory, BlockEntity entity) {
        this(id, entity, inventory, new SimpleContainer(2), ContainerLevelAccess.NULL, stack -> {});
    }

    public AlchemicalTableMenu(int id, BlockEntity entity, Inventory inventory, Container container, ContainerLevelAccess access, Consumer<NonNullList<ItemStack>> containerData) {
        super(ModdedMenuTypes.ALCHEMICAL_MENU_TYPE.get(), id);
        this.craftSlots = new StationContainer(container, this);
        this.resultSlots = new MultipleResultItemContainer();
        this.access = access;
        this.player = inventory.player;
        this.containerData = containerData;

        if (entity instanceof AlchemicalTableBlockEntity be) {
            this.tileEntity = be;
        }
        else {
            throw new IllegalStateException("Incorrect block entity class (%s) passed into AlchemicalTableMenu".formatted(entity.getClass().getCanonicalName()));
        }

        layoutPlayerInventorySlots(player.getInventory(), 8, 94);

        this.addSlot(new Slot(craftSlots, 0, 80, 19) {
            @Override
            public void setChanged() {
                slotsChanged(inventory);
                super.setChanged();
            }

            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                if (!resultSlots.isEmpty() && !(!resultSlots.getItem(0).isEmpty() && !resultSlots.getItem(1).isEmpty())) {
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
        this.addSlot(new AlchemicalOutputSlot(this, this.craftSlots, this.resultSlots, 0, 55, 59, player));
        this.addSlot(new AlchemicalOutputSlot(this, this.craftSlots, this.resultSlots, 1, 104, 59, player));

        this.addSlotListener(this);
        slotsChanged(inventory);
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
            System.out.println("Invalid slotIndex: " + pIndex);
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
        return this.craftSlots.stillValid(player);
    }

    private int addSlotRange(Container playerInventory, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new Slot(playerInventory, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private void addSlotBox(Container playerInventory, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(playerInventory, index, x, y, horAmount, dx);
            y += dy;
        }
    }

    private void layoutPlayerInventorySlots(Container playerInventory, int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    @SuppressWarnings("null")
    public static Recipe<?> searchRecipe(ItemStack input, RecipeManager recipeManager) {
        Item inputItem = input.getItem();
        Optional<Recipe<?>> optionalRecipe = recipeManager.getRecipes().stream()
                .filter(recipe -> recipe.getType().equals(ModdedRecipes.SEAMSTRESS_RECIPE.get()))
                .filter(recipe -> recipe.getResultItem(RegistryAccess.EMPTY).getItem() == inputItem
                        && !recipe.getIngredients().isEmpty())
                .findAny();
        return optionalRecipe.orElse(null);
    }

    @Override
    public void slotsChanged(@NotNull Container pInventory) {
        this.access.execute((p_39386_, p_39387_) -> {
            slotChangedCraftingGrid(this, p_39386_, this.player, this.craftSlots, this.resultSlots);
            NonNullList<ItemStack> list = NonNullList.withSize(2, ItemStack.EMPTY);
            list.set(0, this.resultSlots.getItem(0));
            list.set(1, this.resultSlots.getItem(1));
            this.containerData.accept(list);
        });
        if (tileEntity.getLevel() != null) {
            tileEntity.getLevel().sendBlockUpdated(tileEntity.getBlockPos(), tileEntity.getBlockState(), tileEntity.getBlockState(), 3);
        }
    }

    protected void slotChangedCraftingGrid(AbstractContainerMenu pMenu, @NotNull Level pLevel, Player pPlayer, StationContainer pContainer, MultipleResultItemContainer pResult) {
        if (!pLevel.isClientSide) {
            ServerPlayer $$5 = (ServerPlayer)pPlayer;
            ItemStack $$6 = !resultSlots.getItem(0).isEmpty() && resultSlots.getItem(1).isEmpty() ? resultSlots.getItem(0) : ItemStack.EMPTY;
            ItemStack $$8 = !resultSlots.getItem(1).isEmpty() && !resultSlots.getItem(0).isEmpty() ? resultSlots.getItem(1) : ItemStack.EMPTY;
            SeamstressRecipe $$7 = (SeamstressRecipe) searchRecipe(pContainer.getItem(0), pLevel.getRecipeManager());
            if ($$7 != null) {
                if (pResult.setRecipeUsed(pLevel, $$5, $$7)) {
                    ItemStack $$9 = $$7.getInputItem(0);
                    if ($$9.isItemEnabled(pLevel.enabledFeatures())) {
                        $$6 = $$9;
                    }
                }if (pResult.setRecipeUsed(pLevel, $$5, $$7)) {
                    ItemStack $$10 = $$7.getInputItem(1);
                    if ($$10.isItemEnabled(pLevel.enabledFeatures())) {
                        $$8 = $$10;
                    }
                }
            }

            pResult.setItem(0, $$6);
            pMenu.setRemoteSlot(37, $$6);
            $$5.connection.send(new ClientboundContainerSetSlotPacket(pMenu.containerId, pMenu.incrementStateId(), 37, $$6));

            pResult.setItem(1, $$8);
            pMenu.setRemoteSlot(38, $$8);
            $$5.connection.send(new ClientboundContainerSetSlotPacket(pMenu.containerId, pMenu.incrementStateId(), 38, $$8));
        }
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

    @Override
    public @NotNull MenuType<?> getType() {
        return ModdedMenuTypes.ALCHEMICAL_MENU_TYPE.get();
    }

    @SuppressWarnings("null")
    @Override
    public boolean canTakeItemForPickAll(@NotNull ItemStack stack, Slot slot) {
        return slot.container != resultSlots && super.canTakeItemForPickAll(stack, slot);
    }

    @Override
    public void slotChanged(@NotNull AbstractContainerMenu abstractContainerMenu, int i, @NotNull ItemStack itemStack) {
        if (abstractContainerMenu == this) {
            this.access.execute((Level level, BlockPos blockPos) -> {
                if (i >= 1 && i < 10) {
                    this.slotsChanged(this.craftSlots);
                }
            });
        }
    }

    @Override
    public void dataChanged(@NotNull AbstractContainerMenu abstractContainerMenu, int i, int i1) {

    }

    @Override
    public void removed(@NotNull Player player) {
        // copied from container base class
        if (player instanceof ServerPlayer) {
            ItemStack itemstack = this.getCarried();
            if (!itemstack.isEmpty()) {
                if (player.isAlive() && !((ServerPlayer) player).hasDisconnected()) {
                    player.getInventory().placeItemBackInInventory(itemstack);
                } else {
                    player.drop(itemstack, false);
                }

                this.setCarried(ItemStack.EMPTY);
            }
        }
    }
}
