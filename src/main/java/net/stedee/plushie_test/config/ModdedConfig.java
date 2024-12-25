package net.stedee.plushie_test.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.stedee.plushie_test.plushie_test;

@Config(name = plushie_test.MOD_ID)
@Config.Gui.Background("plushie_test:textures/gui/config_bg.png")
public class ModdedConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject
    public ClientConfig clientConfig = new ClientConfig();

    @ConfigEntry.Category("server")
    @ConfigEntry.Gui.TransitiveObject
    public ServerConfig serverConfig = new ServerConfig();
}
