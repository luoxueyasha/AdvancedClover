package com.roseyasa.advanced_clover.registry;

import com.roseyasa.advanced_clover.Main;
import com.roseyasa.advanced_clover.item.MagicCloverItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.roseyasa.advanced_clover.registry.BlockRegister.*;
import static com.roseyasa.advanced_clover.utils.Utils.createItemKey;


public class ItemRegister {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Main.MODID);

    // Items
    public static final DeferredItem<MagicCloverItem> ITEM_MAGIC_CLOVER;
    public static final String ITEM_MAGIC_CLOVER_ID = "magic_clover";

    // BlockItems with no BlockEntity
    public static final DeferredHolder<Item, BlockItem> ITEM_THREE_LEAF_CLOVER;
    public static final String ITEM_THREE_LEAF_CLOVER_ID = "three_leaf_clover";
    public static final DeferredHolder<Item, BlockItem> ITEM_FOUR_LEAF_CLOVER;
    public static final String ITEM_FOUR_LEAF_CLOVER_ID = "four_leaf_clover";


    static {
        // Items
        ITEM_MAGIC_CLOVER = ITEMS.register(ITEM_MAGIC_CLOVER_ID, ()-> new MagicCloverItem(new Item.Properties().setId(createItemKey(ITEM_MAGIC_CLOVER_ID))));

        // BlockItems with no BlockEntity
        ITEM_THREE_LEAF_CLOVER = ITEMS.register(ITEM_THREE_LEAF_CLOVER_ID,
                ()-> new BlockItem(BLOCK_THREE_LEAF_CLOVER.get(),new Item.Properties().setId(createItemKey(ITEM_THREE_LEAF_CLOVER_ID))));
        ITEM_FOUR_LEAF_CLOVER = ITEMS.register(ITEM_FOUR_LEAF_CLOVER_ID,
                ()-> new BlockItem(BLOCK_FOUR_LEAF_CLOVER.get(),new Item.Properties().setId(createItemKey(ITEM_FOUR_LEAF_CLOVER_ID))));
    }

}
