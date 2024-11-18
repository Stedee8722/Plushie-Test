package net.stedee.plushie_test.init;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.stedee.plushie_test.compat.curios.CuriosCompat;
import net.stedee.plushie_test.plushie_test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = plushie_test.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCompat {
    private ModCompat() {}

    private static final Map<String, Supplier<Callable<ICompat>>> compatFactories = new HashMap<>();
    static {
        compatFactories.put("curios", () -> CuriosCompat::new);
    }

    private static final Set<ICompat> loadedCompats = new HashSet<>();

    public static void initCompats() {
        for(Map.Entry<String, Supplier<Callable<ICompat>>> entry : compatFactories.entrySet()) {
            if (ModList.get().isLoaded(entry.getKey())) {
                try {
                    loadedCompats.add(entry.getValue().get().call());
                }
                catch (Exception e) {
                    plushie_test.LOGGER.error("Error instantiating compatibility ", e);
                }
            }
        }
    }

    public static void setupCompats() {
        loadedCompats.forEach(ICompat::setup);
    }
}
