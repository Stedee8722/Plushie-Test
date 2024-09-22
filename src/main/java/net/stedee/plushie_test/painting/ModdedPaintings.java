package net.stedee.plushie_test.painting;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.plushie_test.plushie_test;

public class ModdedPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS = 
        DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, plushie_test.MOD_ID);

    public static final RegistryObject<PaintingVariant> CREATURE = PAINTING_VARIANTS.register("creature", () -> new PaintingVariant(64, 64));
    public static final RegistryObject<PaintingVariant> TEA = PAINTING_VARIANTS.register("tea", () -> new PaintingVariant(64, 64));

    public static void register(IEventBus eventBus) {
        PAINTING_VARIANTS.register(eventBus);
    }
}
