package net.stedee.plushie_test.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CuriosCrownRenderer implements ICurioRenderer {
    protected CrownRenderer renderer = new CrownRenderer();

    public CuriosCrownRenderer() {
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack itemStack,
                                                                          SlotContext slotContext,
                                                                          PoseStack poseStack,
                                                                          RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource multiBufferSource,
                                                                          int i,
                                                                          float v,
                                                                          float v1,
                                                                          float v2,
                                                                          float v3,
                                                                          float v4,
                                                                          float v5)
    {
        ICurioRenderer.followHeadRotations(slotContext.entity(), this.renderer.getHead());
        //noinspection rawtypes
        HumanoidModel model = (HumanoidModel) renderLayerParent.getModel();
        this.renderer.prepForRender(slotContext.entity(), itemStack, EquipmentSlot.HEAD, model);
        //noinspection DataFlowIssue
        this.renderer.renderToBuffer(poseStack, null, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
            1.0F, 1.0F);
    }
}
