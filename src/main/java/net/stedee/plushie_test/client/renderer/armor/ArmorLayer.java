package net.stedee.plushie_test.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.stedee.plushie_test.item.custom.MaskItem;
import net.stedee.plushie_test.plushie_test;

public class ArmorLayer<T extends LivingEntity, M extends EntityModel<T> & HeadedModel> extends RenderLayer<T, M> {
    //private final RenderLayerParent<T, M> renderLayerParent;

    public ArmorLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
        //this.renderLayerParent = renderer;
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

                poseStack.scale(1.0F, 1.0F, 1.0F);
                boolean flag = entity instanceof Villager || entity instanceof ZombieVillager;

                if (entity.isBaby() && !(entity instanceof Villager)) {
                    float f = 2.0F;
                    float f1 = 1.4F;
                    poseStack.translate(0.0F, 0.03125F, 0.0F);
                    poseStack.scale(0.7F, 0.7F, 0.7F);
                    poseStack.translate(0.0F, 1.0F, 0.0F);
                }

                this.getParentModel().getHead().translateAndRotate(poseStack);

                translateToHead(poseStack, flag);
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

    public static void translateToHead(PoseStack pPoseStack, boolean pIsVillager) {
        float f = 0.625F;
        pPoseStack.translate(0.0F, -0.25F, 0.0F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        pPoseStack.scale(0.625F, -0.625F, -0.625F);
        if (pIsVillager) {
            pPoseStack.translate(0.0F, 0.1875F, 0.0F);
        }
    }
}
