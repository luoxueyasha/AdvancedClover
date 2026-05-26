package com.roseyasa.advanced_clover.item;

import com.roseyasa.advanced_clover.Main;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class CloverItem extends BlockItem {
    public CloverItem(Block block, Properties properties) {
        super(block, properties);
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(Component.translatable("tooltip." + Main.MODID + ".four_leaf_clover").withStyle(ChatFormatting.GRAY));
    }

}
