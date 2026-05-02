package com.roseyasa.advanced_clover.datagen;

import com.roseyasa.advanced_clover.Main;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.roseyasa.advanced_clover.registry.ItemRegister.*;

public class MagicCloverItemModelProvider extends ItemModelProvider {
    public MagicCloverItemModelProvider(PackOutput gen, ExistingFileHelper helper) {
        super(gen, Main.MODID, helper);
    }

    @Override
    protected void registerModels() {
        this.singleTexture(ITEM_MAGIC_CLOVER_ID, ResourceLocation.withDefaultNamespace("item/generated"),
                "layer0", ResourceLocation.fromNamespaceAndPath(Main.MODID, "item/" + ITEM_MAGIC_CLOVER_ID));

        this.singleTexture(ITEM_THREE_LEAF_CLOVER_ID, ResourceLocation.withDefaultNamespace("item/generated"),
                "layer0", ResourceLocation.fromNamespaceAndPath(Main.MODID, "item/" + ITEM_THREE_LEAF_CLOVER_ID));

        this.singleTexture(ITEM_FOUR_LEAF_CLOVER_ID, ResourceLocation.withDefaultNamespace("item/generated"),
                "layer0", ResourceLocation.fromNamespaceAndPath(Main.MODID, "item/" + ITEM_FOUR_LEAF_CLOVER_ID));
    }
}
