package com.roseyasa.advanced_clover.event;

import com.roseyasa.advanced_clover.utils.MagicCloverHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static com.roseyasa.advanced_clover.Main.MODID;
import static com.roseyasa.advanced_clover.registry.SoundRegister.SOUND_CLOVER_FAIL;

@EventBusSubscriber(value = Dist.CLIENT,modid = MODID)

public class ClientEventHandler {
    @SubscribeEvent
    public static void onClientLoad(FMLCommonSetupEvent event) {
        MagicCloverHandler.updateItemsList();
    }

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
            MagicCloverEvent.SoundEffectPayload.TYPE,
            MagicCloverEvent.SoundEffectPayload.STREAM_CODEC,
            (payload, context) -> {}
        );
    }

    @SubscribeEvent
    public static void registerClientPayloadHandlers(RegisterClientPayloadHandlersEvent event) {
        event.register( MagicCloverEvent.SoundEffectPayload.TYPE, (payload, context) -> { context.enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;
            Level level = Minecraft.getInstance().level;
            if (level != null && player != null) {
                if ("clover_fail".equals(payload.id())) {
                    double random = 0.8 + Math.pow(Math.random(), 3);
                    level.playPlayerSound(SOUND_CLOVER_FAIL.get(), SoundSource.PLAYERS, 0.6F, (float) random);
                }
            }
        });});
    }
}