package com.roseyasa.advanced_clover.recipe;

import com.mojang.serialization.MapCodec;
import com.roseyasa.advanced_clover.item.content.IngredientNamespceContent;
import com.roseyasa.advanced_clover.registry.ComponentRegister;
import com.roseyasa.advanced_clover.registry.ItemRegister;
import com.roseyasa.advanced_clover.registry.RecipeRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
public class MagicCloverRecipe extends CustomRecipe {
    public static final MapCodec<MagicCloverRecipe> MAP_CODEC = MapCodec.unit(MagicCloverRecipe::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, MagicCloverRecipe> STREAM_CODEC =
        StreamCodec.of((buf, recipe) -> {}, buf -> new MagicCloverRecipe());
    public static final RecipeSerializer<MagicCloverRecipe> SERIALIZER =
        new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public MagicCloverRecipe() {
        super();
    }
    @Override
    public boolean matches(CraftingInput input, Level level) {
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
    public ItemStack assemble(CraftingInput input) {
        ItemStack otherItem = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty() && !stack.is(ItemRegister.ITEM_FOUR_LEAF_CLOVER.get())) {
                otherItem = stack;
                break;
            }
        }
        ItemStack result = new ItemStack(ItemRegister.ITEM_MAGIC_CLOVER.get());
        Identifier id = otherItem.getItem().builtInRegistryHolder().key().identifier();
        result.set(ComponentRegister.INGREDIENT_NAMESPACE, new IngredientNamespceContent(id.getNamespace()));
        return result;
    }

    @Override
    public RecipeSerializer<MagicCloverRecipe> getSerializer() {
        return RecipeRegister.MAGIC_CLOVER_RECIPE.get();
    }
}