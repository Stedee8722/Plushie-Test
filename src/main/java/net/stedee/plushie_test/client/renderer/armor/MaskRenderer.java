package net.stedee.plushie_test.client.renderer.armor;

import net.stedee.plushie_test.item.custom.MaskItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class MaskRenderer extends GeoArmorRenderer<MaskItem> {
    public MaskRenderer() {
        super(new MaskModel());
    }
}
