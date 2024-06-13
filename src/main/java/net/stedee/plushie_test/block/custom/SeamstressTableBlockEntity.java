package net.stedee.plushie_test.block.custom;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.stedee.plushie_test.block.ModdedBlockEntities;
import net.stedee.plushie_test.inventory.custom.SeamstressTableItemHandler;
import net.stedee.plushie_test.inventory.custom.SeamstressTableMenu;

public class SeamstressTableBlockEntity extends BlockEntity implements MenuProvider {

    public SeamstressTableItemHandler input;
    private int currentContainer = 0;

    ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return currentContainer;
        }

        @Override
        public void set(int pIndex, int pValue) {
            currentContainer = pValue;
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public SeamstressTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModdedBlockEntities.SEAMSTRESS_TABLE_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.input = new SeamstressTableItemHandler(2);
    }

    @SuppressWarnings("null")
    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SeamstressTableMenu(pContainerId, pPlayerInventory, this);
    }

    @Override
    public Component getDisplayName() {
        return SeamstressTableBlock.CONTAINER_TITLE;
    }

    @SuppressWarnings("null")
    @Override
    public void saveAdditional(CompoundTag tag) {
        CompoundTag compound = this.input.serializeNBT();
        tag.put("seamstress_inv", compound);
    }

    @SuppressWarnings("null")
    @Override
    public void load(CompoundTag tag) {
        CompoundTag invTag = tag.getCompound("seamstress_inv");
        this.input.deserializeNBT(invTag);
        super.load(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public ItemStackHandler getInventory() {
        return this.input;
    }
}
