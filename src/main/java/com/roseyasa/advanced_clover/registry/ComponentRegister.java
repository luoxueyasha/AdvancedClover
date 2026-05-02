package com.roseyasa.advanced_clover.registry;

import com.mojang.serialization.Codec;
import com.roseyasa.advanced_clover.Main;
import com.roseyasa.advanced_clover.item.content.IngredientNamespceContent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ComponentRegister {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = 
        DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Main.MODID);
    
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<IngredientNamespceContent>> INGREDIENT_NAMESPACE =
        DATA_COMPONENT_TYPES.register(
                "ingredient_namespace", 
                () -> DataComponentType.<IngredientNamespceContent>builder()
                    .persistent(IngredientNamespceContent.CODEC)
                    .networkSynchronized(IngredientNamespceContent.STREAM_CODEC)
                    .cacheEncoding()
                    .build()
        );

}