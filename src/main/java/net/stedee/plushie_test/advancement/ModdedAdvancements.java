package net.stedee.plushie_test.advancement;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.stedee.plushie_test.advancement.trigger.PlayerDropItemTrigger;

import java.util.function.Supplier;

public class ModdedAdvancements {
    public static final PlayerDropItemTrigger PLAYER_DROP_ITEM = register(PlayerDropItemTrigger::new);

    private static <T extends CriterionTrigger<?>> T register(Supplier<T> supplier) {
        return CriteriaTriggers.register(supplier.get());
    }

    public static void init() {
        //<clinit>
    }
}
