package net.stedee.plushie_test.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.stedee.plushie_test.plushie_test;
import org.jetbrains.annotations.NotNull;

public class ElectrostormGlaiveModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(plushie_test.MOD_ID, "electrostorm_glaive"), "main");
    private final ModelPart root;

    public ElectrostormGlaiveModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition group = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 25).addBox(-0.75F, 15.025F, -1.0F, 1.5F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(6, 25).addBox(-0.75F, 43.725F, -1.0F, 1.5F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 0).addBox(-0.5F, 15.2F, -0.5F, 1.0F, 30.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 12).addBox(-0.95F, 13.025F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(22, 0).addBox(-0.45F, 7.025F, -2.5F, 1.0F, 7.25F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.05F, -2.725F, -4.0F, 0.0F, 16.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(22, 20).addBox(-0.45F, 3.025F, -1.5F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(12, 0).addBox(-1.0F, 13.2639F, -0.7583F, 0.0F, 1.5F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(12, 0).addBox(1.075F, 13.2639F, -0.7583F, 0.0F, 1.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.2F, 0.0F));

        return LayerDefinition.create(meshdefinition, 40, 40);
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
