package com.roseyasa.advanced_clover.item;

import com.roseyasa.advanced_clover.event.MagicCloverEvent;
import com.roseyasa.advanced_clover.item.content.IngredientNamespceContent;
import com.roseyasa.advanced_clover.registry.ComponentRegister;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import com.roseyasa.advanced_clover.registry.ItemRegister;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.util.IConsumer;

import javax.annotation.Nullable;
import java.util.List;

public class MagicCloverItem extends Item {

    private int cooldown = 10;

    public MagicCloverItem() {
        super(new Properties().component(
                ComponentRegister.INGREDIENT_NAMESPACE,
                IngredientNamespceContent.DEFAULT
        ));
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        return super.getDefaultInstance();
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getCooldown() {
        return cooldown;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        
        // @debug，添加调试信息，显示存储的命名空间
        IngredientNamespceContent ingredient_namespace = itemStack.get(ComponentRegister.INGREDIENT_NAMESPACE);

//        pPlayer.getCooldowns().addCooldown(this, this.cooldown);
//        if (CloverRandomMethod(itemStack, pLevel, pPlayer)) {
//            pPlayer.swing(pUsedHand, true);
//            if (!pLevel.isClientSide) {
//                pPlayer.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
//                if (ingredient_namespace != null) {
//                    pPlayer.sendSystemMessage(Component.literal("当前物品的命名空间: " + ingredient_namespace)); // @debug
//                }
//
//            }
//            return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
//        }

        // @debug, 用来调试isbf的1.21
        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.EMPTY);

        return InteractionResultHolder.pass(itemStack);
    }

    public static boolean CloverRandomMethod(ItemStack itemStack, Level level, @Nullable Player player){
        if (player == null || level.isClientSide || itemStack == null) return false;

        MagicCloverEvent event = new MagicCloverEvent(player, level, itemStack);
        NeoForge.EVENT_BUS.post(event);

        if (event.isCanceled()) {
            return event.isSuccessful();
        }

        itemStack.consume(1, player);

        return true;
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        IngredientNamespceContent ingredientNamespceContent = stack.get(ComponentRegister.INGREDIENT_NAMESPACE);
        if(ingredientNamespceContent != null) {
            ingredientNamespceContent.addToTooltip(context, tooltipComponents::add, tooltipFlag);
        }
    }


}
