package net.stedee.plushie_test.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "server")
@Config.Gui.Background("plushie_test:textures/gui/config_bg.png")
public class ServerConfig implements ConfigData {
}
