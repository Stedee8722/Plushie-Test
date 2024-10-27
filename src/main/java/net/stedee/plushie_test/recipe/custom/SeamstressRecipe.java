package net.stedee.plushie_test.recipe.custom;

import com.google.gson.JsonArray;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.block.ModdedBlocks;

public class SeamstressRecipe implements Recipe<Container> {

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack outputItem;
    private final ResourceLocation id;

    public SeamstressRecipe(NonNullList<Ingredient> inputItems, ItemStack outputItem, ResourceLocation id) {
        this.inputItems = inputItems;
        this.outputItem = outputItem;
        this.id = id;
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(ModdedBlocks.SEAMSTRESS_TABLE.get());
    }
    
    @SuppressWarnings("null")
    @Override
    public boolean matches(@NotNull Container pContainer, Level pLevel) {
        if(pLevel.isClientSide()){
            return false;
        }
        
        return (inputItems.get(0).test(pContainer.getItem(0)) && inputItems.get(1).test(pContainer.getItem(1))) || (inputItems.get(0).test(pContainer.getItem(1)) && inputItems.get(1).test(pContainer.getItem(0)));
    }

    @SuppressWarnings("null")
    @Override
    public @NotNull ItemStack assemble(@NotNull Container pContainer, @NotNull RegistryAccess pRegistryAccess) {
        return outputItem.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @SuppressWarnings("null")
    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess pRegistryAccess) {
        return outputItem.copy();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }

    // It is important to copy item even if it's input or output
    public ItemStack getInputItem(int index) {
        return inputItems.get(index).getItems()[0].copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SeamstressRecipe> {
        public static final RecipeType<SeamstressRecipe> INSTANCE = new RecipeType<>(){};
        public static final String ID = "seamstress";
    }

    public static class Serializer implements RecipeSerializer<SeamstressRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(plushie_test.MOD_ID, "seamstress");

        @SuppressWarnings("null")
        @Override
        public @NotNull SeamstressRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");

            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new SeamstressRecipe(inputs, output, pRecipeId);
        }

        @SuppressWarnings("null")
        @Override
        public @Nullable SeamstressRecipe fromNetwork(@NotNull ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));

            ItemStack output = pBuffer.readItem();
            return new SeamstressRecipe(inputs, output, pRecipeId);
        }

        @SuppressWarnings("null")
        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SeamstressRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(RegistryAccess.EMPTY), false);
        }
    }
}
