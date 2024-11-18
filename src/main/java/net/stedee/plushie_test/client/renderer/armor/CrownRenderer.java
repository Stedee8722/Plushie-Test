package net.stedee.plushie_test.client.renderer.armor;

import net.stedee.plushie_test.item.custom.CrownItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class CrownRenderer extends GeoArmorRenderer<CrownItem> {
    public CrownRenderer() {
        super(new CrownModel());
    }
}
