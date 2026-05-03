package com.roseyasa.advanced_clover.utils;

import com.roseyasa.advanced_clover.Main;
import net.minecraft.IdentifierException;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static net.minecraft.resources.Identifier.isValidPath;

public class Utils {

    public static ResourceKey<Block> createBlockKey(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Main.MODID,name));
    }

    public static ResourceKey<Item> createItemKey(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Main.MODID,name));
    }
}
