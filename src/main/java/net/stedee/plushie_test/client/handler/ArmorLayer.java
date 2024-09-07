package net.stedee.plushie_test.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.stedee.plushie_test.item.custom.MaskItem;
import net.stedee.plushie_test.plushie_test;

public class ArmorLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final RenderLayerParent<T, M> renderLayerParent;

    public ArmorLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
        this.renderLayerParent = renderer;
    }

    @SuppressWarnings("null")
    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (plushie_test.itemRenderer == null) {
            plushie_test.LOGGER.debug("Minecraft: "+plushie_test.minecraft);
            plushie_test.LOGGER.debug("Renderer: "+plushie_test.itemRenderer);
            return;
        }
        Iterable<ItemStack> armorSlots = entity.getArmorSlots();
        for (ItemStack item : armorSlots) {
            if (item.getItem() instanceof MaskItem) {
                // Get the model
                BakedModel model = plushie_test.itemRenderer.getModel(item, entity.level(), entity, 200);
                poseStack.pushPose();

                poseStack.scale(1.1F, 1.1F, 1.1F);

                // Rotate the model to match the player's head rotation
                poseStack.mulPose(Axis.YP.rotationDegrees(netHeadYaw));
                poseStack.mulPose(Axis.XP.rotationDegrees(headPitch));

                // Flip the model upside down if needed
                poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

                // Additional fine-tuning translations if needed
                poseStack.translate(-0.5F, -0.35F, -0.95F); // Adjust the Y value as needed

                // Render the model
                plushie_test.itemRenderer.render(item,
                        ItemDisplayContext.HEAD,
                        false,
                        poseStack,
                        multiBufferSource,
                        light,
                        OverlayTexture.NO_OVERLAY,
                        model);

                poseStack.popPose();
            }
        }
    }
}
