package net.stedee.plushie_test.recipe.custom;

import com.google.gson.JsonArray;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
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
    public ItemStack getToastSymbol() {
        return new ItemStack(ModdedBlocks.SEAMSTRESS_TABLE.get());
    }
    
    @SuppressWarnings("null")
    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        if(pLevel.isClientSide()){
            return false;
        }
        
        return (inputItems.get(0).test(pContainer.getItem(0)) && inputItems.get(1).test(pContainer.getItem(1))) || (inputItems.get(0).test(pContainer.getItem(1)) && inputItems.get(1).test(pContainer.getItem(0)));
    }

    @SuppressWarnings("null")
    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return outputItem.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @SuppressWarnings("null")
    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return outputItem.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }

    // It is important to copy item even if it's input or output
    public ItemStack getInputItem(int index) {
        return inputItems.get(index).getItems()[0].copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public CraftingBookCategory category() {
      return CraftingBookCategory.MISC;
   }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
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
        public SeamstressRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
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
        public @Nullable SeamstressRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

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

            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}
