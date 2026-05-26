package com.roseyasa.advanced_clover.item;

import com.roseyasa.advanced_clover.event.MagicCloverEvent;
import com.roseyasa.advanced_clover.item.content.CustomItemListContext;
import com.roseyasa.advanced_clover.item.content.IngredientNamespceContent;
import com.roseyasa.advanced_clover.item.content.EntityListContent;
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

import static com.roseyasa.advanced_clover.event.MagicCloverEvent.MAX_CHANCE;

public class MagicCloverItem extends Item {

    private int cooldown = 10;

    public MagicCloverItem(Properties properties) {
        super(properties.component(
                ComponentRegister.INGREDIENT_NAMESPACE,
                IngredientNamespceContent.DEFAULT
        ).component(
            ComponentRegister.ENTITY_TYPE,
            new EntityListContent(-1,null)
        ).component(
            ComponentRegister.CUSTOM_ITEM_LIST,
            CustomItemListContext.DEFAULT
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

        // 自定义list和自定义生物可以同时存在
        CustomItemListContext customItemListContext = stack.get(ComponentRegister.CUSTOM_ITEM_LIST);
        boolean isItemListExist = true;
        if(customItemListContext == null || customItemListContext.ilist().isEmpty()){
            isItemListExist = false;
        } else{
            customItemListContext.addToTooltip(context,builder,tooltipFlag,stack);
        }

        EntityListContent entityTypeContent = stack.get(ComponentRegister.ENTITY_TYPE);
        if (entityTypeContent == null) {
            entityTypeContent = new EntityListContent(-1,null);
        }
        entityTypeContent.addToTooltip(context,builder, tooltipFlag, stack);

        // 如果自定义生物chance满的话，就不显示丢东西的namespace了
        // 如果有自己的list，也不处理默认的黑白名单，转为自己的白名单
        int chance = entityTypeContent.chance();
        if(isItemListExist || chance == MAX_CHANCE) {
            return;
        }
        if (ingredientNamespceContent == null) {
            ingredientNamespceContent = new IngredientNamespceContent(null);
        }
        ingredientNamespceContent.addToTooltip(context, builder, tooltipFlag, stack);

    }

    @Override
    public boolean isFoil(ItemStack itemStack){
        return true;
    }


}
