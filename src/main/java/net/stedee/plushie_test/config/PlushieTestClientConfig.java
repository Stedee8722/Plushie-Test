package net.stedee.plushie_test.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class PlushieTestClientConfig {

    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.BooleanValue SHOW_ITEMS_IN_TABLE;

    static {
        BUILDER.push("Configs for Plushie Test");
        SHOW_ITEMS_IN_TABLE = BUILDER.comment("Show items in table?")
                .define("Show items", true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
