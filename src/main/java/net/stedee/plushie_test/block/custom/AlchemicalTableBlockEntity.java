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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.stedee.plushie_test.block.ModdedBlockEntities;
import net.stedee.plushie_test.inventory.custom.MultipleResultItemContainer;
import net.stedee.plushie_test.inventory.custom.ModdedItemHandler;
import net.stedee.plushie_test.inventory.custom.Alchemical.AlchemicalTableMenu;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AlchemicalTableBlockEntity extends BlockEntity implements MenuProvider {

    public ModdedItemHandler inventory;
    public final Container craftResult = new MultipleResultItemContainer();
    private AlchemicalTableMenu menu;

    public AlchemicalTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModdedBlockEntities.ALCHEMICAL_TABLE_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.inventory = new ModdedItemHandler(3);
    }

    @SuppressWarnings("null")
    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        menu = new AlchemicalTableMenu(pContainerId, pPlayerInventory, this);
        return menu;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return AlchemicalTableBlock.CONTAINER_TITLE;
    }

    @SuppressWarnings("null")
    @Override
    public void saveAdditional(CompoundTag tag) {
        CompoundTag compound = this.inventory.serializeNBT();
        tag.put("alchemical_inv", compound);
    }

    @SuppressWarnings("null")
    @Override
    public void load(CompoundTag tag) {
        CompoundTag invTag = tag.getCompound("alchemical_inv");
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

