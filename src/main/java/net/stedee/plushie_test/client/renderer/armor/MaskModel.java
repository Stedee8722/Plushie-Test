package net.stedee.plushie_test.client.renderer.armor;

import net.minecraft.resources.ResourceLocation;
import net.stedee.plushie_test.item.custom.MaskItem;
import net.stedee.plushie_test.plushie_test;
import software.bernie.geckolib.model.GeoModel;

public class MaskModel extends GeoModel<MaskItem> {

    @Override
    public ResourceLocation getModelResource(MaskItem maskItem) {
        return new ResourceLocation(plushie_test.MOD_ID, "geo/mask/" + maskItem.asItem() + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MaskItem maskItem) {
        return new ResourceLocation(plushie_test.MOD_ID, "textures/item/" + maskItem.asItem() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(MaskItem maskItem) {
        return new ResourceLocation(plushie_test.MOD_ID, "animations/" + maskItem.asItem() + ".animation.json");
    }
}
