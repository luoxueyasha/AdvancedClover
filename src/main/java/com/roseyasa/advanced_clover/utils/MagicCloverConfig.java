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
            .define("Working Mode", "whitelist",
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
                    "minecraft:*diamond*",
                    "minecraft:*dirt*",
                    "minecraft:*wool*",
                    "minecraft:*glass*",
                    "minecraft:*iron*",
                    "minecraft:*gold*",
                    "minecraft:*grass*",
                    "minecraft:*lapis*",
                    "minecraft:*copper*",
                    "minecraft:*redstone*",
                    "minecraft:*seed*",
                    "minecraft:*bundle*",
                    "minecraft:*boat*",
                    "minecraft:minecart",
                    "minecraft:*birch*",
                    "minecraft:*oak*",
                    "minecraft:*jungle*",
                    "minecraft:*acacia*",
                    "minecraft:*mangrove*",
                    "minecraft:*cherry*",
                    "minecraft:*cobble*",
                    "minecraft:*bamboo*",
                    "minecraft:*apple*",
                    "minecraft:*diorite*",
                    "minecraft:*deepslate*",
                    "minecraft:*granite*",
                    "minecraft:*tuff*",
                    "minecraft:*sand*",
                    "minecraft:*obsidian*",
                    "minecraft:*prismarin*",
                    "minecraft:*nether*",
                    "minecraft:*end*",
                    "minecraft:*purpur*",
                    "minecraft:*cake*",
                    "minecraft:*fish*",
                    "minecraft:*berries*",
                    "minecraft:*cooked*",
                    "minecraft:bread",
                    "minecraft:*undying",
                    "minecraft:*armor",
                    "minecraft:*potato*",
                    "minecraft:*carrot*",
                    "minecraft:heart_of_the_sea",
                    "minecraft:*_dye",
                    "minecraft:*dandelion*",

                    "*_banner_pattern",
                    "*_pottery_sherd",
                    "*_smithing_template",

                    "bakeries:*powder*",
                    "bakeries:*flour*",
                    "bakeries:*toast*",
                    "bakeries:*yolk*",
                    "bakeries:*bread*",
                    "bakeries:*dough*",

                    "bsf3lite:*snow*",
                    "bsf3lite:*glove*",

                    "modulargolems:*golem*",

                    "advanced_clover:*_leaf_clover",

                    "harvestheritage:*_stand_block",
                    "harvestheritage:grass_shear",
                    "harvestheritage:magnifying_glass",

                    "mekanism:*dust_*",
                    "mekanism:*_scrap",
                    "mekanism:pellet*",
                    "mekanism:enriched*",
                    "mekanism:*circuit*",
                    "mekanism:alloy*",
                    "mekanism:nugget_*",
                    "mekanism:raw_*",
                    "mekanism:ingot_*",
                    "mekanism:*_installer",
                    "mekanism:hazmat_*",

                    "meka*:*_helmet",
                    "meka*:*_chestplate",
                    "meka*:*_leggings",
                    "meka*:*_boots",
                    "meka*:*_shield",
                    "meka*:*_sword",
                    "meka*:*reactor*",
                    "mekmm:*factory*",

                    "super_lead:super_lead",

                    "eclipticseasons:pinwheel*",
                    "eclipticseasons:*chimes",
                    "eclipticseasons:*wand",

                    "shadowsandpetals:*chair*",
                    "shadowsandpetals:*sakura*",
                    "shadowsandpetals:*maple*",
                    "shadowsandpetals:*ginkgo*",
                    "shadowsandpetals:*autumn*",
                    "shadowsandpetals:*aluminum*",

                    "programmable_magic:*alloy_wand",
                    "programmable_magic:*plugin*",
                    "programmable_magic:spell_display*",

                    "letter_signal_phone:*phone*",
                    "letter_signal_phone:*telegram*",
                    "letter_signal_phone:stamp_pack",

                    "nestle:nestle_lead",

                    "shakenstir:shaker",
                    "shakenstir:ice*",
                    "shakenstir:lemon*",
                    "shakenstir:*tea*",
                    "shakenstir:vodka",
                    "shakenstir:rum",
                    "shakenstir:whisky",
                    "shakenstir:gin",
                    "shakenstir:brandy",
                    "shakenstir:tequ*",
                    "shakenstir:*glassware",

                    "terraria_boulders:*boulder*"

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


