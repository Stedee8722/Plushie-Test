package net.stedee.plushie_test.block.custom;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.stedee.plushie_test.block.ModdedBlockEntities;
import net.stedee.plushie_test.inventory.custom.ModdedItemHandler;
import net.stedee.plushie_test.inventory.custom.Seamstress.SeamstressTableMenu;
import org.jetbrains.annotations.NotNull;

public class SeamstressTableBlockEntity extends BlockEntity implements MenuProvider {

    public ModdedItemHandler inventory;
    public final ResultContainer craftResult = new ResultContainer();

    public SeamstressTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModdedBlockEntities.SEAMSTRESS_TABLE_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.inventory = new ModdedItemHandler(2);
    }

    @SuppressWarnings("null")
    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        return new SeamstressTableMenu(pContainerId, pPlayerInventory, this);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return SeamstressTableBlock.CONTAINER_TITLE;
    }

    @SuppressWarnings("null")
    @Override
    public void saveAdditional(CompoundTag tag) {
        CompoundTag compound = this.inventory.serializeNBT();
        tag.put("seamstress_inv", compound);
    }

    @SuppressWarnings("null")
    @Override
    public void load(CompoundTag tag) {
        CompoundTag invTag = tag.getCompound("seamstress_inv");
        this.inventory.deserializeNBT(invTag);
        super.load(tag);
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

    public ItemStackHandler getInventory() {
        return this.inventory;
    }
    public Container getOutputs() { return craftResult; }
}
