package net.stedee.plushie_test.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.stedee.plushie_test.plushie_test;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CuriosEarRenderer implements ICurioRenderer {
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack itemStack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource multiBufferSource, int i, float v, float v1, float v2, float v3, float v4, float v5) {
        if (plushie_test.ITEM_RENDERER == null) {
            return;
        }

        // Get the model
        BakedModel model = plushie_test.ITEM_RENDERER.getModel(itemStack, slotContext.entity().level(), slotContext.entity(), 200);
        poseStack.pushPose();

        // Rotate the model to match the player's head rotation
        ((HumanoidModel<?>)renderLayerParent.getModel()).getHead().translateAndRotate(poseStack);

        // Flip the model upside down if needed
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

        poseStack.scale(0.6F, 0.6F, 0.6F);

        // Additional fine-tuning translations if needed
        poseStack.translate(0F, 0.45F, 0F); // Adjust the Y value as needed

        // Render the model
        plushie_test.ITEM_RENDERER.render(itemStack,
                ItemDisplayContext.HEAD,
                false,
                poseStack,
                multiBufferSource,
                i,
                OverlayTexture.NO_OVERLAY,
                model);

        poseStack.popPose();
    }
}
