package com.roseyasa.advanced_clover.utils;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

import static com.roseyasa.advanced_clover.event.MagicCloverEvent.MAX_CHANCE;

public class MagicCloverConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<String> WORKING_MODE;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> BLACKLIST_ITEMS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> WHITELIST_ITEMS;
    public static final ModConfigSpec.ConfigValue<Integer> MOB_SPAWN_CHANCE;

    static {
        BUILDER.comment("Common Configs for Advanced Clover");

        BUILDER.push("mode_settings");

        WORKING_MODE = BUILDER
            .comment("Working mode of the magic clover. Avaliable values are",
                "'whitelist', 'blacklist'.",
                "Default: 'blacklist'")
            .translation("config.advanced_clover.working_mode")
            .define("Working Mode", "blacklist",
                value -> "whitelist".equals(value) || "blacklist".equals(value));

        BLACKLIST_ITEMS = BUILDER
            .comment("Items in Magic Clover blacklist. These items will not be spawned if mod is working on blacklist mode.",
                "Supports wildcard '*'. For example:",
                "- 'minecraft:diamond' matches exactly 'minecraft:diamond'",
                "- '*:diamond*' matches any item from any mod that contains 'diamond'",
                "- 'minecraft:*' matches all items from minecraft namespace")
            .translation("config.advanced_clover.blacklist_items")
            .defineListAllowEmpty("Blacklist Items",
                List.of(
                    "minecraft:air",
                    "minecraft:bedrock",
                    "minecraft:barrier",
                    "minecraft:light",
                    "minecraft:*command_block*",
                    "minecraft:jigsaw",
                    "minecraft:structure_block",
                    "minecraft:structure_void",
                    "minecraft:debug_stick",
                    "advanced_clover:.*_leaf_clover",
                    "*debug*",
                    "minecraft:map",
                    "minecraft:potion",
                    "minecraft:tipped_arrow",
                    "minecraft:suspicious_stew",
                    "minecraft:enchanted_book",
                    "minecraft:goat_horn",
                    "minecraft:vault"
                ),
                () -> "",
                s -> s instanceof String);

        WHITELIST_ITEMS = BUILDER
            .comment("Items in Magic Clover whitelist. These items will only be spawned if mod is working on whitelist mode.",
                "Supports wildcard '*'.")
            .translation("config.advanced_clover.whitelist_items")
            .defineListAllowEmpty("Whitelist Items",
                List.of(
                    "minecraft:diamond",
                    "aqua*:neptune*",
                    "minecraft:dirt*",
                    "minecraft:*wood*"
                ),
                () -> "",
                s -> s instanceof String);

        MOB_SPAWN_CHANCE = BUILDER
            .comment("Chance to spawn mob when using clover. Range 0 ~ 1000, default value 10(1%).")
            .translation("config.advanced_clover.mob_spawn_chance")
            .defineInRange("mobSpawnChance", 10, 0, MAX_CHANCE);

        BUILDER.pop();


    }

    public static final ModConfigSpec SPEC = BUILDER.build();
}