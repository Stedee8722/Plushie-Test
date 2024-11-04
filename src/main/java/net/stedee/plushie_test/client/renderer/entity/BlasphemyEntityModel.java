package net.stedee.plushie_test.client.renderer.entity;

// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


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

public class BlasphemyEntityModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(plushie_test.MOD_ID, "blasphemy"), "main");
    private final ModelPart bb_main;

    public BlasphemyEntityModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(18, 20).addBox(-1.5F, -2.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(18, 0).addBox(-1.5F, -11.0F, -4.0F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(18, 26).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(26, 26).addBox(-2.0F, -11.0F, 4.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 30).addBox(-2.0F, -11.0F, -6.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.5F, -33.0F, -4.0F, 1.0F, 22.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(18, 10).addBox(-0.5F, -37.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(32, 10).addBox(-0.5F, -39.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
