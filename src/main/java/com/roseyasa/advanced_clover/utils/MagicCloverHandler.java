package com.roseyasa.advanced_clover.utils;

import com.roseyasa.advanced_clover.item.MagicCloverItem;
import com.roseyasa.advanced_clover.item.content.CustomItemListContext;
import com.roseyasa.advanced_clover.item.content.IngredientNamespceContent;
import com.roseyasa.advanced_clover.registry.ComponentRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;

import java.util.*;
import java.util.regex.Pattern;

import static com.roseyasa.advanced_clover.registry.ItemRegister.ITEM_FOUR_LEAF_CLOVER;
import static com.roseyasa.advanced_clover.utils.MagicCloverConfig.WHITELIST_ITEMS;
import static com.roseyasa.advanced_clover.utils.MagicCloverConfig.WORKING_MODE;

public class MagicCloverHandler {
    private static final Map<String, List<Item>> NAMESPACE_ITEMS = new HashMap<>();
    private static final List<String> LISTED_ITEMS = new ArrayList<String>();
    private static Map<String, List<Item>> ALL_ITEMS = new HashMap<>();
    private static final List<Pattern> BLACKLIST_PATTERNS = new ArrayList<>();
    private static final List<Pattern> WHITELIST_PATTERNS = new ArrayList<>();

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
            BLACKLIST_PATTERNS.clear();
            for (String patternStr : MagicCloverConfig.BLACKLIST_ITEMS.get()) {
                BLACKLIST_PATTERNS.add(Pattern.compile(patternStr));
            }

            NAMESPACE_ITEMS.forEach((namespace, items) -> {
                items.removeIf(item -> {
                    String itemId = BuiltInRegistries.ITEM.getKey(item).toString();
                    return BLACKLIST_PATTERNS.stream().anyMatch(p -> p.matcher(itemId).matches());
                });
            });
        }

        private static void updateWhiteList(){
            // 更新白名单
            WHITELIST_PATTERNS.clear();
            for (String patternStr : WHITELIST_ITEMS.get()) {
                WHITELIST_PATTERNS.add(Pattern.compile(patternStr));
            }

            NAMESPACE_ITEMS.forEach((namespace, items) -> {
                items.removeIf(item -> {
                    String itemId = BuiltInRegistries.ITEM.getKey(item).toString();
                    return WHITELIST_PATTERNS.stream().noneMatch(p -> p.matcher(itemId).matches());
                });
            });
        }

        // 更新绑定于itemstack的custom_item_list
        // @debug,暂时不支持正则。获取数量会比较头疼
        // @debug，需要支持component
        private static List<Item> updateCustomItemList(List<String> ilist) {
            List<Item> result = new ArrayList<>();
            for (Map.Entry<String, List<Item>> entry : ALL_ITEMS.entrySet()) {
                for (Item item : entry.getValue()) {
                    String itemId = BuiltInRegistries.ITEM.getKey(item).toString();
                    if (ilist.contains(itemId)) {
                        result.add(item);
                    }
                }
            }
            return result;
        }
    }

    public static ItemStack generateRandomItem(Level level, ItemStack cloverStack) {
        if (NAMESPACE_ITEMS.isEmpty()) {
            updateItemsList();
        }

         // 如果有默认itemlist，则忽略默认namespace和黑白名单，直接抽自己的玩意
        CustomItemListContext customItemListContext = cloverStack.get(ComponentRegister.CUSTOM_ITEM_LIST);
        if(customItemListContext != null){
            List<Item> itemList = UpdateItemsListEvent.updateCustomItemList(customItemListContext.ilist());
            if(!itemList.isEmpty()){
                Item randomItem = itemList.get(level.getRandom().nextInt(itemList.size()));
                return new ItemStack(randomItem);
            }
        }

        IngredientNamespceContent inContent = cloverStack.get(ComponentRegister.INGREDIENT_NAMESPACE);
        if (inContent == null || inContent.namespace() == null) {
            // 如果namespace为空，获取任意物品（只能通过编辑component实现）
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

        List<Item> itemList = NAMESPACE_ITEMS.get(inContent.namespace());
        if (itemList == null || itemList.isEmpty()) {
            return null;
        }

        Item randomItem = itemList.get(level.getRandom().nextInt(itemList.size()));

        return new ItemStack(randomItem);
    }

    public static EntityType getRandomEntity(Level level, ItemStack cloverStack){
        // @debug, 目前我们固定返回creeper
        // 未来如果加入了匠craft（前提是日本人移植到26.1.2），那么这里预留一个联动
        return EntityType.CREEPER;
    }


    public static ItemStack RandomFallback(){
        return new ItemStack(ITEM_FOUR_LEAF_CLOVER.get());
    }
}
