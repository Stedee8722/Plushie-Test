package net.stedee.plushie_test.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.stedee.plushie_test.entity.custom.BlasphemyAbstractProjectile;
import net.stedee.plushie_test.plushie_test;
import org.jetbrains.annotations.NotNull;

public class BlasphemyEntityRenderer extends EntityRenderer<BlasphemyAbstractProjectile> {
    protected EntityModel<BlasphemyAbstractProjectile> model;
    protected EntityRendererProvider.Context pContext;
    ResourceLocation TEXTURE = new ResourceLocation(plushie_test.MOD_ID, "textures/entity/blasphemy_layer.png");

    public BlasphemyEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        model = new BlasphemyEntityModel<>(pContext.bakeLayer(BlasphemyEntityModel.LAYER_LOCATION));
        this.pContext = pContext;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BlasphemyAbstractProjectile pEntity) {
        return TEXTURE;
    }

    @Override
    public void render(BlasphemyAbstractProjectile pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();

        pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.yRotO, pEntity.getYRot()) + 90F));
        pPoseStack.mulPose(Axis.ZN.rotationDegrees(Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot()) + 90F));

        pPoseStack.translate(-0.25, 0, 0);

        float red;
        float green;
        float blue;
        int $$14 = pEntity.tickCount / 25 + pEntity.getId();
        int $$15 = DyeColor.values().length;
        int $$16 = $$14 % $$15;
        int $$17 = ($$14 + 1) % $$15;
        float $$18 = ((float)(pEntity.tickCount % 25) + pPartialTick) / 25.0F;
        float[] $$19 = Sheep.getColorArray(DyeColor.byId($$16));
        float[] $$20 = Sheep.getColorArray(DyeColor.byId($$17));
        red = $$19[0] * (1.0F - $$18) + $$20[0] * $$18;
        green = $$19[1] * (1.0F - $$18) + $$20[1] * $$18;
        blue = $$19[2] * (1.0F - $$18) + $$20[2] * $$18;

        this.model.renderToBuffer(pPoseStack, pBuffer.getBuffer(model.renderType(getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }
}
