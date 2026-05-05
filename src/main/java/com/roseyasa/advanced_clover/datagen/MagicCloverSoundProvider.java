package com.roseyasa.advanced_clover.datagen;

import com.roseyasa.advanced_clover.registry.SoundRegister;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import static com.roseyasa.advanced_clover.Main.MODID;
import static com.roseyasa.advanced_clover.registry.SoundRegister.SOUND_CLOVER_FAIL_ID;
import static net.neoforged.neoforge.common.data.SoundDefinition.definition;

public class MagicCloverSoundProvider extends SoundDefinitionsProvider {
    public MagicCloverSoundProvider(PackOutput output) {
        super(output, MODID);
    }

    @Override
    public void registerSounds() {
        this.add(SOUND_CLOVER_FAIL_ID, SoundDefinition.definition()
            .with(
                sound( MODID+":"+SOUND_CLOVER_FAIL_ID, SoundDefinition.SoundType.SOUND)
                    .volume(0.8f)
                    .pitch(2f)
                    .attenuationDistance(4)
                    .stream(true)
                    .preload(true),
                sound(MODID+":"+SOUND_CLOVER_FAIL_ID)
            )
            .subtitle("sound."+MODID+"."+SOUND_CLOVER_FAIL_ID)
            .replace(true)
        );
    }
}