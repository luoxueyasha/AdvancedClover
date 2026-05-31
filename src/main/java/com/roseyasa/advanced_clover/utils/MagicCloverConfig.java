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
                "Supports regex.")
            .translation("config.advanced_clover.blacklist_items")
            .defineListAllowEmpty("blacklist_items",
                List.of(
                    "minecraft:air",
                    "minecraft:bedrock",
                    "minecraft:barrier",
                    "minecraft:light",
                    "minecraft:.*command_block.*",
                    "minecraft:jigsaw",
                    "minecraft:structure_block",
                    "minecraft:structure_void",
                    "minecraft:debug_stick",
                    "advanced_clover:..*_leaf_clover",
                    ".*debug.*",
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
                "Supports regex.")
            .translation("config.advanced_clover.whitelist_items")
            .defineListAllowEmpty("whitelist_items",
                List.of(
                    "minecraft:(?!.*spawn_egg).*(copper|iron|gold|diamond|redstone|lapis|emerald|netherite).*",
                    "minecraft:(?!.*spawn_egg).*(dirt|wool|glass|grass|seed|bundle|boat|wood|birch|oak|jungle|acacia|mangrove|cherry|cobble|bamboo).*",
                    "minecraft:(?!.*spawn_egg).*(diorite|deepslate|granite|tuff|sand|obsidian|prismari|nether|end|purpur).*",
                    "minecraft:minecart",
                    "minecraft:(?!.*spawn_egg).*(apple|cake|fish|berries|cooked|bread|potato|carrot|dandelion).*",
                    "minecraft:.*(undying_tot|armor|_dye)",
                    "minecraft:heart_of_the_sea",

                    ".*_(banner_pattern|pottery_sherd|smithing_template)",

                    "bakeries:.*(powder|flour|toast|yolk|bread|dough|coffee|latte|raw_|egg|tomato|taro|olive|crosian|apple|roll|butter|).*",

                    "bsf3lite:.*(snow|glove|skates|boots|core|vodka).*",

                    "modulargolems:(?!.*spawn_egg).*golem.*",
                    "golemdungeons:.*(eye_of|samurai|sword|forge|core|upgrade).*",

                    "advanced_clover:.*_leaf_clover",

                    "harvestheritage:.*_stand_block",
                    "harvestheritage:grass_shear",
                    "harvestheritage:magnifying_glass",

                    "mekanism:.*(dust_|nugget_|block_|raw_|ingot_|hazmat_|circuit).*",
                    "mekanism:.*(_scrap|_installer)",
                    "mekanism:(pellet|enriched|alloy).*",
                    "meka.*:.*(_helmet|_chestplate|_leggings|_boots|_shield|_sword|_paxel|_axe|_hoe|_shovel)",
                    "meka.*:.*reactor.*",
                    "mekmm:.*factory.*",

                    "super_lead:super_lead",

                    "eclipticseasons:pinwheel.*",
                    "eclipticseasons:.*(chimes|wand)",

                    "shadowsandpetals:.*(chair|sakura|maple|ginkgo|autumn|aluminum).*",

                    "programmable_magic:.*alloy_wand",
                    "programmable_magic:.*plugin.*",
                    "programmable_magic:spell_display.*",

                    "letter_signal_phone:.*phone.*",
                    "letter_signal_phone:.*telegram.*",
                    "letter_signal_phone:stamp_pack",

                    "nestle:nestle_lead",

                    "shakenstir:shaker",
                    "shakenstir:(ice|lemon|tequ).*",
                    "shakenstir:.*tea.*",
                    "shakenstir:(vodka|rum|whisky|gin|brandy)",
                    "shakenstir:.*glassware",

                    "terraria_boulders:.*boulder.*",

                    "beecrasy:(?!.*spawn_egg).*(comb|bee_nest).*",
                    "beecrasy:(skep|apite|honey_drop|beeswax|pheromone)",

                    "displaydoll:.*",

                    "ashihara:(?!.*spawn_egg).*(daifuku|rice|dango|pot|potato|otsuchi|mochi|tofu|soy_bean|cotton|cucumber|tomato|sapling|sushi|reed|candle).*",
                    "ashihara:(?!.*spawn_egg).*(cherry|maple|red|cypress|lantern|white|water|lamp|stick|tatami).*",
                    "ashihara:(?!.*spawn_egg).*(sakura|bamboo|powder|flour|bucket|dirt|soil|tamago|shatter|salt).*",

                    "superpipeslide:.*(_anchor|upgrade|projector)",
                    "minecartrevolution:*(_chest|anvil|_plate|campfire|cactus|beacon|portal|obsidian|amethyst|piston|_rail)",
                    "netmusic:music_cd",
                    "powertool:(marting_car|clap).*",
                    "powertool:.*(boat|minecart|)",


                    "kaleidoscope:.*(lights|painting|board|grape|bucket|wine|brandy|champag|bottle|bride|dry_white).*",

                    "wenyan_progr.*:.*(_ink|paper)",
                    "anvilcraft.*:.*(ingot|nugget|shell|blade|cluster|shard|fiber|_ore|component|charger|resin|sponge|topaz|ruby|sappire|exp_gem|amber|processor|board|cocoa|dough|flour|cream|raw_|_matter|_core|_bucket).*",
                    "anvilcraft.*:.*(magnet|tank|_anvil|neoforge|grindstone|_table|_plate|generator|_block|heavy_iron|concrete|crystal|casing|hatch|tardis|sulfur|dry_ice|void|).*",

                    "cartridge:(cartridge|surgery_table|backpack)",
                    "colorful_crystals:.*(polish|raw|ore).*",
                    "essentialism:analyzers_lens",
                    "ether_craft:.*(cheese|blade|needle|pipe|block)",
                    "orlu:.*",
                    "touhou_little_.*:.*(garage_kit|_gohei|power_point|backpack|bookshelf|computer|beacon|basket|maid_bed|).*"


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


