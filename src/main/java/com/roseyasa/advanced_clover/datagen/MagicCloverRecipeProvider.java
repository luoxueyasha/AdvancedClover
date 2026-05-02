package com.roseyasa.advanced_clover.datagen;

import com.roseyasa.advanced_clover.Main;
import com.roseyasa.advanced_clover.recipe.MagicCloverRecipe;
import com.roseyasa.advanced_clover.registry.ItemRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class MagicCloverRecipeProvider extends RecipeProvider {
    public MagicCloverRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes() {
        // 四叶草
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegister.ITEM_FOUR_LEAF_CLOVER.get(), 3)
                .requires(ItemRegister.ITEM_THREE_LEAF_CLOVER.get(), 4)
                .unlockedBy("has_three_leaf_clover", has(ItemRegister.ITEM_THREE_LEAF_CLOVER.get()))
                .unlockedBy("has_four_leaf_clover", has(ItemRegister.ITEM_FOUR_LEAF_CLOVER.get()))
            .save(this.output);

        // 三叶草
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegister.ITEM_THREE_LEAF_CLOVER.get(), 4)
                .requires(ItemRegister.ITEM_FOUR_LEAF_CLOVER.get(), 3)
                .unlockedBy("has_three_leaf_clover", has(ItemRegister.ITEM_THREE_LEAF_CLOVER.get()))
                .unlockedBy("has_four_leaf_clover", has(ItemRegister.ITEM_FOUR_LEAF_CLOVER.get()))
            .save(this.output);

        buildMagicCloverRecipies(recipeOutput);
    }

    private void buildMagicCloverRecipies() {
        SpecialRecipeBuilder.special(MagicCloverRecipe::new)
            .save(this.output, Main.MODID + ":crafting_special_magic_clover");
    }
}