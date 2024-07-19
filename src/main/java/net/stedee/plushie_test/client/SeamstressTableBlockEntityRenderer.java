package net.stedee.plushie_test.client;

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

    private float _blockScale = 0.25f;
    private float _itemScale = 0.125f;
    public SeamstressTableBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @SuppressWarnings("null")
    @Override
    public void render(SeamstressTableBlockEntity blockEntity, float var2, PoseStack matrixStack,
            MultiBufferSource iRenderTypeBuffer, int light, int var6) {
        ClientConfig config = AutoConfig.getConfigHolder(ClientConfig.class).getConfig();
        //try(Level level = blockEntity.getLevel()) {
        //    isClient = level.isClientSide;
        //} catch(IOException err)
        //{System.out.println(err);}

        if (!config.displayitemsintable || blockEntity.input.isEmpty())// || !isClient) 
            return;

        BlockState state = blockEntity.getBlockState();

        double height = state.hasProperty(SlabBlock.TYPE) ? state.getValue(SlabBlock.TYPE) == SlabType.BOTTOM ?.5:1:1;

        final double spacing = .189;
        final double offset = .31;
        //translate x,y,z
        matrixStack.translate(0,.0625 + height, 0);
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 2; j++) {
                    ItemStack item = blockEntity.input.getStackInSlot(j + 3 * i);
                    if (item.isEmpty()) continue;

                    boolean isItem = !(item.getItem() instanceof BlockItem);

                    //pushmatrix
                    matrixStack.pushPose();
                    matrixStack.translate(0.5, 0.5, 0.5);
                    matrixStack.mulPose((new Quaternionf()).rotationY(getRotation(state.getValue(SeamstressTableBlock.FACING)) * ((float)Math.PI / 180F)));
                    matrixStack.translate(-0.5, -0.5, -0.5);
                    //translate x,y,z (invert direction of j to correct the positioning on the table)
                    matrixStack.translate(spacing * i + offset, isItem ? -0.05 : 0, spacing * (2-j) + offset);
                    matrixStack.mulPose((new Quaternionf()).rotationY(270 * ((float)Math.PI / 180F)));
                    //scale x,y,z
                    float scale = isItem ? _itemScale : _blockScale;
                    matrixStack.scale(scale, scale, scale);

                    if (isItem) {
                    matrixStack.mulPose((new Quaternionf()).rotationX(90 * ((float)Math.PI / 180F)));
                    }

                    BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer()
                            .getModel(item, blockEntity.getLevel(), null, 0);


                    int lightAbove = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above());
                    Minecraft.getInstance().getItemRenderer().render(item, ItemDisplayContext.FIXED,
                                false, matrixStack, iRenderTypeBuffer, lightAbove, OverlayTexture.NO_OVERLAY, bakedmodel);
                    //popmatrix
                    matrixStack.popPose();
            }
        }
    }

    private int getRotation(Direction dir) {
        switch (dir) {
          case NORTH -> {return 270;}
          case EAST  -> {return 180;}
          case SOUTH -> {return 90;}
          case WEST  -> {return 0;}
          default -> {return 0;}
        }
    }
}
