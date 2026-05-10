package com.roseyasa.advanced_clover.item;

import com.roseyasa.advanced_clover.event.MagicCloverEvent;
import com.roseyasa.advanced_clover.item.content.IngredientNamespceContent;
import com.roseyasa.advanced_clover.item.content.EntityTypeContent;
import com.roseyasa.advanced_clover.registry.ComponentRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class MagicCloverItem extends Item {

    private int cooldown = 10;

    public MagicCloverItem(Properties properties) {
        super(properties.component(
                ComponentRegister.INGREDIENT_NAMESPACE,
                IngredientNamespceContent.DEFAULT
        ).component(
            ComponentRegister.ENTITY_TYPE,
            new EntityTypeContent("minecraft:creeper")
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
    public @NotNull InteractionResult use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        pPlayer.getCooldowns().addCooldown(itemStack, this.cooldown);
        pPlayer.swing(pUsedHand, true);


        if (CloverRandomMethod(itemStack, pLevel, pPlayer)) {
            pPlayer.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public static boolean CloverRandomMethod(ItemStack itemStack, Level level, @Nullable Player player){
        if (player == null || level.isClientSide() || itemStack == null) return false;

        MagicCloverEvent event = new MagicCloverEvent(player, level, itemStack);
        NeoForge.EVENT_BUS.post(event);

        if (event.isCanceled()) {
            return event.isSuccess();
        }

        itemStack.consume(1, player);
        return event.isSuccess();
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        IngredientNamespceContent ingredientNamespceContent = stack.get(ComponentRegister.INGREDIENT_NAMESPACE);
        if (ingredientNamespceContent == null) {
            ingredientNamespceContent = new IngredientNamespceContent(null);
        }
        ingredientNamespceContent.addToTooltip(context,builder, tooltipFlag, stack);

        EntityTypeContent entityTypeContent = stack.get(ComponentRegister.ENTITY_TYPE);
        if (entityTypeContent == null) {
            entityTypeContent = new EntityTypeContent(null);
        }
        entityTypeContent.addToTooltip(context,builder, tooltipFlag, stack);
    }

    @Override
    public boolean isFoil(ItemStack itemStack){
        return true;
    }


}
