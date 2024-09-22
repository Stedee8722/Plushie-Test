package net.stedee.plushie_test.client.renderer;

import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.stedee.plushie_test.recipe.custom.SeamstressRecipe;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.stedee.plushie_test.block.custom.SeamstressTableBlock;
import net.stedee.plushie_test.block.custom.SeamstressTableBlockEntity;
import net.stedee.plushie_test.config.ClientConfig;

public class SeamstressTableBlockEntityRenderer implements BlockEntityRenderer<SeamstressTableBlockEntity> {

    private final float _blockScale = 0.25f;
    private final float _itemScale = 0.126f;
    private final BlockEntityRendererProvider.Context context;

    public SeamstressTableBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @SuppressWarnings("null")
    @Override
    public void render(@NotNull SeamstressTableBlockEntity blockEntity, float pPartialTick, @NotNull PoseStack matrixStack,
                       @NotNull MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        ClientConfig config = AutoConfig.getConfigHolder(ClientConfig.class).getConfig();
        //try(Level level = blockEntity.getLevel()) {
        //    isClient = level.isClientSide;
        //} catch(IOException err)
        //{System.out.println(err);}

        if (!config.displayitemsintable || blockEntity.input.isEmpty())// || !isClient) 
            return;

        ItemStack stack1 = blockEntity.getInventory().getStackInSlot(0);
        ItemStack stack2 = blockEntity.getInventory().getStackInSlot(1);
        ItemStack stack_out = blockEntity.getInventory().getStackInSlot(2);

        if (stack1.isEmpty() && stack2.isEmpty())
            return;

        Level level = blockEntity.getLevel();
        if (level == null)
            return;

        BlockPos pos = blockEntity.getBlockPos().above();

        //render first slot
        matrixStack.pushPose();
        matrixStack.translate(0.5, 0, 0.5);
        matrixStack.rotateAround((new Quaternionf()).rotationY(getRotation(blockEntity.getBlockState().getValue(SeamstressTableBlock.FACING)) * ((float)Math.PI / 180F)), (float)0, (float)0, (float)0);
        matrixStack.translate(-0.5, 0, -0.5);
        matrixStack.translate(0.245, 1.05, 0.245);
        matrixStack.scale(_itemScale, _itemScale, _itemScale);
        matrixStack.mulPose(Axis.XP.rotationDegrees(90));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(180));
        this.context.getItemRenderer().renderStatic(
                stack1,
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
        matrixStack.rotateAround((new Quaternionf()).rotationY(getRotation(blockEntity.getBlockState().getValue(SeamstressTableBlock.FACING)) * ((float)Math.PI / 180F)), (float)0, (float)0, (float)0);
        matrixStack.translate(-0.5, 0, -0.5);
        matrixStack.translate(0.755, 1.05, 0.245);
        matrixStack.scale(_itemScale, _itemScale, _itemScale);
        matrixStack.mulPose(Axis.XP.rotationDegrees(90));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(180));
        this.context.getItemRenderer().renderStatic(
                stack2,
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
        matrixStack.rotateAround((new Quaternionf()).rotationY(getRotation(blockEntity.getBlockState().getValue(SeamstressTableBlock.FACING)) * ((float)Math.PI / 180F)), (float)0, (float)0, (float)0);
        matrixStack.translate(-0.5, 0, -0.5);
        matrixStack.translate(0.5, 1.05, 0.7);
        matrixStack.scale(_itemScale, _itemScale, _itemScale);
        matrixStack.mulPose(Axis.XP.rotationDegrees(90));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(180));
        this.context.getItemRenderer().renderStatic(
                stack_out,
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


        //BlockState state = blockEntity.getBlockState();
//
        //double height = state.hasProperty(SlabBlock.TYPE) ? state.getValue(SlabBlock.TYPE) == SlabType.BOTTOM ?.5:1:1;
        //// get type of block
//
        //final double spacing = .189;
        //final double offset = .31;
        ////translate x,y,z
        //matrixStack.translate(0,.0625 + height, 0);
        //for (int i = 0; i < 1; i++) {
        //    for (int j = 0; j < 2; j++) {
        //            ItemStack item = blockEntity.input.getStackInSlot(j + 3 * i);
        //            if (item.isEmpty()) continue;
//
        //            boolean isItem = !(item.getItem() instanceof BlockItem);
//
        //            //pushmatrix
        //            matrixStack.pushPose();
        //            matrixStack.translate(0.5, 0.5, 0.5);
        //            matrixStack.mulPose((new Quaternionf()).rotationY(getRotation(state.getValue(SeamstressTableBlock.FACING)) * ((float)Math.PI / 180F)));
        //            matrixStack.translate(-0.5, -0.5, -0.5);
        //            //translate x,y,z (invert direction of j to correct the positioning on the table)
        //            matrixStack.translate(spacing * i + offset, isItem ? -0.05 : 0, spacing * (2-j) + offset);
        //            matrixStack.mulPose((new Quaternionf()).rotationY(270 * ((float)Math.PI / 180F)));
        //            //scale x,y,z
        //            float scale = isItem ? _itemScale : _blockScale;
        //            matrixStack.scale(scale, scale, scale);
//
        //            if (isItem) {
        //            matrixStack.mulPose((new Quaternionf()).rotationX(90 * ((float)Math.PI / 180F)));
        //            }
//
        //            BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer()
        //                    .getModel(item, blockEntity.getLevel(), null, 0);
//
//
        //            int lightAbove = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above());
        //            Minecraft.getInstance().getItemRenderer().render(item, ItemDisplayContext.FIXED,
        //                        false, matrixStack, iRenderTypeBuffer, lightAbove, OverlayTexture.NO_OVERLAY, bakedmodel);
        //            //popmatrix
        //            matrixStack.popPose();
        //    }
        //}
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
