package net.stedee.plushie_test.block.custom;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.stedee.plushie_test.block.ModdedBlockEntities;
import net.stedee.plushie_test.inventory.custom.Alchemical.AlchemicalTableMenu;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AlchemicalTableBlockEntity extends BaseContainerBlockEntity {
    public final NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);
    private NonNullList<ItemStack> lastResult = NonNullList.withSize(2, ItemStack.EMPTY);

    public AlchemicalTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModdedBlockEntities.ALCHEMICAL_TABLE_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @SuppressWarnings("null")
    @Override
    public @NotNull AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory) {
        return new AlchemicalTableMenu(pContainerId, this, pPlayerInventory, this, ContainerLevelAccess.create(Objects.requireNonNull(this.getLevel()), this.getBlockPos()), this::setLastResult);
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return AlchemicalTableBlock.CONTAINER_TITLE;
    }

    @SuppressWarnings("null")
    @Override
    public void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.inventory, true);
        ListTag $$3 = new ListTag();

        for(int $$4 = 0; $$4 < this.lastResult.size(); ++$$4) {
            ItemStack $$5 = this.lastResult.get($$4);
            if (!$$5.isEmpty()) {
                CompoundTag $$6 = new CompoundTag();
                $$6.putByte("Slot", (byte)$$4);
                $$5.save($$6);
                $$3.add($$6);
            }
        }
        tag.put("LastResults", $$3);

    }

    @SuppressWarnings("null")
    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.inventory.clear();
        ContainerHelper.loadAllItems(tag, this.inventory);
        ListTag $$2 = tag.getList("LastResults", 10);

        for(int $$3 = 0; $$3 < $$2.size(); ++$$3) {
            CompoundTag $$4 = $$2.getCompound($$3);
            int $$5 = $$4.getByte("Slot") & 255;
            if ($$5 < this.lastResult.size()) {
                this.lastResult.set($$5, ItemStack.of($$4));
            }
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public int getContainerSize() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.inventory) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int index) {
        return index >= 0 && index < this.inventory.size() ? this.inventory.get(index) : ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItem(int index, int count) {
        ItemStack itemStack = ContainerHelper.removeItem(this.inventory, index, count);
        if (!itemStack.isEmpty()) {
            // vanilla is fine, but crafting tweaks mod doesn't update the client properly without this
            this.setChanged();
        }
        return itemStack;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int index) {
        ItemStack itemStack = ContainerHelper.takeItem(this.inventory, index);
        if (!itemStack.isEmpty()) {
            // vanilla is fine, but crafting tweaks mod doesn't update the client properly without this
            this.setChanged();
        }
        return itemStack;
    }

    @Override
    public void setItem(int index, @NotNull ItemStack stack) {
        if (index >= 0 && index < this.inventory.size()) {
            this.inventory.set(index, stack);
            // vanilla is fine, but crafting tweaks mod doesn't update the client properly without this
            this.setChanged();
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        if (Objects.requireNonNull(this.getLevel()).getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return !(player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public void clearContent() {
        this.inventory.clear();
    }

    public NonNullList<ItemStack> getLastResult() {
        return this.lastResult;
    }

    private void setLastResult(NonNullList<ItemStack> lastResult) {
        this.lastResult = lastResult;
        this.setChanged();
    }
}

