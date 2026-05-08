package com.roseyasa.advanced_clover.utils;

import com.roseyasa.advanced_clover.item.MagicCloverItem;
import com.roseyasa.advanced_clover.item.content.IngredientNamespceContent;
import com.roseyasa.advanced_clover.registry.ComponentRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;

import java.util.*;
import java.util.stream.Collectors;

import static com.roseyasa.advanced_clover.registry.ComponentRegister.INGREDIENT_NAMESPACE;
import static com.roseyasa.advanced_clover.registry.ItemRegister.ITEM_FOUR_LEAF_CLOVER;
import static com.roseyasa.advanced_clover.registry.ItemRegister.ITEM_MAGIC_CLOVER;
import static com.roseyasa.advanced_clover.registry.SoundRegister.SOUND_CLOVER_FAIL;
import static com.roseyasa.advanced_clover.utils.MagicCloverConfig.WORKING_MODE;

public class MagicCloverHandler {
    private static final Map<String, List<Item>> NAMESPACE_ITEMS = new HashMap<>();
    private static final List<String> LISTED_ITEMS = new ArrayList<String>();
    private static Map<String, List<Item>> ALL_ITEMS = new HashMap<>();

    public static void updateItemsList() {
        Event event = new UpdateItemsListEvent();
        NeoForge.EVENT_BUS.post(event);

    }

    static {
        BuiltInRegistries.ITEM.stream().forEach(item -> {
            String namespace = BuiltInRegistries.ITEM.getKey(item).getNamespace();
            ALL_ITEMS.computeIfAbsent(namespace, k -> new ArrayList<>()).add(item);
        });
    }

    public static class UpdateItemsListEvent extends Event {

        public UpdateItemsListEvent() {
            NAMESPACE_ITEMS.clear();
            for (Map.Entry<String, List<Item>> entry : ALL_ITEMS.entrySet()) {
                List<Item> copiedList = new ArrayList<>(entry.getValue());
                NAMESPACE_ITEMS.put(entry.getKey(), copiedList);
            }

            LISTED_ITEMS.clear();
            if(WORKING_MODE.get().equals("whitelist")){
                updateWhiteList();
            } else {
                updateBlackList();
            }


        }

        private static void updateBlackList(){
            // 更新黑名单
            LISTED_ITEMS.addAll(MagicCloverConfig.BLACKLIST_ITEMS.get());

            NAMESPACE_ITEMS.forEach((namespace, items) -> {
                items.removeIf(item -> {
                    String itemId = BuiltInRegistries.ITEM.getKey(item).toString();
                    return LISTED_ITEMS.stream().anyMatch(pattern ->
                        itemId.matches(pattern.replace("*", ".*"))
                    );
                });
            });
        }

        private static void updateWhiteList(){
            // 更新白名单
            LISTED_ITEMS.addAll(MagicCloverConfig.WHITELIST_ITEMS.get());

            NAMESPACE_ITEMS.forEach((namespace, items) -> {
                items.removeIf(item -> {
                    String itemId = BuiltInRegistries.ITEM.getKey(item).toString();
                    return LISTED_ITEMS.stream().noneMatch(pattern ->
                        itemId.matches(pattern.replace("*", ".*"))
                    );
                });
            });
        }
    }

    public static ItemStack generateRandomItem(Level level, ItemStack cloverStack) {
        if (NAMESPACE_ITEMS.isEmpty()) {
            updateItemsList();
        }

        IngredientNamespceContent content = cloverStack.get(ComponentRegister.INGREDIENT_NAMESPACE);
        if (content == null || content.namespace() == null) {
            // 获取任意物品（只能通过编辑component实现）
            List<List<Item>> nonEmptyLists = ALL_ITEMS.values().stream()
                .filter(list -> list != null && !list.isEmpty())
                .toList();

            if (nonEmptyLists.isEmpty()) {
                return null;
            }

            List<Item> itemList = nonEmptyLists.get(level.getRandom().nextInt(nonEmptyLists.size()));
            Item randomItem = itemList.get(level.getRandom().nextInt(itemList.size()));
            if(randomItem instanceof MagicCloverItem){
                return RandomFallback();
            }
            return new ItemStack(randomItem);

        }

        List<Item> itemList = NAMESPACE_ITEMS.get(content.namespace());
        if (itemList == null || itemList.isEmpty()) {
            return null;
        }

        Item randomItem;
        randomItem = itemList.get(level.getRandom().nextInt(itemList.size()));

        return new ItemStack(randomItem);
    }


    public static ItemStack RandomFallback(){
        return new ItemStack(ITEM_FOUR_LEAF_CLOVER.get());
    }
}
