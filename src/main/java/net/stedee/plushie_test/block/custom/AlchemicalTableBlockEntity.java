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
import net.stedee.plushie_test.inventory.custom.alchemical.AlchemicalTableMenu;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AlchemicalTableBlockEntity extends BaseContainerBlockEntity {
    public ItemStack inventory = ItemStack.EMPTY;
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
        ContainerHelper.saveAllItems(tag, this.lastResult, true);
        CompoundTag compoundTag = new CompoundTag();
        this.inventory.save(compoundTag);
        tag.put("Inventory", compoundTag);
    }

    @SuppressWarnings("null")
    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.lastResult.clear();
        this.inventory = tag.contains("Inventory") ? ItemStack.of(tag.getCompound("Inventory")) : ItemStack.EMPTY;
        ContainerHelper.loadAllItems(tag, this.lastResult);
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
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public @NotNull ItemStack getItem(int index) {
        return index == 0 ? this.inventory : ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItem(int index, int count) {
        ItemStack itemStack = index == 0 && !this.inventory.isEmpty() && count > 0 ? this.inventory.split(count) : ItemStack.EMPTY;
        if (!itemStack.isEmpty()) {
            // vanilla is fine, but crafting tweaks mod doesn't update the client properly without this
            this.setChanged();
        }
        return itemStack;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int index) {
        ItemStack itemStack = index == 0 ? this.inventory = ItemStack.EMPTY : ItemStack.EMPTY;
        if (!itemStack.isEmpty()) {
            // vanilla is fine, but crafting tweaks mod doesn't update the client properly without this
            this.setChanged();
        }
        return itemStack;
    }

    @Override
    public void setItem(int index, @NotNull ItemStack stack) {
        if (index == 0) {
            this.inventory = stack;
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
        this.inventory = ItemStack.EMPTY;
    }

    public NonNullList<ItemStack> getLastResult() {
        return this.lastResult;
    }

    private void setLastResult(NonNullList<ItemStack> lastResult) {
        this.lastResult = lastResult;
        this.setChanged();
    }
}

