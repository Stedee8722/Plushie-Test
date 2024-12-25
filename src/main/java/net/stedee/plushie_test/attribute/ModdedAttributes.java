package net.stedee.plushie_test.attribute;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.plushie_test.plushie_test;

public class ModdedAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, plushie_test.MOD_ID);

    public static final RegistryObject<Attribute> PROJECTILE_DAMAGE = ATTRIBUTES.register("blasphemy.projectile_damage", () -> new RangedAttribute("attribute.plushie_test.name.blasphemy.projectile_damage", 7.0D, 0.0D, 1024.0D).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
