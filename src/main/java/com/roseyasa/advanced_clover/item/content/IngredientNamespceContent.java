package com.roseyasa.advanced_clover.item.content;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.roseyasa.advanced_clover.Main;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record IngredientNamespceContent(String namespace) implements TooltipProvider {
    public static final IngredientNamespceContent DEFAULT = new IngredientNamespceContent("minecraft");

    public static final Codec<IngredientNamespceContent> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(
            Codec.STRING.fieldOf("namespace").orElse("minecraft").forGetter(IngredientNamespceContent::namespace)
        ).apply(builder, IngredientNamespceContent::new);
    });

    public static final StreamCodec<ByteBuf, IngredientNamespceContent> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8,
        IngredientNamespceContent::namespace,
        IngredientNamespceContent::new
    );

    public IngredientNamespceContent withNamespace(String newNamespace) {
        return new IngredientNamespceContent(newNamespace);
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        if (this.namespace == null) {
            consumer.accept(Component.translatable("tooltip." + Main.MODID + ".random_source.none").withStyle(ChatFormatting.RED));
            return;
        }
        
        String key = this.namespace.equals("minecraft") ? 
            "tooltip." + Main.MODID + ".random_source.vanilla" :
            "tooltip." + Main.MODID + ".random_source.mod";
        consumer.accept(Component.translatable(key, this.namespace).withStyle(ChatFormatting.GRAY));
    }
}