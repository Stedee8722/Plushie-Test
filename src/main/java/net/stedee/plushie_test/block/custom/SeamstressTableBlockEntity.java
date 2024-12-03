package net.stedee.plushie_test.block.custom;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
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
import net.stedee.plushie_test.inventory.custom.seamstress.SeamstressTableMenu;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SeamstressTableBlockEntity extends BaseContainerBlockEntity {
    public final NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);
    private ItemStack lastResult = ItemStack.EMPTY;

    public SeamstressTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModdedBlockEntities.SEAMSTRESS_TABLE_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @SuppressWarnings("null")
    @Override
    public @NotNull AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory) {
        return new SeamstressTableMenu(pContainerId, this, pPlayerInventory, this, ContainerLevelAccess.create(Objects.requireNonNull(this.getLevel()), this.getBlockPos()), this::setLastResult);
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return SeamstressTableBlock.CONTAINER_TITLE;
    }

    @SuppressWarnings("null")
    @Override
    public void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.inventory, true);
        if (!this.lastResult.isEmpty()) {
            CompoundTag compoundTag = new CompoundTag();
            this.lastResult.save(compoundTag);
            tag.put("LastResult", compoundTag);
        }
    }

    @SuppressWarnings("null")
    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.inventory.clear();
        ContainerHelper.loadAllItems(tag, this.inventory);
        if (tag.contains("LastResult")) {
            this.lastResult = ItemStack.of(tag.getCompound("LastResult"));
        } else {
            this.lastResult = ItemStack.EMPTY;
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

    public ItemStack getLastResult() {
        return this.lastResult;
    }

    private void setLastResult(ItemStack lastResult) {
        this.lastResult = lastResult;
        this.setChanged();
    }
}
