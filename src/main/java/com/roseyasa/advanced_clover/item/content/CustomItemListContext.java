package com.roseyasa.advanced_clover.item.content;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.roseyasa.advanced_clover.Main;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.List;
import java.util.function.Consumer;

public record CustomItemListContext(List<String> ilist) implements TooltipProvider {
    public static final CustomItemListContext DEFAULT = null;

    public static final Codec<CustomItemListContext> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(
            Codec.STRING.listOf().fieldOf("item_list").forGetter(CustomItemListContext::ilist)
        ).apply(builder, CustomItemListContext::new);
    });

    public static final StreamCodec<ByteBuf, CustomItemListContext> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()),
        CustomItemListContext::ilist,
        CustomItemListContext::new
    );

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter var4) {
        if (this.ilist == null || this.ilist.isEmpty()) {
            return;
        }

        // @debug：目前我们不知道用户添加的item是否真实存在。等teacon2026结束后再更新速查、以及可视化编辑吧

        MutableComponent line = Component.translatable("tooltip." + Main.MODID + ".custom_item_list.1")
            .withStyle(ChatFormatting.GRAY);
        consumer.accept(line);
        line = Component.empty();

        int print_len = Math.min(3, ilist.size());
        for (int i = 0; i < print_len; i++) {
            String item_name = "\"" + ilist.get(i) + "\"";
            line.append(Component.literal(item_name).withStyle(ChatFormatting.YELLOW));

            if (i != print_len - 1) {
                line.append(Component.translatable("tooltip." + Main.MODID + ".custom_item_list.comma")
                    .withStyle(ChatFormatting.GRAY));

            }
        }

        if (ilist.size() > 3) {
            line.append(Component.translatable("tooltip." + Main.MODID + ".custom_item_list.2")
                .withStyle(ChatFormatting.GRAY));
        }
        consumer.accept(line);
        line = Component.empty();

        line.append(Component.translatable("tooltip." + Main.MODID + ".custom_item_list.3", ilist.size())
            .withStyle(ChatFormatting.GRAY));

        consumer.accept(line);

    }
}