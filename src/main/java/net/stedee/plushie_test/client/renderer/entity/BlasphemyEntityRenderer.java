package net.stedee.plushie_test.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.stedee.plushie_test.entity.BlasphemyProjectileEntity;
import net.stedee.plushie_test.plushie_test;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class BlasphemyEntityRenderer extends EntityRenderer<BlasphemyProjectileEntity> {
    protected EntityModel<BlasphemyProjectileEntity> model;
    protected EntityRendererProvider.Context pContext;
    ResourceLocation TEXTURE = new ResourceLocation(plushie_test.MOD_ID, "textures/entity/blasphemy.png");

    public BlasphemyEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        model = new BlasphemyEntityModel<>(pContext.bakeLayer(BlasphemyEntityModel.LAYER_LOCATION));
        this.pContext = pContext;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BlasphemyProjectileEntity pEntity) {
        return TEXTURE;
    }

    @Override
    public void render(BlasphemyProjectileEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();

        pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot())));

        pPoseStack.translate(0.25, -0.25, 0);
        this.model.renderToBuffer(pPoseStack, pBuffer.getBuffer(model.renderType(getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
    }

    public Vec3 getShotDirection(BlasphemyProjectileEntity pEntity) {
        return pEntity.getDeltaMovement().normalize();
    }
}
