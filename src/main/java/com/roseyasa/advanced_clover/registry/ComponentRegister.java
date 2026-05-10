package com.roseyasa.advanced_clover.registry;

import com.roseyasa.advanced_clover.Main;
import com.roseyasa.advanced_clover.item.content.IngredientNamespceContent;
import com.roseyasa.advanced_clover.item.content.EntityTypeContent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ComponentRegister {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = 
        DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Main.MODID);

    // @debug,todo: 当前我们所有的component返回的都是一个map。例如INGREDIENT_NAMESPACE项返回的是{namespace:"aaa"}。后续考虑优化
    // give指令时也 /give @p advanced_clover:magic_clover[advanced_clover:ingredient_namespace={"namespace":"minecraft"}]
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<IngredientNamespceContent>> INGREDIENT_NAMESPACE =
        DATA_COMPONENT_TYPES.register(
                "ingredient_namespace",
                () -> DataComponentType.<IngredientNamespceContent>builder()
                    .persistent(IngredientNamespceContent.CODEC)
                    .networkSynchronized(IngredientNamespceContent.STREAM_CODEC)
                    .cacheEncoding()
                    .build()
        );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<EntityTypeContent>> ENTITY_TYPE =
        DATA_COMPONENT_TYPES.register(
            "entity_type",
            () -> DataComponentType.<EntityTypeContent>builder()
                .persistent(EntityTypeContent.CODEC)
                .networkSynchronized(EntityTypeContent.STREAM_CODEC)
                .cacheEncoding()
                .build()
        );


}