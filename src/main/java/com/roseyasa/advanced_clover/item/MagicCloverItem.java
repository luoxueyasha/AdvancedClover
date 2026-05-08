package com.roseyasa.advanced_clover.item;

import com.roseyasa.advanced_clover.event.MagicCloverEvent;
import com.roseyasa.advanced_clover.item.content.IngredientNamespceContent;
import com.roseyasa.advanced_clover.registry.ComponentRegister;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipDisplay;
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
import java.util.function.Consumer;

import static com.roseyasa.advanced_clover.registry.SoundRegister.SOUND_CLOVER_FAIL;

public class MagicCloverItem extends Item {

    private int cooldown = 10;

    public MagicCloverItem(Properties properties) {
        super(properties.component(
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
    }

    @Override
    public boolean isFoil(ItemStack itemStack){
        return true;
    }


}
