package net.stedee.plushie_test.client.renderer.armor;

import net.minecraft.resources.ResourceLocation;
import net.stedee.plushie_test.item.custom.CrownItem;
import net.stedee.plushie_test.plushie_test;
import software.bernie.geckolib.model.GeoModel;

public class CrownModel extends GeoModel<CrownItem> {
    @Override
    public ResourceLocation getModelResource(CrownItem crownItem) {
        return new ResourceLocation(plushie_test.MOD_ID, "geo/crowns/" + crownItem.asItem() + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CrownItem crownItem) {
        return new ResourceLocation(plushie_test.MOD_ID, "textures/item/crowns/" + crownItem.asItem() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(CrownItem crownItem) {
        return new ResourceLocation(plushie_test.MOD_ID, "animations/" + crownItem.asItem() + ".animation.json");
    }
}
