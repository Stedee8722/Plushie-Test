package net.stedee.plushie_test.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.stedee.plushie_test.block.custom.AlchemicalTableBlock;
import net.stedee.plushie_test.block.custom.AlchemicalTableBlockEntity;
import net.stedee.plushie_test.config.ClientConfig;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

public class AlchemicalTableBlockEntityRenderer implements BlockEntityRenderer<AlchemicalTableBlockEntity> {

    //private final float _blockScale = 0.25f;
    private final float _itemScale = 0.126f;
    private final BlockEntityRendererProvider.Context context;

    public AlchemicalTableBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @SuppressWarnings("null")
    @Override
    public void render(@NotNull AlchemicalTableBlockEntity blockEntity, float pPartialTick, @NotNull PoseStack matrixStack,
                       @NotNull MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        ClientConfig config = AutoConfig.getConfigHolder(ClientConfig.class).getConfig();
        //try(Level level = blockEntity.getLevel()) {
        //    isClient = level.isClientSide;
        //} catch(IOException err)
        //{System.out.println(err);}

        if (!config.display_items_in_table || (blockEntity.inventory.isEmpty() && blockEntity.getLastResult().isEmpty()))
            return;


        ItemStack stack = blockEntity.getItem(0);
        NonNullList<ItemStack> result = blockEntity.getLastResult();
        ItemStack stack_out1 = result.get(0);
        ItemStack stack_out2 = result.get(1);

        if (stack.isEmpty() && stack_out1.isEmpty() && stack_out2.isEmpty())
            return;

        Level level = blockEntity.getLevel();
        if (level == null)
            return;

        BlockPos pos = blockEntity.getBlockPos().above();

        //render first slot
        matrixStack.pushPose();
        matrixStack.translate(0.5, 0, 0.5);
        matrixStack.rotateAround((new Quaternionf()).rotationY(getRotation(blockEntity.getBlockState().getValue(AlchemicalTableBlock.FACING)) * ((float)Math.PI / 180F)), (float)0, (float)0, (float)0);
        matrixStack.translate(-0.5, 0, -0.5);
        matrixStack.translate(0.5, 1.05, 0.245);
        matrixStack.scale(_itemScale, _itemScale, _itemScale);
        matrixStack.mulPose(Axis.XP.rotationDegrees(90));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(180));
        this.context.getItemRenderer().renderStatic(
                stack,
                ItemDisplayContext.FIXED,
                LightTexture.pack(
                        level.getBrightness(LightLayer.BLOCK, pos),
                        level.getBrightness(LightLayer.SKY, pos)
                ),
                OverlayTexture.NO_OVERLAY,
                matrixStack,
                pBuffer,
                level,
                0
        );
        matrixStack.popPose();

        //render second slot
        matrixStack.pushPose();
        matrixStack.translate(0.5, 0, 0.5);
        matrixStack.rotateAround((new Quaternionf()).rotationY(getRotation(blockEntity.getBlockState().getValue(AlchemicalTableBlock.FACING)) * ((float)Math.PI / 180F)), (float)0, (float)0, (float)0);
        matrixStack.translate(-0.5, 0, -0.5);
        matrixStack.translate(0.245, 1.05, 0.755);
        matrixStack.scale(_itemScale, _itemScale, _itemScale);
        matrixStack.mulPose(Axis.XP.rotationDegrees(90));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(180));
        this.context.getItemRenderer().renderStatic(
                stack_out1,
                ItemDisplayContext.FIXED,
                LightTexture.pack(
                        level.getBrightness(LightLayer.BLOCK, pos),
                        level.getBrightness(LightLayer.SKY, pos)
                ),
                OverlayTexture.NO_OVERLAY,
                matrixStack,
                pBuffer,
                level,
                0
        );
        matrixStack.popPose();

        //render result slot
        matrixStack.pushPose();
        matrixStack.translate(0.5, 0, 0.5);
        matrixStack.rotateAround((new Quaternionf()).rotationY(getRotation(blockEntity.getBlockState().getValue(AlchemicalTableBlock.FACING)) * ((float)Math.PI / 180F)), (float)0, (float)0, (float)0);
        matrixStack.translate(-0.5, 0, -0.5);
        matrixStack.translate(0.755, 1.05, 0.755);
        matrixStack.scale(_itemScale, _itemScale, _itemScale);
        matrixStack.mulPose(Axis.XP.rotationDegrees(90));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(180));
        this.context.getItemRenderer().renderStatic(
                stack_out2,
                ItemDisplayContext.FIXED,
                LightTexture.pack(
                        level.getBrightness(LightLayer.BLOCK, pos),
                        level.getBrightness(LightLayer.SKY, pos)
                ),
                OverlayTexture.NO_OVERLAY,
                matrixStack,
                pBuffer,
                level,
                0
        );
        matrixStack.popPose();
    }

    private int getRotation(Direction dir) {
        switch (dir) {
          case NORTH -> {return 180;}
          case EAST  -> {return 90;}
          case SOUTH -> {return 0;}
          case WEST  -> {return 270;}
          default -> {return -1;}
        }
    }
}
