package net.stedee.plushie_test.block.custom;

import java.util.List;

import javax.annotation.Nullable;

import java.util.stream.IntStream;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;
import net.minecraft.world.Containers;
import org.jetbrains.annotations.NotNull;;

public class SeamstressTableBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {

    public static final Component CONTAINER_TITLE = Component.translatable("container.plushie_test.seamstress_table");

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SEAMSTRESS_TABLE_SHAPE = makeShape();

    public SeamstressTableBlock(Properties pProperties) {
        super(pProperties);
        this.stateDefinition.any().setValue(WATERLOGGED, false);
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
    protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED);
    }

    @SuppressWarnings("null")
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelAccessor level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        boolean water = level.getFluidState(pos).getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, water);
    }
    
    @SuppressWarnings("null")
    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    @SuppressWarnings("null")
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SEAMSTRESS_TABLE_SHAPE;
    }

    @SuppressWarnings("null")
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public static VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();

        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.0625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.03125, 0.0625, 0.03125, 0.96875, 0.125, 0.96875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.125, 0.25, 0.75, 0.4375, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.4375, 0.125, 0.875, 0.5625, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.6875, 0, 1, 0.828125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.828125, 0.0625, 0.9375, 0.890625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.890625, 0, 1, 0.953125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.03125, 0.953125, 0.03125, 0.96875, 1.015625, 0.96875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.5625, 0.125, 0.875, 0.6875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.125, 0.25, 0.75, 0.4375, 0.75), BooleanOp.OR);
        
        return shape;
    }

    @SuppressWarnings({ "deprecation", "null" })
    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
            LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if ((pDirection == Direction.DOWN) && !this.canSurvive(pState, pLevel, pCurrentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if ((boolean) pState.getValue(WATERLOGGED)) {
                pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
            }

            return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
        }
    }

    @SuppressWarnings("null")
    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canSupportCenter(pLevel, pPos.below(), Direction.UP);
    }

    @SuppressWarnings("null")
    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SeamstressTableBlockEntity(pPos, pState);
    }

    @SuppressWarnings("null")
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
            BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity tEntity = pLevel.getBlockEntity(pPos);
            if (tEntity instanceof MenuProvider) {
                NetworkHooks.openScreen((ServerPlayer) pPlayer, (MenuProvider) tEntity, tEntity.getBlockPos());
            }
        }
        return InteractionResult.SUCCESS;
    }
    
    @SuppressWarnings("null")
    @Override
    @Nullable
    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        return be instanceof SeamstressTableBlockEntity ? (MenuProvider) be : null;
    }

    @SuppressWarnings({ "null", "deprecation" })
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof SeamstressTableBlockEntity seamstressTableBlock) {
                dropItems(seamstressTableBlock.inventory, pLevel, pPos);
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    public static void dropItems(IItemHandler inv, Level pLevel, BlockPos pos) {
        IntStream.range(0, 2).mapToObj(inv::getStackInSlot).filter(s -> !s.isEmpty()).forEach(stack -> Containers.dropItemStack(pLevel, pos.getX(), pos.getY(), pos.getZ(), stack));
    }

    public @NotNull FluidState getFluidState(BlockState $$0) {
        return (Boolean)$$0.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState($$0);
    }

    //inv.getSlots()
}
