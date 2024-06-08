package net.stedee.plushie_test.inventory;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.inventory.custom.SeamstressContainer;

public class ModdedMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = 
        DeferredRegister.create(ForgeRegistries.MENU_TYPES, plushie_test.MOD_ID);

    public static final RegistryObject<MenuType<SeamstressContainer>> SEAMSTRESS_CONTAINER = MENU_TYPES.register("seamstress_container",
        () -> IForgeMenuType.create((windowId, inv, data) -> new SeamstressContainer(windowId, inv.player, data.readBlockPos())));
        
    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
