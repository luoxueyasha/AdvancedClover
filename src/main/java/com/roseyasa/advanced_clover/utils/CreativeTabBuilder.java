package com.roseyasa.advanced_clover.utils;

import com.roseyasa.advanced_clover.Main;
import com.roseyasa.advanced_clover.item.content.IngredientNamespceContent;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.roseyasa.advanced_clover.registry.ComponentRegister.INGREDIENT_NAMESPACE;
import static com.roseyasa.advanced_clover.registry.ItemRegister.*;

public class CreativeTabBuilder {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_ADVANCED_CLOVER;

    static {
        TAB_ADVANCED_CLOVER = CREATIVE_MODE_TABS.register("advanced_clover", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup."+Main.MODID+".name"))
            .icon(() -> ITEM_MAGIC_CLOVER.get().getDefaultInstance())
            .displayItems(((itemDisplayParameters, output) -> {
                ItemStack magicCloverMinecraft = new ItemStack(ITEM_MAGIC_CLOVER.get());
                magicCloverMinecraft.set(INGREDIENT_NAMESPACE.get(), new IngredientNamespceContent("minecraft") );
                output.accept(magicCloverMinecraft);

                output.accept(ITEM_THREE_LEAF_CLOVER.get());
                output.accept(ITEM_FOUR_LEAF_CLOVER.get());

            }))
            .build());
    }


}
