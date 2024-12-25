package net.stedee.plushie_test.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.stedee.plushie_test.entity.custom.ThrownGlaive;
import net.stedee.plushie_test.plushie_test;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("rawtypes")
public class ElectrostormGlaiveRenderer extends EntityRenderer<ThrownGlaive> {
    public static final ResourceLocation ELECTROSTORM_GLAIVE_LOCATION = new ResourceLocation(plushie_test.MOD_ID, "textures/item/weapons/electrostorm_glaive.png");
    private final ElectrostormGlaiveModel model;

    public ElectrostormGlaiveRenderer(EntityRendererProvider.Context $$0) {
        super($$0);
        this.model = new ElectrostormGlaiveModel($$0.bakeLayer(ElectrostormGlaiveModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ThrownGlaive thrownGlaive) {
        return ELECTROSTORM_GLAIVE_LOCATION;
    }

    public void render(ThrownGlaive $$0, float $$1, float $$2, PoseStack $$3, @NotNull MultiBufferSource $$4, int $$5) {
        $$3.pushPose();
        $$3.mulPose(Axis.YP.rotationDegrees(Mth.lerp($$2, $$0.yRotO, $$0.getYRot()) - 90.0F));
        $$3.mulPose(Axis.ZP.rotationDegrees(Mth.lerp($$2, $$0.xRotO, $$0.getXRot()) + 90.0F));
        VertexConsumer $$6 = ItemRenderer.getFoilBufferDirect($$4, this.model.renderType(this.getTextureLocation($$0)), false, $$0.isFoil());
        this.model.renderToBuffer($$3, $$6, $$5, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        $$3.popPose();
        super.render($$0, $$1, $$2, $$3, $$4, $$5);
    }
}
