package com.roseyasa.advanced_clover.event;

import com.roseyasa.advanced_clover.Main;
import com.roseyasa.advanced_clover.utils.MagicCloverHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.roseyasa.advanced_clover.Main.MODID;

@EventBusSubscriber(value = Dist.CLIENT,modid = MODID)

public class EventHandler {
    @SubscribeEvent
    public static void onClientLoad(FMLCommonSetupEvent event) {
        MagicCloverHandler.updateItemsList();
    }
}