package com.roseyasa.advanced_clover;

import com.mojang.logging.LogUtils;
//import com.roseyasa.advanced_clover.datagen.MagicCloverItemModelProvider;
import com.roseyasa.advanced_clover.datagen.MagicCloverRecipeProvider;
import com.roseyasa.advanced_clover.datagen.MagicCloverSoundProvider;
import com.roseyasa.advanced_clover.registry.*;
import com.roseyasa.advanced_clover.utils.CreativeTabBuilder;
import com.roseyasa.advanced_clover.utils.MagicCloverConfig;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import org.slf4j.Logger;

@Mod(Main.MODID)
public class Main {
    public static final String MODID = "advanced_clover";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Main(IEventBus modEventBus, ModContainer modContainer) {
        BlockRegister.BLOCKS.register(modEventBus);
        ItemRegister.ITEMS.register(modEventBus);
        CreativeTabBuilder.CREATIVE_MODE_TABS.register(modEventBus);
        RecipeRegister.RECIPE_SERIALIZERS.register(modEventBus);
        ComponentRegister.DATA_COMPONENT_TYPES.register(modEventBus);
        SoundRegister.SOUND_EVENTS.register(modEventBus);

        modEventBus.register(DispenserRegister.class);

        modEventBus.addListener(Main::onGatherDataClient);

        modContainer.registerConfig(ModConfig.Type.COMMON, MagicCloverConfig.SPEC);
    }

    public static void onGatherDataClient(GatherDataEvent.Client event) {
        event.createProvider(MagicCloverSoundProvider::new);
        event.createProvider(MagicCloverRecipeProvider.Runner::new);

    }

}