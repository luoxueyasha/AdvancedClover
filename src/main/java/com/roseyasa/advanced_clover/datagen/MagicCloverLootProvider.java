package com.roseyasa.advanced_clover.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MagicCloverLootProvider extends LootTableProvider {
    public MagicCloverLootProvider(PackOutput gen, CompletableFuture<HolderLookup.Provider> lookup) {
        super(gen, Set.of(), List.of(new SubProviderEntry(MagicCloverCustomBlockLoot::new, LootContextParamSets.BLOCK)), lookup);
    }

    @Override
    protected void validate(WritableRegistry<LootTable> tables, ValidationContextSource validationContext, ProblemReporter.Collector problems) {
        // @debug，这是干什么的？
        // FIXME Need proper migration
        // map.forEach((key, value) -> LootTables.validate(context, key, value));
    }
}
