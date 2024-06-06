package net.stedee.plushie_test.block.custom;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TransparentModdedBlocks extends Block {

    public TransparentModdedBlocks(Properties pProperties) {
        super(pProperties);
    }

    @SuppressWarnings("null")
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip,
            TooltipFlag pFlag) {
        pTooltip.add(Component.translatable(pStack.getDescriptionId() + ".tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @SuppressWarnings("null")
    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 13, 16);

    @SuppressWarnings("null")
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @SuppressWarnings("null")
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

}
