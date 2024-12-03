package net.stedee.plushie_test.inventory;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.inventory.custom.alchemical.AlchemicalTableMenu;
import net.stedee.plushie_test.inventory.custom.seamstress.SeamstressTableMenu;

public class ModdedMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = 
        DeferredRegister.create(ForgeRegistries.MENU_TYPES, plushie_test.MOD_ID);

    public static final RegistryObject<MenuType<SeamstressTableMenu>> SEAMSTRESS_MENU_TYPE = MENU_TYPES.register("seamstress_menu_type",
        () -> IForgeMenuType.create(SeamstressTableMenu::new));
    public static final RegistryObject<MenuType<AlchemicalTableMenu>> ALCHEMICAL_MENU_TYPE = MENU_TYPES.register("alchemical_menu_type",
        () -> IForgeMenuType.create(AlchemicalTableMenu::new));

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
