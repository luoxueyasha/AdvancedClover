package com.roseyasa.advanced_clover.utils;

import com.roseyasa.advanced_clover.item.content.IngredientNamespceContent;
import com.roseyasa.advanced_clover.registry.ComponentRegister;
import com.roseyasa.advanced_clover.utils.MagicCloverConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;

import java.util.*;

public class MagicCloverHandler {
    private static final Map<String, List<Item>> NAMESPACE_ITEMS = new HashMap<>();
    private static final List<String> BLACKLISTED_ITEMS = new ArrayList<String>();

    public static void updateItemsList() {
        Event event = new UpdateItemsListEvent();
        NeoForge.EVENT_BUS.post(event);

    }

    public static class UpdateItemsListEvent extends Event {
        public UpdateItemsListEvent() {
            NAMESPACE_ITEMS.clear();
            // 按命名空间分组收集物品
            BuiltInRegistries.ITEM.stream()
                .forEach(item -> {
                    String namespace = BuiltInRegistries.ITEM.getKey(item).getNamespace();
                    NAMESPACE_ITEMS.computeIfAbsent(namespace, k -> new java.util.ArrayList<>()).add(item);
                });

            // 更新黑名单
            BLACKLISTED_ITEMS.clear();
            BLACKLISTED_ITEMS.addAll(MagicCloverConfig.BLACKLIST_ITEMS.get());

            // 处理通配符黑名单
            NAMESPACE_ITEMS.forEach((namespace, items) -> {
                items.removeIf(item -> {
                    String itemId = BuiltInRegistries.ITEM.getKey(item).toString();
                    // 检查是否匹配任何黑名单规则（包括通配符）
                    return BLACKLISTED_ITEMS.stream().anyMatch(pattern ->
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

        // 获取命名空间设置
        IngredientNamespceContent content = cloverStack.get(ComponentRegister.INGREDIENT_NAMESPACE);
        if (content == null || content.namespace() == null) {
            return null;
        }

        // 获取对应命名空间的物品列表
        List<Item> itemList = NAMESPACE_ITEMS.get(content.namespace());
        if (itemList == null || itemList.isEmpty()) {
            return null;
        }

        // 随机选择物品，如果在黑名单中就重新选择
        Item randomItem;
        //do {
        randomItem = itemList.get(level.getRandom().nextInt(itemList.size()));
        //} while (BLACKLISTED_ITEMS.contains(randomItem.toString())
        //        && itemList.size() > 1); // 防止无限循环

        // @debug, 这里处理的并不正确。具体见备忘吧

        return new ItemStack(randomItem);
    }
}
