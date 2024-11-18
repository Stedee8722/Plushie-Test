package net.stedee.plushie_test.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.stedee.plushie_test.plushie_test;

@Config(name = plushie_test.MOD_ID)
@Config.Gui.Background("plushie_test:textures/gui/config_bg.png")
public class ClientConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean display_items_in_table = true;
}