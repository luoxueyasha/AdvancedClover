package com.roseyasa.advanced_clover.item.content;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.roseyasa.advanced_clover.Main;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record EntityTypeContent(int chance, String entity_type) implements TooltipProvider {
    public static final EntityTypeContent DEFAULT = null;

    public static final Codec<EntityTypeContent> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(
            Codec.INT.fieldOf("chance").forGetter(EntityTypeContent::chance),
            Codec.STRING.fieldOf("entity_type").orElse("minecraft:creeper").forGetter(EntityTypeContent::entity_type)
        ).apply(builder, EntityTypeContent::new);
    });

    public static final StreamCodec<ByteBuf, EntityTypeContent> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,  EntityTypeContent::chance,
        ByteBufCodecs.STRING_UTF8,  EntityTypeContent::entity_type,
        EntityTypeContent::new
    );

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter var4) {
        if (this.entity_type == null || this.entity_type.equals("minecraft:creeper") || this.chance < 0) {
            return;
        }

        String key = "tooltip." +Main.MODID +".entity_type.custom";
        consumer.accept(Component.translatable(key, this.entity_type).withStyle(ChatFormatting.GRAY));
    }

}