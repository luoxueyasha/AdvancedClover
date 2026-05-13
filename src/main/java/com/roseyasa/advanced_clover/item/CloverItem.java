package com.roseyasa.advanced_clover.item;

import com.roseyasa.advanced_clover.Main;
import com.roseyasa.advanced_clover.item.content.EntityTypeContent;
import com.roseyasa.advanced_clover.item.content.IngredientNamespceContent;
import com.roseyasa.advanced_clover.registry.ComponentRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

import static com.roseyasa.advanced_clover.event.MagicCloverEvent.MAX_CHANCE;
import static net.neoforged.neoforge.common.NeoForgeMod.MOD_ID;

public class CloverItem extends BlockItem {
    public CloverItem(Block block, Properties properties) {
        super(block, properties);
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(Component.translatable("tooltip." + Main.MODID + ".four_leaf_clover").withStyle(ChatFormatting.GRAY));
    }

}
