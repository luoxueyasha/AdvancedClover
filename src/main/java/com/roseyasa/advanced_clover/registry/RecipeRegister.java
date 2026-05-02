package com.roseyasa.advanced_clover.registry;

import com.roseyasa.advanced_clover.Main;
import com.roseyasa.advanced_clover.recipe.MagicCloverRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;

public class RecipeRegister {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = 
        DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Main.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MagicCloverRecipe>> MAGIC_CLOVER_RECIPE =
        RECIPE_SERIALIZERS.register("crafting_special_magic_clover", 
            () -> new SimpleCraftingRecipeSerializer<>(MagicCloverRecipe::new));
}


