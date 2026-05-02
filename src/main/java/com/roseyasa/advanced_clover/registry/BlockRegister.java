package com.roseyasa.advanced_clover.registry;

import com.roseyasa.advanced_clover.Main;
import com.roseyasa.advanced_clover.block.CloverBlock;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegister {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Main.MODID);

    public static final DeferredHolder<Block, Block> BLOCK_THREE_LEAF_CLOVER;

    public static final DeferredHolder<Block, Block> BLOCK_FOUR_LEAF_CLOVER;



    static{
        BLOCK_THREE_LEAF_CLOVER = BLOCKS.register("three_leaf_clover", ()->new CloverBlock(
                MobEffects.UNLUCK, 9.0F, BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollision()
                .instabreak()
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY)));
        BLOCK_FOUR_LEAF_CLOVER = BLOCKS.register("four_leaf_clover", ()->new CloverBlock(
                MobEffects.LUCK, 12.0F, BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollision()
                .instabreak()
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY)));
    }

}
