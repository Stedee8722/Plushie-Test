package net.stedee.plushie_test.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.stedee.plushie_test.item.ModdedItems;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.recipe.custom.SeamstressRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SeamstressCategory implements IRecipeCategory<SeamstressRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(plushie_test.MOD_ID, "seamstress");
    public static final ResourceLocation TEXTURE = new ResourceLocation(plushie_test.MOD_ID, "textures/gui/seamstress_table_gui.png");
    public static final RecipeType<SeamstressRecipe> SEAMSTRESS_RECIPE_TYPE = new RecipeType<>(UID, SeamstressRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public SeamstressCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 176);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModdedItems.PLUSH_AYM.get()));
    }

    @Override
    public @NotNull RecipeType<SeamstressRecipe> getRecipeType() {
        return SEAMSTRESS_RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("container.plushie_test.seamstress_table");
    }

    @SuppressWarnings("removal")
    @Override
    @Nullable
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder iRecipeLayoutBuilder, @NotNull SeamstressRecipe seamstressRecipe, @NotNull IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 55, 19).addIngredients(seamstressRecipe.getIngredients().get(0));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 104, 19).addIngredients(seamstressRecipe.getIngredients().get(1));

        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 80, 59).addItemStack(seamstressRecipe.getResultItem(RegistryAccess.EMPTY));
    }
}
