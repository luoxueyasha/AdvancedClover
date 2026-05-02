package com.roseyasa.advanced_clover.recipe;

import com.roseyasa.advanced_clover.item.content.IngredientNamespceContent;
import com.roseyasa.advanced_clover.registry.ComponentRegister;
import com.roseyasa.advanced_clover.registry.ItemRegister;
import com.roseyasa.advanced_clover.registry.RecipeRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MagicCloverRecipe extends CustomRecipe {
    public MagicCloverRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, @NotNull Level level) {
        boolean hasClover = false;
        boolean hasOtherItem = false;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.is(ItemRegister.ITEM_FOUR_LEAF_CLOVER.get())) {
                    if (hasClover) return false;
                    hasClover = true;
                } else {
                    if (hasOtherItem) return false;
                    hasOtherItem = true;
                }
            }
        }

        return hasClover && hasOtherItem;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack otherItem = ItemStack.EMPTY;
        
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty() && !stack.is(ItemRegister.ITEM_FOUR_LEAF_CLOVER.get())) {
                otherItem = stack;
                break;
            }
        }

        ItemStack result = new ItemStack(ItemRegister.ITEM_MAGIC_CLOVER.get());
        ResourceLocation id = otherItem.getItem().builtInRegistryHolder().key().location();
        result.set(ComponentRegister.INGREDIENT_NAMESPACE, new IngredientNamespceContent(id.getNamespace()));

        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegister.MAGIC_CLOVER_RECIPE.get();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return new ItemStack(ItemRegister.ITEM_MAGIC_CLOVER.get());
    }
}