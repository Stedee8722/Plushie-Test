package net.stedee.plushie_test.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.stedee.plushie_test.client.screen.AlchemicalTableScreen;
import net.stedee.plushie_test.client.screen.SeamstressTableScreen;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.recipe.custom.SeamstressRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class JEISeamstressPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(plushie_test.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new SeamstressCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new AlchemicalCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        if (Minecraft.getInstance().level != null) {
            RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

            List<SeamstressRecipe> seamstressRecipes = recipeManager.getAllRecipesFor(SeamstressRecipe.Type.INSTANCE);
            registration.addRecipes(SeamstressCategory.SEAMSTRESS_RECIPE_TYPE, seamstressRecipes);
            registration.addRecipes(AlchemicalCategory.SEAMSTRESS_RECIPE_TYPE, seamstressRecipes);
        }
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SeamstressTableScreen.class, 62, 36, 52, 22,
            SeamstressCategory.SEAMSTRESS_RECIPE_TYPE);
        registration.addRecipeClickArea(AlchemicalTableScreen.class, 62, 36, 52, 22,
            AlchemicalCategory.SEAMSTRESS_RECIPE_TYPE);
    }
}
