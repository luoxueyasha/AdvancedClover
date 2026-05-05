package com.roseyasa.advanced_clover;

import com.mojang.logging.LogUtils;
//import com.roseyasa.advanced_clover.datagen.MagicCloverItemModelProvider;
import com.roseyasa.advanced_clover.datagen.MagicCloverRecipeProvider;
import com.roseyasa.advanced_clover.datagen.MagicCloverSoundProvider;
import com.roseyasa.advanced_clover.event.EventHandler;
import com.roseyasa.advanced_clover.registry.*;
import com.roseyasa.advanced_clover.utils.CreativeTabBuilder;
import com.roseyasa.advanced_clover.utils.MagicCloverConfig;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Main.MODID)
public class Main {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "advanced_clover";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Main(IEventBus modEventBus, ModContainer modContainer) {
        // Register all DeferredRegisters to the mod event bus
        BlockRegister.BLOCKS.register(modEventBus);
        ItemRegister.ITEMS.register(modEventBus);
        CreativeTabBuilder.CREATIVE_MODE_TABS.register(modEventBus);
        RecipeRegister.RECIPE_SERIALIZERS.register(modEventBus);
        ComponentRegister.DATA_COMPONENT_TYPES.register(modEventBus);
        SoundRegister.SOUND_EVENTS.register(modEventBus);

        // Register dispenser behaviors handler
        modEventBus.register(DispenserRegister.class);

        // Register data generation listener
        modEventBus.addListener(Main::onGatherDataClient);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, MagicCloverConfig.SPEC);
    }

    public static void onGatherDataClient(GatherDataEvent.Client event) {
        // var gen = event.getGenerator();
        event.createProvider(MagicCloverSoundProvider::new);
        event.createProvider(MagicCloverRecipeProvider.Runner::new);

    }

}