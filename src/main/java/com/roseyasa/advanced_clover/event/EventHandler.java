package com.roseyasa.advanced_clover.event;

import com.roseyasa.advanced_clover.utils.MagicCloverHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber
// @debug, 记得用上
public class EventHandler {
    @SubscribeEvent
    public static void onDatapackReload(AddReloadListenerEvent event) {
        MagicCloverHandler.updateItemsList();
    }
}