package com.roseyasa.advanced_clover.datagen;

import com.roseyasa.advanced_clover.registry.BlockRegister;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.include.com.google.common.collect.Iterables;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.IntStream;

import static com.roseyasa.advanced_clover.registry.BlockRegister.BLOCKS;

public class MagicCloverCustomBlockLoot extends BlockLootSubProvider {
    protected MagicCloverCustomBlockLoot(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
    }

    protected LootTable.Builder createCloverDrops(Block cloverBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
            .add(applyExplosionDecay(
                cloverBlock,
                LootItem.lootTableItem(cloverBlock).apply(
                    IntStream.rangeClosed(1, 4).boxed().toList(),
                    amount -> SetItemCountFunction.setCount(ConstantValue.exactly((float) amount)).when(
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(cloverBlock).setProperties(
                                StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(BlockStateProperties.FLOWER_AMOUNT, amount)
                        )
                    )
                )
            ))
        );
    }

    @Override
    protected void generate() {
        this.add(BlockRegister.BLOCK_THREE_LEAF_CLOVER.get(), this::createCloverDrops);
        this.add(BlockRegister.BLOCK_FOUR_LEAF_CLOVER.get(), this::createCloverDrops);

    }

    @Nonnull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        // 模组自定义的方块战利品表必须覆盖此方法，以绕过对原版方块战利品表的检查（此处返回该模组的所有方块）
        return Iterables.transform(BLOCKS.getEntries(), DeferredHolder::get);
    }
}