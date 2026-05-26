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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.List;
import java.util.function.Consumer;

public record EntityListContent(int chance, List<String> entity_list) implements TooltipProvider {
    public static final EntityListContent DEFAULT = null;

    public static final Codec<EntityListContent> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(
            Codec.INT.fieldOf("chance").forGetter(EntityListContent::chance),
            Codec.STRING.listOf().fieldOf("entity_list").orElse(List.of("minecraft:creeper")).forGetter(EntityListContent::entity_list)
        ).apply(builder, EntityListContent::new);
    });

    public static final StreamCodec<ByteBuf, EntityListContent> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,  EntityListContent::chance,
        ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()),  EntityListContent::entity_list,
        EntityListContent::new
    );

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter var4) {
        if (this.entity_list == null
                || (this.entity_list.getFirst().equals("minecraft:creeper") && this.entity_list.size() == 1)
                || this.chance < 0
            ) {
            return;
        }

        MutableComponent line = Component.translatable("tooltip." + Main.MODID + ".custom_entity_list.1")
            .withStyle(ChatFormatting.GRAY);
        consumer.accept(line);
        line = Component.empty();

        int print_len = Math.min(3, entity_list.size());
        for (int i = 0; i < print_len; i++) {
            String item_name = "\"" + entity_list.get(i) + "\"";
            line.append(Component.literal(item_name).withStyle(ChatFormatting.YELLOW));

            if (i != print_len - 1) {
                line.append(Component.translatable("tooltip." + Main.MODID + ".custom_entity_list.comma")
                    .withStyle(ChatFormatting.GRAY));

            }
        }

        if (entity_list.size() > 3) {
            line.append(Component.translatable("tooltip." + Main.MODID + ".custom_entity_list.2")
                .withStyle(ChatFormatting.GRAY));
        }
        consumer.accept(line);
        line = Component.empty();

        line.append(Component.translatable("tooltip." + Main.MODID + ".custom_entity_list.3", entity_list.size())
            .withStyle(ChatFormatting.GRAY));

        consumer.accept(line);

    }

}