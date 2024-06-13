package net.stedee.plushie_test.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.recipe.custom.SeamstressRecipe;

public class ModdedRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = 
        DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, plushie_test.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = 
        DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, plushie_test.MOD_ID);

    public static final RegistryObject<RecipeSerializer<SeamstressRecipe>> SEAMSTRESS_SERIALIZER = 
        SERIALIZERS.register("seamstress", () -> SeamstressRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<SeamstressRecipe>> SEAMSTRESS_RECIPE =
        RECIPE_TYPES.register("seamstress_recipe_type", () -> SeamstressRecipe.Type.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
