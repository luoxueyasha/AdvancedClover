package com.roseyasa.advanced_clover.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.roseyasa.advanced_clover.Main.MODID;

public class SoundRegister {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
        DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MODID);
    public static final String SOUND_CLOVER_FAIL_ID = "clover_fail";


    public static final DeferredHolder<SoundEvent,SoundEvent> SOUND_CLOVER_FAIL = SOUND_EVENTS.register(
        SOUND_CLOVER_FAIL_ID,  SoundEvent::createVariableRangeEvent
    );
}
