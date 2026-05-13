package com.roseyasa.advanced_clover.datagen;

import com.roseyasa.advanced_clover.Main;
import com.roseyasa.advanced_clover.item.content.IngredientNamespceContent;
import com.roseyasa.advanced_clover.recipe.MagicCloverRecipe;
import com.roseyasa.advanced_clover.registry.ItemRegister;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;


public class MagicCloverRecipeProvider extends RecipeProvider {
    protected MagicCloverRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {
        HolderGetter<Item> items = this.registries.lookupOrThrow(Registries.ITEM);
        // 四叶草
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ItemRegister.ITEM_FOUR_LEAF_CLOVER.get(), 3)
                .requires(ItemRegister.ITEM_THREE_LEAF_CLOVER.get(), 4)
                .unlockedBy("has_three_leaf_clover", has(ItemRegister.ITEM_THREE_LEAF_CLOVER.get()))
                .unlockedBy("has_four_leaf_clover", has(ItemRegister.ITEM_FOUR_LEAF_CLOVER.get()))
            .save(this.output);

        // 三叶草
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ItemRegister.ITEM_THREE_LEAF_CLOVER.get(), 4)
                .requires(ItemRegister.ITEM_FOUR_LEAF_CLOVER.get(), 3)
                .unlockedBy("has_three_leaf_clover", has(ItemRegister.ITEM_THREE_LEAF_CLOVER.get()))
                .unlockedBy("has_four_leaf_clover", has(ItemRegister.ITEM_FOUR_LEAF_CLOVER.get()))
            .save(this.output);

        // 给JEI看的假合成表
        ShapelessRecipeBuilder.shapeless(items,RecipeCategory.MISC, ItemRegister.ITEM_MAGIC_CLOVER.get())
            .requires(ItemRegister.ITEM_FOUR_LEAF_CLOVER.get())
            .requires(Items.STRUCTURE_VOID)  // "anything"
            .unlockedBy("has_four_leaf_clover", has(ItemRegister.ITEM_FOUR_LEAF_CLOVER.get()))
            .save(this.output, Main.MODID + ":jei_dummy_magic_clover");

        buildMagicCloverRecipies();
    }

    private void buildMagicCloverRecipies() {
        SpecialRecipeBuilder.special(MagicCloverRecipe::new)
            .save(this.output, Main.MODID + ":crafting_special_magic_clover");
    }

    public static class Runner extends RecipeProvider.Runner {
        // Get the parameters from the `GatherDataEvent`s.
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new MagicCloverRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return Main.MODID+":RecipeProvider";
        }
    }
}