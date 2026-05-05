package com.roseyasa.advanced_clover.event;

import com.roseyasa.advanced_clover.utils.MagicCloverHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.roseyasa.advanced_clover.Main.MODID;
import static com.roseyasa.advanced_clover.registry.SoundRegister.SOUND_CLOVER_FAIL;
import static com.roseyasa.advanced_clover.registry.SoundRegister.SOUND_CLOVER_FAIL_ID;
import static com.roseyasa.advanced_clover.utils.MagicCloverHandler.*;

public class MagicCloverEvent extends Event implements ICancellableEvent {
    private final @Nullable Player player;
    private final Level level;
    private boolean isSuccess = false;
    private final ItemStack cloverStack;

    public MagicCloverEvent(@Nullable Player player, Level level, ItemStack cloverStack){
        super();
        this.player = player;
        this.level = level;
        this.cloverStack = cloverStack;
        execute();
    }

    public void execute() {
        if (player != null) {
            // @debug, todo：创造模式下判断背包是否满。若满则抛射一个物品。
            // 但是不知道那个版本开始，创造模式additem会忽略上限，我也不知道该怎么办了
            // 暂时先只生成掉落物吧
            // todo: 苦力怕

            //if (!player.hasInfiniteMaterials() && !player.addItem(randomStack)) {

            if(!level.isClientSide()) {
                ItemStack itemStack = MagicCloverHandler.generateRandomItem(level, cloverStack);
                if(itemStack == null){
                    this.isSuccess = false;
                    itemStack = RandomFallback();
                    ((ServerPlayer) player).connection.send(
                        new ClientboundCustomPayloadPacket(new SoundEffectPayload(SOUND_CLOVER_FAIL_ID))
                    );
                } else {
                    this.isSuccess = true;
                }
                player.drop(itemStack, false);
            }
        } else {
            this.isSuccess = false;
        }
    }

    public record SoundEffectPayload(String id) implements CustomPacketPayload {
        public static final Type<SoundEffectPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(MODID, "sound_effect"));

        public static final StreamCodec<ByteBuf, SoundEffectPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SoundEffectPayload::id,
            SoundEffectPayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public Level getLevel() {
        return level;
    }

    public boolean isSuccess() {
        return isSuccess;
    }



    public @Nullable Player getPlayer() {
        return player;
    }

    public void setSuccessful(boolean success) {
        this.isSuccess = success;
        this.setCanceled(true);
    }

    @Override
    public void setCanceled(boolean canceled) {
        ICancellableEvent.super.setCanceled(canceled);
    }

    public boolean isSuccessful() {
        return this.isSuccess;
    }
}
