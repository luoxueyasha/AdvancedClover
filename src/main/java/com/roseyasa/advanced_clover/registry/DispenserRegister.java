package com.roseyasa.advanced_clover.registry;

import com.roseyasa.advanced_clover.event.MagicCloverEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;

import static com.roseyasa.advanced_clover.utils.MagicCloverHandler.*;

public class DispenserRegister {

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(ItemRegister.ITEM_MAGIC_CLOVER.get(), BEHAVIOR_MAGIC_CLOVER);
        });
    }

    public static final DispenseItemBehavior BEHAVIOR_MAGIC_CLOVER = new OptionalDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultBehavior = new DefaultDispenseItemBehavior();

        @Override
        protected ItemStack execute(BlockSource source, @NotNull ItemStack cloverItemStack) {
            this.setSuccess(false);
            ItemStack randomStack = null;
            Level level = source.level();
            
            if (!level.isClientSide()) {
                randomStack = generateRandomItem(level, cloverItemStack);
            }

            if(randomStack != null) {
                this.setSuccess(true);
                cloverItemStack.shrink(1);
                defaultBehavior.dispense(source, randomStack);
            }
            return cloverItemStack;
        }
    };


}