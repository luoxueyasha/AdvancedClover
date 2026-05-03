package com.roseyasa.advanced_clover.registry;

import com.roseyasa.advanced_clover.Main;
import com.roseyasa.advanced_clover.block.CloverBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBedBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.roseyasa.advanced_clover.utils.Utils.createBlockKey;

public class BlockRegister {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Main.MODID);

    public static final DeferredHolder<Block, Block> BLOCK_THREE_LEAF_CLOVER;
    static final String BLOCK_THREE_LEAF_CLOVER_ID =  "three_leaf_clover";
    public static final DeferredHolder<Block, Block> BLOCK_FOUR_LEAF_CLOVER;
    static final String BLOCK_FOUR_LEAF_CLOVER_ID =  "four_leaf_clover";

    static{
        BLOCK_THREE_LEAF_CLOVER = BLOCKS.register(BLOCK_THREE_LEAF_CLOVER_ID, ()->new CloverBlock(
                MobEffects.UNLUCK, 9.0F, BlockBehaviour.Properties.of()
                .setId(createBlockKey(BLOCK_THREE_LEAF_CLOVER_ID))
                .mapColor(MapColor.PLANT)
                .noCollision()
                .instabreak()
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY)));
        BLOCK_FOUR_LEAF_CLOVER = BLOCKS.register(BLOCK_FOUR_LEAF_CLOVER_ID, ()->new CloverBlock(
                MobEffects.LUCK, 12.0F, BlockBehaviour.Properties.of()
                .setId(createBlockKey(BLOCK_FOUR_LEAF_CLOVER_ID))
                .mapColor(MapColor.PLANT)
                .noCollision()
                .instabreak()
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY)));


    }

}
